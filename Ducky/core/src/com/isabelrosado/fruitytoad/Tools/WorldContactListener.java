package com.isabelrosado.fruitytoad.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.*;
import com.isabelrosado.fruitytoad.FruityToad;
import com.isabelrosado.fruitytoad.Sprites.Enemies.Enemy;
import com.isabelrosado.fruitytoad.Sprites.Frog;
import com.isabelrosado.fruitytoad.Sprites.Items.Fruit;
import com.isabelrosado.fruitytoad.Sprites.TileObjects.CheckPoint;
import com.isabelrosado.fruitytoad.Sprites.TileObjects.InteractiveTileObject;

/**
 * <p>
 * Class to handle collision between objects, extends {@link ContactListener}.
 * </p>
 * @author Isabel Rosado
 */
public class WorldContactListener implements ContactListener {
    /**
     *  First fixture identified.
     */
    private Fixture fixA;

    /**
     *  Second fixture identified.
     */
    private Fixture fixB;

    /**
     *  Head fixture.
     */
    private Fixture head;

    /**
     *  Generic object fixture.
     */
    private Fixture object;


    /**
     *
     * @param contact
     */
    @Override
    public void beginContact(Contact contact) {
        fixA = contact.getFixtureA();
        fixB = contact.getFixtureB();

        //gets the result of the operation between two category bits to know which objects are colliding
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        //gets if the main character head has collided with something
        if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
            head = fixA.getUserData() == "head" ? fixA : fixB;
            object = head == fixA ? fixB : fixA;
            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        //depending on the result of the bitwise operation
        switch (cDef) {
            case FruityToad.ENEMY_HEAD_BIT | FruityToad.FROG_BIT:
                if (fixA.getFilterData().categoryBits == FruityToad.ENEMY_HEAD_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead();
                } else {
                    ((Enemy) fixB.getUserData()).hitOnHead();
                }
                break;
            case FruityToad.FROG_BIT | FruityToad.ENEMY_BIT:
                object = fixA.getFilterData().categoryBits == FruityToad.FROG_BIT ? fixA : fixB;
                ((Frog) object.getUserData()).onHit();
                Gdx.input.vibrate(1000);
                break;

            case FruityToad.FRUIT_BIT | FruityToad.FROG_BIT:
                object = fixA.getFilterData().categoryBits == FruityToad.FRUIT_BIT ? fixA : fixB;
                ((Fruit) object.getUserData()).use();
                break;

            case FruityToad.FROG_BIT | FruityToad.CHECKPOINT_BIT:
                object = fixA.getFilterData().categoryBits == FruityToad.CHECKPOINT_BIT ? fixA : fixB;
                ((CheckPoint) object.getUserData()).use();
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
