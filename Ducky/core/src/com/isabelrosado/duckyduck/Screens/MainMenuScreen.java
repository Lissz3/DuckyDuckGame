package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.isabelrosado.duckyduck.DuckyDuck;
import jdk.jfr.internal.tool.Main;
import org.w3c.dom.Text;

public class MainMenuScreen implements Screen {

    private Viewport vp;
    private Stage stg;
    private DuckyDuck game;
    Music music;

    public MainMenuScreen(final DuckyDuck game){
        this.game = game;
        this.vp = new FitViewport(DuckyDuck.V_WIDTH, DuckyDuck.V_HEIGHT, new OrthographicCamera());
        this.stg = new Stage(vp, game.sprite);

        music = game.getAssetManager().get("Audio/Music/MainTheme.mp3", Music.class);
        music.setLooping(true);
        music.play();

        Label.LabelStyle lblFont = new Label.LabelStyle();
        BitmapFont font = new BitmapFont(Gdx.files.internal("Fonts/white.fnt"));
        lblFont.font = font;
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        int btnHeight = 48;
        int btnWidth = 160;
        Skin skin = new Skin();
        skin.addRegions(new TextureAtlas("Sprites/btns.atlas"));
        TextButton.TextButtonStyle btnStyle = new TextButton.TextButtonStyle();
        btnStyle.font = font;
        btnStyle.up = skin.getDrawable("Normal");
        btnStyle.down = skin.getDrawable("Clicked");

        TextButton btnPlay = new TextButton("PLAY", btnStyle);
        btnPlay.setSize(btnWidth,btnHeight);
        btnPlay.getLabel().setAlignment(Align.top);
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getAssetManager().get("Audio/Sounds/PlayButton.mp3", Sound.class).play();
                game.setScreen(new PlayScreen(game));
                music.stop();
                dispose();
            };
        });

        TextButton btnOptions = new TextButton("OPTIONS", btnStyle);
        btnOptions.setSize(btnWidth,btnHeight);
        btnOptions.getLabel().setAlignment(Align.top);

        TextButton btnRecords = new TextButton("RECORDS", btnStyle);
        btnRecords.setSize(btnWidth,btnHeight);
        btnRecords.getLabel().setAlignment(Align.top);

        TextButton btnCredits = new TextButton("CREDITS", btnStyle);
        btnCredits.setSize(btnWidth,btnHeight);
        btnCredits.getLabel().setAlignment(Align.top);
        btnCredits.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getAssetManager().get("Audio/Sounds/Button.mp3", Sound.class).play();
                game.setScreen(new CreditsScreen(game));
                music.stop();
                dispose();
            };
        });

        TextButton btnHelp = new TextButton("HELP", btnStyle);
        btnHelp.setSize(btnWidth,btnHeight);
        btnHelp.getLabel().setAlignment(Align.top);

        table.add(btnPlay).expand().prefSize(btnWidth, btnHeight);
        table.row();
        table.add(btnOptions).expand().prefSize(btnWidth, btnHeight);
        table.row();
        table.add(btnRecords).expand().prefSize(btnWidth, btnHeight);
        table.row();
        table.add(btnCredits).expand().prefSize(btnWidth, btnHeight);
        table.row();
        table.add(btnHelp).expand().prefSize(btnWidth, btnHeight);

        stg.addActor(table);
        Gdx.input.setInputProcessor(stg);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
