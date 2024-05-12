package com.mygdx.game.screens;

import com.badlogic.gdx.Screen;

public enum ScreenType {

    MENU(Menu.class);

    private final Class <? extends Screen> screenClass;
    ScreenType(final Class<? extends Screen> screenClass) {
        this.screenClass = screenClass;
    }
    public Class<? extends Screen> getScreenClass() {
        return screenClass;
    }
}
