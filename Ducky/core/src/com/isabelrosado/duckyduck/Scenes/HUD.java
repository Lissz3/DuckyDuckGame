package com.isabelrosado.duckyduck.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.OptionsMenuScreen;
import com.isabelrosado.duckyduck.Screens.ScreenI;

public class HUD extends ScreenI {
    private int score;
    private Label lblScore;
    private Label lblWarning;
    private boolean showLvls;
    private Table tableLvls;

    public HUD(final DuckyDuck game) {
        super(game, "Skins/hud.json", false);
        setScore(0);
        defineScreen();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Label getLblScore() {
        return lblScore;
    }

    public Label getLblWarning() {
        return lblWarning;
    }

    @Override
    protected void defineScreen() {
        showLvls = false;
        lblScore = stg.getRoot().findActor("lblScore");
        getLblScore().setText(getScore());
        lblWarning = stg.getRoot().findActor("lblWarning");
        getLblWarning().setText(game.getBundle().get("playscreen.popup"));
        Button btnShowLvls = stg.getRoot().findActor("btnLvls");
        tableLvls = stg.getRoot().findActor("tableLvls");

        btnShowLvls.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showLvls = !showLvls;
                tableLvls.setVisible(showLvls);
            };
        });
    }

    @Override
    public void show() {

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

    public void update(float dt) {
        lblScore.setText(getScore());
    }

}

