package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.isabelrosado.duckyduck.DuckyDuck;

public class RecordsScreen extends ScreenI {

    public RecordsScreen(final DuckyDuck game) {
        super(game, "Skins/mrecords.json", true);
        defineScreen();
    }

    @Override
    protected void defineScreen() {
        Label lblTitle = stg.getRoot().findActor("lblTitle");
        lblTitle.setText(game.getBundle().get("recmenu.title"));

        Label lblTy = stg.getRoot().findActor("lblRecords");
        lblTy.setText(game.getBundle().get("recmenu.text"));
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
