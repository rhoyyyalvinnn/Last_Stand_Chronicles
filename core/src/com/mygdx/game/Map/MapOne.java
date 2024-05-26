package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapOne extends Map{
    @Override
    public void loadMap() {
        try {
        tiledMap = new TmxMapLoader().load("assets/map_textures/map1/forExperiment.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
          if(tiledMap == null){
              System.out.println("null value");
          }
        textures = new Texture[]{
                new Texture("assets/map_textures/map1/terrainwithwater.png"),
                new Texture("assets/map_textures/map1/obsbehindwall.png"),
                new Texture("assets/map_textures/map1/wallfinal.png"),
                new Texture("assets/map_textures/map1/obs.png")
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
        return tiledMap = new TmxMapLoader().load("assets/map_textures/map1/forExperiment.tmx");
    }

    @Override
    public Texture[] getTextures() {
        textures = new Texture[]{
                new Texture("assets/map_textures/map1/terrainwithwater.png"),
                new Texture("assets/map_textures/map1/obsbehindwall.png"),
                new Texture("assets/map_textures/map1/wallfinal.png"),
                new Texture("assets/map_textures/map1/obs.png")
        };
        return textures;
    }

    @Override
    public MapObjects getCollison() {

        collison = getTiledMap().getLayers().get("collision_layer").getObjects();
        return collison;
    }
}
