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

    public Bullet( Vector2 startLocation, Vector2 targetLocation, Vector2 velocity) {

        this.bulletLocation = new Vector2(startLocation);
        this.bulletTargetLocation=new Vector2(targetLocation);
        this.bulletVelocity = velocity.nor().scl(10); // Adjust speed as needed



        // Create Bullet Fixture
       // TextureAtlas bulletAtlas = new TextureAtlas(Gdx.files.internal("bullet.atlas"));
       // bulletAnimation = new Animation<>(0.1f, bulletAtlas.findRegions("tile"), Animation.PlayMode.LOOP);
    }

    public void Update(float delta) {
        bulletLocation.x += bulletVelocity.x * delta;
        bulletLocation.y += bulletVelocity.y * delta;
    }
    public TextureRegion getCurrentFrame() {
        return bulletAnimation.getKeyFrame(animationTime);
    }

    public Body getBody() {
        return body;
    }
    //uncommit
}