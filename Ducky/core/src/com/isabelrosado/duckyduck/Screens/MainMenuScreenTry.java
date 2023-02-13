package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;
import java.util.*;

import javax.swing.text.View;

public class MainMenuScreenTry implements Screen {
    private DuckyDuck game;
    private Stage stg;
    private Viewport vp;
    private Skin skin;
    private Music music;

    public MainMenuScreenTry(DuckyDuck game){
        this.game = game;
        this.vp = new FitViewport(DuckyDuck.V_WIDTH, DuckyDuck.V_HEIGHT, new OrthographicCamera());
        this.stg = new Stage(vp, game.sprite);

        skin = new Skin(Gdx.files.internal("Skins/mskin.json"));

        music = game.getAssetManager().get("Audio/Music/MainTheme.mp3", Music.class);
        music.setLooping(true);
        music.play();

        SceneComposerStageBuilder builder = new SceneComposerStageBuilder();
        builder.build(stg, skin, Gdx.files.internal("Skins/mskin.json"));


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stg.act();
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
        skin.dispose();
    }
}
