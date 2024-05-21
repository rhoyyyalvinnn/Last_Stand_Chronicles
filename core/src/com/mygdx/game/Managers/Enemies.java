package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemies {

    // Enemy Characteristics
    float movementSpeed; // world units per seconds


    // Poistions & Dimensions
    float xPosition, yPosition;
    float width, height;

    // Graphics
    TextureRegion enemyTexture;

    public Enemies(float movementSpeed,
                   float xCentre,
                   float yCentre,
                   float width,
                   float height,
                   TextureRegion enemyTexture) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xCentre - width / 2 ;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.enemyTexture = enemyTexture;
    }

    public void draw(Batch batch){
        batch.draw(enemyTexture, xPosition, yPosition, width, height);
    }




}
