package com.isabelrosado.duckyduck.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.isabelrosado.duckyduck.DuckyDuck;

public class Ground extends InteractiveTileObject {
    public Ground(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(DuckyDuck.GROUND);
    }

    @Override
    public void onHeadHit() {

    }

    @Override
    public void onFeetHit() {
        Gdx.app.log("Ground", "Collision");
    }

}
