package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.isabelrosado.duckyduck.DuckyDuck;

public class HelpScreen extends ScreenI {
    private Label lblHelp;
    private Image imgHelp;

    public HelpScreen(DuckyDuck game) {
        super(game, "Skins/mhelp.json", true, true);
        defineScreen();
    }

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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
