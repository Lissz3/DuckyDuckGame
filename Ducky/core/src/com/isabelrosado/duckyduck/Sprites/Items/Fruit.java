package com.isabelrosado.duckyduck.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;
import com.isabelrosado.duckyduck.Sprites.Duck;
import com.isabelrosado.duckyduck.Sprites.Enemies.FatBird;
import com.isabelrosado.duckyduck.Tools.Animator;

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


    public Fruit(DuckyDuck game, PlayScreen screen, float x, float y) {
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
        shape.setRadius(7 / DuckyDuck.PIXEL_PER_METER);

        fruitFDef.filter.categoryBits = DuckyDuck.FRUIT_BIT;
        fruitFDef.filter.maskBits = DuckyDuck.DUCK_BIT | DuckyDuck.GROUND_BIT | DuckyDuck.BRICK_BIT | DuckyDuck.BRICKHIT_BIT;

        fruitFDef.shape = shape;

        fruitFDef.restitution = 0.5f;

        body.createFixture(fruitFDef).setUserData(this);
    }

    @Override
    public void use() {
        screen.getHud().setScore(screen.getHud().getScore() + 1);
        game.getAssetManager().get("Audio/Sounds/FruitCollected.mp3", Sound.class).play();
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
