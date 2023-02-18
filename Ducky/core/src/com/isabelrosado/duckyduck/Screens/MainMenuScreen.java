package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.isabelrosado.duckyduck.DuckyDuck;

public class MainMenuScreen extends ScreenI {
    private Music music;

    public MainMenuScreen(final DuckyDuck game){
        super(game, "Skins/mmskin.json", false);

        defineScreen();

        Gdx.input.setInputProcessor(stg);
    }

    @Override
    protected void defineScreen() {
        music = game.getAssetManager().get("Audio/Music/MainTheme.mp3", Music.class);
        music.setLooping(true);
        music.play();

        ImageTextButton btnPlay = stg.getRoot().findActor("btnPlay");
        btnPlay.getLabel().setText(game.getBundle().get("mainmenu.play"));
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getAssetManager().get("Audio/Sounds/PlayButton.mp3", Sound.class).play();
                game.setScreen(new PlayScreen(game, 1));
                dispose();
            };
        });

        ImageTextButton options = stg.getRoot().findActor("btnOptions");
        options.getLabel().setText(game.getBundle().get("mainmenu.options"));
        options.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnSound.play();
                game.setScreen(new OptionsMenuScreen(game));
            };
        });

        ImageTextButton records = stg.getRoot().findActor("btnRecords");
        records.getLabel().setText(game.getBundle().get("mainmenu.records"));
        records.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnSound.play();
                game.setScreen(new RecordsScreen(game));
            };
        });

        ImageTextButton credits = stg.getRoot().findActor("btnCredits");
        credits.getLabel().setText(game.getBundle().get("mainmenu.credits"));
        credits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnSound.play();
                game.setScreen(new CreditsScreen(game));
            };
        });

        ImageTextButton help = stg.getRoot().findActor("btnHelp");
        help.getLabel().setText(game.getBundle().get("mainmenu.help"));

        Button exit = stg.getRoot().findActor("btnExit");
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                btnSound.play();
                Gdx.app.exit();
                dispose();
            };
        });

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
        music.stop();
    }
}
