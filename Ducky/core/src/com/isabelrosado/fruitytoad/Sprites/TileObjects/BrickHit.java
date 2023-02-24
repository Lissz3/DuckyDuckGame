package com.isabelrosado.fruitytoad.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
import com.isabelrosado.fruitytoad.Sprites.Items.Fruit;
import com.isabelrosado.fruitytoad.Sprites.Items.ItemDef;

public class BrickHit extends InteractiveTileObject {

    /**
     * Initialize the values to the values given to the super constructor.
     * <p>Sets the fixture data to the object data itself and gives the unique bit for this object.</p>
     * @param game main screen
     * @param screen actual screen
     * @param object this item at the tiled map
     */
    public BrickHit(FruityToad game, PlayScreen screen, MapObject object) {
        super(game, screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FruityToad.BRICKHIT_BIT);
    }

    /**
     * Called when the main character head fixture collides with this object fixture.
     * <p>Sets the filter to {@link FruityToad#DESTROYED_BIT} and the tile on the map as null (empty).</p>
     * <p>Plays a sound and spawns a {@link Fruit} item.</p>
     */
    @Override
    public void onHeadHit() {
        setCategoryFilter(FruityToad.DESTROYED_BIT);
        getCell().setTile(null);
        screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / FruityToad.PIXEL_PER_METER), Fruit.class));
        game.getAssetManager().get("Audio/Sounds/BreakableBox.mp3", Sound.class).play(FruityToad.FX_VOLUME);
    }

}