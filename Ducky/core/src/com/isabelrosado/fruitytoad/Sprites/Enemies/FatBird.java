package com.isabelrosado.fruitytoad.Sprites.Enemies;

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
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
import com.isabelrosado.fruitytoad.Screens.ScreenI;
import com.isabelrosado.fruitytoad.Tools.Animator;

/**
 * Unique enemy called FatBird or Fe'Yi.
 * @see Enemy
 */
public class FatBird extends Enemy {
    //40x48

    /**
     * Difference the Fat Bird states
     */
    public enum State {
        FALLING,
        GROUNDED,
        HITTED,
        IDLE,
        FLAPPING
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
     * Timer for sprite updates
     */
    private float stateTime;

    /**
     * <code>State</code> animations
     */
    private Animation<TextureRegion> fbFall;
    private Animation<TextureRegion> fbGround;
    private Animation<TextureRegion> fbHit;
    private Animation<TextureRegion> fbIdle;

    /**
     * Sets if the FatBird should be destroyed (true) or not (false).
     */
    public boolean setToDestroy;

    /**
     * Looks if the FatBird has been destroyed (true) or not (false).
     */
    public boolean destroyed;

    /**
     * Body definition as type of body.
     */
    private BodyDef fatBirdBdef;

    /**
     * Sets if the FatBird has touched the ground (true) or not (false).
     */
    private boolean touchedGround;

    /**
     * Sets if the FatBird has raised to the top (true) or not (false).
     */
    private boolean top;

    /**
     * Sets if the FatBird should smash(true) or not (false).
     */
    private boolean smash;


    /**
     * Initialize the values to the values given to the super constructor.
     * <p>Sets the texture needed for this specific enemy, creates the animations and initialize the current & previous <code>State</code>.</p>
     * Also gives the first Region to be drawn.
     * @param game main screen
     * @param screen actual screen
     * @param x position of the body in the world for <b>x-axis</b>
     * @param y position of the body in the world for <b>y-axis</b>
     * @param velX velocity for the <b>x-axis</b>
     * @param velY velocity for the <b>y-axis</b>
     */
    public FatBird(FruityToad game, PlayScreen screen, float x, float y, float velX, float velY) {
        super(game, screen, x, y, velX, velY);
        Texture animationTexture = new Texture("Sprites/FatBird.png");
        Animator animator = new Animator(animationTexture, 40, 48);
        setToDestroy = false;
        destroyed = false;
        touchedGround = false;
        top = false;

        fbIdle = animator.getAnimation(8, 0, 0);
        fbHit = animator.getAnimation(5, 320, 0);
        fbFall = animator.getAnimation(4, 520, 0);
        fbGround = animator.getAnimation(4, 680, 0);

        stateTime = 0;
        setSmash(false);

        setCurrentState(State.IDLE);
        setPreviousState(State.IDLE);

        setRegion(fbIdle.getKeyFrame(stateTime));
    }

    /**
     * Used to define the body definition, type, his fixtures and shapes.
     * <p>Also set the position and the size of the sprite to be drawn</p>
     * <p>Sets the bird unique bit and sets the bits who can collide with him.</p>
     */
    @Override
    protected void defineEnemy() {
        fatBirdBdef = new BodyDef();
        fatBirdBdef.position.set(getX(), getY());
        fatBirdBdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(fatBirdBdef);
        FixtureDef fbFDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13 / FruityToad.PIXEL_PER_METER);
        fbFDef.filter.categoryBits = FruityToad.ENEMY_BIT;
        fbFDef.filter.maskBits = FruityToad.GROUND_BIT | FruityToad.FROG_BIT;

        fbFDef.shape = shape;
        b2body.createFixture(fbFDef).setUserData(this);
        setBounds(getX(), getY(), 40 / FruityToad.PIXEL_PER_METER, 48 / FruityToad.PIXEL_PER_METER);

        PolygonShape head = new PolygonShape();
        Vector2[] v = new Vector2[4];
        v[0] = new Vector2(-10, 9).scl(1 / FruityToad.PIXEL_PER_METER);
        v[1] = new Vector2(10, 9).scl(1 / FruityToad.PIXEL_PER_METER);
        v[2] = new Vector2(-8, 14).scl(1 / FruityToad.PIXEL_PER_METER);
        v[3] = new Vector2(8, 14).scl(1 / FruityToad.PIXEL_PER_METER);
        head.set(v);

        fbFDef.shape = head;
        fbFDef.restitution = 0.5f;
        fbFDef.filter.categoryBits = FruityToad.ENEMY_HEAD_BIT;
        b2body.createFixture(fbFDef).setUserData(this);
    }

    /**
     * @return the <code>State</code> depending on conditions as the current {@link #velocity} and if it is {@link #destroyed}
     */
    public State getState() {
        if (destroyed) {
            return State.HITTED;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (getY() < 0) {
            return State.GROUNDED;
        } else if (b2body.getLinearVelocity().y > 0) {
            return State.FLAPPING;
        } else {
            return State.IDLE;
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
     * @return the frame region depending on the FatBird <code>State</code>
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case HITTED:
                region = fbHit.getKeyFrame(stateTime);
                break;
            case FALLING:
                region = fbFall.getKeyFrame(stateTime);
                break;
            case GROUNDED:
                region = fbGround.getKeyFrame(stateTime);
                break;
            case IDLE:
            case FLAPPING:
            default:
                region = fbIdle.getKeyFrame(stateTime, true);
                break;
        }

        stateTime = getCurrentState() == getPreviousState() ? stateTime + dt : 0;
        setPreviousState(getCurrentState());
        return region;
    }

    /**
     * Called when the sprite should update his position and frame if {@link #setToDestroy} is false.
     * @param dt The time in seconds since the last update.
     */
    @Override
    public void update(float dt) {
        stateTime += dt;

        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
            stateTime = 0;
        } else if (!destroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            reverseVelocity(false, true);
        }
        setRegion(getFrame(dt));
    }

    /**
     * Plays a sound when the FatBird is hitted on the head and sets {@link #setToDestroy} to true.
     */
    @Override
    public void hitOnHead() {
        setToDestroy = true;
        game.getAssetManager().get("Audio/Sounds/FatBirdKilled.mp3", Sound.class).play(FruityToad.FX_VOLUME);
    }

    /**
     * Draws the specified sprite animation
     * @param batch 2D texture (region) to draw
     */
    public void draw(Batch batch) {
        if (!destroyed || !fbHit.isAnimationFinished(stateTime)) {
            super.draw(batch);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reverseVelocity(boolean x, boolean y) {
        if (!screen.frog.isHit() || getState() != State.IDLE) {
            if ((getX() < screen.frog.getX() + 0.5 || getX() < screen.frog.getX() - 0.5)) {
                setSmash(true);
            }
        } else {
            setSmash(false);
        }

        if (isSmash() || getState() != State.IDLE) {
            if (getY() >= 2 && !top) {
                velocity.y = 3f;
                super.reverseVelocity(x, y);
                top = true;
                touchedGround = false;
            } else if (getY() < 0.3 && !touchedGround) {
                velocity.y = -0.5f;
                super.reverseVelocity(x, y);
                top = false;
                touchedGround = true;
                game.getAssetManager().get("Audio/Sounds/FatBirdGround.mp3", Sound.class).play(FruityToad.FX_VOLUME);
            }
        }
    }

    public boolean isSmash() {
        return smash;
    }

    public void setSmash(boolean smash) {
        this.smash = smash;
    }
}
