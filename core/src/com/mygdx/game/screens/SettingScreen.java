package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Managers.SoundManager;

public class SettingScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private final MyGdxGame context;
    private Texture backgroundTexture;
    private Music backgroundMusic;

    public SettingScreen(final MyGdxGame context) {
        this.context = context;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("sample.json"));
        backgroundTexture = new Texture(Gdx.files.internal("menubg.png"));

        backgroundMusic = SoundManager.getBackgroundMusic();

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        root.pad(20);

        // Volume Label
        Label volumeLabel = new Label("Volume:", skin);
        root.add(volumeLabel).padBottom(20).row();

        // Volume Dropdown
        SelectBox<String> volumeDropdown = new SelectBox<>(skin);
        volumeDropdown.setItems("Low", "Medium", "High", "Mute");
        volumeDropdown.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                    float volumeLevel = getVolumeLevelFromString(volumeDropdown.getSelected());
                    backgroundMusic.setVolume(volumeLevel);
            }
        });
        root.add(volumeDropdown).padBottom(20).row();

        // Back Button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch back to the menu screen
                context.setScreen(new LoadingScreen(context));
            }
        });
        root.add(backButton).fillX().uniformX().padBottom(20).row();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getBatch().end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }

    private float getVolumeLevelFromString(String volumeLevelString) {
        switch (volumeLevelString) {
            case "Mute":
                return 0;
            case "Low":
                return 0.3f;
            case "Medium":
                return 0.6f;
            case "High":
                return 1.0f;
            default:
                // Handle unknown volume levels here (e.g., log a warning)
                return 1.0f; // Default to full volume
        }
    }
}
