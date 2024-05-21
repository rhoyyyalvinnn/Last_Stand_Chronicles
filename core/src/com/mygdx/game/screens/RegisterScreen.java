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

    private Label passwordDiff;
    private Label emptyFieldsWarning; // Label for empty fields warning

    public RegisterScreen(MyGdxGame context) {
        this.context = context;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skinfiles/last_stand2.json"));
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("loading/balo.png"));

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label titleLabel = new Label("Register", skin);
        titleLabel.setAlignment(Align.center);


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

        passwordDiff = new Label("", skin, "kapoya", Color.RED);
        emptyFieldsWarning = new Label("", skin, "kapoya", Color.RED); // Initialize the empty fields warning label

        TextButton registerButton = new TextButton("  Register  ", skin);
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
                                System.out.println("User Inserted Successfully");
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
        Label loginLabel = new Label("Already have an Account? Log in here", skin, "kapoya", Color.BLUE);
        loginLabel.setAlignment(Align.center);
        loginLabel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                context.setScreen(new LoginScreen(context)); // Switch to the login screen when clicked
            }
        });

        // Position the form elements within the table
        table.add(titleLabel).height(100).colspan(2).padBottom(1).row();
        //
        table.center();
        table.add(firstNameLabel).width(600).height(100).align(Align.left).pad(5);
        table.add(firstNameField).width(600).height(100).pad(5).row();
        table.row();
        table.add(lastNameLabel).width(600).height(100).height(100).align(Align.left).pad(5);
        table.add(lastNameField).width(600).height(100).pad(5).row();
        table.row();
        table.add(emailLabel).width(600).height(100).height(100).align(Align.left).pad(5);
        table.add(emailField).width(600).height(100).pad(5).row();
        table.row();
        table.add(usernameLabel).width(600).height(100).height(100).align(Align.left).pad(5);
        table.add(usernameField).width(600).height(100).pad(5).row();
        table.row();
        table.add(passwordLabel).width(600).height(100).height(100).align(Align.left).pad(5);
        table.add(passwordField).width(600).height(100).pad(5).row();
        table.row();
        table.add(confirmPasswordLabel).width(600).height(100).align(Align.left).pad(5);
        table.add(confirmPasswordField).width(600).height(100).pad(5).row();
        System.out.println(confirmPasswordField.getWidth());
        table.row();
        table.add(registerButton).width(350).height(120).colspan(2).pad(5).row();
        table.add(loginLabel).colspan(2).pad(5).row();
        table.row();
        table.add(passwordDiff).colspan(2).center().bottom().row();
        table.row();
        table.add(emptyFieldsWarning).colspan(2).pad(5).center().bottom(); // Add the empty fields warning label to the table
    }

    private boolean validateInput(String firstName, String lastName, String email, String username, String password, String confirmPassword) {
        boolean isValid = true;

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            passwordDiff.setText("Inputted passwords do not match");
            isValid = false;
        } else {
            passwordDiff.setText("");
        }

        // Check if any field is empty
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            emptyFieldsWarning.setText("Please fill in the blank text fields");
            isValid = false;
        } else {
            emptyFieldsWarning.setText("");
        }

        return isValid;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();



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
