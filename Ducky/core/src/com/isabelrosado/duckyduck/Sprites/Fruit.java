package com.isabelrosado.duckyduck.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.isabelrosado.duckyduck.DuckyDuck;

public class Fruit extends InteractiveTileObject{
    public Fruit(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(DuckyDuck.FRUIT_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Fruit", "Collision");
        setCategoryFilter(DuckyDuck.DESTROYED_BIT);
    }

    @Override
    public void onFeetHit() {

    }
}
