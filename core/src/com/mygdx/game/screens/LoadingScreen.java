/*
package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

public class LoadingScreen extends ScreenAdapter {
    public Skin skin;
    public Stage stage;
    private final MyGdxGame context;
    public LoadingScreen(final MyGdxGame context) {
        this.context = context;
    }
    private Table uiTable;
    private ProgressBar progressBar;
    private final TextButton pressAnyButtonInfo;
    private boolean isMusicLoaded;

    @Override
    public void show() {
        skin=new Skin(Gdx.files.internal("hud/hud.json"));
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        uiTable = new Table();
        uiTable.setFillParent(true);
        stage.addActor(uiTable);
        uiTable.pad(20);

        progressBar = new ProgressBar(0,1,0.1f,false,skin);

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
*/
