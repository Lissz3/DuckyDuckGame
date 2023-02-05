package com.isabelrosado.duckyduck.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;
import com.isabelrosado.duckyduck.Tools.Animator;
import com.sun.org.apache.bcel.internal.generic.RET;
import jogamp.graph.font.typecast.ot.table.ID;


public class FatBird extends Enemy {
    //40x48
    public enum State {
        FALLING,
        GROUNDED,
        HITTED,
        IDLE,
        FLAPPING
    }
    public State currentState;
    public State previousState;
    private float stateTime;

    Animator animator;
    private Texture animationTexture;
    private Animation<TextureRegion> fbFall;
    private Animation<TextureRegion> fbGround;
    private Animation<TextureRegion> fbHit;
    private Animation<TextureRegion> fbIdle;

    public boolean setToDestroy;
    public boolean destroyed;

    private BodyDef fatBirdBdef;

    private FixtureDef fbFDef;

    private boolean touchedGround;
    private boolean top;

    private boolean smash;


    public FatBird(PlayScreen screen, float x, float y, float velX, float velY) {
        super(screen, x, y, velX, velY);
        animationTexture = new Texture("FatBird.png");
        animator = new Animator(animationTexture, 40, 48);
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


    @Override
    protected void defineEnemy() {
        fatBirdBdef = new BodyDef();
        fatBirdBdef.position.set(getX(), getY());
//        fatBirdBdef.type = BodyDef.BodyType.DynamicBody;
        fatBirdBdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(fatBirdBdef);
        FixtureDef fbFDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13 / DuckyDuck.PIXEL_PER_METER);
        fbFDef.filter.categoryBits = DuckyDuck.ENEMY_BIT;
        fbFDef.filter.maskBits = DuckyDuck.GROUND_BIT | DuckyDuck.DUCK_BIT;

        fbFDef.shape = shape;
        b2body.createFixture(fbFDef).setUserData(this);
        setBounds(getX(), getY(), 40 / DuckyDuck.PIXEL_PER_METER, 48 / DuckyDuck.PIXEL_PER_METER);

        PolygonShape head = new PolygonShape();
        Vector2[] v = new Vector2[4];
        v[0] = new Vector2(-10, 9).scl(1 / DuckyDuck.PIXEL_PER_METER);
        v[1] = new Vector2(10, 9).scl(1 / DuckyDuck.PIXEL_PER_METER);
        v[2] = new Vector2(-8, 14).scl(1 / DuckyDuck.PIXEL_PER_METER);
        v[3] = new Vector2(8, 14).scl(1 / DuckyDuck.PIXEL_PER_METER);
        head.set(v);

        fbFDef.shape = head;
        fbFDef.restitution = 0.5f;
        fbFDef.filter.categoryBits = DuckyDuck.ENEMY_HEAD_BIT;
        b2body.createFixture(fbFDef).setUserData(this);
    }

    public State getState() {
        if (destroyed) {
            return State.HITTED;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (getY() < screen.duck.getY()) {
            return State.GROUNDED;
        } else if (b2body.getLinearVelocity().y > 0) {
            return  State.FLAPPING;
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

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }


    public void draw(Batch batch) {
        if (!destroyed || !fbHit.isAnimationFinished(stateTime)) {
            super.draw(batch);
        }
    }

    @Override
    public void reverseVelocity(boolean x, boolean y){
        if (getX() < screen.duck.getX() + 0.5 || getX() < screen.duck.getX() - 0.5){
            setSmash(true);
        } else {
            setSmash(false);
        }

        if (isSmash() || getState() != State.IDLE){
            if (getY() >= 2 && !top) {
                velocity.y = 3f;
                super.reverseVelocity(x, y);
                top = true;
                touchedGround = false;
            } else if (getY() < screen.duck.getY() && !touchedGround) {
                velocity.y = -0.5f;
                super.reverseVelocity(x, y);
                top = false;
                touchedGround = true;
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
