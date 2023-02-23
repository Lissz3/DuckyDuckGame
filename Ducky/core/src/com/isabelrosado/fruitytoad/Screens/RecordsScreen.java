package com.isabelrosado.fruitytoad.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.isabelrosado.fruitytoad.FruityToad;

import java.util.*;

/**
 * Screen used to show the score records.
 * @see ScreenI
 */
public class RecordsScreen extends ScreenI {

    /**
     * <p>Creates a screen with the given values.</p>
     * <p>It also gives values needed to the main constructor {@link ScreenI} as the path for the JSON file skin,
     * if there is need of an InputProcessor or if the screen has a Back Button.
     * Defines the screen with {@link #defineScreen()}.</p>
     * @param game main screen
     */
    public RecordsScreen(final FruityToad game) {
        super(game, "Skins/mrecords.json", true, true);
        defineScreen();
    }

    /**
     * Used to define the unique actors of the screen to change their values and/or give them listeners.
     * <p>Adds new actors to the Stage if needed.</p>
     */
    @Override
    protected void defineScreen() {
        Label lblTitle = stg.getRoot().findActor("lblTitle");
        lblTitle.setText(game.getBundle().get("recmenu.title"));

        Label lblExp = stg.getRoot().findActor("lblExp");
        lblExp.setText(game.getBundle().get("recmenu.text"));

        Label lblRecords = stg.getRoot().findActor("lblRecords");

        LinkedHashMap<String, Integer> sorted = sortScores(game.getPreferences().get());

        for (Map.Entry<String, Integer> value : sorted.entrySet()) {
            lblRecords.setText(String.format("%s%s: %s - %s: %s\n", lblRecords.getText(), game.getBundle().get("recmenu.name"), value.getKey(), game.getBundle().get("recmenu.score"), value.getValue()));
        }
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

    /**
     * Used to sort the scores.
     */
    private LinkedHashMap<String, Integer> sortScores(Map<String, ?> value){
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
        ArrayList<Integer> list = new ArrayList<>();

        for (Map.Entry<String, ?> entry : value.entrySet()) {
            int score = Integer.parseInt((String) entry.getValue());
            list.add(score);
        }

        Collections.sort(list, new Comparator<Integer>(){
            @Override
            public int compare(Integer f, Integer s){
                return f.compareTo(s);
            }
        });

        for (int score : list) {
            for (Map.Entry<String, ?> entry : value.entrySet()) {
                if (Integer.parseInt((String) entry.getValue()) == score) {
                    sortedMap.put(entry.getKey(), score);
                }
            }
        }
        return sortedMap;
    }
}
