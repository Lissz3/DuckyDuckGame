package com.isabelrosado.fruitytoad.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.isabelrosado.fruitytoad.FruityToad;

/**
 * Screen used to show game credits.
 * @see ScreenI
 */
public class CreditsScreen extends ScreenI {

    /**
     * <p>Creates a screen with the given values.</p>
     * <p>It also gives values needed to the main constructor {@link ScreenI} as the path for the JSON file skin,
     * if there is need of an InputProcessor or if the screen has a Back Button.
     * Defines the screen with {@link #defineScreen()}.</p>
     * @param game main screen
     */
    public CreditsScreen(final FruityToad game) {
        super(game, "Skins/mcredits.json", true, true);
        defineScreen();
    }

    /**
     * Used to define the unique actors of the screen to change their values and/or give them listeners.
     * <p>Adds new actors to the Stage if needed.</p>
     */
    @Override
    protected void defineScreen() {
        Label lblTitle = stg.getRoot().findActor("lblTitle");
        lblTitle.setText(game.getBundle().get("credmenu.title"));

        Label lblTy = stg.getRoot().findActor("lblTy");
        lblTy.setText(game.getBundle().get("credmenu.text"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void render(float delta) {
       super.render(delta);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void pause() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resume() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hide() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        super.dispose();
    }
}