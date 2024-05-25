package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemies {

    // Enemy Characteristics
    float movementSpeed; // world units per seconds
    private PlayerManager player;

    // Poistions & Dimensions
    float xPosition, yPosition;
    float width, height;

    // Graphics
    TextureRegion enemyTexture;

    // enemy movement

    private static final float MOVEMENT_SPEED = 1.0f;

    public Enemies(float movementSpeed,
                   float xCentre,
                   float yCentre,
                   float width,
                   float height,
                   TextureRegion enemyTexture,
                    PlayerManager player) {
        this.movementSpeed = movementSpeed;
        this.xPosition = xCentre - width / 2 ;
        this.yPosition = yCentre - height / 2;
        this.width = width;
        this.height = height;
        this.enemyTexture = enemyTexture;
        this.player = player;
    }

    public void draw(Batch batch){
        batch.draw(enemyTexture, xPosition, yPosition, width, height);
    }

    public void update(){

        if(xPosition < player.getPosition().x){
            xPosition += MOVEMENT_SPEED;
            System.out.println("coming na x");
        }else if(xPosition > player.getPosition().x){
            xPosition -= MOVEMENT_SPEED;
            System.out.println("coming na -x");
        }
        if(yPosition < player.getPosition().y){
            yPosition += MOVEMENT_SPEED;
            System.out.println("coming na y");
        }else if(yPosition > player.getPosition().y){
            yPosition -= MOVEMENT_SPEED;
            System.out.println("coming na -y");
        }
    }




}