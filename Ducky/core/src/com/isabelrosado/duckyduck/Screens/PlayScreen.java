package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Scenes.HUD;
import com.isabelrosado.duckyduck.Sprites.Duck;
import com.isabelrosado.duckyduck.Sprites.Enemies.FatBird;
import com.isabelrosado.duckyduck.Sprites.Items.Fruit;
import com.isabelrosado.duckyduck.Sprites.Items.Item;
import com.isabelrosado.duckyduck.Sprites.Items.ItemDef;
import com.isabelrosado.duckyduck.Tools.WorldContactListener;
import com.isabelrosado.duckyduck.Tools.WorldCreator;

import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PlayScreen implements Screen {
    private DuckyDuck newGame;
    private TextureAtlas atlas;

    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private HUD hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private WorldCreator creator;

    private Music music;

    public Duck duck;

    private Array<Item> items;
    private LinkedBlockingQueue<ItemDef> itemsToSpawn;

    public PlayScreen(DuckyDuck game) {
        atlas = new TextureAtlas("Sprites/Frog.atlas");

        newGame = game;

        //follows the Duck through world
        gameCam = new OrthographicCamera();

        //maintains the virtual aspect ratio despite screen size
        //or maybe ScreenViewport?
        gamePort = new FitViewport(DuckyDuck.V_WIDTH / DuckyDuck.PIXEL_PER_METER, DuckyDuck.V_HEIGHT / DuckyDuck.PIXEL_PER_METER, gameCam);

        //create the HUD
        hud = new HUD(game.sprite);

        //load map and setup the renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("Maps/prueba2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / DuckyDuck.PIXEL_PER_METER);

        //setup the gamecam at the start of the game
        gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        creator = new WorldCreator(newGame, this);

        //create Duck in our world
        duck = new Duck(newGame,this);

        //create Items in our world
        items = new Array<Item>();
        itemsToSpawn = new LinkedBlockingQueue<ItemDef>();

        world.setContactListener(new WorldContactListener());

        music = game.getAssetManager().get("Audio/Music/Gameplay.mp3", Music.class);
        music.setLooping(true);
        music.play();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        //update logic
        update(dt);

        //clear game screen
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //map render
        renderer.render();

        //box2D renderer
        b2dr.render(world, gameCam.combined);

        //draw the sprites
        newGame.sprite.setProjectionMatrix(gameCam.combined);
        newGame.sprite.begin();
        duck.draw(newGame.sprite);

        for (FatBird fb : creator.getFatBirds()) {
            fb.draw(newGame.sprite);
        }

        for (Item item : items) {
            item.draw(newGame.sprite);
        }
        newGame.sprite.end();

        //set batch with the HUD
        newGame.sprite.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()){
            newGame.setScreen(new GameOverScreen(newGame));
            music.stop();
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

    public void handleInput(float dt) {
        if (duck.currentState != Duck.State.HITTED) {
            //holding down keys moves camera through world
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && duck.canDoubleJump()) {
                duck.dBody.applyLinearImpulse(new Vector2(0, 4f), duck.dBody.getWorldCenter(), true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && duck.dBody.getLinearVelocity().x <= 2) {
                duck.dBody.applyLinearImpulse(new Vector2(0.1f, 0), duck.dBody.getWorldCenter(), true);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && duck.dBody.getLinearVelocity().x >= -2) {
                duck.dBody.applyLinearImpulse(new Vector2(-0.1f, 0), duck.dBody.getWorldCenter(), true);
            }
        }
    }

    public void update(float dt) {
        //handle user input
        handleInput(dt);

        //update hud
        hud.update(dt);

        //handle item creation
        handleSpawningItems();

        world.step(1 / 60f, 6, 2);

        //update the duck sprite
        duck.update(dt);

        //update the fatBird sprite
        for (FatBird fb : creator.getFatBirds()) {
            fb.update(dt);
        }

        //update the item sprite
        for (Item fruit : items) {
            fruit.update(dt);
        }

        //the game cam follows the main char
        if (duck.currentState != Duck.State.HITTED) {
            gameCam.position.x = duck.dBody.getPosition().x;
        }

        //update gamecam with correct coordinates
        gameCam.update();

        //tell render to draw only what camera sees in the world
        renderer.setView(gameCam);

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    public void spawnItem(ItemDef itemDef) {
        itemsToSpawn.add(itemDef);
    }

    public void handleSpawningItems() {
        if (!itemsToSpawn.isEmpty()) {
            ItemDef itemDef = itemsToSpawn.poll();
            if (itemDef.type.equals(Fruit.class)) {
                items.add(new Fruit(newGame, this, itemDef.position.x, itemDef.position.y));
            }
        }
    }

    public boolean gameOver (){
        if (duck.isHit() && duck.getStateTimer() > 3){
            return true;
        }
        return false;
    }

    public HUD getHud() {
        return hud;
    }

    public void setHud(HUD hud) {
        this.hud = hud;
    }

}
