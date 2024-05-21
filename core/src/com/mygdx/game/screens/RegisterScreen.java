package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import com.mygdx.game.MyGdxGame;
import jdbc.MySQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterScreen implements Screen {

    private Stage stage;
    private Skin skin;
    private MyGdxGame context;
    private SpriteBatch batch;
    private Texture background;
    private ShapeRenderer shapeRenderer;
    private Label passwordDiff;

    public RegisterScreen(MyGdxGame context) {
        this.context = context;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("sample.json"));
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("loading/balo.png"));
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label titleLabel = new Label("Register", skin, "title");
        titleLabel.setAlignment(Align.center);
        table.add(titleLabel).colspan(2).padBottom(30).row();

        Label firstNameLabel = new Label("First Name:", skin);
        final TextField firstNameField = new TextField("", skin);

        Label lastNameLabel = new Label("Last Name:", skin);
        final TextField lastNameField = new TextField("", skin);

        Label emailLabel = new Label("Email:", skin);
        final TextField emailField = new TextField("", skin);

        Label usernameLabel = new Label("Username:", skin);
        final TextField usernameField = new TextField("", skin);

        Label passwordLabel = new Label("Password:", skin);
        final TextField passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);

        Label confirmPasswordLabel = new Label("Confirm Password:", skin);
        final TextField confirmPasswordField = new TextField("", skin);
        confirmPasswordField.setPasswordCharacter('*');
        confirmPasswordField.setPasswordMode(true);

        passwordDiff = new Label("", skin, "font", Color.RED);

        TextButton registerButton = new TextButton("Register", skin);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();

                // Validate input and password confirmation
                if (validateInput(firstName, lastName, email, username, password, confirmPassword)) {
                    try (Connection connection = MySQLConnection.getConnection()) {
                        String insertUserInfo = "INSERT INTO users (fname, lname, email, username, password) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement userStatement = connection.prepareStatement(insertUserInfo)) {
                            userStatement.setString(1, firstName);
                            userStatement.setString(2, lastName);
                            userStatement.setString(3, email);
                            userStatement.setString(4, username);
                            userStatement.setString(5, password);
                            int insertedRows = userStatement.executeUpdate();
                            if (insertedRows > 0) {
                                System.out.println("Enrollment Inserted Successfully");
                                context.setScreen(new LoginScreen(context)); // Switch to login screen after successful registration
                            }
                        }
                    } catch (SQLException e) {
                        // Roll back the transaction if any operation fails
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Invalid input. Please check your information.");
                }
            }
        });

        // Add clickable label for login
        Label loginLabel = new Label("Already have an Account? Log in here", skin, "font", Color.BLUE);
        loginLabel.setAlignment(Align.center);
        loginLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.setScreen(new LoginScreen(context)); // Switch to the login screen when clicked
            }
        });

        // Position the form elements within the table
        table.center();
        table.add(firstNameLabel).align(Align.left).pad(10);
        table.add(firstNameField).width(200).pad(10).row();
        table.row();
        table.add(lastNameLabel).align(Align.left).pad(10);
        table.add(lastNameField).width(200).pad(10).row();
        table.row();
        table.add(emailLabel).align(Align.left).pad(10);
        table.add(emailField).width(200).pad(10).row();
        table.row();
        table.add(usernameLabel).align(Align.left).pad(10);
        table.add(usernameField).width(200).pad(10).row();
        table.row();
        table.add(passwordLabel).align(Align.left).pad(10);
        table.add(passwordField).width(200).pad(10).row();
        table.row();
        table.add(confirmPasswordLabel).align(Align.left).pad(10);
        table.add(confirmPasswordField).width(200).pad(10).row();
        table.row();
        table.add(registerButton).colspan(2).pad(10).row();
        table.add(loginLabel).colspan(2).pad(10).row();
        table.add(passwordDiff).colspan(2).pad(10).center().bottom();
    }

    private boolean validateInput(String firstName, String lastName, String email, String username, String password, String confirmPassword) {
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            passwordDiff.setText("Inputted passwords do not match");
            return false;
        } else {
            passwordDiff.setText("");
        }
        // Basic validation for non-empty fields
        return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty();
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
        float rectangleHeight = 750;
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
    }
}
