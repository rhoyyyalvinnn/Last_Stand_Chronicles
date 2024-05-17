package com.mygdx.game.Managers;

import com.badlogic.gdx.math.Vector2;

public class Bullet {
    public Vector2 bulletLocation;
    public Vector2 bulletVelocity;

    public Bullet(Vector2 startLocation, Vector2 velocity) {
        this.bulletLocation = new Vector2(startLocation);
        this.bulletVelocity = velocity.nor().scl(10); // Adjust speed as needed
    }

    public void Update(float delta) {
        bulletLocation.x += bulletVelocity.x * delta;
        bulletLocation.y += bulletVelocity.y * delta;
    }
}
