package com.isabelrosado.fruitytoad.Screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.isabelrosado.fruitytoad.FruityToad;

/**
 * Screen used to show game help.
 * @see ScreenI
 */
public class HelpScreen extends ScreenI {
    /**
     * Label used to show text in the screen
     */
    private Label lblHelp;

    /**
     * Image used to show instructions in the screen
     */
    private Image imgHelp;

    /**
     * <p>Creates a screen with the given values.</p>
     * <p>It also gives values needed to the main constructor {@link ScreenI} as the path for the JSON file skin,
     * if there is need of an InputProcessor or if the screen has a Back Button.
     * Defines the screen with {@link #defineScreen()}.</p>
     * @param game main screen
     */
    public HelpScreen(FruityToad game) {
        super(game, "Skins/mhelp.json", true, true);
        defineScreen();
    }

    /**
     * Used to define the unique actors of the screen to change their values and/or give them listeners.
     * <p>Adds new actors to the Stage if needed.</p>
     */
    @Override
    protected void defineScreen() {
        lblHelp = stg.getRoot().findActor("lblHelp");
        imgHelp = stg.getRoot().findActor("imgHelp");
        final SelectBox sbHelp = stg.getRoot().findActor("sbHelp");
        Array<String> sbHelpTexts = new Array<>();
        sbHelpTexts.add(game.getBundle().get("helpmenu.walk"));
        sbHelpTexts.add(game.getBundle().get("helpmenu.jump"));
        sbHelpTexts.add(game.getBundle().get("helpmenu.fruit"));
        sbHelpTexts.add(game.getBundle().get("helpmenu.score"));
        sbHelpTexts.add(game.getBundle().get("helpmenu.pause.play"));
        sbHelpTexts.add(game.getBundle().get("helpmenu.selectlvl"));
        sbHelpTexts.add(game.getBundle().get("helpmenu.enemies"));
        sbHelpTexts.add(game.getBundle().get("helpmenu.checkpoints"));
        sbHelp.clearItems();
        sbHelp.setItems(sbHelpTexts);
        imgHelp.setDrawable(skin.getDrawable("main"));
        lblHelp.setText(" "+game.getBundle().get("helpmenu.walk.lbl"));
        sbHelp.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String drawable;
                String bundle;
                switch (sbHelp.getSelectedIndex()) {
                    default:
                    case 0:
                        drawable = "main";
                        bundle = "helpmenu.walk.lbl";
                        break;
                    case 1:
                        drawable = "jump";
                        bundle = "helpmenu.jump.lbl";
                        break;
                    case 2:
                        drawable = "fruits";
                        bundle = "helpmenu.fruit.lbl";
                        break;
                    case 3:
                        drawable = "score";
                        bundle = "helpmenu.score.lbl";
                        break;
                    case 4:
                        drawable = "playpause";
                        bundle = "helpmenu.pause.play.lbl";
                        break;
                    case 5:
                        drawable = "selectlvl";
                        bundle = "helpmenu.selectlvl.lbl";
                        break;
                    case 6:
                        drawable = "feyi";
                        bundle = "helpmenu.enemies.lbl";
                        break;
                    case 7:
                        drawable = "checkpoint";
                        bundle = "helpmenu.checkpoints.lbl";
                        break;
                }
                imgHelp.setDrawable(skin.getDrawable(drawable));
                lblHelp.setText(" "+game.getBundle().get(bundle));
            }
        });
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
