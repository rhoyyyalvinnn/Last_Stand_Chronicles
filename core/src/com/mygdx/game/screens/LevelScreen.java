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
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Map.MapOneFactory;
import com.mygdx.game.MyGdxGame;

public class LevelScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private MyGdxGame context;
    private SpriteBatch batch,imagebatch;
    private Texture background;
    private ShapeRenderer shapeRenderer;
    private Label actionTarget;
    private Texture bgTexture;
    private Image imageBg;
   public static String  mapValue;

    public String getMapValue() {
        return mapValue;
    }

    public void setMapValue(String mapValue) {
        this.mapValue = mapValue;
    }

    public LevelScreen(MyGdxGame context) {
        this.context = context;
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("skinfiles/last_stand2.json"));
        batch = new SpriteBatch();
        imagebatch=new SpriteBatch();
        background = new Texture(Gdx.files.internal("loading/balo.png"));
        shapeRenderer = new ShapeRenderer();
        bgTexture = new Texture(Gdx.files.internal("skinfiles/bg_login.png"));
        imageBg=new Image(bgTexture);


    }
    @Override
    public void show() {
        float rectangleWidth = 1650;
        float rectangleHeight = 900;
        imageBg.setPosition(((Gdx.graphics.getWidth() - rectangleWidth) / 2),(Gdx.graphics.getHeight() - rectangleHeight) / 2);
        imageBg.setSize(rectangleWidth, rectangleHeight);
        stage.addActor(imageBg);

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);

        stage.addActor(table);

        TextButton lvl1 = new TextButton(" Map 1 ", skin);
        TextButton lvl2 = new TextButton(" Map 2 ", skin);

        lvl1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setMapValue("First");
                mapValue="First";// Set mapValue to "First"
                context.setScreen(ScreenType.GAME);
            }
        });

        lvl2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setMapValue("Second"); // Set mapValue to "Second"
                context.setScreen(ScreenType.GAME);
            }
        });

        table.center();
        table.add(lvl1).height(100).pad(10);
        table.row();
        table.add(lvl2).height(100).pad(10);
        table.row();
        table.add(actionTarget).colspan(2).pad(10).center().bottom();
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

    }

}
