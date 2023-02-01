package com.isabelrosado.duckyduck.Scenes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isabelrosado.duckyduck.DuckyDuck;

import com.isabelrosado.duckyduck.Tools.Animator;

public class HUD implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private int worldTimer;
    private float timeCount;
    private int score;

    Image heart1;
    Image heart2;
    Image heart3;

    Image fruit;

    Table table;

    public HUD(SpriteBatch sb) {
        score = 0;
        viewport = new FitViewport(DuckyDuck.V_WIDTH, DuckyDuck.V_HEIGHT);
        stage = new Stage(viewport, sb);


        table = new Table();
        table.top();
//        table.setDebug(true);
        table.padTop(10);
        table.padLeft(10);
        table.align(Align.topLeft);
        table.setFillParent(true);
        Texture heartTexture = new Texture("100-percent.png");
        heart1 = new Image(heartTexture);
        heart2 = new Image(heartTexture);
        heart3 = new Image(heartTexture);
        fruit = new Image(new Texture("swbgif.gif"));
        table.add(heart1).width(Value.percentWidth(0.07f)).height(Value.percentHeight(0.07f)).pad(3);
        table.add(heart2).width(Value.percentWidth(0.07f)).height(Value.percentHeight(0.07f)).pad(3);
        table.add(heart3).width(Value.percentWidth(0.07f)).height(Value.percentHeight(0.07f)).pad(3);
        table.row();
        table.add(fruit);

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
