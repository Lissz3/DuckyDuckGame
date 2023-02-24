package com.isabelrosado.fruitytoad.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;

/**
 * Unique interactive object called Ground.
 * @see InteractiveTileObject
 */
public class Ground extends InteractiveTileObject {

    /**
     * Initialize the values to the values given to the super constructor.
     * <p>Sets the fixture data to the object data itself and gives the unique bit for this object.</p>
     * @param game main screen
     * @param screen actual screen
     * @param object this item at the tiled map
     */
    public Ground(FruityToad game, PlayScreen screen, MapObject object) {
        super(game, screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FruityToad.GROUND_BIT);
    }


    @Override
    public void onHeadHit() {

    }


}
