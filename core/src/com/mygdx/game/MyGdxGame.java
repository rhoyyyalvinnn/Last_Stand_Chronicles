package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.mygdx.game.screens.Menu;
import com.mygdx.game.screens.ScreenType;
import com.mygdx.game.screens.SplashScreen;

import java.util.EnumMap;

public class MyGdxGame extends Game implements SplashScreen.SplashScreenListener{

	private SplashScreen splashScreen;
	private EnumMap<ScreenType, Screen> screenCache;


	
	@Override
	public void create () {
		screenCache = new EnumMap<ScreenType, Screen>(ScreenType.class);
		splashScreen = new SplashScreen(this);
		//menuscreen=new Menu();
		setScreen(splashScreen);
		splashScreen.setListener(new SplashScreen.SplashScreenListener() {
	@Override
	public void onSplashScreenFinished() {setScreen(ScreenType.MENU);
		}

});
		setScreen(splashScreen);
	}

	@Override
	public void onSplashScreenFinished() {
		// Transition to GameScreen after splash animation completes
		setScreen(ScreenType.MENU);
	}
	@Override
	public void render () {
	 super.render();
	}
	
	@Override
	public void dispose () {
	super.dispose();
	splashScreen.dispose();

	}
public void setScreen(final ScreenType screenType) {
	final Screen screen = screenCache.get(screenType);

	if (screen == null) {
		try {
			//Gdx.app.debug(TAG, "Creating new screen: " + screenType);
			final Screen newScreen = (Screen) ClassReflection.getConstructor(screenType.getScreenClass(), MyGdxGame.class).newInstance(this);
			screenCache.put(screenType, newScreen);
			setScreen(newScreen);
		} catch (ReflectionException e) {
			throw new GdxRuntimeException("Could not create screen: " + screenType, e);
		}
	} else {
		//Gdx.app.debug(TAG, "Switching to screen: " + screenType);
		setScreen(screen);
	}
}
}
