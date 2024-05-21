package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Managers.SoundManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import jdbc.MySQLConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MenuScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture background;
    private Music backgroundMusic;
    private final MyGdxGame context;


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
        skin = new Skin(Gdx.files.internal("skinfiles/last_stand2.json"));
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("loading/nindot.png"));



        SoundManager.create();
        SoundManager.getBackgroundMusic().setLooping(true);
        SoundManager.getBackgroundMusic().play();



        stage.addActor(createButton("  New game  ",230,700));
        stage.addActor(createButton("  Load game  ",230,500));
        stage.addActor(createButton("  Settings  ",230,300));
        stage.addActor(createButton("  Exit  ",230,100));


        Gdx.input.setInputProcessor(stage);



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
    private TextButton createButton(String text,int x, int y) {
        TextButton button = new TextButton(text, skin);
        button.setPosition(x,y);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (text.equals("  Settings  ")) {
                    context.setScreen(ScreenType.SETTING);
                }
                if(text.equals("  New game  ")){
                    context.setScreen(ScreenType.GAME);
                }else if (text.equals("  Exit  ")) {
                    Gdx.app.exit();
                }

            }

        });
        return button;

    }
}
