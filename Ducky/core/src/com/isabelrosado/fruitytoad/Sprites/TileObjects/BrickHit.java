package com.isabelrosado.fruitytoad.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
import com.isabelrosado.fruitytoad.Sprites.Items.Fruit;
import com.isabelrosado.fruitytoad.Sprites.Items.ItemDef;

public class BrickHit extends InteractiveTileObject {

    public BrickHit(FruityToad game, PlayScreen screen, MapObject object) {
        super(game, screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FruityToad.BRICKHIT_BIT);
    }

    @Override
    public void onHeadHit() {
        setCategoryFilter(FruityToad.DESTROYED_BIT);
        getCell().setTile(null);
        screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / FruityToad.PIXEL_PER_METER), Fruit.class));
        game.getAssetManager().get("Audio/Sounds/BreakableBox.mp3", Sound.class).play(FruityToad.FX_VOLUME);
    }

}