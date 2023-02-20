package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.isabelrosado.duckyduck.DuckyDuck;


public class OptionsMenuScreen extends ScreenI {
    MainMenuScreen mmscreen;
    private Music music;
    private ImageTextButton btnMusic;
    private ImageTextButton btnSound;
    private Window delScorePopUp;
    public OptionsMenuScreen(final DuckyDuck game, MainMenuScreen mmscreen){
        super(game, "Skins/moptions.json", true, true);
        this.mmscreen = mmscreen;
        this.music = mmscreen.getMusic();
        defineScreen();
    }

    @Override
    protected void defineScreen() {
        btnMusic = stg.getRoot().findActor("btnMusic");
        btnMusic.setChecked(DuckyDuck.musicOffChecked);
        btnSound = stg.getRoot().findActor("btnSound");
        btnSound.setChecked(DuckyDuck.soundOffChecked);
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
                soundBtn.play(DuckyDuck.FX_VOLUME);
                btnMusic.setChecked(btnMusic.isChecked());
                if (btnMusic.isChecked()){
                    DuckyDuck.muteMSC();
                    music.pause();
                } else {
                    DuckyDuck.normalizeMSC();
                    music.play();
                }
                DuckyDuck.musicOffChecked = btnMusic.isChecked();
                btnMusic.getLabel().setText(game.getBundle().get("optmenu.music")+" "+(btnMusic.isChecked() ? game.getBundle().get("optmenu.off") : game.getBundle().get("optmenu.on"))+" ");
            };
        });

        btnSound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(DuckyDuck.FX_VOLUME);
                btnSound.setChecked(btnSound.isChecked());
                if (btnSound.isChecked()){
                    DuckyDuck.muteFX();
                } else {
                    DuckyDuck.normalizeFX();
                }
                DuckyDuck.soundOffChecked = btnSound.isChecked();
                btnSound.getLabel().setText(game.getBundle().get("optmenu.sound")+" "+(btnSound.isChecked() ? game.getBundle().get("optmenu.off") : game.getBundle().get("optmenu.on"))+" ");
            };
        });

        btnReset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(DuckyDuck.FX_VOLUME);
                delScorePopUp.setVisible(true);
            };
        });

        //delete score popup
        createDelScorePopUp();

        stg.addActor(delScorePopUp);
    }

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
        delScorePopUp.setPosition(DuckyDuck.V_WIDTH / 2 - delScorePopUp.getWidth() / 2, DuckyDuck.V_HEIGHT / 2 - delScorePopUp.getHeight()  / 2);
        delScorePopUp.setMovable(false);
        delScorePopUp.setVisible(false);

        btnYes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(DuckyDuck.FX_VOLUME);
                game.getPreferences().clear();
                game.getPreferences().flush();
                delScorePopUp.setVisible(false);
            }
        });

        btnNo.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(DuckyDuck.FX_VOLUME);
                delScorePopUp.setVisible(false);
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
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
        super.dispose();
    }
}
