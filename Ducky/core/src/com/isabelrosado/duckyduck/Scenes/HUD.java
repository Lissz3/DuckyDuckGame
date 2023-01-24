package com.isabelrosado.duckyduck.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isabelrosado.duckyduck.DuckyDuck;

import org.w3c.dom.Text;

public class HUD implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private int worldTimer;
    private float timeCount;
    private int score;

    ImageButton heart1Img;
    ImageButton heart2Img;
    ImageButton heart3Img;

    public HUD(SpriteBatch sb) {
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        viewport = new FitViewport(DuckyDuck.V_WIDTH, DuckyDuck.V_HEIGHT);
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.align(Align.topLeft);
        table.setFillParent(true);
        Texture heartTexture = new Texture("Heart.png");
        TextureRegion heartTR = new TextureRegion(heartTexture);
        TextureRegionDrawable heartD = new TextureRegionDrawable(heartTR);
        heart1Img = new ImageButton(heartD);
        heart2Img = new ImageButton(heartD);
        heart3Img = new ImageButton(heartD);

        table.add(heart1Img).pad(3);
        table.add(heart2Img).pad(3);
        table.add(heart3Img).pad(3);
        table.row();

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
