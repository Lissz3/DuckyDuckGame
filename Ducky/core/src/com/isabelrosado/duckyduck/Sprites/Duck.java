package com.isabelrosado.duckyduck.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;
import com.isabelrosado.duckyduck.Tools.Animator;

public class Duck extends Sprite {
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
    private Animation<TextureRegion> duckIdle;
    private Animation<TextureRegion> duckRun;
    private Animation<TextureRegion> duckDoubleJump;
    private Animation<TextureRegion> duckHit;
    private TextureRegion duckFall;
    private TextureRegion duckJump;

    private boolean runningRight;

    private float stateTimer;

    private int jumps;

    private final int maxJumps = 2;

    private boolean hit;
    private DuckyDuck game;
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

    public Duck(DuckyDuck game, PlayScreen screen) {
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
        duckIdle = animator.getAnimation(11, 384, 0);
        duckRun = animator.getAnimation(12, 0, 0);
        duckDoubleJump = animator.getAnimation(6, 960, 0);
        duckHit = animator.getAnimation(7, 736, 0);
        duckJump = new TextureRegion(getTexture(), 1344, 0, 32, 32);
        duckFall = new TextureRegion(getTexture(), 1312, 0, 32, 32);

        setBounds(0, 0, 36 / DuckyDuck.PIXEL_PER_METER, 36 / DuckyDuck.PIXEL_PER_METER);
        setRegion(duckIdle.getKeyFrame(stateTimer));
    }

    public void defineDuck() {
        BodyDef duckBdef = new BodyDef();
        duckBdef.position.set(130 / DuckyDuck.PIXEL_PER_METER, 130 / DuckyDuck.PIXEL_PER_METER);
        duckBdef.type = BodyDef.BodyType.DynamicBody;
        dBody = world.createBody(duckBdef);

        FixtureDef duckFDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / DuckyDuck.PIXEL_PER_METER);
        duckFDef.filter.categoryBits = DuckyDuck.DUCK_BIT;
        duckFDef.filter.maskBits = DuckyDuck.DEFAULT_BIT | DuckyDuck.BRICK_BIT | DuckyDuck.FRUIT_BIT | DuckyDuck.BRICKHIT_BIT | DuckyDuck.GROUND_BIT | DuckyDuck.ENEMY_BIT | DuckyDuck.ENEMY_HEAD_BIT | DuckyDuck.CHECKPOINT_BIT;

        duckFDef.shape = shape;
        dBody.createFixture(duckFDef).setUserData(this);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-4 / DuckyDuck.PIXEL_PER_METER, 13 / DuckyDuck.PIXEL_PER_METER), new Vector2(4 / DuckyDuck.PIXEL_PER_METER, 13 / DuckyDuck.PIXEL_PER_METER));
        duckFDef.shape = head;
        duckFDef.isSensor = true;
        duckFDef.filter.categoryBits = DuckyDuck.DUCK_HEAD_BIT;
        dBody.createFixture(duckFDef).setUserData("head");
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case JUMPING:
                region = duckJump;
                break;
            case RUNNING:
                region = duckRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = duckFall;
                break;
            case DOUBLEJUMPING:
                region = duckDoubleJump.getKeyFrame(stateTimer);
                break;
            case HITTED:
                region = duckHit.getKeyFrame(stateTimer);
                break;
            case STANDING:
            default:
                region = duckIdle.getKeyFrame(stateTimer, true);
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
        game.getAssetManager().get("Audio/Sounds/GameOver.mp3", Sound.class).play(DuckyDuck.FX_VOLUME);
        Filter filter = new Filter();
        filter.maskBits = DuckyDuck.NOTHING_BIT;
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
