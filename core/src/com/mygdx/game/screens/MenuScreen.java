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
    protected Animation<TextureRegion> animation;
    private Texture newgame, loadgame, setting, exit, hoverNewgame;
    private boolean isHoveringNewGame;

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
        background = new Texture(Gdx.files.internal("menu/nindotmenu.png"));

        newgame = new Texture(Gdx.files.internal("menu/1.png"));
        loadgame = new Texture(Gdx.files.internal("menu/1.png"));
        setting = new Texture(Gdx.files.internal("menu/1.png"));
        exit = new Texture(Gdx.files.internal("menu/1.png"));
        hoverNewgame = new Texture(Gdx.files.internal("menu/1_hover.png"));

        Image newGameImage = new Image(newgame);
        Image loadgameImage = new Image(newgame);
        Image settingImage = new Image(newgame);
        Image exitImage = new Image(newgame);
        Image newGameImageHover = new Image(hoverNewgame);

        SoundManager.create();
        SoundManager.getBackgroundMusic().setLooping(true);
        SoundManager.getBackgroundMusic().play();


        newGameImage.setPosition(225, 700);
        loadgameImage.setPosition(225, 500);
        settingImage.setPosition(225, 300);
        exitImage.setPosition(225, 100);

        stage.addActor(newGameImage);
        stage.addActor(loadgameImage);
        stage.addActor(settingImage);
        stage.addActor(exitImage);

        Gdx.input.setInputProcessor(stage);

        newGameImage.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor fromActor) {
                isHoveringNewGame = true;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, com.badlogic.gdx.scenes.scene2d.Actor toActor) {
                isHoveringNewGame = false;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(isHoveringNewGame ? hoverNewgame : newgame, 50, 200);
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
