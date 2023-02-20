package com.isabelrosado.fruitytoad.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
import com.isabelrosado.fruitytoad.Tools.Animator;

public class Frog extends Sprite {
    public World world;
    public Body dBody;
    public enum State {
        FALLING,
        JUMPING,
        STANDING,
        RUNNING,
        DOUBLEJUMPING,
        HITTED
    }
    public State currentState;
    public State previousState;
    private Texture animationTexture;
    Animator animator;
    private Animation<TextureRegion> frogIdle;
    private Animation<TextureRegion> frogRun;
    private Animation<TextureRegion> frogDoubleJump;
    private Animation<TextureRegion> frogHit;
    private TextureRegion frogFall;
    private TextureRegion frogJump;

    private boolean runningRight;

    private float stateTimer;

    private int jumps;

    private final int maxJumps = 2;

    private boolean hit;
    private FruityToad game;
    private boolean leftMove;
    private boolean rightMove;
    private boolean jumping;

    public State getState() {
        if (!isHit()){
            if (dBody.getLinearVelocity().y > 0 && jumps == 1) {
                return State.JUMPING;
            } else if (dBody.getLinearVelocity().y > 0 && jumps == 2) {
                return State.DOUBLEJUMPING;
            } else if (dBody.getLinearVelocity().y < 0) {
                return State.FALLING;
            } else if (dBody.getLinearVelocity().x != 0) {
                return State.RUNNING;
            } else {
                return State.STANDING;
            }
        } else {
            return State.HITTED;
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

    public Frog(FruityToad game, PlayScreen screen) {
        super(screen.getAtlas().findRegion("FrogRun"));
        this.world = screen.getWorld();
        this.game = game;
        defineDuck();

        setCurrentState(State.STANDING);
        setPreviousState(State.STANDING);
        stateTimer = 0;
        runningRight = true;
        jumps = 0;
        setHit(false);

        animationTexture = new Texture("Sprites/Frog.png");
        animator = new Animator(getTexture(), 32, 32);
        frogIdle = animator.getAnimation(11, 384, 0);
        frogRun = animator.getAnimation(12, 0, 0);
        frogDoubleJump = animator.getAnimation(6, 960, 0);
        frogHit = animator.getAnimation(7, 736, 0);
        frogJump = new TextureRegion(getTexture(), 1344, 0, 32, 32);
        frogFall = new TextureRegion(getTexture(), 1312, 0, 32, 32);

        setBounds(0, 0, 36 / FruityToad.PIXEL_PER_METER, 36 / FruityToad.PIXEL_PER_METER);
        setRegion(frogIdle.getKeyFrame(stateTimer));
    }

    public void defineDuck() {
        BodyDef frogBDef = new BodyDef();
        frogBDef.position.set(130 / FruityToad.PIXEL_PER_METER, 130 / FruityToad.PIXEL_PER_METER);
        frogBDef.type = BodyDef.BodyType.DynamicBody;
        dBody = world.createBody(frogBDef);

        FixtureDef duckFDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / FruityToad.PIXEL_PER_METER);
        duckFDef.filter.categoryBits = FruityToad.FROG_BIT;
        duckFDef.filter.maskBits = FruityToad.DEFAULT_BIT | FruityToad.BRICK_BIT | FruityToad.FRUIT_BIT | FruityToad.BRICKHIT_BIT | FruityToad.GROUND_BIT | FruityToad.ENEMY_BIT | FruityToad.ENEMY_HEAD_BIT | FruityToad.CHECKPOINT_BIT;

        duckFDef.shape = shape;
        dBody.createFixture(duckFDef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-4 / FruityToad.PIXEL_PER_METER, 13 / FruityToad.PIXEL_PER_METER), new Vector2(4 / FruityToad.PIXEL_PER_METER, 13 / FruityToad.PIXEL_PER_METER));
        duckFDef.shape = head;
        duckFDef.isSensor = true;
        duckFDef.filter.categoryBits = FruityToad.FROG_HEAD_BIT;
        dBody.createFixture(duckFDef).setUserData("head");
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case JUMPING:
                region = frogJump;
                break;
            case RUNNING:
                region = frogRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = frogFall;
                break;
            case DOUBLEJUMPING:
                region = frogDoubleJump.getKeyFrame(stateTimer);
                break;
            case HITTED:
                region = frogHit.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = frogIdle.getKeyFrame(stateTimer, true);
                break;
        }


        if ((dBody.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((dBody.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }


        stateTimer = getCurrentState() == getPreviousState() ? stateTimer + dt : 0;
        setPreviousState(getCurrentState());
        return region;
    }

    public void update(float dt) {
        setPosition(dBody.getPosition().x - getWidth() / 2, (dBody.getPosition().y - getHeight() / 2) + 0.05f);
        setRegion(getFrame(dt));
        movementUpdate();
        if (this.dBody.getLinearVelocity().y == 0) {
            jumps = 0;
        }
        if (!isHit() && this.dBody.getPosition().y < -4){
            onHit();
        }
    }

    public boolean canDoubleJump() {
        if (jumps == maxJumps) {
            return false;
        }
        jumps++;
        return true;
    }


    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void onHit() {
        game.getAssetManager().get("Audio/Sounds/GameOver.mp3", Sound.class).play(FruityToad.FX_VOLUME);
        Filter filter = new Filter();
        filter.maskBits = FruityToad.NOTHING_BIT;
        for (Fixture fix : dBody.getFixtureList()) {
            fix.setFilterData(filter);
        }
        dBody.applyLinearImpulse(new Vector2(0.1f, 4f), dBody.getWorldCenter(), true);
        setHit(true);
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public boolean isLeftMove() {
        return leftMove;
    }

    public void setLeftMove(boolean leftMove) {
        if (isRightMove() && leftMove){
            setRightMove(false);
        }
        this.leftMove = leftMove;
    }

    public boolean isRightMove() {
        return rightMove;
    }

    public void setRightMove(boolean rightMove) {
        if (isLeftMove() && rightMove){
            setLeftMove(false);
        }
        this.rightMove = rightMove;
    }

    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }
    public boolean isJumping() {
        return jumping;
    }


    public void movementUpdate(){
        if (getCurrentState() != State.HITTED) {
            if (isRightMove() && dBody.getLinearVelocity().x <= 2) {
                dBody.applyLinearImpulse(new Vector2(0.1f, 0), dBody.getWorldCenter(), true);
            }
            if (isLeftMove() && dBody.getLinearVelocity().x >= -2) {
                dBody.applyLinearImpulse(new Vector2(-0.1f, 0), dBody.getWorldCenter(), true);
            }
            if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) || isJumping()) && canDoubleJump()) {
                dBody.applyLinearImpulse(new Vector2(0, 4f), dBody.getWorldCenter(), true);
                setJumping(false);
            }
        }
    }
}
