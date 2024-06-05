package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HUD {
    private Stage stage;
    private Array<Image> hearts;
    private Texture heartTexture;
    private int maxHealth;

    //Asher pax codes
    private Label scoreLabel;
    private int score;


    public HUD(int maxHealth, Skin skin) {
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

        scoreLabel = new Label("Score: 0", skin);
//        scoreLabel.setPosition(Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 50);
//        scoreLabel.setPosition((Gdx.graphics.getWidth() - scoreLabel.getWidth()) / 2 + 50, Gdx.graphics.getHeight() - 200);
        scoreLabel.setPosition(30,30);

        scoreLabel.setWidth(200);
        scoreLabel.setHeight(80);
        scoreLabel.setFontScale(0.5f);
        stage.addActor(scoreLabel);
        System.out.println(score);
        Gdx.input.setInputProcessor(stage);
    }

    public void update(float delta, int currentHealth, int score) {
        stage.act(delta);

        // Update hearts visibility based on current health
        for (int i = 0; i < hearts.size; i++) {
            if (i < currentHealth) {
                hearts.get(i).setVisible(true);
            } else {
                hearts.get(i).setVisible(false);
            }
        }

        this.score = score;
        scoreLabel.setText("Score: " + score);
        System.out.println(score);
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

    public int getScore(){
        return score;
    }
    public void resetScore() {
        score = 0;
    }
    public void setScore(int score){
        this.score = score;
    }
}
