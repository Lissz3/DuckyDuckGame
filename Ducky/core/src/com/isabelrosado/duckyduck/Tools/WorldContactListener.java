package com.isabelrosado.duckyduck.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.isabelrosado.duckyduck.DuckyDuck;
import com.isabelrosado.duckyduck.Sprites.Duck;
import com.isabelrosado.duckyduck.Sprites.Enemies.Enemy;
import com.isabelrosado.duckyduck.Sprites.Items.Fruit;
import com.isabelrosado.duckyduck.Sprites.TileObjects.CheckPoint;
import com.isabelrosado.duckyduck.Sprites.TileObjects.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    private Fixture fixA;
    private Fixture fixB;
    private Fixture head;
    private Fixture object;

    @Override
    public void beginContact(Contact contact) {
        fixA = contact.getFixtureA();
        fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
            head = fixA.getUserData() == "head" ? fixA : fixB;
            object = head == fixA ? fixB : fixA;
            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        switch (cDef) {
            case DuckyDuck.ENEMY_HEAD_BIT | DuckyDuck.DUCK_BIT:
                if (fixA.getFilterData().categoryBits == DuckyDuck.ENEMY_HEAD_BIT) {
                    ((Enemy) fixA.getUserData()).hitOnHead();
                } else {
                    ((Enemy) fixB.getUserData()).hitOnHead();
                }
                break;
            case DuckyDuck.DUCK_BIT | DuckyDuck.ENEMY_BIT:
                object = fixA.getFilterData().categoryBits == DuckyDuck.DUCK_BIT ? fixA : fixB;
                ((Duck) object.getUserData()).onHit();
                break;

            case DuckyDuck.FRUIT_BIT | DuckyDuck.DUCK_BIT:
                object = fixA.getFilterData().categoryBits == DuckyDuck.FRUIT_BIT ? fixA : fixB;
                ((Fruit) object.getUserData()).use();
                break;

            case DuckyDuck.DUCK_BIT | DuckyDuck.CHECKPOINT_BIT:
                object = fixA.getFilterData().categoryBits == DuckyDuck.CHECKPOINT_BIT ? fixA : fixB;
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
