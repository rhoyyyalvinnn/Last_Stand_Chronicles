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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
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
import com.mygdx.game.Map.MapTwoFactory;
import com.mygdx.game.MyGdxGame;
import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.mygdx.game.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
    public static HUD hud;
    private boolean isPaused = false;
    private boolean escapePressed = false;
    private Skin skin;
    private Stage stage;
    private Label paused;
    private List<Enemies> enemies = new ArrayList<>();
    private Enemies enemy;
    private TextureRegion enemyTextureRegion;
    private TextureAtlas textureAtlas;
    private float timer;

    // para sa spawn mga bossing --pax
    private float spawnTimer = 0f;
    private final float SPAWN_INTERVAL = 0.3f;

    private User loggedInUser;
    // user id
    private int userId;

    public GameScreen(MyGdxGame context) {
        this.context = context;
        loggedInUser = context.getLoggedInUser();
        if(loggedInUser != null){
            System.out.println("Logged in user: " + loggedInUser.getUsername());
        }
    }
    @Override
    public void show() {
        //bulletTexture = new Texture("tile000.png");
        stage = new Stage(new ScreenViewport()); //initialize stage
        skin = new Skin(Gdx.files.internal("skinfiles/last_stand2.json")); //initialize skin
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);
        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();
        player = new PlayerManager(world);
        player.run();
        //System.out.println("x: " + player.getPosition().x + " y: " + player.getPosition().y);
        player.setPosition(new Vector2(5,5));
        batch = player.getBatch();
        MapOneFactory first = new MapOneFactory();
        MapTwoFactory second = new MapTwoFactory();


        if(LevelScreen.mapValue == "First"){
            mapManager = new MapManager(first, world);
        }else if(LevelScreen.mapValue=="Second"){
            mapManager = new MapManager(second, world);
        }

        // Light setup
        rayHandler = new RayHandler(world);
        suga = new PointLight(rayHandler, 50, Color.GRAY, 5, player.getPosition().x, player.getPosition().y);
        suga.attachToBody(PlayerManager.player);
        // Initialize HUD
        hud = new HUD(100, skin); // Max health is 100'
        if(hud.getScore() != 0){
            hud.resetScore();
        }

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
        hud.resetScore();
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
            Gdx.gl.glClearColor(58 / 255f, 58 / 255f, 80 / 255f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            elapsedTime += delta;
            Animation<TextureRegion> currentAnimation = player.determineCurrentAnimation();
            TextureRegion currentFrame = currentAnimation.getKeyFrame(elapsedTime, true);
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            mapManager.renderMap(camera);
            batch.draw(currentFrame, player.getPosition().x * PPM - ((float) currentFrame.getRegionWidth() / 2), player.getPosition().y * PPM - ((float) currentFrame.getRegionHeight() / 8));
            for (Enemies enemy : enemies) {
                enemy.update(delta);
                enemy.draw(batch);
            }


//            for (Bullet bullet : bulletManager) {
//                if (bullet.isActive()) {
//                    bullet.Update(delta);
//                    batch.draw(bulletTexture, bullet.bulletLocation.x * PPM, bullet.bulletLocation.y * PPM);
//                }
//            }
            batch.end();
            rayHandler.render();
            hud.update(delta, player.getHealth(), (int)elapsedTime); // Update health based on player's current health
            hud.draw();
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
        skin.dispose();
        stage.dispose();
        hud.dispose();



    }

    private void cameraUpdate(float delta) {
        Vector2 position = player.getPosition();
        camera.position.set(position.x * PPM, position.y * PPM, 0);
        camera.update();
    }

    private void update(float delta) {

        world.step(1 / 60f, 6, 2);
        for(Enemies enemy: enemies){
            enemy.update(delta);
        }
        Vector2 playerPosition = player.getPosition();
        suga.setPosition(playerPosition.x * PPM, playerPosition.y * PPM);
        handlePlayerEnemyCollisions();
        rayHandler.update();
        rayHandler.setAmbientLight(.002f);
        player.inputUpdate(delta);
//        player.getBoundingBox();
        cameraUpdate(delta);
        mapManager.renderMap(camera);
        batch.setProjectionMatrix(camera.combined);
        rayHandler.setCombinedMatrix(camera.combined.cpy().scl(PPM));
//        handleBulletInput();
//        bulletManager.removeIf(bullet -> bullet.bulletLocation.x < -50 || bullet.bulletLocation.x > Gdx.graphics.getWidth() + 50 || bullet.bulletLocation.y < -50 || bullet.bulletLocation.y > Gdx.graphics.getHeight() + 50);

        spawnTimer += delta;
        if (spawnTimer >= SPAWN_INTERVAL) {
            spawnEnemies(6);
            spawnTimer = 0f; // Reset timer
        }

        handleCollisions();
        timer += delta;
        System.out.println("Player Score: " + hud.getScore());
        userId = loggedInUser.getId();
        System.out.println("Player ID: " + userId);
        if (player.getHealth() <= 0) {
            elapsedTime=0;
            context.setScreen(new GameOverScreen(context, hud.getScore(), userId));
        }
    }

//    private void handleBulletInput() {
//        if (Gdx.input.justTouched()) {
//            Vector2 bulletStartPosition = player.getPosition().cpy();
//
//            Vector3 mouseScreenPosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
//            Vector3 mouseWorldPosition3 = camera.unproject(mouseScreenPosition);
//            Vector2 mouseWorldPosition = new Vector2(mouseWorldPosition3.x / PPM, mouseWorldPosition3.y / PPM);
//
////            Vector2 direction = mouseWorldPosition.cpy().sub(bulletStartPosition).nor();
////            Vector2 bulletVelocity = direction.scl(bulletSpeed);
////
////            float bulletWidth = bulletTexture.getWidth();
////            float bulletHeight = bulletTexture.getHeight();
////
////            Bullet myBullet = new Bullet(bulletStartPosition, mouseWorldPosition, bulletVelocity, 70f, bulletWidth, bulletHeight);
////            bulletManager.add(myBullet);
//        }
//    }

    private void spawnEnemies(int numberOfEnemies) {
        Random random = new Random();

        for (int i = 0; i < numberOfEnemies; i++) {
//            float x = random.nextFloat() * Gdx.graphics.getWidth() / SCALE;
            float x = random.nextFloat() * Gdx.graphics.getWidth();

            float y = random.nextFloat() * Gdx.graphics.getHeight();
//            float y = random.nextFloat() * Gdx.graphics.getHeight() / SCALE;

            Enemies enemy = new Enemies(15, x, y, 13, 13, enemyTextureRegion, player);
            enemies.add(enemy);
        }
    }

    private void handleCollisions() {
        Iterator<Bullet> bulletIterator = bulletManager.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            if (!bullet.isActive()) continue;

            Rectangle bulletBounds = bullet.getBoundingBox();

            Iterator<Enemies> enemyIterator = enemies.iterator();
            while (enemyIterator.hasNext()) {
                Enemies enemy = enemyIterator.next();
                //Rectangle enemyBounds = enemy.getBoundingBox();
                Rectangle enemyBounds=enemy.getBoundingBox();
                if (bulletBounds.overlaps(enemyBounds)) {
                    // Remove bullet and enemy upon collision
                    bullet.deactivate();
                    enemyIterator.remove();
                    break; // Exit the inner loop
                }
            }
        }

        // Remove inactive bullets
        bulletManager.removeIf(bullet -> !bullet.isActive());
    }

    private void handlePlayerEnemyCollisions() {
        Rectangle playerBounds = new Rectangle(player.getPosition().x * PPM - 16, player.getPosition().y * PPM - 16, 24, 24);
        Iterator<Enemies> enemyIterator = enemies.iterator();

        while (enemyIterator.hasNext()) {
            Enemies enemy = enemyIterator.next();
            Rectangle enemyBounds = enemy.getBoundingBox();

            if (playerBounds.overlaps(enemyBounds)) {
                player.decrementHealth(); // Decrement player's health
                enemyIterator.remove(); // Remove enemy
                break; // Only handle one collision per frame for simplicity
            }
        }
    }

}
