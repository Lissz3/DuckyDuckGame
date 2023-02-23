package com.isabelrosado.fruitytoad.Screens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.isabelrosado.fruitytoad.FruityToad;

/**
 * Screen used to change game options.
 * @see ScreenI
 */
public class OptionsMenuScreen extends ScreenI {
    /**
     * Music being played.
     */
    private Music music;

    /**
     * Button that activates or desactivates the music.
     */
    private ImageTextButton btnMusic;
    /**
     * Button that activates or desactivates sound effects.
     */
    private ImageTextButton btnSound;

    /**
     * Warning window to delete scores
     */
    private Window delScorePopUp;

    /**
     * <p>Creates a screen with the given values.</p>
     * <p>It also gives values needed to the main constructor {@link ScreenI} as the path for the JSON file skin,
     * if there is need of an InputProcessor or if the screen has a Back Button.
     * Defines the screen with {@link #defineScreen()}.</p>
     * @param game main screen
     */
    public OptionsMenuScreen(final FruityToad game, Music music){
        super(game, "Skins/moptions.json", true, true);
        this.music = music;
        defineScreen();
    }

    /**
     * Used to define the unique actors of the screen to change their values and/or give them listeners.
     * <p>Adds new actors to the Stage if needed.</p>
     */
    @Override
    protected void defineScreen() {
        btnMusic = stg.getRoot().findActor("btnMusic");
        btnMusic.setChecked(FruityToad.musicOffChecked);
        btnSound = stg.getRoot().findActor("btnSound");
        btnSound.setChecked(FruityToad.soundOffChecked);
        final ImageTextButton btnReset = stg.getRoot().findActor("btnDelRecords");
        SelectBox sbxLanguage = stg.getRoot().findActor("sbxLanguage");
        sbxLanguage.clearItems();
        Array<String> languages = new Array<>();
        languages.add(game.getBundle().get("optmenu.spanish"));
        languages.add(game.getBundle().get("optmenu.english"));
        sbxLanguage.setItems(languages);

        btnMusic.getLabel().setText(game.getBundle().get("optmenu.music")+" "+game.getBundle().get("optmenu.on")+" ");
        btnSound.getLabel().setText(game.getBundle().get("optmenu.sound")+" "+game.getBundle().get("optmenu.on")+" ");
        btnReset.getLabel().setText(game.getBundle().get("optmenu.deleter")+" ");

        btnMusic.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                btnMusic.setChecked(btnMusic.isChecked());
                if (btnMusic.isChecked()){
                    FruityToad.muteMSC();
                    music.pause();
                } else {
                    FruityToad.normalizeMSC();
                    music.play();
                }
                FruityToad.musicOffChecked = btnMusic.isChecked();
                btnMusic.getLabel().setText(game.getBundle().get("optmenu.music")+" "+(btnMusic.isChecked() ? game.getBundle().get("optmenu.off") : game.getBundle().get("optmenu.on"))+" ");
            };
        });

        btnSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                btnSound.setChecked(btnSound.isChecked());
                if (btnSound.isChecked()){
                    FruityToad.muteFX();
                } else {
                    FruityToad.normalizeFX();
                }
                FruityToad.soundOffChecked = btnSound.isChecked();
                btnSound.getLabel().setText(game.getBundle().get("optmenu.sound")+" "+(btnSound.isChecked() ? game.getBundle().get("optmenu.off") : game.getBundle().get("optmenu.on"))+" ");
            };
        });

        btnReset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                delScorePopUp.setVisible(true);
            };
        });

        //delete score popup
        createDelScorePopUp();

        stg.addActor(delScorePopUp);
    }

    /**
     * Creates a warning popup window for delete the scores with his own listeners.
     */
    private void createDelScorePopUp() {
        Drawable trash = skin.getDrawable("Icon_Trash");
        Image crownImg = new Image(trash);
        Label lblWarning = new Label(game.getBundle().get("optmenu.warning"), skin);
        lblWarning.setAlignment(Align.center);
        TextButton btnYes = new TextButton(game.getBundle().get("optmenu.yes"), skin);
        btnYes.getLabel().setAlignment(Align.center);
        TextButton btnNo = new TextButton(game.getBundle().get("optmenu.no"), skin);
        btnNo.getLabel().setAlignment(Align.center);
        delScorePopUp = new Window(game.getBundle().get("optmenu.del"), skin);
        delScorePopUp.getTitleLabel().setAlignment(Align.center);
        delScorePopUp.add(crownImg).padBottom(5).colspan(2);
        delScorePopUp.row();
        delScorePopUp.add(lblWarning).colspan(2);
        delScorePopUp.row();
        delScorePopUp.add(btnYes);
        delScorePopUp.add(btnNo);
        delScorePopUp.setSize(stg.getHeight() / 1.5f, stg.getHeight() / 2.5f);
        delScorePopUp.setPosition(FruityToad.V_WIDTH / 2 - delScorePopUp.getWidth() / 2, FruityToad.V_HEIGHT / 2 - delScorePopUp.getHeight()  / 2);
        delScorePopUp.setMovable(false);
        delScorePopUp.setVisible(false);

        btnYes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                game.getPreferences().clear();
                game.getPreferences().flush();
                delScorePopUp.setVisible(false);
            }
        });

        btnNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                delScorePopUp.setVisible(false);
            }
        });
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(float delta) {
        super.render(delta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        super.dispose();
    }
}
