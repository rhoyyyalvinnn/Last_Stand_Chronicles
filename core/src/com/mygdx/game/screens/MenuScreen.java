package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import sounds.SoundManager;

public class MenuScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture background;
    private Music backgroundMusic;
    private final MyGdxGame context;

    public MenuScreen(final MyGdxGame context) {
        this.context = context;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("sample.json"));
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("background.png"));
        SoundManager.create();
        SoundManager.getBackgroundMusic().setLooping(true);
        SoundManager.getBackgroundMusic().play();

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        root.pad(20);

        // New Game Button
        TextButton newGameButton = new TextButton("New Game", skin);
        newGameButton.addListener(createButtonListener(newGameButton));
        newGameButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.setScreen(new GameScreen(context)); // Switch to game screen
            }
        });

        // Load Game Button
        TextButton loadGameButton = new TextButton("Load Game", skin);
        loadGameButton.addListener(createButtonListener(loadGameButton));

        // Mute Button
        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.setScreen(new SettingScreen(context)); // Switch to settings screen
            }
        });

        // Exit Button
        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit(); // Exit the application
            }
        });

        // Add buttons to the root table
        root.add(newGameButton).fillX().uniformX().padBottom(20).row();
        root.add(loadGameButton).fillX().uniformX().padBottom(20).row();
        root.add(settingsButton).fillX().uniformX().padBottom(20).row();
        root.add(exitButton).fillX().uniformX().padBottom(20).row();

        Gdx.input.setInputProcessor(stage);
        // Start playing background music
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
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
        stage.dispose();
        skin.dispose();
        batch.dispose();
        background.dispose();
        backgroundMusic.dispose();
    }

    private ClickListener createButtonListener(TextButton button) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Add your logic for handling button click
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                button.setColor(1f, 1f, 1f, 0.7f); // Set button color to semi-transparent white when hovered
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                button.setColor(1f, 1f, 1f, 1f); // Set button color back to normal when not hovered
            }
        };
    }
}
