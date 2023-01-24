package com.isabelrosado.duckyduck.Sprites;

import com.badlogic.gdx.Gdx;
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

public class Duck extends Sprite {
    public World world;
    public Body dBody;

    public enum State {
        FALLING,
        JUMPING,
        STANDING,
        RUNNING
    }

    public State currentState;
    public State previousState;

    private Texture animationTexture;

    private TextureRegion[] frames;
    private Animation<TextureRegion> duckIdle;
    private Animation<TextureRegion> duckRun;
    private Animation<TextureRegion> duckDoubleJump;
    private TextureRegion duckFall;
    private TextureRegion duckJump;

    private boolean runningRight;
    private boolean canDoubleJump;
    private boolean touchedGround;

    private float stateTimer;

    public State getState(){
        if (dBody.getLinearVelocity().y > 0){
            return State.JUMPING;
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

    public Duck(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("FrogRun"));
        this.world = world;
        defineDuck();

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;
        canDoubleJump = true;
        touchedGround = true;

        animationTexture = new Texture("Frog.png");

        frames = new TextureRegion[11];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new TextureRegion(getTexture(), (384)+32*i, 0, 32, 32);
        }
        duckIdle = new Animation<TextureRegion>(0.12f, frames);

        frames = new TextureRegion[12];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new TextureRegion(getTexture(), 32*i, 0, 32, 32);
        }
        duckRun = new Animation<TextureRegion>(0.12f, frames);

        frames = new TextureRegion[6];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new TextureRegion(getTexture(), (960)+32*i, 0, 32, 32);
        }
        duckDoubleJump = new Animation<TextureRegion>(0.12f, frames);
        
        duckJump = new TextureRegion(getTexture(), 1152, 0, 32, 32);
        duckFall = new TextureRegion(getTexture(), 1312, 0, 32, 32);

        setBounds(0, 0, 36/DuckyDuck.PIXEL_PER_METER, 36/DuckyDuck.PIXEL_PER_METER);
        setRegion(duckIdle.getKeyFrame(stateTimer));
    }

    public void defineDuck(){
        BodyDef duckBdef = new BodyDef();
        duckBdef.position.set(32 / DuckyDuck.PIXEL_PER_METER, 32 / DuckyDuck.PIXEL_PER_METER);
        duckBdef.type = BodyDef.BodyType.DynamicBody;
        dBody = world.createBody(duckBdef);

        FixtureDef duckFDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / DuckyDuck.PIXEL_PER_METER);
        duckFDef.filter.categoryBits = DuckyDuck.DUCK_BIT;
        duckFDef.filter.maskBits = DuckyDuck.DEFAULT_BIT | DuckyDuck.BRICK_BIT | DuckyDuck.FRUIT_BIT | DuckyDuck.BRICKHIT_BIT;

        duckFDef.shape = shape;
        dBody.createFixture(duckFDef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-4 / DuckyDuck.PIXEL_PER_METER, 13 / DuckyDuck.PIXEL_PER_METER), new Vector2(4 / DuckyDuck.PIXEL_PER_METER, 13 / DuckyDuck.PIXEL_PER_METER));
        duckFDef.shape = head;
        duckFDef.isSensor = true;

        dBody.createFixture(duckFDef).setUserData("head");
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch (currentState){
            case JUMPING:
                region = duckJump;
                break;
            case RUNNING:
                region = duckRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = duckFall;
                break;
            case STANDING:
            default:
                region = duckIdle.getKeyFrame(stateTimer, true);
                break;
        }

        if ((dBody.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        } else if ((dBody.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public void update(float dt){
        setPosition(dBody.getPosition().x - getWidth() / 2, (dBody.getPosition().y - getHeight() / 2) + 0.05f);
        setRegion(getFrame(dt));
    }
}
