package com.isabelrosado.duckyduck.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Screens.PlayScreen;
import com.isabelrosado.duckyduck.Tools.Animator;

public class FatBird extends Enemy {
    //40x48
    private float stateTime;
    Animator animator;
    private Texture animationTexture;
    private Animation<TextureRegion> fbFall;
    private Animation<TextureRegion> fbGround;
    private Animation<TextureRegion> fbHit;
    private Animation<TextureRegion> fbIdle;

    public boolean setToDestroy;
    public boolean destroyed;

    private final int timeToDissapear = 40;
    private int currentFrame;
    private BodyDef fatBirdBdef;

    public FatBird(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        animationTexture = new Texture("FatBird.png");
        animator = new Animator(animationTexture, 40, 48);
        setToDestroy = false;
        destroyed = false;

        fbIdle = animator.getAnimation(8, 0,0);
        fbHit = animator.getAnimation(5, 320, 0);
        fbFall = animator.getAnimation(4, 520, 0);
        fbGround = animator.getAnimation(4, 680, 0);

        stateTime = 0;
        currentFrame = 0;
    }



    @Override
    protected void defineEnemy() {
        fatBirdBdef = new BodyDef();
        fatBirdBdef.position.set(getX(), getY());
        fatBirdBdef.type = BodyDef.BodyType.KinematicBody;
        b2body = world.createBody(fatBirdBdef);

        FixtureDef fbFDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / DuckyDuck.PIXEL_PER_METER);
        fbFDef.filter.categoryBits = DuckyDuck.ENEMY_BIT;
        fbFDef.filter.maskBits = DuckyDuck.DEFAULT_BIT | DuckyDuck.BRICK_BIT | DuckyDuck.ENEMY_BIT | DuckyDuck.BRICKHIT_BIT | DuckyDuck.GROUND_BIT | DuckyDuck.DUCK_BIT;

        fbFDef.shape = shape;
        b2body.createFixture(fbFDef);
        setBounds(getX(), getY(), 40 / DuckyDuck.PIXEL_PER_METER, 48 / DuckyDuck.PIXEL_PER_METER);

        PolygonShape head = new PolygonShape();
        Vector2[] v = new Vector2[4];
        v[0] = new Vector2(-10, 13).scl(1 / DuckyDuck.PIXEL_PER_METER);
        v[1] = new Vector2(10, 13).scl(1 / DuckyDuck.PIXEL_PER_METER);
        v[2] = new Vector2(-8, 9).scl(1 / DuckyDuck.PIXEL_PER_METER);
        v[3] = new Vector2(8, 9).scl(1 / DuckyDuck.PIXEL_PER_METER);
        head.set(v);

        fbFDef.shape = head;
        fbFDef.restitution = 0.5f;
        fbFDef.filter.categoryBits = DuckyDuck.ENEMY_HEAD_BIT;
        b2body.createFixture(fbFDef).setUserData(this);
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        fbBehavior();
        if (setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            screen.fatBird.rotate90(true);
            setRegion(fbHit.getKeyFrame(dt));
        } else if (!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
            setRegion(fbIdle.getKeyFrame(stateTime, true));
        } else if (currentFrame == timeToDissapear) {
//            fatBirdBdef.type = BodyDef.BodyType.DynamicBody;
            screen.fatBird.setAlpha(0);
        } else if (destroyed)  {
            currentFrame++;
        }
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }

    public void fbBehavior() {
        if ((screen.duck.dBody.getPosition().x - screen.duck.getWidth() / 2) != (screen.fatBird.b2body.getPosition().x - screen.fatBird.getWidth() / 2)) {
            this.b2body.applyLinearImpulse(new Vector2(0, -4f), this.b2body.getWorldCenter(), true);
        }
    }
}
