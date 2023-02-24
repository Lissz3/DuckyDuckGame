package com.isabelrosado.fruitytoad.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
import com.isabelrosado.fruitytoad.Sprites.Items.Item;

/**
 * Unique interactive object called Brick.
 * @see InteractiveTileObject
 */
public class Brick extends InteractiveTileObject {

    /**
     * Initialize the values to the values given to the super constructor.
     * <p>Sets the fixture data to the object data itself and gives the unique bit for this object.</p>
     * @param game main screen
     * @param screen actual screen
     * @param object this item at the tiled map
     */
    public Brick(FruityToad game, PlayScreen screen, MapObject object) {
        super(game, screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FruityToad.BRICK_BIT);
    }

    /**
     * Called when the main character head fixture collides with this object fixture
     * <p>Plays a sound.</p>
     */
    @Override
    public void onHeadHit() {
        game.getAssetManager().get("Audio/Sounds/NotBreakable.mp3", Sound.class).play(FruityToad.FX_VOLUME);
    }

}
