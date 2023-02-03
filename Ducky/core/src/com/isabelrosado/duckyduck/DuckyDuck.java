package com.isabelrosado.duckyduck;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.isabelrosado.duckyduck.Screens.PlayScreen;

public class DuckyDuck extends Game {
	public SpriteBatch sprite;
	Texture texture;
	public static final int V_WIDTH = 600;
	public static final int V_HEIGHT = 420;
	public static final float PIXEL_PER_METER = 100;

	public static final short DEFAULT_BIT = 1;
	public static final short DUCK_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short BRICKHIT_BIT = 8;
	public static final short FRUIT_BIT = 16;
	public static final short DESTROYED_BIT = 32;
	public static final short GROUND = 64;

	private AssetManager assetManager;
	
	@Override
	public void create () {
		sprite = new SpriteBatch();
		assetManager = new AssetManager();
		assetManager.load("MainTheme.mp3", Music.class);
		assetManager.load("01.mp3", Sound.class);
		assetManager.load("02.mp3", Sound.class);
		assetManager.finishLoading();
		assetManager.getLoadedAssets();

		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		sprite.dispose();
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
