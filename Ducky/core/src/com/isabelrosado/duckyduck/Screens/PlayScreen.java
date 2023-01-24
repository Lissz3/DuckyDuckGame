package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Scenes.HUD;
import com.isabelrosado.duckyduck.Sprites.Duck;
import com.isabelrosado.duckyduck.Tools.WorldContactListener;
import com.isabelrosado.duckyduck.Tools.WorldCreator;

public class PlayScreen implements Screen {
    private DuckyDuck game;
    private TextureAtlas atlas;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private HUD hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private World world;
    private Box2DDebugRenderer b2dr;
    private WorldCreator worldCreator;

    private Duck duck;

    public PlayScreen(Game game){
        atlas = new TextureAtlas("Frog.atlas");

        this.game = (DuckyDuck) game;

        //follows the Duck through world
        gameCam = new OrthographicCamera();

        //maintains the virtual aspect ratio despite screen size
        //or maybe ScreenViewport?
        gamePort = new FitViewport(DuckyDuck.V_WIDTH / DuckyDuck.PIXEL_PER_METER, DuckyDuck.V_HEIGHT / DuckyDuck.PIXEL_PER_METER, gameCam);

        //create the HUD
        hud = new HUD(((DuckyDuck) game).sprite);

        //load map and setup the renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("prueba2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / DuckyDuck.PIXEL_PER_METER);

        //setup the gamecam at the start of the game
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);

        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();

        worldCreator = new WorldCreator(world, map);

        //create Duck in our world
        duck = new Duck(world, this);

        world.setContactListener(new WorldContactListener());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float dt) {
        //update logic
        update(dt);

        //clear game screen
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //map render
        renderer.render();

        //box2D renderer
        b2dr.render(world, gameCam.combined);

        game.sprite.setProjectionMatrix(gameCam.combined);
        game.sprite.begin();
        duck.draw(game.sprite);
        game.sprite.end();

        //set batch with the HUD
        game.sprite.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

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

    public void handleInput(float dt){
        //holding down keys moves camera through world
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            duck.dBody.applyLinearImpulse(new Vector2(0, 4f), duck.dBody.getWorldCenter(), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && duck.dBody.getLinearVelocity().x <= 2){
            duck.dBody.applyLinearImpulse(new Vector2(0.1f, 0), duck.dBody.getWorldCenter(), true);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && duck.dBody.getLinearVelocity().x >= -2){
            duck.dBody.applyLinearImpulse(new Vector2(-0.1f, 0), duck.dBody.getWorldCenter(), true);
        }
    }

    public void update(float dt){
        //handle user input
        handleInput(dt);

        world.step(1/60f, 6, 2);

        //update the duck sprite
        duck.update(dt);

        gameCam.position.x = duck.dBody.getPosition().x;

        //update gamecam with correct coordinates
        gameCam.update();

        //tell render to draw only what camera sees in the world
        renderer.setView(gameCam);

//        //update the duck sprite
        duck.update(dt);
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }
}
