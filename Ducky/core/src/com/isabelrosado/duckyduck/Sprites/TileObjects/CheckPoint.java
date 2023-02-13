package com.isabelrosado.duckyduck.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;

public class CheckPoint extends InteractiveTileObject {
    public CheckPoint(DuckyDuck game, PlayScreen screen, MapObject object) {
        super(game, screen, object);
        fixture.setUserData(this);
        setCategoryFilter(DuckyDuck.CHECKPOINT_BIT);
    }

    @Override
    public void onHeadHit() {
        use();
    }

    public void use() {
        if (screen.getHud().getScore() >= 5) {

        } else {
            screen.getHud().getTable().add(screen.getHud().getPopUp()).colspan(2);
        }
    }
}
