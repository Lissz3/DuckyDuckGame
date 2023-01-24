package com.isabelrosado.duckyduck;

import com.badlogic.gdx.Game;
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
	
	@Override
	public void create () {
		sprite = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		sprite.dispose();
	}
}
