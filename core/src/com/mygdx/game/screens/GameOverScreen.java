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
import com.mygdx.game.HUD;
import com.mygdx.game.MyGdxGame;
import jdbc.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import static com.mygdx.game.screens.GameScreen.hud;

public class GameOverScreen extends ScreenAdapter {
    private MyGdxGame game;
    private Stage stage;
    private Skin skin;
    private int score;
    private int userId;

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

        Label scoreLabel = new Label("Score: " + score, skin);
        scoreLabel.setFontScale(1.5f);

        TextButton retryButton = new TextButton("Retry", skin);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game)); // Restart the game
            }
        });

        TextButton exitButton = new TextButton("Exit to Main Menu", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game)); // Go to main menu
            }
        });

        table.center();
        table.add(gameOverLabel).padBottom(40).width(1000);
        table.row();
        table.add(scoreLabel).padBottom(20).width(1000);
        table.row();
        table.add(retryButton).uniformX().pad(10).width(1000);
        table.row();
        table.add(exitButton).uniformX().pad(10).width(1000);
        table.row();

        Gdx.input.setInputProcessor(stage);

        try (Connection connection = MySQLConnection.getConnection()) {
            String insertScore = "INSERT INTO score_history (user_id, score, achieved_at) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertScore)) {
                statement.setInt(1, userId);
                statement.setInt(2, score);
                statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

                int insertedRows = statement.executeUpdate();
                if (insertedRows > 0) {
                    System.out.println("Score inserted successfully.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
