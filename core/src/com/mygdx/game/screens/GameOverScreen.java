package com.mygdx.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.MyGdxGame;

public class GameOverScreen extends ScreenAdapter {
    private final MyGdxGame context;
    private int level;


    public GameOverScreen(MyGdxGame context, int level) {
        this.context = context;
        this.level = level;
    }
}
