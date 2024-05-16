package com.mygdx.game.Managers;

import com.badlogic.gdx.math.Vector2;

public class Bullet{
    public Vector2 bulletLocation;
    public Vector2 bulletVelocity;

    public Bullet(Vector2 startLocation, Vector2 targetVelocity) {
        this.bulletLocation = new Vector2(startLocation);
        this.bulletVelocity = targetVelocity.nor().scl(10); // Adjust speed as needed
    }

    public void Update() {
        bulletLocation.x += bulletVelocity.x ;
        bulletLocation.y += bulletVelocity.y;
    }
}
