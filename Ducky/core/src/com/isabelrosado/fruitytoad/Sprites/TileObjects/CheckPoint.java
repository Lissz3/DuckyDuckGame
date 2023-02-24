package com.isabelrosado.fruitytoad.Sprites.TileObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Scenes.HUD;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
import com.isabelrosado.fruitytoad.Sprites.Items.Fruit;

/**
 * Unique interactive object called Checkpoint.
 * @see InteractiveTileObject
 */
public class CheckPoint extends InteractiveTileObject {
    /**
     * Initialize the values to the values given to the super constructor.
     * <p>Sets the fixture data to the object data itself and gives the unique bit for this object.</p>
     * @param game main screen
     * @param screen actual screen
     * @param object this item at the tiled map
     */
    public CheckPoint(FruityToad game, PlayScreen screen, MapObject object) {
        super(game, screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FruityToad.CHECKPOINT_BIT);
    }


    /**
     * Called when the main character head fixture collides with this object fixture.
     * <p>Done by safety.</p>
     */
    @Override
    public void onHeadHit() {
        use();
    }

    /**
     * If the {@link HUD#getScore()} is lower than 5 shows a Warning label, otherwise makes the {@link HUD} "WIN" popUp window visible.
     */
    public void use() {
        if (screen.getHud().getScore() >= 5) {
            screen.getHud().getLblWarning().setVisible(false);
            screen.getHud().setPaused(true);
            game.setFinalScore(game.getFinalScore()+screen.getHud().getScore());
            Gdx.app.log("FinalScore", game.getFinalScore()+"");
            if (screen.getHud().getGameLevel() != game.getTotalLevels()) {
                screen.getHud().getWinScreen().setVisible(true);
            } else {
                screen.getHud().getSaveScoreScreen().setVisible(true);
            }
        } else {
            screen.getHud().getLblWarning().setVisible(true);
        }
    }
}
