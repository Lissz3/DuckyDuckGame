package com.isabelrosado.fruitytoad.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;

public class Brick extends InteractiveTileObject {

    public Brick(FruityToad game, PlayScreen screen, MapObject object) {
        super(game, screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FruityToad.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        game.getAssetManager().get("Audio/Sounds/NotBreakable.mp3", Sound.class).play(FruityToad.FX_VOLUME);
    }

}
