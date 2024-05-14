package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import sounds.SoundManager;

public class LoadingScreen extends ScreenAdapter implements InputProcessor{

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

    public LoadingScreen(final MyGdxGame context) {

        this.context = context;
        this.shapeRenderer = new ShapeRenderer();


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

        //inputProcessor=new InputProcessorHelper();
        Gdx.input.setInputProcessor(this);


        skin=new Skin(Gdx.files.internal("sample.json"));
        stage=new Stage(new ScreenViewport());

        imgTexture = new Texture("loading/loading.png");

        uiTable = new Table();
        uiTable.setFillParent(true);
        stage.addActor(uiTable);


        pressAnyButtonInfo = new TextButton("pressAnyKey", skin,"default");
        pressAnyButtonInfo.setVisible(true);




        uiTable.setBackground(new TextureRegionDrawable(new TextureRegion(imgTexture)));

        // shapeRenderer.setProjectionMatrix(context.camera.combined);
        this.progress=0f;
        queueAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateProgress(delta);

        batch.begin();
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (context.assetManager.update() && progress >= context.assetManager.getProgress() - .001f) {
            // Assets are loaded, show the "Press Any Button" message
            pressAnyButtonInfo.setVisible(true);
            uiTable.setBackground(new TextureRegionDrawable(new TextureRegion(imgTexture)));
            if(assetsLoaded){
                SoundManager.getBackgroundMusic().play();
            }

        } else {
            // Assets are still loading, hide the "Press Any Button" message and progress bar
            pressAnyButtonInfo.setVisible(false);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(16, context.camera.viewportHeight / 2 - 8, context.camera.viewportWidth - 64, 16);

            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(16, context.camera.viewportHeight / 2 - 8, context.camera.viewportWidth - 64, 16);
            shapeRenderer.end();
        }

    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
        backgroundMusic.dispose();
        imgTexture.dispose();
    }
    private void updateProgress(float delta) {
        progress = MathUtils.lerp(progress, context.assetManager.getProgress(), .1f);
        if (context.assetManager.update() && progress >= context.assetManager.getProgress() - .001f) {
            if (!uiTable.getChildren().contains(pressAnyButtonInfo, true)) {
                uiTable.add(pressAnyButtonInfo).expand().fillX().center().row();
                assetsLoaded = true;

            }
        }
    }


    @Override
    public boolean keyDown(int keycode) {
        context.setScreen(new MenuScreen(context));
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
