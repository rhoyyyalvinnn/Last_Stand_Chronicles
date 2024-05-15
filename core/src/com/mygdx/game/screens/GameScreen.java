package com.mygdx.game.screens;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Managers.MapManager;
import com.mygdx.game.Managers.PlayerManager;
import com.mygdx.game.MyGdxGame;

import static com.mygdx.game.utils.Constants.PPM;

public class GameScreen extends ScreenAdapter {
    private final MyGdxGame context;
    private TextureAtlas atlas;
    private final float SCALE = 2.5f;

    private float elapsedTime = 0f;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera camera;

    private World world;
    private PlayerManager player;

    private SpriteBatch batch;
    private Texture tex;
    private AssetManager assetManager;

    private MapManager map;
    private RayHandler rayHandler;
    private PointLight suga;

    public GameScreen(MyGdxGame context) {
        this.context = context;
    }


    @Override
    public void render (float delta) {
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClearColor(58 / 255f, 58 / 255f, 80 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        elapsedTime += Gdx.graphics.getDeltaTime();

        Animation<TextureRegion> currentAnimation = player.determineCurrentAnimation();
        TextureRegion currentFrame = currentAnimation.getKeyFrame(elapsedTime, true);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.drawLayerTextures(batch, currentFrame);
        batch.draw(currentFrame, player.getPosition().x * PPM - ((float) currentFrame.getRegionWidth() / 2), player.getPosition().y * PPM - ((float) currentFrame.getRegionHeight() / 8));
        // stove render
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            context.setScreen(ScreenType.LOADING);
        }
        rayHandler.render();
    }

    @Override

    public void resize(int width, int height) {
        camera.setToOrtho(false, width / SCALE, height / SCALE);
    }

    @Override
    public void show () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / SCALE, h / SCALE);
        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();
        player = new PlayerManager(world);
        player.run();batch = player.getBatch();

        map = new MapManager(world);
        //light
        rayHandler = new RayHandler(world);
        suga = new PointLight(rayHandler,50, Color.GRAY,4,0,0);
        suga.attachToBody(PlayerManager.player,.2f,0.3f);

    }

    @Override
    public void hide () {
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
        world.dispose();
        batch.dispose();
        b2dr.dispose();
        map.dispose();
    }
    public void cameraUpdate(float delta) {
        Vector2 position = player.getPosition();

        camera.position.set(position.x * PPM, position.y * PPM, 0);

        camera.update();
    }
    public void update(float delta) {
        world.step(1 / 60f, 6, 2);

        Vector2 playerPosition = player.getPosition();
        suga.setPosition(playerPosition.x * PPM, playerPosition.y * PPM);

        rayHandler.update();
        rayHandler.setAmbientLight(.2f);

        player.inputUpdate(delta);
        cameraUpdate(delta);
        map.tmr.setView(camera);
        batch.setProjectionMatrix(camera.combined);
        rayHandler.setCombinedMatrix(camera.combined.cpy().scl(PPM));

    }
}
