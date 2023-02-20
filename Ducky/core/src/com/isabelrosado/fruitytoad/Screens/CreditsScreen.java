package com.isabelrosado.fruitytoad.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.isabelrosado.fruitytoad.FruityToad;


public class CreditsScreen extends ScreenI {

    public CreditsScreen(final FruityToad game) {
        super(game, "Skins/mcredits.json", true, true);
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