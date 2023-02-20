package com.isabelrosado.fruitytoad.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
import com.isabelrosado.fruitytoad.Tools.Animator;

public class Fruit extends Item {
    public enum State {
        NOT_COLLECTED,
        COLLECTED
    }

    public State currentState;
    public State previousState;

    private Texture animationTexture;

    Animator animator;
    private Animation<TextureRegion> fruitNotCollected;
    private Animation<TextureRegion> fruitCollected;

    BodyDef fruitBDef;
    private float stateTimer;


    public Fruit(FruityToad game, PlayScreen screen, float x, float y) {
        super(game, screen, x, y);
        stateTimer = 0;
        animationTexture = new Texture("Sprites/Fruits.png");
        animator = new Animator(animationTexture, 32, 32);
        fruitNotCollected = animator.getAnimation(17, 0, 0);
        fruitCollected = animator.getAnimation(6, 544, 0);
        setCurrentState(State.NOT_COLLECTED);
        setPreviousState(State.NOT_COLLECTED);

        setRegion(fruitNotCollected.getKeyFrame(stateTimer, true));
    }

    public State getState() {
        if (toDestroy) {
            return State.COLLECTED;
        } else {
            return State.NOT_COLLECTED;
        }
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case COLLECTED:
                region = fruitCollected.getKeyFrame(stateTimer);
                break;
            case NOT_COLLECTED:
            default:
                region = fruitNotCollected.getKeyFrame(stateTimer, true);
                break;
        }

        stateTimer = getCurrentState() == getPreviousState() ? stateTimer + dt : 0;
        setPreviousState(getCurrentState());
        return region;
    }

    @Override
    public void defineItem() {
        fruitBDef = new BodyDef();
        fruitBDef.position.set(getX(), getY());
        fruitBDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(fruitBDef);

        FixtureDef fruitFDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(7 / FruityToad.PIXEL_PER_METER);

        fruitFDef.filter.categoryBits = FruityToad.FRUIT_BIT;
        fruitFDef.filter.maskBits = FruityToad.FROG_BIT | FruityToad.GROUND_BIT | FruityToad.BRICK_BIT | FruityToad.BRICKHIT_BIT;

        fruitFDef.shape = shape;

        fruitFDef.restitution = 0.5f;

        body.createFixture(fruitFDef).setUserData(this);
    }

    @Override
    public void use() {
        screen.getHud().setScore(screen.getHud().getScore() + 1);
        game.getAssetManager().get("Audio/Sounds/FruitCollected.mp3", Sound.class).play(FruityToad.FX_VOLUME);
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    @Override
    public void draw(Batch batch) {
        if (!destroyed || !fruitCollected.isAnimationFinished(stateTimer)) {
            super.draw(batch);
        }
    }

}
