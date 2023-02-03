package com.isabelrosado.duckyduck.Sprites;

import static com.badlogic.gdx.utils.JsonValue.ValueType.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;

public class Brick extends InteractiveTileObject {

    public Brick(DuckyDuck game, PlayScreen screen, Rectangle bounds) {
        super(game, screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(DuckyDuck.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        game.getAssetManager().get("01.mp3", Sound.class).play();
//        Gdx.app.log("Brick", "Collision");
    }

    @Override
    public void onFeetHit() {
        Gdx.app.log("Brick", "Collision");
    }
}
