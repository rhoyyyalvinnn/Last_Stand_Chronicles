package com.mygdx.game.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SpriteAccessor;

public class SplashScreen implements Screen {
    private Sprite splash;
    private SpriteBatch batch;
    private TweenManager tweenManager;
    private final MyGdxGame context;

    public interface SplashScreenListener {
        void onSplashScreenFinished();
    }
    private SplashScreenListener listener;
    // Set the listener
    public void setListener(SplashScreenListener listener) {
        this.listener = listener;
    }
    // Method to call when splash animation completes
    private void splashAnimationFinished() {
        if (listener != null) {
            listener.onSplashScreenFinished();
        }
    }

    public SplashScreen(final MyGdxGame context) {
            this.context = context;
        }
    @Override
    public void show() {
        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        Texture splashtexture = new Texture("splash.png");
        splash = new Sprite(splashtexture);
        splash.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).start(tweenManager);
        Tween.to(splash,SpriteAccessor.ALPHA,2).target(0).delay(2).start(tweenManager);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batch.begin();
        splash.draw(batch);
        batch.end();
        if (tweenManager.getRunningTweensCount() == 0) {
            splashAnimationFinished();
        }
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



