package com.isabelrosado.fruitytoad.Sprites.Items;

import com.badlogic.gdx.graphics.g2d.Batch;
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
 * Generic class to create different Items.
 * </p>
 * @author Isabel Rosado
 */
public abstract class Item extends Sprite {
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
     * Manager for physic entities and dynamic simulation.
     */
    protected World world;

    /**
     * Sets if the item should be destroyed (true) or not (false).
     */
    protected boolean toDestroy;

    /**
     * Looks if the item has been destroyed (true) or not (false).
     */
    protected boolean destroyed;

    /**
     * The rigid body of the item
     */
    protected Body body;

    /**
     * Initialize the values to the values given to the constructor.
     * <p>Sets the position and the bounds of the sprite to be drawn and defines the specific item.</p>
     * @param game main screen
     * @param screen actual screen
     * @param x position of the body in the world for <b>x-axis</b>
     * @param y position of the body in the world for <b>y-axis</b>
     */
    public Item(FruityToad game, PlayScreen screen, float x, float y) {
        this.game = game;
        this.screen = screen;
        this.world = screen.getWorld();
        setPosition(x, y);
        setBounds(getX(), getY(), 32 / FruityToad.PIXEL_PER_METER, 32 / FruityToad.PIXEL_PER_METER);
        defineItem();
        toDestroy = false;
        destroyed = false;
    }

    /**
     * Called to define the specific item.
     */
    public abstract void defineItem();

    /**
     * Called when the item is used.
     */
    public abstract void use();

    public void destroy() {
        toDestroy = true;
    }

    /**
     * Called when the sprite should update itslef.
     *
     * @param dt The time in seconds since the last update.
     */
    public void update(float dt) {
        if (toDestroy && !destroyed) {
            world.destroyBody(body);
            destroyed = true;
        }
    }

    /**
     * Draws the specified sprite animation
     * @param batch 2D texture (region) to draw
     */
    public void draw(Batch batch) {
        super.draw(batch);
    }

}
