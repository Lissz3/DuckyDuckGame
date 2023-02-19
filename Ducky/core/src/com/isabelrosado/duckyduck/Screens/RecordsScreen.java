package com.isabelrosado.duckyduck.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.isabelrosado.duckyduck.DuckyDuck;

import java.util.*;

public class RecordsScreen extends ScreenI {

    public RecordsScreen(final DuckyDuck game) {
        super(game, "Skins/mrecords.json", true);
        defineScreen();
    }

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
