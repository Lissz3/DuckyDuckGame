package com.isabelrosado.fruitytoad;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;
import com.isabelrosado.fruitytoad.Screens.MainMenuScreen;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
import com.isabelrosado.fruitytoad.Sprites.TileObjects.InteractiveTileObject;

/**
 * <p>
 * A {@link Game} class implementing {@link ApplicationListener} that delegates to a {@link Screen}.
 * </p>
 * This is the Main screen for our game, allowing to easily have multiple screens.
 */
public class FruityToad extends Game {
    /**
     * <p>
     * Given a texture and coordinates draws a texture or regions for every rectangle.
     * </p>
     */
    public SpriteBatch sprite;

    /**
     * <p>
     * Bits used for collisions.
     * </p>
     * @see InteractiveTileObject
     */
    public static final int V_WIDTH = 600;
    public static final int V_HEIGHT = 420;
    public static final float PIXEL_PER_METER = 100;
    public static final short NOTHING_BIT = 0;
    public static final short DEFAULT_BIT = 1;
    public static final short FROG_BIT = 2;
    public static final short BRICK_BIT = 4;
    public static final short BRICKHIT_BIT = 8;
    public static final short FRUIT_BIT = 16;
    public static final short DESTROYED_BIT = 32;
    public static final short GROUND_BIT = 64;
    public static final short ENEMY_BIT = 128;
    public static final short ENEMY_HEAD_BIT = 256;
    public static final short FROG_HEAD_BIT = 512;
    public static final short CHECKPOINT_BIT = 1024;

    /**
     * <p>
     * Total levels of the game.
     * </p>
     */
    private final int totalLevels = 2;

    /**
     * <p>
     * Final score every game.
     * </p>
     */
    private int finalScore;

    /**
     * <p>
     * Loads and stores sounds and music for the game.
     * </p>
     */
    private AssetManager assetManager;

    /**
     * Provides Locale-specific resources loaded from property files.
     */
    private I18NBundle bundle;

    /**
     * Instance that holds scores.
     */
    private Preferences preferences;

    /**
     * Music volume.
     */
    public static float MUSIC_VOLUME = 1f;

    /**
     * Sound effects volume.
     */
    public static float FX_VOLUME = 1f;

    /**
     * Checks if the music is OFF(true) or ON(false).
     */
    public static boolean musicOffChecked;

    /**
     * Checks if the sound effects are OFF(true) or ON(false).
     */
    public static boolean soundOffChecked;

    /**
     * Called when the Game is first created.
     */
    @Override
    public void create() {
        sprite = new SpriteBatch();
        finalScore = 0;
        musicOffChecked = false;
        soundOffChecked = false;
        assetManager = new AssetManager();
        bundle = I18NBundle.createBundle(Gdx.files.internal("Locale/locale"));
        assetManager.load("Audio/Music/MainTheme.mp3", Music.class);
        assetManager.load("Audio/Music/Gameplay.mp3", Music.class);
        assetManager.load("Audio/Sounds/BreakableBox.mp3", Sound.class);
        assetManager.load("Audio/Sounds/Button.mp3", Sound.class);
        assetManager.load("Audio/Sounds/FatBirdGround.mp3", Sound.class);
        assetManager.load("Audio/Sounds/FatBirdKilled.mp3", Sound.class);
        assetManager.load("Audio/Sounds/NotBreakable.mp3", Sound.class);
        assetManager.load("Audio/Sounds/PlayButton.mp3", Sound.class);
        assetManager.load("Audio/Sounds/FruitCollected.mp3", Sound.class);
        assetManager.load("Audio/Sounds/GameOver.mp3", Sound.class);
        assetManager.finishLoading();
        assetManager.getLoadedAssets();

        preferences = Gdx.app.getPreferences("com.isabelrosado.fruitytoad");

        setScreen(new MainMenuScreen(this));
    }

    /**
     * Called when the Game should render itself.
     */
    @Override
    public void render() {
        super.render();
    }

    /**
     * Called when the Game is destroyed.
     */
    @Override
    public void dispose() {
        super.dispose();
        sprite.dispose();
        assetManager.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public I18NBundle getBundle() {
        return bundle;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getTotalLevels() {
        return totalLevels;
    }

    /**
     * Sets the sound effects volume to 0.
     */
    public static void muteFX(){
        FX_VOLUME = 0f;
    }

    /**
     * Sets the music volume to 0.
     */
    public static void muteMSC(){
        MUSIC_VOLUME = 0f;
    }

    /**
     * Sets the sound effects volume to 1.
     */
    public static void normalizeFX(){
        FX_VOLUME = 1f;
    }

    /**
     * Sets the music volume to 1.
     */
    public static void normalizeMSC(){
        MUSIC_VOLUME = 1f;
    }

}
