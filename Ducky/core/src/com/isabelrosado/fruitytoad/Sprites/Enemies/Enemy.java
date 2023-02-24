package com.isabelrosado.fruitytoad.Sprites.Enemies;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Screens.PlayScreen;
/**
 * <p>
 * Abstract class implementing {@link Sprite}.
 * </p>
 * <p>
 * Generic class to create different enemies.
 * </p>
 * @author Isabel Rosado
 */
public abstract class Enemy extends Sprite {
    /**
     * Manager for physic entities and dynamic simulation.
     */
    protected World world;

    /**
     * Main screen of the game.
     *
     * @see FruityToad
     */
    protected FruityToad game;

    /**
     * Game screen
     * @see PlayScreen
     */
    protected PlayScreen screen;

    /**
     * The rigid body of the enemy
     */
    public Body b2body;

    /**
     * Movement velocity of the enemy
     */
    public Vector2 velocity;

    /**
     * Initialize the values to the values given to the constructor.
     * <p>Sets the position of the body and the movement velocity.</p>
     * @param game main screen
     * @param screen actual screen
     * @param x position of the body in the world for <b>x-axis</b>
     * @param y position of the body in the world for <b>y-axis</b>
     * @param velX velocity for the <b>x-axis</b>
     * @param velY velocity for the <b>y-axis</b>
     */
    public Enemy(FruityToad game, PlayScreen screen, float x, float y, float velX, float velY){
        this.world = screen.getWorld();
        this.screen = screen;
        this.game = game;
        velocity = new Vector2(velX, velY);
        setPosition(x, y);
        defineEnemy();
    }

    /**
     * Called to define the specific enemy.
     */
    protected abstract void defineEnemy();

    /**
     * Called when the sprite should update itslef.
     *
     * @param dt The time in seconds since the last update.
     */
    protected abstract void update(float dt);

    /**
     * Called when the enemy head collision makes an interaction
     */
    public abstract void hitOnHead();

    /**
     * Called when the direction needs to be changed
     */
    public void reverseVelocity(boolean x, boolean y){
        if (x){
            velocity.x = -velocity.x;
        }
        if (y) {
            velocity.y = -velocity.y;
        }
    }
}
