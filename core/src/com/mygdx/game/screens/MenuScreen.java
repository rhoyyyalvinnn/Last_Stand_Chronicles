package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Managers.SoundManager;
import com.badlogic.gdx.graphics.g2d.Animation;

public class MenuScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture background;
    private Music backgroundMusic;
    private final MyGdxGame context;
    protected Animation<TextureRegion> animation;

    public MenuScreen(final MyGdxGame context) {
        this.context = context;

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("sample.json"));
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("loading/nindot.png"));
        SoundManager.create();
        SoundManager.getBackgroundMusic().setLooping(true);
        SoundManager.getBackgroundMusic().play();

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        root.pad(20);

        // Left side buttons
        Table leftButtons = new Table();
        leftButtons.add(createButton("New Game")).fillX().uniformX().padBottom(20).row();
        leftButtons.add(createButton("Load Game")).fillX().uniformX().padBottom(20).row();

        // Right side buttons
        Table rightButtons = new Table();
        rightButtons.add(createButton("Settings")).fillX().uniformX().padBottom(20).row();
        rightButtons.add(createButton("Exit")).fillX().uniformX().padBottom(20).row();

        // Aligning buttons horizontally in the center
        root.add(leftButtons).expandX().left().uniform().padRight(20);
        root.add().expandX();
        root.add(rightButtons).expandX().right().uniform();

        Gdx.input.setInputProcessor(stage);
    }

    private TextButton createButton(String text) {
        TextButton button = new TextButton(text, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (text.equals("Settings")) {
                    context.setScreen(ScreenType.SETTING); // Switch to settings screen
                }
                if(text.equals("New Game")){
                    context.setScreen(ScreenType.GAME);
                }else if (text.equals("Exit")) {
                    Gdx.app.exit(); // Exit the application
                }

                // Add logic for other buttons if needed
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                button.setColor(1f, 1f, 1f, 0.7f); // Set button color to semi-transparent white when hovered
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                button.setColor(1f, 1f, 1f, 1f); // Set button color back to normal when not hovered
            }
        });
        return button;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        batch.dispose();
        background.dispose();
        backgroundMusic.dispose();
    }
}
