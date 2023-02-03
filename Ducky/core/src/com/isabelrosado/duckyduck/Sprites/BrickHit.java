package com.isabelrosado.duckyduck.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;
import com.isabelrosado.duckyduck.Tools.Animator;

public class BrickHit extends InteractiveTileObject {
    private TextureAtlas atlas;
    Animator animator;
    Texture animationTexture;
    private Animation<TextureRegion> onHit;


    public BrickHit(DuckyDuck game, PlayScreen screen, Rectangle bounds) {
        super(game, screen, bounds);
        atlas = new TextureAtlas("Box.atlas");
        animationTexture = new Texture("Box.png");
        animator = new Animator(atlas.findRegion("Hit").getTexture(), 28, 24);
        onHit = animator.getAnimation(3, 112, 0);

        fixture.setUserData(this);
        setCategoryFilter(DuckyDuck.BRICKHIT_BIT);
    }

    @Override
    public void onHeadHit() {
//        Gdx.app.log("BrickHit", "Collision");
        setCategoryFilter(DuckyDuck.DESTROYED_BIT);
        getCell().setTile(null);
        game.getAssetManager().get("02.mp3", Sound.class).play();
    }

    @Override
    public void onFeetHit() {
        Gdx.app.log("BrickHit", "Collision");
    }
}