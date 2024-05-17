package com.mygdx.game.Managers;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.Constants;

import java.awt.*;

public class EnemyBullet {
    private static final int SPEED = 5;
    private static final int DAMAGE = 10;
    private int x, y;
    private double vx, vy;
    private boolean activeBullet;

    public EnemyBullet(int x, int y, int targetX, int targetY) {
        this.x = x;
        this.y = y;
        this.activeBullet = true;
        calculateVelocity(targetX, targetY);
    }

    private void calculateVelocity(int targetX, int targetY) {
        double angle = Math.atan2(targetY - y, targetX - x);
        vx = SPEED * Math.cos(angle);
        vy = SPEED * Math.sin(angle);
    }

    public int getDamage() {
        return DAMAGE;
    }

    public boolean isActive() {
        return activeBullet;
    }

    public void setInactive() {
        this.activeBullet = false;
    }

    public void update() {
        if (activeBullet) {
            x += vx;
            y += vy;

            // Deactivate bullet if it goes off-screen
            if (x < 0 || x > MyGdxGame.V_WIDTH || y < 0 || y > MyGdxGame.V_HEIGHT) {
                activeBullet = false;
            }
        }
    }

    public void draw(Graphics g) {
        if (activeBullet) {
            g.setColor(Color.RED);
            g.fillRect(x, y, 5, 5);
        }
    }

    public boolean intersects(PlayerManager player) {
        Vector2 playerPos = player.getPosition();
        float playerX = playerPos.x * Constants.PPM;
        float playerY = playerPos.y * Constants.PPM;
        int playerWidth = PlayerManager.WORLD_WIDTH;
        int playerHeight = PlayerManager.WORLD_HEIGHT;

        Rectangle bulletRect = new Rectangle(x, y, 5, 5);
        Rectangle playerRect = new Rectangle((int) playerX, (int) playerY, playerWidth, playerHeight);

        return bulletRect.intersects(playerRect);
    }
}
