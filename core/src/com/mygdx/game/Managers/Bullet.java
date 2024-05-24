package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;

import static com.mygdx.game.utils.Constants.PPM;

public class Bullet {
    public Vector2 bulletLocation;
    public Vector2 bulletVelocity;
    public Vector2 bulletTargetLocation;

    private TextureAtlas bulletAtlas;
    private World world;
    private Body body;
    private Animation<TextureRegion> bulletAnimation;
    private float animationTime = 0f;

    private float maxDistance;
    private float traveledDistance = 0f;
    private boolean active = true;

    public Bullet( Vector2 startLocation, Vector2 targetLocation, Vector2 velocity,float maxDistance) {

        this.bulletLocation = new Vector2(startLocation);
        this.bulletTargetLocation=new Vector2(targetLocation);
        this.bulletVelocity = velocity.nor().scl(10); // Adjust speed as needed
        this.maxDistance = maxDistance;


        // Create Bullet Fixture
       // TextureAtlas bulletAtlas = new TextureAtlas(Gdx.files.internal("bullet.atlas"));
       // bulletAnimation = new Animation<>(0.1f, bulletAtlas.findRegions("tile"), Animation.PlayMode.LOOP);
    }

    public void Update(float delta) {
        if (!active) return;

        float distanceThisFrame = bulletVelocity.len() * delta;
        traveledDistance += distanceThisFrame;

        if (traveledDistance > maxDistance) {
            active = false;
            return;
        }

        bulletLocation.x += bulletVelocity.x * delta;
        bulletLocation.y += bulletVelocity.y * delta;
    }
  //  public TextureRegion getCurrentFrame() {
   //     return bulletAnimation.getKeyFrame(animationTime);
  //  }

    public Body getBody() {
        return body;
    }
    //uncommit
}