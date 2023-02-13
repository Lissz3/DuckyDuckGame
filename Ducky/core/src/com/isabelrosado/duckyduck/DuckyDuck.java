package com.isabelrosado.duckyduck;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.isabelrosado.duckyduck.Screens.MainMenuScreen;
import com.isabelrosado.duckyduck.Screens.MainMenuScreenTry;
import com.isabelrosado.duckyduck.Screens.PlayScreen;

public class DuckyDuck extends Game {
	public SpriteBatch sprite;
	Texture texture;
	public static final int V_WIDTH = 600;
	public static final int V_HEIGHT = 420;
	public static final float PIXEL_PER_METER = 100;

	public static final short NOTHING_BIT = 0;
	public static final short DEFAULT_BIT = 1;
	public static final short DUCK_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short BRICKHIT_BIT = 8;
	public static final short FRUIT_BIT = 16;
	public static final short DESTROYED_BIT = 32;
	public static final short GROUND_BIT = 64;
	public static final short ENEMY_BIT = 128;
	public static final short ENEMY_HEAD_BIT = 256;

	public static final short DUCK_HEAD_BIT = 512;

	public static final short CHECKPOINT_BIT = 1024;

	private AssetManager assetManager;
	
	@Override
	public void create () {
		sprite = new SpriteBatch();
		assetManager = new AssetManager();
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

		setScreen(new MainMenuScreenTry(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		sprite.dispose();
		assetManager.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(AssetManager assetManager) {
		this.assetManager = assetManager;
	}
}
