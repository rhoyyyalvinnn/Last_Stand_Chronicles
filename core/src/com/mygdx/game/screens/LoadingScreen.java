package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Managers.SoundManager;

public class LoadingScreen extends ScreenAdapter implements InputProcessor {

    public Skin skin;
    public Stage stage;
    private final MyGdxGame context;
    private Table uiTable;
    private TextButton pressAnyButtonInfo;
    private Music backgroundMusic;
    private SpriteBatch batch;
    private Texture imgTexture;
    private float progress;
    private final ShapeRenderer shapeRenderer;

    private boolean assetsLoaded;
    private Animation<TextureRegion> animations;

    private float stateTime;

    public LoadingScreen(final MyGdxGame context) {
        this.context = context;
        this.shapeRenderer = new ShapeRenderer();
        TextureAtlas runningAni = new TextureAtlas(Gdx.files.internal("loading/loadingrun.atlas"));
        Gdx.app.log("LoadingScreen", "Atlas loaded successfully: " + runningAni);
        createAnimation(runningAni);
    }

    private void queueAssets() {
        context.assetManager.load("sample.atlas", TextureAtlas.class);
    }

    @Override
    public void show() {
        SoundManager.create();
        SoundManager.getBackgroundMusic().setLooping(true);

        stage = new Stage(new ScreenViewport());
        batch = new SpriteBatch();



        skin = new Skin(Gdx.files.internal("sample.json"));
        stage = new Stage(new ScreenViewport());

        imgTexture = new Texture("loading/nindot.png");

        uiTable = new Table();
        uiTable.setFillParent(true);
        stage.addActor(uiTable);
        uiTable.setBackground(new TextureRegionDrawable(new TextureRegion(imgTexture)));

        pressAnyButtonInfo = new TextButton("Press Any Key", skin, "default");
        pressAnyButtonInfo.setVisible(true);


        this.progress = 0f;
        queueAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateProgress(delta);
        stateTime += delta;
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.begin();
        if (animations != null) {
            TextureRegion currentFrame = animations.getKeyFrame(stateTime, true);
            if (currentFrame != null && currentFrame.getRegionHeight() != 0) {
                float xPosition = progress * (Gdx.graphics.getWidth() - currentFrame.getRegionWidth());
                float yPosition = (Gdx.graphics.getHeight()/7f)-30;
                batch.draw(currentFrame, xPosition, yPosition,currentFrame.getRegionWidth()*2,currentFrame.getRegionHeight()*2);

            }
        }
        batch.end();



        if (context.assetManager.update() && progress >= context.assetManager.getProgress() - .01f) {
            pressAnyButtonInfo.setVisible(true);
            if (assetsLoaded) {
                SoundManager.getBackgroundMusic().play();
            }
        } else {
            pressAnyButtonInfo.setVisible(false);
        }
    }

    private void createAnimation(TextureAtlas running) {
        Array<TextureAtlas.AtlasRegion> runningFrames = running.findRegions("tile000");
        runningFrames.add(running.findRegion("tile001"));
        runningFrames.add(running.findRegion("tile002"));
        runningFrames.add(running.findRegion("tile003"));
        runningFrames.add(running.findRegion("tile004"));
        runningFrames.add(running.findRegion("tile005"));
        runningFrames.add(running.findRegion("tile006"));
        runningFrames.add(running.findRegion("tile007"));

        if (runningFrames.size > 0) {
            animations = new Animation<>(0.09f, runningFrames, Animation.PlayMode.LOOP);
            Gdx.app.log("LoadingScreen", "Animation frames loaded successfully.");
        } else {
            Gdx.app.error("LoadingScreen", "No frames found for the animation.");
        }
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
        imgTexture.dispose();
        stage.dispose();
        skin.dispose();
    }

    private void updateProgress(float delta) {
        // Calculate the target progress directly from the asset manager
        float targetProgress = context.assetManager.getProgress();

        // Define a constant speed factor for progress increment
        float speedFactor = 0.1f; // Adjust this factor to control the speed of the progress bar

        // Increment progress at a constant rate towards the target progress
        if (progress < targetProgress) {
            progress += speedFactor * delta;
        } else {
            progress = targetProgress;
        }

        // Check if loading is complete
        if (context.assetManager.update() && progress >= targetProgress - .00001f) {
            if (!uiTable.getChildren().contains(pressAnyButtonInfo, true)) {
                uiTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("loading/NINDOT2.png"))));
                assetsLoaded = true;
                Gdx.input.setInputProcessor(this);
            }
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        context.setScreen(ScreenType.SPLASH2);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
