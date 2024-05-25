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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.HUD;
import com.mygdx.game.Managers.*;
import com.mygdx.game.Map.MapOneFactory;
import com.mygdx.game.MyGdxGame;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    private MapManager mapManager;
    private RayHandler rayHandler;
    private PointLight suga;

    private Texture bulletTexture;
    private ArrayList<Bullet> bulletManager = new ArrayList<>();
    private final float bulletSpeed = 500;
    private HUD hud;

    private boolean isPaused = false;

    private boolean escapePressed = false;
    private Skin skin;
    private Stage stage;
    private Label paused;

    // asher pax

    // game objects

    private List<Enemies> enemies = new ArrayList<>();
    private Enemies enemy;
    private TextureRegion enemyTextureRegion;
    private TextureAtlas textureAtlas;

    public GameScreen(MyGdxGame context) {
        this.context = context;
    }

    @Override
    public void show() {
        bulletTexture = new Texture("tile000.png");
        stage = new Stage(new ScreenViewport()); //initialize stage
        skin = new Skin(Gdx.files.internal("skinfiles/last_stand2.json")); //initialize skin
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);

        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();
        player = new PlayerManager(world);
        player.run();
        batch = player.getBatch();
        MapOneFactory first = new MapOneFactory();

        mapManager = new MapManager(first, world);

        // Light setup
        rayHandler = new RayHandler(world);
        suga = new PointLight(rayHandler, 50, Color.GRAY, 5, player.getPosition().x, player.getPosition().y);
        suga.attachToBody(PlayerManager.player);

        // Initialize HUD
        hud = new HUD(100); // Max health is 100

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        paused = new Label("Game is paused", skin);
        root.center();
        root.add(paused).width(600).expandX().center().uniform();

        root.row();
        root.add(createButton("Resume")).center().width(600).uniformX().pad(3).row();

        root.row();
        root.add(createButton("Exit")).center().width(600).uniformX().pad(3).row();

        Gdx.input.setInputProcessor(stage);

        textureAtlas = new TextureAtlas("enemies/Spirit.atlas");
        enemyTextureRegion = textureAtlas.findRegion("walk_down");

        // game objects // enemies
        enemy = new Enemies(2, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 4, 10, 10, enemyTextureRegion, player);
    }

    private TextButton createButton(String text) {
        TextButton button = new TextButton(text, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (text.equals("Resume")) {
                    isPaused = false;
                } else if (text.equals("Exit")) {
                    Gdx.app.exit(); // Exit the application
                }
            }
        });
        return button;
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !escapePressed) {
            isPaused = !isPaused;
            escapePressed = true;
        } else if (!Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            escapePressed = false;
        }

        if (isPaused) {
            stage.act(delta);
            stage.draw();
        } else {
            update(delta);

            // Clear the screen
            Gdx.gl.glClearColor(58 / 255f, 58 / 255f, 80 / 255f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            elapsedTime += delta;
            Animation<TextureRegion> currentAnimation = player.determineCurrentAnimation();
            TextureRegion currentFrame = currentAnimation.getKeyFrame(elapsedTime, true);

            // Begin batch rendering
            batch.setProjectionMatrix(camera.combined);
            batch.begin();

            // Draw map and player
            mapManager.renderMap(camera); // Render the map
            enemy.draw(batch); // draw enemy

//            Iterator<Enemies> enemiesIterator = enemies.iterator();

            // Draw the player
            batch.draw(currentFrame, player.getPosition().x * PPM - ((float) currentFrame.getRegionWidth() / 2), player.getPosition().y * PPM - ((float) currentFrame.getRegionHeight() / 8));
            // End batch rendering
            batch.end();

            // Render HUD
         //  hud.update(delta, player.getHealth());
            hud.draw();

            // Render bullets
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
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / SCALE, height / SCALE);
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        world.dispose();
        batch.dispose();
        b2dr.dispose();
        mapManager.dispose();
        bulletTexture.dispose();
        skin.dispose();
        stage.dispose();
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
        enemy.update();
        rayHandler.update();
        rayHandler.setAmbientLight(.000002f);
        player.inputUpdate(delta);
        cameraUpdate(delta);
        mapManager.renderMap(camera);
        batch.setProjectionMatrix(camera.combined);
        rayHandler.setCombinedMatrix(camera.combined.cpy().scl(PPM));
        handleBulletInput();
        bulletManager.removeIf(bullet -> bullet.bulletLocation.x < -50 || bullet.bulletLocation.x > Gdx.graphics.getWidth() + 50 || bullet.bulletLocation.y < -50 || bullet.bulletLocation.y > Gdx.graphics.getHeight() + 50);
    }

    private void handleBulletInput() {
        if (Gdx.input.justTouched()) {
            Vector2 bulletStartPosition = player.getPosition().cpy();

            Vector3 mouseScreenPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 mouseWorldPosition3 = camera.unproject(mouseScreenPosition);
            Vector2 mouseWorldPosition = new Vector2(mouseWorldPosition3.x / PPM, mouseWorldPosition3.y / PPM);

            Vector2 direction = mouseWorldPosition.cpy().sub(bulletStartPosition).nor();
            Vector2 bulletVelocity = direction.scl(bulletSpeed);

            Bullet myBullet = new Bullet(bulletStartPosition, mouseWorldPosition, bulletVelocity, 70f);
            bulletManager.add(myBullet);
        }
    }

}
