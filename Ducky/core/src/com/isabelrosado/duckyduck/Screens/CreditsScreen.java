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


public class CreditsScreen extends ScreenI {

    public CreditsScreen(final DuckyDuck game) {
        super(game, "Skins/mcredits.json", true);
        defineScreen();
    }

    @Override
    protected void defineScreen() {
        Label lblTitle = stg.getRoot().findActor("lblTitle");
        lblTitle.setText(game.getBundle().get("credmenu.title"));

        Label lblTy = stg.getRoot().findActor("lblTy");
        lblTy.setText(game.getBundle().get("credmenu.text"));
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