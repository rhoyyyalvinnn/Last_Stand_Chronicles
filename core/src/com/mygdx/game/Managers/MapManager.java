package com.mygdx.game.Managers;

import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.TiledObjectUtil;

import static com.mygdx.game.Managers.PlayerManager.player;
import static com.mygdx.game.utils.Constants.PPM;


public class MapManager {
    private MyGdxGame context;
    private TiledMap map;
    Texture[] test_map_textures;
    public OrthogonalTiledMapRenderer tmr;
    private RayHandler rayHandler;


    public MapManager(World world){

       //map = new TmxMapLoader().load("assets/map_textures/dungeonmap.tmx");
        map = new TmxMapLoader().load("assets/map_textures/map2/map_2_notdoneyet.tmx");
        tmr = new OrthogonalTiledMapRenderer(map);


        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("rectangleObs").getObjects());
       //TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("boundaries").getObjects());

//        test_map_textures = new Texture[]{
//                new Texture("assets/map_textures/map1/terrainwithwater.png"),
//                new Texture("assets/map_textures/map1/obsbehindwall.png"),
//                new Texture("assets/map_textures/map1/wallfinal.png"),
//                new Texture("assets/map_textures/map1/obs.png"),
//        };
        test_map_textures = new Texture[]{
                new Texture("assets/map_textures/map2/ground2.png"),
                new Texture("assets/map_textures/map2/obsafterwall.png"),
                new Texture("assets/map_textures/map2/wall2.png"),
                new Texture("assets/map_textures/map2/obs22.png"),
        };



       rayHandler=new RayHandler(world);
       rayHandler.setAmbientLight(.2f);
    }

    public void drawLayerTextures(SpriteBatch batch, TextureRegion textregion) {

      //  shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
      //  shapeRenderer.setColor(0, 0, 0, 1);
       // shapeRenderer.rect(0, PlayerManager.WORLD_HEIGHT - 100, PlayerManager.WORLD_WIDTH, 100); // Adjust height and width as needed
      //  shapeRenderer.end();
       // rayHandler.updateAndRender();
        for (int i = 0; i < test_map_textures.length; i++) {
            Texture texturez = test_map_textures[i];
            if (i == 4) { // Check if it's the layer for the player
                batch.draw(textregion, player.getPosition().x * PPM - ((float) textregion.getRegionWidth() / 2), player.getPosition().y * PPM - ((float) textregion.getRegionHeight() / 8));
            }
            batch.draw(texturez, 0, 0);
        }

    }

    public void dispose() {
        tmr.dispose();
    }

    public TiledMap getMap() {
        return map;
    }





}
