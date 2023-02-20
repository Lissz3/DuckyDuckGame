package com.isabelrosado.fruitytoad.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.isabelrosado.fruitytoad.Sprites.Frog;

public class MyInputProcessor implements InputProcessor {
    private Frog frog;

    public MyInputProcessor(Frog frog) {
        this.frog = frog;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                frog.setLeftMove(true);
                break;
            case Input.Keys.RIGHT:
                frog.setRightMove(true);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.LEFT:
                frog.setLeftMove(false);
                break;
            case Input.Keys.RIGHT:
                frog.setRightMove(false);
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
            frog.setRightMove(true);
        } else {
            frog.setLeftMove(true);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        frog.setRightMove(false);
        frog.setLeftMove(false);
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
