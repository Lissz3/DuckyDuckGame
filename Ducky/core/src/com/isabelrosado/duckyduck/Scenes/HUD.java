package com.isabelrosado.duckyduck.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isabelrosado.duckyduck.DuckyDuck;

public class HUD implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private int score;

    private Label popUp;

    private Table table;
    private Label scoreLbl;

    private Texture fruitTexture;

    private TextureRegion fruit;

    public HUD(SpriteBatch sb) {
        score = 0;
        viewport = new FitViewport(DuckyDuck.V_WIDTH, DuckyDuck.V_HEIGHT);
        stage = new Stage(viewport, sb);

        fruitTexture = new Texture("Sprites/Fruits.png");
        fruit =  new TextureRegion(fruitTexture, 0, 64, 32, 32);
        Image img = new Image(fruit);
        img.setSize(64, 64);

        Label.LabelStyle font = new Label.LabelStyle();
        font.font = new BitmapFont(Gdx.files.internal("Fonts/black.fnt"));
        Label.LabelStyle littleF = new Label.LabelStyle();
        littleF.font = new BitmapFont(Gdx.files.internal("Fonts/12.fnt"));
        Skin skin = new Skin();
        skin.addRegions(new TextureAtlas("Sprites/btns.atlas"));

        table = new Table();
        table.top();
        table.setFillParent(true);

        scoreLbl = new Label("x "+score, font);
        popUp = new Label("You need more fruit!", littleF);


        table.add(img).size(64, 64).align(Align.right);
        table.add(scoreLbl);
        table.row();
        table.debug();
        stage.addActor(table);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void update(float dt){
        scoreLbl.setText("x "+score);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Label getPopUp() {
        return popUp;
    }

    public void setPopUp(Label popUp) {
        this.popUp = popUp;
    }

}

