package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.utils.Constants.PPM;

public class Bullet {
    public Vector2 bulletLocation;
    public Vector2 bulletVelocity;
    public Vector2 bulletTargetLocation;
    private Map<String, Animation<TextureRegion>> bulletanimations = new HashMap<>();
    private TextureAtlas bulletAtlas;
    private World world;
    private Body body;

    public Bullet(World world,Vector2 startLocation,Vector2 targetLocation, Vector2 velocity) {
        this.world = world;
        this.bulletLocation = new Vector2(startLocation);
        this.bulletTargetLocation=new Vector2(targetLocation);
        this.bulletVelocity = velocity.nor().scl(10); // Adjust speed as needed

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startLocation);
        body = world.createBody(bodyDef);

        // Create Bullet Fixture
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / PPM); // Adjust the radius as needed

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);
        body.setLinearVelocity(velocity);
        body.setUserData(this); // Set user data to the bullet

        shape.dispose();
    }

/*    public void Update(float delta) {
        bulletLocation.x += bulletVelocity.x * delta;
        bulletLocation.y += bulletVelocity.y * delta;
    }*/
    public void bulletAnimation(){
        bulletAtlas = new TextureAtlas(Gdx.files.internal("bullet.atlas"));
        bulletanimations = new HashMap<>();
        bulletanimations.put("tile000",new Animation<>(0.1f,bulletAtlas.findRegions("tile000")));
        bulletanimations.put("tile001",new Animation<>(0.1f,bulletAtlas.findRegions("tile001")));
        bulletanimations.put("tile002",new Animation<>(0.1f,bulletAtlas.findRegions("tile002")));
        bulletanimations.put("tile003",new Animation<>(0.1f,bulletAtlas.findRegions("tile003")));
        bulletanimations.put("tile004",new Animation<>(0.1f,bulletAtlas.findRegions("tile004")));
    }
   /* public Animation<TextureRegion> determineCurrentAnimation(){
return bulletanimations.get("tile"+);
    }*/
   public void Update(float delta) {
       bulletLocation.set(body.getPosition());
   }

    public Body getBody() {
        return body;
    }
    //uncommit
}
