package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;

public enum ScreenType {
    GAME(GameScreen.class),
    LOADING(LoadingScreen.class),
    SETTING(SettingScreen.class),
    MENU(MenuScreen.class),
    SPLASH2(SplashTWO.class),
    LEVEL(LevelScreen.class),
    LOGIN(LoginScreen.class);  // Add the LOGIN screen type here

    private final Class<? extends Screen> screenClass;

    ScreenType(final Class<? extends Screen> screenClass) {
        this.screenClass = screenClass;
    }

    public Class<? extends Screen> getScreenClass() {
        return screenClass;
    }
}
