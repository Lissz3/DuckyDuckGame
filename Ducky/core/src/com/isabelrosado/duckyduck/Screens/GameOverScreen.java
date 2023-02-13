package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isabelrosado.duckyduck.DuckyDuck;

public class GameOverScreen implements Screen {
    private Viewport vp;
    private Stage stg;
    private Game game;

    public GameOverScreen(Game game){
        this.game = game;
        this.vp = new FitViewport(DuckyDuck.V_WIDTH, DuckyDuck.V_HEIGHT, new OrthographicCamera());
        this.stg = new Stage(vp, ((DuckyDuck) game).sprite);

        Label.LabelStyle font = new Label.LabelStyle();
        font.font = new BitmapFont(Gdx.files.internal("Fonts/a.fnt"));
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLbl = new Label("GAME OVER", font);
        Label playAgainLbl = new Label("Try again", font);

        table.add(gameOverLbl);
        table.row();
        table.add(playAgainLbl);

        stg.addActor(table);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            game.setScreen(new PlayScreen((DuckyDuck) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stg.draw();
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
        stg.dispose();
    }
}
