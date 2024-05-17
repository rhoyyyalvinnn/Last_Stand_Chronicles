package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.HUD;
import com.mygdx.game.Managers.Bullet;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Managers.MapManager;
import com.mygdx.game.Managers.PlayerManager;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import java.util.ArrayList;

import static com.mygdx.game.utils.Constants.PPM;

public class GameScreen extends ScreenAdapter {
    private final MyGdxGame context;
    private final float SCALE = 2.5f;

    private float elapsedTime = 0f;
    private Box2DDebugRenderer b2dr;

    private OrthographicCamera camera;

    private World world;
    private PlayerManager player;

    private SpriteBatch batch;

    private MapManager map;
    private RayHandler rayHandler;
    private PointLight suga;

    private Texture bulletTexture;
    private ArrayList<Bullet> bulletManager = new ArrayList<>();
    private final float bulletSpeed = 500;

    private HUD hud;

    public GameScreen(MyGdxGame context) {
        this.context = context;
    }

    @Override
    public void show() {
        bulletTexture = new Texture("tile000.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);

        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();
        player = new PlayerManager(world);
        player.run();
        batch = player.getBatch();

        map = new MapManager(world);

        // Light setup
        rayHandler = new RayHandler(world);
        suga = new PointLight(rayHandler, 50, Color.GRAY, 4, 0, 0);
        suga.attachToBody(PlayerManager.player, .2f, 0.3f);

        // Initialize HUD
        hud = new HUD(100); // Max health is 100
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(58 / 255f, 58 / 255f, 80 / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        elapsedTime += delta;

        Animation<TextureRegion> currentAnimation = player.determineCurrentAnimation();
        TextureRegion currentFrame = currentAnimation.getKeyFrame(elapsedTime, true);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        map.drawLayerTextures(batch, currentFrame);
        batch.draw(currentFrame, player.getPosition().x * PPM - ((float) currentFrame.getRegionWidth() / 2), player.getPosition().y * PPM - ((float) currentFrame.getRegionHeight() / 8));
        batch.end();

        // Render health bar
//        hud.update(delta, player.getHealth()); // Update health based on player's current health
        hud.draw();

        // Bullet render
        rayHandler.render();
        batch.begin();
        for (Bullet bullet : bulletManager) {
            bullet.Update(delta);
            if (bullet.bulletLocation.x > -50 && bullet.bulletLocation.x < Gdx.graphics.getWidth() + 50 && bullet.bulletLocation.y > -50 && bullet.bulletLocation.y < Gdx.graphics.getHeight() + 50) {
                batch.draw(bulletTexture, bullet.bulletLocation.x * PPM, bullet.bulletLocation.y * PPM);
            }
        }
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / SCALE, height / SCALE);
        hud.resize(width, height); // Update HUD viewport
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        world.dispose();
        batch.dispose();
        b2dr.dispose();
        map.dispose();
        bulletTexture.dispose();
        hud.dispose(); // Dispose HUD resources
    }

    private void cameraUpdate(float delta) {
        Vector2 position = player.getPosition();
        camera.position.set(position.x * PPM, position.y * PPM, 0);
        camera.update();
    }

    private void update(float delta) {
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
//
//        // Handle health logic (e.g., decrease health when hit)
//        // For demonstration purposes, reducing health on key press
//        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
//            player.decreaseHealth(10); // Decrease player health by 10
//        }

        Vector2 bulletStartPosition = player.getPosition().cpy();

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            Bullet myBullet = new Bullet(bulletStartPosition, new Vector2(0, bulletSpeed));
            bulletManager.add(myBullet);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            Bullet myBullet = new Bullet(bulletStartPosition, new Vector2(0, -bulletSpeed));
            bulletManager.add(myBullet);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            Bullet myBullet = new Bullet(bulletStartPosition, new Vector2(-bulletSpeed, 0));
            bulletManager.add(myBullet);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            Bullet myBullet = new Bullet(bulletStartPosition, new Vector2(bulletSpeed, 0));
            bulletManager.add(myBullet);
        }

        bulletManager.removeIf(bullet -> bullet.bulletLocation.x < -50 || bullet.bulletLocation.x > Gdx.graphics.getWidth() + 50 || bullet.bulletLocation.y < -50 || bullet.bulletLocation.y > Gdx.graphics.getHeight() + 50);
    }
}
