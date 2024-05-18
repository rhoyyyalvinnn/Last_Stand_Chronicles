package com.mygdx.game.Managers;

import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;

public class BulletContactListener implements ContactListener {
    private ArrayList<Body> bodiesToRemove;

    public BulletContactListener() {
        bodiesToRemove = new ArrayList<>();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (isBulletCollision(fixtureA, fixtureB)) {
            Body bulletBody = fixtureA.getBody().getUserData() instanceof Bullet ? fixtureA.getBody() : fixtureB.getBody();
            bodiesToRemove.add(bulletBody);
        } else if (isCollisionLayerContact(fixtureA, fixtureB)) {
            Body bulletBody = fixtureA.getBody().getUserData() instanceof Bullet ? fixtureA.getBody() : fixtureB.getBody();
            bodiesToRemove.add(bulletBody);
        }


    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    public ArrayList<Body> getBodiesToRemove() {
        return bodiesToRemove;
    }

    public void clear() {
        bodiesToRemove.clear();
    }
    private boolean isBulletCollision(Fixture fixtureA, Fixture fixtureB) {
        return (fixtureA.getBody().getUserData() instanceof Bullet || fixtureB.getBody().getUserData() instanceof Bullet);
    }
    private boolean isCollisionLayerContact(Fixture fixtureA, Fixture fixtureB) {
        return "CollisionLayer".equals(fixtureA.getBody().getUserData()) ||
                "CollisionLayer".equals(fixtureB.getBody().getUserData());
    }
    //pa kuha na pero di ma remove
}
