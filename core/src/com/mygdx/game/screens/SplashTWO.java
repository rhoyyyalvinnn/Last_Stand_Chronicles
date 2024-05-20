package com.mygdx.game.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.SpriteAccessor;

public class SplashTWO implements Screen {
    private final MyGdxGame context;
    private SpriteBatch batch;
    private Sprite bgSprite;
    private TweenManager tweenManager;
    private Animation<TextureRegion> animations;
    private float stateTime;
    private float elapsedTime = 0;
    private final float transitionDuration = 1.2f;

    public SplashTWO(final MyGdxGame context) {
        this.context = context;
        TextureAtlas pusil = new TextureAtlas(Gdx.files.internal("transition/gun.atlas"));
        createAnimation(pusil);
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        Texture bgTexture = new Texture(Gdx.files.internal("transition/bg.png"));
        bgSprite = new Sprite(bgTexture);
        bgSprite.setSize(bgTexture.getWidth(), bgTexture.getHeight());
        bgSprite.setPosition(0, 0);

        Tween.set(bgSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Tween.to(bgSprite, SpriteAccessor.ALPHA, 20).target(1).start(tweenManager);
        Tween.to(bgSprite,SpriteAccessor.ALPHA,20).target(0).delay(2).start(tweenManager);
    }

    @Override
    public void render(float v) {
        stateTime += v;
        elapsedTime += v;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (animations != null) {
            TextureRegion currentFrame = animations.getKeyFrame(stateTime, true);
            if (currentFrame != null && currentFrame.getRegionHeight() != 0) {
                float xPosition = (Gdx.graphics.getWidth()/6f)-350;
                float yPosition = (Gdx.graphics.getHeight()/7f)-30;
                batch.draw(currentFrame, xPosition, yPosition,currentFrame.getRegionWidth()*4,currentFrame.getRegionHeight()*4);
            }
        }
        bgSprite.draw(batch);
        batch.end();
        tweenManager.update(v);

        // Check if animation has completed a loop
        if (elapsedTime >= transitionDuration) {
            // Transition to the next screen
            context.setScreen(ScreenType.MENU);
        }
    }

    @Override
    public void resize(int i, int i1) {

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
        batch.dispose();
    }

    private void createAnimation(TextureAtlas running) {
        Array<TextureAtlas.AtlasRegion> runningFrames = running.findRegions("tile000");

        int frameCount = 5; // Total number of frames
        for (int i = 1; i <= frameCount; i++) {
            String regionName = String.format("tile%03d", i); // Format the region name
            TextureAtlas.AtlasRegion region = running.findRegion(regionName); // Find the region
            if (region != null) {
                runningFrames.add(region); // Add the region to the frames array
            } else {
                Gdx.app.error("LoadingScreen", "Region not found: " + regionName);
            }
            if (runningFrames.size > 0) {
                animations = new Animation<>(0.1f, runningFrames, Animation.PlayMode.LOOP);
                Gdx.app.log("LoadingScreen", "Animation frames loaded successfully.");
            } else {
                Gdx.app.error("LoadingScreen", "No frames found for the animation.");
            }
        }
    }
}
