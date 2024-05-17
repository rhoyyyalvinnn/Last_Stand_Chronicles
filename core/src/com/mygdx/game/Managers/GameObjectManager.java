package com.mygdx.game.Managers;

import java.awt.*;

public abstract class GameObjectManager {

    protected int x, y;
    protected int width, height;

    public GameObjectManager(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public abstract void update();

    public abstract void draw(Object GameAssets);

    public abstract void draw(Graphics g);

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    protected boolean isActive() {
        return true;
    }


}
