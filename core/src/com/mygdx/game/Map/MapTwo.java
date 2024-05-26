package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapTwo extends Map {
    @Override
    public void loadMap() {
        try {
            tiledMap = new TmxMapLoader().load("assets/map_textures/map2/map_2_notdoneyet.tmx");
            renderer = new OrthogonalTiledMapRenderer(tiledMap);
            if (tiledMap == null) {
                System.out.println("null value");
            }
            textures = new Texture[]{
                    new Texture("assets/map_textures/map2/ground2.png"),
                    new Texture("assets/map_textures/map2/obsafterwall.png"),
                    new Texture("assets/map_textures/map2/wall2.png"),
                    new Texture("assets/map_textures/map2/obs22.png")
            };
        } catch (Exception e) {
            System.out.println("Error loading MapOne: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        renderer.dispose();
        for (Texture texture : textures) {
            texture.dispose();
        }
    }

    @Override
    public TiledMap getTiledMap() {
        return tiledMap = new TmxMapLoader().load("assets/map_textures/map2/map_2_notdoneyet.tmx");
    }

    @Override
    public Texture[] getTextures() {
        textures = new Texture[]{
                new Texture("assets/map_textures/map2/ground2.png"),
                new Texture("assets/map_textures/map2/obsafterwall.png"),
                new Texture("assets/map_textures/map2/wall2.png"),
                new Texture("assets/map_textures/map2/obs22.png")
        };
        return textures;
    }

    @Override
    public MapObjects getCollison() {
        try {

            collison = getTiledMap().getLayers().get("rectangleObs").getObjects();
            System.out.println(getTiledMap().getLayers().get("rectangleObs").getObjects());
            return collison;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
