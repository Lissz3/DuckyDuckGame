package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isabelrosado.duckyduck.DuckyDuck;


public class CreditsScreen implements Screen {
    private Viewport vp;
    private Stage stg;
    private DuckyDuck game;
    Music music;

    public CreditsScreen(final DuckyDuck game) {
        this.game = game;
        this.vp = new FitViewport(DuckyDuck.V_WIDTH, DuckyDuck.V_HEIGHT, new OrthographicCamera());
        this.stg = new Stage(vp, game.sprite);

        music = game.getAssetManager().get("Audio/Music/MainTheme.mp3", Music.class);
        music.setLooping(true);
        music.play();

        Table t = new Table();
        t.center();
        t.setFillParent(true);

        Label.LabelStyle font = new Label.LabelStyle();
        Label.LabelStyle italic = new Label.LabelStyle();
        BitmapFont fontBmap = new BitmapFont(Gdx.files.internal("Fonts/12w.fnt"));
        BitmapFont fontitalic = new BitmapFont(Gdx.files.internal("Fonts/12wItalic.fnt"));
        italic.font = fontitalic;
        font.font = fontBmap;
        Label.LabelStyle bigFont = new Label.LabelStyle();
        bigFont.font = new BitmapFont(Gdx.files.internal("Fonts/a.fnt"));


        Label lblCredits = new Label("CREDITS", bigFont);
        Label lblLibGDX = new Label("Made with LibGDX", font);
        Label lblAuthor = new Label("Author & Level Design by Isabel Rosado ", font);
        Label lblSounds = new Label("Sounds by ColorAlpha", font);
        Label lblMusic = new Label("Music by evilduckk", font);
        Label lblSprites = new Label("Sprites by Pixel Frog", font);
        Label lblTy = new Label("And a hearty thanks to the ones who made me keep going:", italic);
        Label lblFriends = new Label("Monica, Manu, Yu ki, Shunka, Feyi, Apse, King, Txavi.", font);


        t.add(lblCredits).colspan(2).align(Align.center).padBottom(50);
        t.row();
        t.add(lblLibGDX).colspan(2).align(Align.center);
        t.row();
        t.add(lblAuthor).colspan(2).align(Align.center);
        t.row();
        t.add(lblSprites).colspan(2).align(Align.center);
        t.row();
        t.add(lblMusic).colspan(2).align(Align.center);
        t.row();
        t.add(lblSounds).colspan(2).align(Align.center);
        t.row().padTop(100);
        t.add(lblTy).colspan(2).align(Align.center);
        t.row();
        t.add(lblFriends).colspan(2).align(Align.center);

        stg.addActor(t);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stg.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        stg.dispose();
    }
}