package com.isabelrosado.duckyduck.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Tools.Animator;

public class BrickHit extends InteractiveTileObject {

    public BrickHit(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(DuckyDuck.BRICKHIT_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("BrickHit", "Collision");
        setCategoryFilter(DuckyDuck.DESTROYED_BIT);
//        Animator a = new Animator(3, new Texture("/Free/Items/Boxes/Box1/Idle/Hit (28x24).png"), 0, 0, 28, 24);
//        (body.getPosition().x * DuckyDuck.PIXEL_PER_METER / 16), (int)(body.getPosition().y * DuckyDuck.PIXEL_PER_METER / 16)
        getCell().setTile(null);
    }
}