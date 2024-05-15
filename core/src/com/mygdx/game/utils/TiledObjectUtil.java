package com.mygdx.game.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
public class TiledObjectUtil {

    public static void parseTiledObjectLayer(World world, MapObjects objects){
        for(MapObject object : objects){
            Shape shape;

            if(object instanceof PolylineMapObject) {
                shape = ShapeFactory.createPolyline((PolylineMapObject) object);
            } else if (object instanceof RectangleMapObject) {
                shape = ShapeFactory.createRectangle((RectangleMapObject) object);
            } else{
                continue;
            }

            Body body;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;

            body = world.createBody(bodyDef);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }

}
