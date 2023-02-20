package com.isabelrosado.fruitytoad.Sprites.TileObjects;

import com.badlogic.gdx.maps.MapObject;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;

public class Ground extends InteractiveTileObject {
    public Ground(FruityToad game, PlayScreen screen, MapObject object) {
        super(game, screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FruityToad.GROUND_BIT);
    }

    @Override
    public void onHeadHit() {

    }


}
