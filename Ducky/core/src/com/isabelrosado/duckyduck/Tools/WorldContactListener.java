package com.isabelrosado.duckyduck.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.isabelrosado.duckyduck.Sprites.Brick;
import com.isabelrosado.duckyduck.Sprites.Duck;
import com.isabelrosado.duckyduck.Sprites.Ground;
import com.isabelrosado.duckyduck.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    private Fixture fixA;
    private Fixture fixB;

    private Fixture feet;

    private Fixture head;

    private Fixture object;

    @Override
    public void beginContact(Contact contact) {
        fixA = contact.getFixtureA();
        fixB = contact.getFixtureB();

        if (fixA.getUserData() == "head" || fixB.getUserData() == "head") {
            head = fixA.getUserData() == "head" ? fixA : fixB;
            object = head == fixA ? fixB : fixA;
            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
            }
        }

        if (fixA.getUserData() == "feet" || fixB.getUserData() == "feet") {
            feet = fixA.getUserData() == "feet" ? fixA : fixB;
            object = feet == fixA ? fixB : fixA;
            if (object.getUserData() != null) {
                switch (object.getUserData().getClass().equals(Ground.class) ? "Ground" : object.getUserData().getClass().equals(Brick.class) ? "Brick" : "BrickHit"){
                    case "Ground":
                    case "Brick":
                    case "BrickHit":
//                        Duck.setCanDoubleJump(true);
                        break;
                }
            }
        }

        if (fixA.getUserData() != null && fixB.getUserData() != null) {
            Gdx.app.log("FixA:", fixA.getUserData().toString());
            Gdx.app.log("FixB:", fixB.getUserData().toString());
        }
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("End", "Contact");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
