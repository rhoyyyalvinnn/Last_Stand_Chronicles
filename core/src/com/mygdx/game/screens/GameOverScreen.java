package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import jdbc.MySQLConnection;
import jdbc.HighScore;

public class GameOverScreen extends ScreenAdapter {
    private MyGdxGame game;
    private Stage stage;
    private Skin skin;
    private int score;
    private int userId; // The current user's ID

    public GameOverScreen(MyGdxGame game, int score, int userId) {
        this.game = game;
        this.score = score;
        this.userId = userId;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skinfiles/last_stand2.json"));
    }

    @Override
    public void show() {
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label gameOverLabel = new Label("Game Over", skin);
        gameOverLabel.setFontScale(2);
        table.add(gameOverLabel).padBottom(40).row();

        Label scoreLabel = new Label("Score: " + score, skin);
        scoreLabel.setFontScale(1.5f);
        table.add(scoreLabel).padBottom(20).row();

        HighScore highestScore = MySQLConnection.getHighestScore();
        Label highestScoreLabel = new Label("Highest Score: " + highestScore.getScore() + " by " + highestScore.getUsername(), skin);
        highestScoreLabel.setFontScale(1.5f);
        table.add(highestScoreLabel).padBottom(20).row();

        TextButton retryButton = new TextButton("Retry", skin);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game)); // Restart the game
            }
        });
        table.add(retryButton).uniformX().pad(10).row();

        TextButton exitButton = new TextButton("Exit to Main Menu", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game)); // Go to main menu
            }
        });
        table.add(exitButton).uniformX().pad(10).row();

        Gdx.input.setInputProcessor(stage);

        // Save current score if it's the highest
        if (score > highestScore.getScore()) {
            MySQLConnection.insertScore(userId, score);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        stage.dispose();
    }
}


//package com.mygdx.game.screens;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.ScreenAdapter;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Label;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.utils.viewport.ScreenViewport;
//import com.mygdx.game.MyGdxGame;
//
//public class GameOverScreen extends ScreenAdapter {
//    private MyGdxGame game;
//    private Stage stage;
//    private Skin skin;
//    private int score;
//
//    public GameOverScreen(MyGdxGame game, int score) {
//        this.game = game;
//        this.score = score;
//        stage = new Stage(new ScreenViewport());
//        skin = new Skin(Gdx.files.internal("skinfiles/last_stand2.json"));
//    }
//
//    @Override
//    public void show() {
//        Table table = new Table();
//        table.setFillParent(true);
//        stage.addActor(table);
//
//        Label gameOverLabel = new Label("Game Over", skin);
//        gameOverLabel.setFontScale(2);
//        table.add(gameOverLabel).padBottom(40).row();
//
//        Label scoreLabel = new Label("Score: " + score, skin);
//        scoreLabel.setFontScale(1.5f);
//        table.add(scoreLabel).padBottom(20).row();
//
//        TextButton retryButton = new TextButton("Retry", skin);
//        retryButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new GameScreen(game)); // Restart the game
//            }
//        });
//        table.add(retryButton).uniformX().pad(10).row();
//
//        TextButton exitButton = new TextButton("Exit to Main Menu", skin);
//        exitButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new MenuScreen(game)); // Go to main menu
//            }
//        });
//        table.add(exitButton).uniformX().pad(10).row();
//
//        Gdx.input.setInputProcessor(stage);
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        stage.getViewport().update(width, height, true);
//    }
//
//    @Override
//    public void hide() {
//        stage.dispose();
//    }
//}
