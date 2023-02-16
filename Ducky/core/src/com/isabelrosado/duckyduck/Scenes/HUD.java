package com.isabelrosado.duckyduck.Scenes;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.ScreenI;

public class HUD extends ScreenI {
    private int score;
    private Label lblScore;
    private Label lblWarning;

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
        lblScore = stg.getRoot().findActor("lblScore");
        getLblScore().setText(getScore());
        lblWarning = stg.getRoot().findActor("lblWarning");
        getLblWarning().setText(game.getBundle().get("playscreen.popup"));
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

