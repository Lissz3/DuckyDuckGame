package com.isabelrosado.duckyduck.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;
import com.isabelrosado.duckyduck.Sprites.Duck;

public class MyInputProcessor implements InputProcessor {
    private Duck duck;

    public MyInputProcessor(Duck duck) {
        this.duck = duck;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                duck.setLeftMove(true);
                break;
            case Input.Keys.RIGHT:
                duck.setRightMove(true);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                duck.setLeftMove(false);
                break;
            case Input.Keys.RIGHT:
                duck.setRightMove(false);
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Gdx.input.getX() > Gdx.graphics.getWidth() / 2) {
            duck.setRightMove(true);
        } else {
            duck.setLeftMove(true);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        duck.setRightMove(false);
        duck.setLeftMove(false);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
