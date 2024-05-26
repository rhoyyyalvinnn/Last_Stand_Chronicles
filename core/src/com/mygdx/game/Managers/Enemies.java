package com.mygdx.game.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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

    private static final float MOVEMENT_SPEED = 2.0f;

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

    public void update(float delta){

        Vector2 enemyPosition = new Vector2(xPosition, yPosition);
        Vector2 playerPosition = player.getPosition();

        // Calculate the direction from the enemy to the player
        Vector2 direction = new Vector2(playerPosition.x - enemyPosition.x, playerPosition.y - enemyPosition.y).nor();

        // Calculate the movement vector based on the direction and movement speed
        Vector2 movement = direction.scl(movementSpeed * delta);

        // Update the enemy's position based on the movement vector
        xPosition += movement.x;
        yPosition += movement.y;



        // test 4

//        Vector2 playerPosition = player.getPosition();
//        Vector2 enemyPosition = new Vector2(xPosition, yPosition);
//
//        Vector2 direction = playerPosition.cpy().sub(enemyPosition).nor();
//        Vector2 movement = direction.scl(movementSpeed * delta);
//
//        xPosition += movement.x;
//        yPosition += movement.y;

        //test 3
//        if (player == null) {
//            System.out.println("Player is not set");
//            return;
//        }
//
//        // Get the player's position
//        float playerX = player.getPosition().x;
//        float playerY = player.getPosition().y;
//
//        // Calculate the direction vector from the enemy to the player
//        float directionX = playerX - xPosition;
//        float directionY = playerY - yPosition;
//
//        // Normalize the direction vector (make its length 1)
//        float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);
//        if (length != 0) {
//            directionX /= length;
//            directionY /= length;
//        }
//
//        // Multiply the direction vector by the movement speed to get the velocity
//        float velocityX = directionX * MOVEMENT_SPEED;
//        float velocityY = directionY * MOVEMENT_SPEED;
//
//        // Update the enemy's position
//        xPosition += velocityX;
//        yPosition += velocityY;
//
//        // Log the movement for debugging purposes
//        System.out.println("Enemy moving towards player: (" + xPosition + ", " + yPosition + ")");


        //test 1
//        float playerX = player.getPosition().x;
//        float playerY = player.getPosition().y;
//
//        // Calculate the direction vector from the enemy to the player
//        float directionX = playerX - xPosition;
//        float directionY = playerY - yPosition;
//
//        // Normalize the direction vector (make its length 1)
//        float length = (float) Math.sqrt(directionX * directionX + directionY * directionY);
//        if (length != 0) {
//            directionX /= length;
//            directionY /= length;
//        }
//
//        // Multiply the direction vector by the movement speed to get the velocity
//        float velocityX = directionX * MOVEMENT_SPEED;
//        float velocityY = directionY * MOVEMENT_SPEED;
//
//        // Update the enemy's position
//        xPosition += velocityX;
//        yPosition += velocityY;
//
//        // Log the movement for debugging purposes
//        System.out.println("Enemy moving towards player: (" + xPosition + ", " + yPosition + ")");




        //test2
//        if(xPosition < player.getPosition().x){
//            xPosition += MOVEMENT_SPEED;
//            System.out.println("coming na x");
//        }else if(xPosition > player.getPosition().x){
//            xPosition -= MOVEMENT_SPEED;
//            System.out.println("coming na -x");
//        }
//        if(yPosition < player.getPosition().y){
//            yPosition += MOVEMENT_SPEED;
//            System.out.println("coming na y");
//        }else if(yPosition > player.getPosition().y){
//            yPosition -= MOVEMENT_SPEED;
//            System.out.println("coming na -y");
//        }
    }



    public void setPlayer(PlayerManager player) {
        this.player = player;
    }

    public Rectangle getBoundingBox() {
        return new Rectangle(xPosition, yPosition, width, height);
    }


}