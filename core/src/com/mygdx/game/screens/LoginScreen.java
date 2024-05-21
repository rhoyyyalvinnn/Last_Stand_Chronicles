package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.MyGdxGame;
import jdbc.MySQLConnection;
import jdk.javadoc.internal.doclets.formats.html.markup.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginScreen implements Screen {

    private Stage stage;
    private Skin skin;
    private MyGdxGame context;
    private SpriteBatch batch;
    private Texture background;
    private ShapeRenderer shapeRenderer;
    private Label actionTarget;

    public LoginScreen(MyGdxGame context) {
        this.context = context;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("sample.json"));
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("loading/balo.png"));
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

        //create table if it doesn't exist
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "fname VARCHAR(20) NOT NULL," +
                    "lname VARCHAR(20) NOT NULL," +
                    "email VARCHAR(100) NOT NULL," +
                    "username VARCHAR(50) NOT NULL, " +
                    "password VARCHAR(50) NOT NULL)";
            statement.execute(createTableQuery);
            System.out.println("Table Created Successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }




        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label usernameLabel = new Label("Username:", skin);
        final TextField usernameField = new TextField("", skin);

        Label passwordLabel = new Label("Password:", skin);
        final TextField passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        actionTarget  = new Label("", skin,"font", Color.RED);
        TextButton loginButton = new TextButton("Login", skin);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Add logic to handle login
                if (authenticate(username, password)) {
                    context.setScreen(new MenuScreen(context)); // Switch to the menu screen on successful login
                } else {
                    actionTarget.setText("Invalid Username or password");
                }
            }
        });

        // Add clickable label for registration
        Label registerLabel = new Label("No Account? Register here", skin, "font", Color.BLUE);
        registerLabel.setAlignment(Align.center);
        registerLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.setScreen(new RegisterScreen(context)); // Switch to the register screen when clicked
            }
        });



        // Position the form elements within the table
        table.center();
        table.add(usernameLabel).pad(10);
        table.add(usernameField).width(200).pad(10);
        table.row();
        table.add(passwordLabel).pad(10);
        table.add(passwordField).width(200).pad(10);
        table.row();
        table.add(loginButton).colspan(2).pad(10).row();
        table.add(registerLabel).colspan(2).pad(10).center().bottom();
        table.row();
        table.add(actionTarget).colspan(2).pad(10).center().bottom();



    }

    private boolean authenticate(String username, String password) {

        String name = "";
        String pass = "";
        try(Connection connection = MySQLConnection.getConnection();
            Statement statement = connection.createStatement()){
            String selectQuery = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            ResultSet result = statement.executeQuery(selectQuery);

            if(result.next()){
                return true;
            } else {
                return false;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        // Implement your authentication logic here
        // For demonstration, let's assume any non-empty username and password are valid
        return !username.isEmpty() && !password.isEmpty();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        // Calculate position and draw the rectangle behind the form
        float rectangleWidth = 500;
        float rectangleHeight = 350;
        float rectangleX = (Gdx.graphics.getWidth() - rectangleWidth) / 2;
        float rectangleY = (Gdx.graphics.getHeight() - rectangleHeight) / 2;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(1, 1, 1, 0.7f)); // Semi-transparent white
        shapeRenderer.rect(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
        shapeRenderer.end();

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
        batch.dispose();
        background.dispose();
        shapeRenderer.dispose();
    }
}
