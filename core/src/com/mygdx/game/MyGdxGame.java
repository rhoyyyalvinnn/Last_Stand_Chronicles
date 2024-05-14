package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.ScreenType;
import com.mygdx.game.screens.SplashScreen;
import jdbc.MySQLConnection;
import sounds.SoundManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;

public class MyGdxGame extends Game implements SplashScreen.SplashScreenListener{

	private SplashScreen splashScreen;
	private EnumMap<ScreenType, Screen> screenCache;
	public AssetManager assetManager;
	public OrthographicCamera camera;
	public static final int V_WIDTH = 480;
	public static final int V_HEIGHT = 420;

	@Override
	public void create() {
		assetManager = new AssetManager();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
		screenCache = new EnumMap<ScreenType, Screen>(ScreenType.class);
		splashScreen = new SplashScreen(this);
		setScreen(splashScreen);
		splashScreen.setListener(this);
	}

	@Override
	public void onSplashScreenFinished() {
		setScreen(ScreenType.LOADING);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		splashScreen.dispose();
		super.dispose();
		SoundManager.dispose();
	}

	public void setScreen(final ScreenType screenType) {
		final Screen screen = screenCache.get(screenType);

		if (screen == null) {
			try {
				final Screen newScreen = (Screen) ClassReflection.getConstructor(screenType.getScreenClass(), MyGdxGame.class).newInstance(this);
				screenCache.put(screenType, newScreen);
				setScreen(newScreen);
			} catch (ReflectionException e) {
				throw new GdxRuntimeException("Could not create screen: " + screenType, e);
			}
		} else {
			setScreen(screen);
		}
	}

	// Start the background music when the game is created
	@Override
	public void resume() {
		super.resume();
		SoundManager.create();
	}

	// Dispose the background music when the game is disposed
}
