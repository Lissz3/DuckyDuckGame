package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.ray3k.stripe.scenecomposer.SceneComposerStageBuilder;

import java.util.Locale;


public class OptionsMenuScreen extends ScreenI {

    public OptionsMenuScreen(final DuckyDuck game){
        super(game, "Skins/moptions.json", true);
        defineScreen();
    }

    @Override
    protected void defineScreen() {

        final CheckBox sound = stg.getRoot().findActor("cbxSound");
        sound.getLabel().setText(" "+game.getBundle().get("optmenu.sound")+" "+(sound.isChecked() ? game.getBundle().get("optmenu.on") : game.getBundle().get("optmenu.off")));

        sound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnSound.play();
                sound.getLabel().setText(" "+game.getBundle().get("optmenu.sound")+" "+(sound.isChecked() ? game.getBundle().get("optmenu.on") : game.getBundle().get("optmenu.off")));
            };
        });

        final CheckBox music = stg.getRoot().findActor("cbxMusic");
        music.getLabel().setText(" "+game.getBundle().get("optmenu.music")+" "+(music.isChecked() ? game.getBundle().get("optmenu.on") : game.getBundle().get("optmenu.off")));
        music.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnSound.play();
                music.getLabel().setText(" "+game.getBundle().get("optmenu.music")+" "+(music.isChecked() ? game.getBundle().get("optmenu.on") : game.getBundle().get("optmenu.off")));
            };
        });

        ImageTextButton btnDel = stg.getRoot().findActor("btnDelRecords");
        btnDel.getLabel().setText(" "+game.getBundle().get("optmenu.deleter"));

        SelectBox sbxLanguage = stg.getRoot().findActor("sbxLanguage");
        sbxLanguage.clearItems();
        sbxLanguage.setItems(game.getBundle().get("optmenu.english"), game.getBundle().get("optmenu.spanish"));
        if (Locale.getDefault().getLanguage().equals("es")){
            sbxLanguage.setSelected(game.getBundle().get("optmenu.spanish"));
        } else {
            sbxLanguage.setSelected(game.getBundle().get("optmenu.english"));
        }
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
