package com.isabelrosado.fruitytoad.Screens;

import com.badlogic.gdx.*;
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
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Sprites.Frog;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;
/**
 * <p>
 * Abstract class implementing {@link Screen}.
 * </p>
 * <p>
 * Generic class to create different Game screens.
 * </p>
 * @author Isabel Rosado
 */
public abstract class ScreenI implements Screen {
    /**
     * Main screen of the game.
     *
     * @see FruityToad
     */
    protected FruityToad game;

    /**
     * Scene2d graph that that handles the viewport and distributes input events.
     */
    protected Stage stg;

    /**
     * Determines how world coordinates are mapped to and from the screen. Also manages our {@link OrthographicCamera}.
     */
    protected Viewport vp;

    /**
     * Resource store for the widgets to use described in the JSON from path skinPath.
     */
    protected Skin skin;

    /**
     * Sound used when a button is pressed.
     */
    protected final Sound soundBtn;

    /**
     * Input processor to receive keyboard, mouse and touch events.
     */
    private InputProcessor hudInput;

    /**
     * Initialize the values to the values given to the constructor.
     * <p>Adds the listener to the Back button and gives the stage to the InputProcessor.</p>
     * @param game           main screen
     * @param skinPath       path to the JSON skin
     * @param inputProcessor true if it needs to have key or touch listeners, false otherwise.
     * @param backButton     true if the screen have a back button, false otherwise.
     *
     */
    public ScreenI(final FruityToad game, String skinPath, boolean inputProcessor, boolean backButton) {
        this.game = game;
        this.vp = new FitViewport(FruityToad.V_WIDTH, FruityToad.V_HEIGHT, new OrthographicCamera());
        this.stg = new Stage(vp, game.sprite);

        skin = new Skin(Gdx.files.internal("Skins/main.json"));

        soundBtn = game.getAssetManager().get("Audio/Sounds/Button.mp3", Sound.class);

        SceneComposerStageBuilder builder = new SceneComposerStageBuilder();
        builder.build(stg, skin, Gdx.files.internal(skinPath));

        if (backButton) {
            Button btnBack = stg.getRoot().findActor("btnBack");
            btnBack.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    soundBtn.play(FruityToad.FX_VOLUME);
                    game.setScreen(new MainMenuScreen(game));
                    dispose();
                }

                ;
            });
        }

        hudInput = stg;

        if (inputProcessor) {
            Gdx.input.setInputProcessor(stg);
        }
    }

    /**
     * Called to define the specific screen.
     */
    protected abstract void defineScreen();

    /**
     * Called when this screen becomes the current screen for the Game.
     */
    public abstract void show();

    /**
     * Called when the screen should render itslef.
     *
     * @param delta The time in seconds since the last render.
     */
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stg.act();
        stg.draw();
    }

    /**
     * Called when the Game is resized. This can happen at any point during a non-paused state but will never happen before a call to create().
     *
     * @param width
     * @param height
     */
    public void resize(int width, int height){
        vp.update(width, height);
    };

    /**
     * Called when the Game is paused, usually when it's not active or visible on-screen. Also used before the Game it is destroyed.
     */
    public abstract void pause();

    /**
     * Called when the Game is resumed from a paused state, usually when it regains focus.
     */
    public abstract void resume();

    /**
     * Called when the screen is no longer the current screen for the Game.
     */
    public abstract void hide();

    /**
     * Called when the screen should release all resources.
     */
    public void dispose() {
        stg.dispose();
        skin.dispose();
    }

    public InputProcessor getHudInput() {
        return hudInput;
    }
}
