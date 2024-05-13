package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;

public enum ScreenType {
LOADING(LoadingScreen.class),
    MENU(MenuScreen.class);

    private final Class <? extends Screen> screenClass;
    ScreenType(final Class<? extends Screen> screenClass) {
        this.screenClass = screenClass;
    }
    public Class<? extends Screen> getScreenClass() {
        return screenClass;
    }
}
