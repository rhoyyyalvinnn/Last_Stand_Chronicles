package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HUD {
    private Stage stage;
    private Skin skin;
    private ProgressBar healthBar;
    private int maxHealth;

    public HUD(int maxHealth) {
        this.maxHealth = maxHealth;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("sample.json"));

        // Initialize the health bar
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = skin.newDrawable("white", 0.3f, 0.3f, 0.3f, 1f);
        progressBarStyle.knobBefore = skin.newDrawable("white", 0.8f, 0f, 0f, 1f);

        healthBar = new ProgressBar(0, maxHealth, 1, false, progressBarStyle);
        healthBar.setValue(maxHealth);
        healthBar.setWidth(200);
        healthBar.setHeight(40);
        healthBar.setPosition(20, Gdx.graphics.getHeight() - 40);


        stage.addActor(healthBar);
        Gdx.input.setInputProcessor(stage);
    }

    public void update(float delta, int currentHealth) {
        stage.act(delta);
        healthBar.setValue(currentHealth);
    }

    public void draw() {
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
