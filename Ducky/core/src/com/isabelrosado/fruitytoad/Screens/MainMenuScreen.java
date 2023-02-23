package com.isabelrosado.fruitytoad.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.isabelrosado.fruitytoad.FruityToad;

/**
 * Game's main menu screen.
 * @see ScreenI
 */
public class MainMenuScreen extends ScreenI {

    /**
     * Music to be played.
     */
    private Music music;

    /**
     * <p>Creates a screen with the given values.</p>
     * <p>It also gives values needed to the main constructor {@link ScreenI} as the path for the JSON file skin,
     * if there is need of an InputProcessor or if the screen has a Back Button.
     * Defines the screen with {@link #defineScreen()}.</p>
     * @param game main screen
     */
    public MainMenuScreen(final FruityToad game){
        super(game, "Skins/mmskin.json", true, false);
        defineScreen();
    }

    /**
     * Used to define the unique actors of the screen to change their values and/or give them listeners.
     * <p>Adds new actors to the Stage if needed.</p>
     */
    @Override
    protected void defineScreen() {
        music = game.getAssetManager().get("Audio/Music/MainTheme.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(FruityToad.MUSIC_VOLUME);
        music.play();

        ImageTextButton btnPlay = stg.getRoot().findActor("btnPlay");
        btnPlay.getLabel().setText(game.getBundle().get("mainmenu.play"));
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getAssetManager().get("Audio/Sounds/PlayButton.mp3", Sound.class).play(FruityToad.FX_VOLUME);
                game.setScreen(new PlayScreen(game, 1));
                dispose();
            };
        });

        ImageTextButton options = stg.getRoot().findActor("btnOptions");
        options.getLabel().setText(game.getBundle().get("mainmenu.options"));
        options.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                game.setScreen(new OptionsMenuScreen(game, music));
            };
        });

        ImageTextButton records = stg.getRoot().findActor("btnRecords");
        records.getLabel().setText(game.getBundle().get("mainmenu.records"));
        records.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                game.setScreen(new RecordsScreen(game));
            };
        });

        ImageTextButton credits = stg.getRoot().findActor("btnCredits");
        credits.getLabel().setText(game.getBundle().get("mainmenu.credits"));
        credits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                game.setScreen(new CreditsScreen(game));
            };
        });

        ImageTextButton help = stg.getRoot().findActor("btnHelp");
        help.getLabel().setText(game.getBundle().get("mainmenu.help"));
        help.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                game.setScreen(new HelpScreen(game));
            };
        });

        Button exit = stg.getRoot().findActor("btnExit");
        exit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                soundBtn.play(FruityToad.FX_VOLUME);
                Gdx.app.exit();
                dispose();
            };
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(float delta) {
        super.render(delta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        super.dispose();
        music.stop();
    }

}
