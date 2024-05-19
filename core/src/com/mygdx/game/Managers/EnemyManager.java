package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.screens.GameScreen;

public abstract class EnemyManager extends Sprite {
    protected World world;
    protected GameScreen screen;

    public EnemyManager(GameScreen screen, MapManager map, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
    }

    protected abstract void defineEnemy();
}
