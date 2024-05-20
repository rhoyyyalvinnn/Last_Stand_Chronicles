package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.screens.GameScreen;

import static com.mygdx.game.utils.Constants.PPM;

public class Monster extends Sprite {
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private TextureAtlas atlas;
    private World world;
    private Body body;

    public Monster(GameScreen screen, MapManager map, World world, float x, float y) {
        this.world = world;
        atlas = new TextureAtlas(Gdx.files.internal("assets/enemies/Spirit.atlas"));
        frames = new Array<TextureRegion>();

        for (int i = 1; i <= 2; i++) {
            TextureRegion frame = atlas.findRegion("walk_left");
            if (frame != null) {
                frames.add(frame);
            } else {
                System.out.println("Frame " + i + " not found in the atlas.");
            }
        }
        walkAnimation = new Animation<>(0.4f, frames);
        stateTime = 0;

        defineEnemy(x, y);
        setBounds(0, 0, 32 / PPM, 32 / PPM); // Set the size of the sprite
        System.out.println("monster is spawing");
    }

    public void update(float dt) {
        stateTime += dt;
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
        System.out.println("monster is walking");
    }

    protected void defineEnemy(float x, float y) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;
        body = world.createBody(def);

        FixtureDef fdef = new FixtureDef();
        CircleShape Cshape = new CircleShape();
        Cshape.setRadius(6 / PPM);
        fdef.shape = Cshape;
        body.createFixture(fdef).setUserData(this);
    }
}


//package com.mygdx.game.Managers;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Animation;
//import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.physics.box2d.*;
//import com.badlogic.gdx.utils.Array;
//import com.mygdx.game.screens.GameScreen;
//import org.w3c.dom.Text;
//
//import static com.mygdx.game.Managers.PlayerManager.player;
//import static com.mygdx.game.utils.Constants.PPM;
//
//public class Monster extends EnemyManager{
//    private float stateTime;
//    private Animation walkAnimation;
//    private Array<TextureRegion> frames;
//    private TextureAtlas atlas;
//
//    public Monster(GameScreen screen, MapManager map, float x, float y) {
//        super(screen, map, x, y);
//        atlas = new TextureAtlas(Gdx.files.internal("assets/enemies/Spirit.atlas"));
//        frames = new Array<TextureRegion>();
//
//
//
//        for(int i = 0; i < 2; i++){
//            TextureRegion frame = atlas.findRegion("walk_left" + i);
//            if(frame != null){
//                frames.add(frame);
//            }
//            else{
//                System.out.println("Frame " + i + " not found in the atlas.");
//            }
//        }
//        walkAnimation = new Animation(0.4f, frames);
//        stateTime = 0;
//    }
//
//    public void update(float dt){
//        stateTime += dt;
//        setPosition(player.getPosition().x - getWidth() / 2, player.getPosition().y - getHeight() / 2);
//        setRegion((Texture) walkAnimation.getKeyFrame(stateTime, true));
//    }
//
//    @Override
//    protected void defineEnemy() {
//        Body pBody;
//        BodyDef def = new BodyDef();
//
////        if (isStatic) {
////            def.type = BodyDef.BodyType.DynamicBody;w
////
////        } else {
////            def.type = BodyDef.BodyType.StaticBody;
////        }
//
//        // def.type = BodyDef.BodyType.DynamicBody;
////        def.position.set(x / PPM, y / PPM); -- real shii test rana ang nasa ubos
//        def.position.set(32 / PPM, 32 / PPM);
//        def.fixedRotation = true;
//        pBody = world.createBody(def);
//
//        FixtureDef fdef = new FixtureDef();
//        CircleShape Cshape = new CircleShape();
//
//        PolygonShape shape = new PolygonShape();
//        Cshape.setRadius(6 / PPM);
////        shape.setAsBox(32/ 2f / PPM, 32 / 2f / PPM);
//        fdef.shape = Cshape;
//        pBody.createFixture(fdef);
////        shape.dispose();
//
////        return pBody;
//    }
//}
