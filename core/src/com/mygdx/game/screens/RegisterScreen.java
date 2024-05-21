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
import com.mygdx.game.MyGdxGame;

public class RegisterScreen implements Screen {

    private Stage stage;
    private Skin skin;
    private MyGdxGame context;
    private SpriteBatch batch;
    private Texture background;

    public RegisterScreen(MyGdxGame context) {
        this.context = context;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("sample.json"));
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("loading/balo.png"));
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

        TextButton registerButton = new TextButton("Register", skin);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();

                // Validate input (optional)
                if (validateInput(firstName, lastName, email, username, password)) {
                    // Perform registration logic (e.g., save to database)
                    System.out.println("Registration successful!");
                    context.setScreen(new MenuScreen(context)); // Switch to menu screen after registration
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
        table.add(firstNameLabel).pad(10);
        table.add(firstNameField).width(200).pad(10);
        table.row();
        table.add(lastNameLabel).pad(10);
        table.add(lastNameField).width(200).pad(10);
        table.row();
        table.add(emailLabel).pad(10);
        table.add(emailField).width(200).pad(10);
        table.row();
        table.add(usernameLabel).pad(10);
        table.add(usernameField).width(200).pad(10);
        table.row();
        table.add(passwordLabel).pad(10);
        table.add(passwordField).width(200).pad(10);
        table.row();
        table.add(registerButton).colspan(2).pad(10).row();
        table.add(loginLabel).colspan(2).pad(10);

    }

    private boolean validateInput(String firstName, String lastName, String email, String username, String password) {
        // Implement your validation logic here
        // For demonstration, let's assume basic validation for non-empty fields
        return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty();
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
