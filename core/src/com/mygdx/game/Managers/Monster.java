package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.screens.GameScreen;

import static com.mygdx.game.utils.Constants.PPM;

public class Monster extends EnemyManager{
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private TextureAtlas atlas;

    public Monster(GameScreen screen, MapManager map, float x, float y) {
        super(screen, map, x, y);
        atlas = new TextureAtlas(Gdx.files.internal("assets/enemies/Spirit.atlas"));
        frames = new Array<TextureRegion>();



        for(int i = 0; i < 2; i++){
            frames.add(new TextureRegion())
        }
    }

    @Override
    protected void defineEnemy() {
        Body pBody;
        BodyDef def = new BodyDef();

//        if (isStatic) {
//            def.type = BodyDef.BodyType.DynamicBody;
//
//        } else {
//            def.type = BodyDef.BodyType.StaticBody;
//        }

        // def.type = BodyDef.BodyType.DynamicBody;
//        def.position.set(x / PPM, y / PPM); -- real shii test rana ang nasa ubos
        def.position.set(32 / PPM, 32 / PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        FixtureDef fdef = new FixtureDef();
        CircleShape Cshape = new CircleShape();

        PolygonShape shape = new PolygonShape();
        Cshape.setRadius(6 / PPM);
//        shape.setAsBox(32/ 2f / PPM, 32 / 2f / PPM);
        fdef.shape = Cshape;
        pBody.createFixture(fdef);
//        shape.dispose();

//        return pBody;
    }
}
