package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;

public abstract class ScreenI implements Screen {
    protected DuckyDuck game;
    protected Stage stg;
    protected Viewport vp;
    protected Skin skin;
    protected final Sound soundBtn;
    private InputProcessor hudInput;

    public ScreenI(final DuckyDuck game, String skinPath, boolean inputProcessor, boolean backButton){
        this.game = game;
        this.vp = new FitViewport(DuckyDuck.V_WIDTH, DuckyDuck.V_HEIGHT, new OrthographicCamera());
        this.stg = new Stage(vp, game.sprite);

        skin = new Skin(Gdx.files.internal("Skins/main.json"));

        soundBtn = game.getAssetManager().get("Audio/Sounds/Button.mp3", Sound.class);

        SceneComposerStageBuilder builder = new SceneComposerStageBuilder();
        builder.build(stg, skin, Gdx.files.internal(skinPath));

        if (backButton){
            Button btnBack = stg.getRoot().findActor("btnBack");
            btnBack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundBtn.play(DuckyDuck.FX_VOLUME);
                    game.setScreen(new MainMenuScreen(game));
                    dispose();
                };
            });
        }

        hudInput = stg;

        if (inputProcessor){
            Gdx.input.setInputProcessor(stg);
        }
    }

    protected abstract void defineScreen();

    public abstract void show();

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stg.act();
        stg.draw();
    }


    public abstract void resize(int width, int height);

    public abstract void pause();

    public abstract void resume();

    public abstract void hide();

    public void dispose() {
        stg.dispose();
        skin.dispose();
    }

    public InputProcessor getHudInput() {
        return hudInput;
    }
}
