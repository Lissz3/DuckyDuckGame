package com.isabelrosado.duckyduck.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;

public class Brick extends InteractiveTileObject {

    public Brick(DuckyDuck game, PlayScreen screen, MapObject object) {
        super(game, screen, object);
        fixture.setUserData(this);
        setCategoryFilter(DuckyDuck.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        game.getAssetManager().get("Audio/Sounds/NotBreakable.mp3", Sound.class).play();
    }

}
