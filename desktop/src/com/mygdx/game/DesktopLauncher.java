package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
	public static void main(String[] arg) {
		// Configure and launch the libGDX application
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Last Stand Chronicles");
		config.setWindowIcon("resources/libgdx192.png","resources/logo_144.png","resources/logo_96.png","resources/logo_72.png","resources/logo_48.png");


		new Lwjgl3Application((ApplicationListener) new MyGdxGame(), config);
	}
}
