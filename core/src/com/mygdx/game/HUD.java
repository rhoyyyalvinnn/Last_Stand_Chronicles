package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HUD {
    private Stage stage;
    private Array<Image> hearts;
    private Texture heartTexture;
    private int maxHealth;

    public HUD(int maxHealth) {
        this.maxHealth = maxHealth;
        stage = new Stage(new ScreenViewport());
        hearts = new Array<>();
        heartTexture = new Texture(Gdx.files.internal("heart.png")); // Load the heart texture

        for (int i = 0; i < 10; i++) { // Assuming maxHealth is 10
            Image heart = new Image(heartTexture);
            heart.setSize(40, 40);
            heart.setPosition(20 + i * 45, Gdx.graphics.getHeight() - 50);
            hearts.add(heart);
            stage.addActor(heart);
        }

        Gdx.input.setInputProcessor(stage);
    }

    public void update(float delta, int currentHealth) {
        stage.act(delta);

        // Update hearts visibility based on current health
        for (int i = 0; i < hearts.size; i++) {
            if (i < currentHealth) {
                hearts.get(i).setVisible(true);
            } else {
                hearts.get(i).setVisible(false);
            }
        }
    }

    public void draw() {
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        heartTexture.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
