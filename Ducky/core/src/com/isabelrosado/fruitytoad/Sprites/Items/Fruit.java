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

/**
 * Unique item called Fruit.
 * @see Item
 */
public class Fruit extends Item {

    /**
     * Difference the Fruit states
     */
    public enum State {
        NOT_COLLECTED,
        COLLECTED
    }

    /**
     * Current <code>State</code>
     */
    public State currentState;

    /**
     * Previous <code>State</code>
     */
    public State previousState;

    /**
     * <code>State</code> animations
     */
    private Animation<TextureRegion> fruitNotCollected;
    private Animation<TextureRegion> fruitCollected;

    /**
     * Body definition as type of body.
     */
    BodyDef fruitBDef;

    /**
     * Timer for sprite updates
     */
    private float stateTimer;

    /**
     * Initialize the values to the values given to the super constructor.
     * <p>Sets the texture needed for this specific item and creates the animations and the current & previous <code>State</code>.</p>
     * Also gives the first Region to be drawn.
     * @param game main screen
     * @param screen actual screen
     * @param x position of the body in the world for <b>x-axis</b>
     * @param y position of the body in the world for <b>y-axis</b>
     */
    public Fruit(FruityToad game, PlayScreen screen, float x, float y) {
        super(game, screen, x, y);
        stateTimer = 0;
        Texture animationTexture = new Texture("Sprites/Fruits.png");
        Animator animator = new Animator(animationTexture, 32, 32);
        fruitNotCollected = animator.getAnimation(17, 0, 0);
        fruitCollected = animator.getAnimation(6, 544, 0);
        setCurrentState(State.NOT_COLLECTED);
        setPreviousState(State.NOT_COLLECTED);

        setRegion(fruitNotCollected.getKeyFrame(stateTimer, true));
    }

    /**
     * @return the <code>State</code> depending on if it is set {@link #toDestroy}
     */
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

    /**
     * Adds time to the <code>stateTimer</code> if the current and previous state are the same or resets it otherwise.
     * @param dt The time in seconds since the last update.
     * @return the frame region depending on the Fruit <code>State</code>
     */
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


    /**
     * Used to define the body definiton, type, his fixtures, shapes and restitution.
     * <p>Also set the position and the size of the sprite to be drawn</p>
     * <p>Sets the fruit unique bit and sets the bits who can collide with him.</p>
     */
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

    /**
     * Plays a sound when the item is used, adds the score to the {@link com.isabelrosado.fruitytoad.Scenes.HUD} and {@link #destroy()} the item.
     */
    @Override
    public void use() {
        screen.getHud().setScore(screen.getHud().getScore() + 1);
        game.getAssetManager().get("Audio/Sounds/FruitCollected.mp3", Sound.class).play(FruityToad.FX_VOLUME);
        destroy();
    }

    /**
     * Called when the sprite should update his position and frame if its not {@link #destroyed}.
     * @param dt The time in seconds since the last update.
     */
    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    /**
     * Draws the specified sprite animation
     * @param batch 2D texture (region) to draw
     */
    @Override
    public void draw(Batch batch) {
        if (!destroyed || !fruitCollected.isAnimationFinished(stateTimer)) {
            super.draw(batch);
        }
    }

}
