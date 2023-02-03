package com.isabelrosado.duckyduck.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
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
        DOUBLEJUMPING
    }

    public State currentState;
    public State previousState;

    private Texture animationTexture;

    Animator animator;
    private Animation<TextureRegion> duckIdle;
    private Animation<TextureRegion> duckRun;
    private Animation<TextureRegion> duckDoubleJump;
    private TextureRegion duckFall;
    private TextureRegion duckJump;

    private boolean runningRight;

    private float stateTimer;

    private int jumps;

    private final int maxJumps = 2;


    public State getState() {
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

    public Duck(PlayScreen screen) {
        super(screen.getAtlas().findRegion("FrogRun"));
        this.world = screen.getWorld();
        defineDuck();

        setCurrentState(State.STANDING);
        setPreviousState(State.STANDING);
        stateTimer = 0;
        runningRight = true;
        jumps = 0;

        animationTexture = new Texture("Frog.png");
        animator = new Animator(getTexture(), 32, 32);
        duckIdle = animator.getAnimation(11, 384, 0);
        duckRun = animator.getAnimation(12, 0, 0);
        duckDoubleJump = animator.getAnimation(6, 960, 0);
        duckJump = new TextureRegion(getTexture(), 1344, 0, 32, 32);
        duckFall = new TextureRegion(getTexture(), 1312, 0, 32, 32);

        setBounds(0, 0, 36 / DuckyDuck.PIXEL_PER_METER, 36 / DuckyDuck.PIXEL_PER_METER);
        setRegion(duckIdle.getKeyFrame(stateTimer));
    }

    public void defineDuck() {
        BodyDef duckBdef = new BodyDef();
        duckBdef.position.set(32 / DuckyDuck.PIXEL_PER_METER, 32 / DuckyDuck.PIXEL_PER_METER);
        duckBdef.type = BodyDef.BodyType.DynamicBody;
        dBody = world.createBody(duckBdef);

        FixtureDef duckFDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / DuckyDuck.PIXEL_PER_METER);
        duckFDef.filter.categoryBits = DuckyDuck.DUCK_BIT;
        duckFDef.filter.maskBits = DuckyDuck.DEFAULT_BIT | DuckyDuck.BRICK_BIT | DuckyDuck.FRUIT_BIT | DuckyDuck.BRICKHIT_BIT | DuckyDuck.GROUND;

        duckFDef.shape = shape;
        dBody.createFixture(duckFDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-4 / DuckyDuck.PIXEL_PER_METER, 13 / DuckyDuck.PIXEL_PER_METER), new Vector2(4 / DuckyDuck.PIXEL_PER_METER, 13 / DuckyDuck.PIXEL_PER_METER));
        duckFDef.shape = head;
        duckFDef.isSensor = true;

        dBody.createFixture(duckFDef).setUserData("head");

        EdgeShape feet = new EdgeShape();
        feet.set(new Vector2(-4 / DuckyDuck.PIXEL_PER_METER, -13 / DuckyDuck.PIXEL_PER_METER), new Vector2(4 / DuckyDuck.PIXEL_PER_METER, -13 / DuckyDuck.PIXEL_PER_METER));
        duckFDef.shape = feet;
        duckFDef.isSensor = true;

        dBody.createFixture(duckFDef).setUserData("feet");
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
        if (this.dBody.getLinearVelocity().y == 0) {
            jumps = 0;
        }
    }

    public boolean canDoubleJump() {
        if (jumps == maxJumps) {
            return false;
        }
        jumps++;
        return true;

    }
}
