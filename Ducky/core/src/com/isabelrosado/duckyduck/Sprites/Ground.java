package com.isabelrosado.duckyduck.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;

public class Ground extends InteractiveTileObject {
    public Ground(DuckyDuck game, PlayScreen screen, Rectangle bounds) {
        super(game, screen, bounds);
        fixture.setUserData(this);
        setCategoryFilter(DuckyDuck.GROUND_BIT);
    }

    @Override
    public void onHeadHit() {

    }

    @Override
    public void onFeetHit() {
        Gdx.app.log("Ground", "Collision");
    }

}
