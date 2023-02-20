package com.isabelrosado.fruitytoad.Sprites.Enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;

public abstract class Enemy extends Sprite {
    protected World world;

    protected FruityToad game;
    protected PlayScreen screen;

    public Body b2body;

    public Vector2 velocity;

    public Enemy(FruityToad game, PlayScreen screen, float x, float y, float velX, float velY){
        this.world = screen.getWorld();
        this.screen = screen;
        this.game = game;
        velocity = new Vector2(velX, velY);
        setPosition(x, y);
        defineEnemy();
    }

    protected abstract void defineEnemy();

    protected abstract void update(float dt);

    public abstract void hitOnHead();

    public void reverseVelocity(boolean x, boolean y){
        if (x){
            velocity.x = -velocity.x;
        }
        if (y) {
            velocity.y = -velocity.y;
        }
    }
}
