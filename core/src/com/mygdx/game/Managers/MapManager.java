package com.mygdx.game.Managers;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Map.Map;
import com.mygdx.game.Map.MapFactory;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.TiledObjectUtil;

import static com.mygdx.game.Managers.PlayerManager.player;
import static com.mygdx.game.utils.Constants.PPM;

public class MapManager {
    private MyGdxGame context;
    private TiledMap map;
    private OrthogonalTiledMapRenderer tmr;

    private Texture[] test_map_textures;
    private MapObjects collision;

    public MapManager(MapFactory mapFactory, World world) {
        Map createdMap = mapFactory.createMap();
        this.map = createdMap.getTiledMap();
        this.test_map_textures= createdMap.getTextures();
        this.collision= createdMap.getCollison();
        if (this.map == null) {
            System.out.println("MapManager: Error - TiledMap is null.");
        } else {
            System.out.println("MapManager: TiledMap loaded successfully.");
        }
        if (this.test_map_textures == null) {
            System.out.println("MapManager: Error - TiledMap is null.");
        } else {
            System.out.println("MapManager: TiledMap loaded successfully.");
        }
        this.tmr = new OrthogonalTiledMapRenderer(map);

        TiledObjectUtil.parseTiledObjectLayer(world,collision);
     //   this.rayHandler.setAmbientLight(0.002f);
        // Initialize other necessary components here
    }

    public void drawLayerTextures(SpriteBatch batch, TextureRegion textregion) {
        for (int i = 0; i < test_map_textures.length; i++) {
            Texture texturez = test_map_textures[i];
            if (i == 4) {
                batch.draw(textregion, player.getPosition().x * PPM - ((float) textregion.getRegionWidth() / 2), player.getPosition().y * PPM - ((float) textregion.getRegionHeight() / 8));
            }
            batch.draw(texturez, 0, 0);
        }
    }

    public void renderMap(OrthographicCamera camera) {
        if (tmr != null && map != null) { // Check if tmr and map are not null
            tmr.setView(camera);
            tmr.render();
        } else {
            System.out.println("MapManager: Error - tmr or map is null.");
        }
    }

    public void dispose() {
        tmr.dispose();
        map.dispose();

    }
}
