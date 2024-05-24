package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapOne extends Map{
    @Override
    public void loadMap() {
        try {
        tiledMap = new TmxMapLoader().load("assets/map_textures/dungeonmap.tmx");
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
          if(tiledMap == null){
              System.out.println("null value");
          }
        textures = new Texture[]{
                new Texture("assets/map_textures/terrain.png"),
                new Texture("assets/map_textures/wall.png"),
                new Texture("assets/map_textures/design.png")
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
        return tiledMap = new TmxMapLoader().load("assets/map_textures/dungeonmap.tmx");
    }

    @Override
    public Texture[] getTextures() {
        textures = new Texture[]{
                new Texture("assets/map_textures/terrain.png"),
                new Texture("assets/map_textures/wall.png"),
                new Texture("assets/map_textures/design.png")
        };
        return textures;
    }
}
