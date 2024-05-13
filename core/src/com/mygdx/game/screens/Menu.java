package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public class Menu implements Screen {
    private Stage stage;
    private Skin skin;
    private SpriteBatch batch;
    private Texture background;
    private final MyGdxGame context;

    public Menu(final MyGdxGame context) {
        this.context = context;
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show () {
        stage = new Stage(new ScreenViewport());
        skin=new Skin(Gdx.files.internal("sample.json"));
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("background.png"));
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        root.pad(20);

        /*final Label enterUserLabel = new Label("Username:", skin);
        final TextField usernameField = new TextField("", skin);
        TextButton okButton = new TextButton("OK", skin);

        root.add(enterUserLabel).padBottom(20).colspan(2);
        root.row();
        root.add(usernameField).fillX().uniformX().padBottom(20).colspan(2);
        root.row();
        root.add(okButton).fillX().uniformX().colspan(2);*/


    }
    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void hide () {
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
        stage.dispose();
        skin.dispose();
        batch.dispose();
        background.dispose();
    }
}
