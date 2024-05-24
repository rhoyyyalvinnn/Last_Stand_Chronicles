package com.mygdx.game.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public abstract class Map {
    protected TiledMap tiledMap;
    protected OrthogonalTiledMapRenderer renderer;
    protected Texture[] textures;

    public abstract void loadMap();

    public abstract void dispose();

    // Getters
    public TiledMap getTiledMap() { return tiledMap; }
    public OrthogonalTiledMapRenderer getRenderer() { return renderer; }
    public Texture[] getTextures() { return textures; }

}
