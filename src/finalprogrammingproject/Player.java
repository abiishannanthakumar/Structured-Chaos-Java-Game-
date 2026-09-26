/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalprogrammingproject;

import static finalprogrammingproject.LevelManager.ching;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

/**
 * Author: Abiishan and Munsif
 * Date: Jan. 24, 2023 
 * Project Name: Player.java
 * Description: This class represents the player in the game. This class handles all the code related to the player, such as player movement, gravity, collision detection, etc.
 */

public class Player {
    LevelManager level;

    // Player positions and dimensions
    int x;
    int y;
    int width;
    int height;

    // Player movement and state flags
    boolean moveLeft = false;
    boolean moveRight = false;
    boolean canJump = true;

    // Player movement parameters (mostly to due with gravity)
    int fallingSpeed = 0;
    int gravitySpeed = 1;
    int jumpCount = 0;
    int maxJumpCount = 2; // Set this to the number of jumps allowed

    // Player hitboxes
    public Rectangle playerHitbox;
    Rectangle playerFeet = new Rectangle();
    Rectangle playerHead = new Rectangle();
    Rectangle playerLeft = new Rectangle();
    Rectangle playerRight = new Rectangle();

    // Player colors
    Color blue = new Color(57, 176, 227);
    Color darkBlue = new Color(0, 51, 102);

    // Constructor initializes player properties
    public Player(int newX, int newY, int newWidth, int newHeight, LevelManager newLevel) {
        this.x = newX;
        this.y = newY;
        this.width = newWidth;
        this.height = newHeight;
        this.level = newLevel;
        this.playerHitbox = new Rectangle(x, y, width, height);
    }

    // Method to draw the player on the game screen
    public void draw(Graphics2D g2) {
        g2.setColor(blue);
        g2.fillRect(x, y, width, height);

        // Draw the inner rectangle
        drawInnerRectangle(g2);
    }

    // Helper method to draw an inner rectangle as a design (to create a two-tone effect)
    private void drawInnerRectangle(Graphics2D g2) {
        // Calculate the dimensions for the inner rectangle
        int innerWidth = width - 20;
        int innerHeight = height - 20;
        int innerX = x + 10;
        int innerY = y + 10;

        // Draw the inner rectangle
        g2.setColor(darkBlue);
        g2.fillRect(innerX, innerY, innerWidth, innerHeight);
    }

    // Getter for the player hitbox
    public Rectangle getPlayerHitbox() {
        return playerHitbox;
    }

    // Method to handle player movement based on key events
    public void playerMoved(KeyEvent evt) {
        int keyCode = evt.getKeyCode();

        if (keyCode == 37 || keyCode == 65) {
            // LEFT ARROW or A key
            moveLeft = true;
        } else if (keyCode == 39 || keyCode == 68) {
            // RIGHT ARROW or D key
            moveRight = true;
        } else if (keyCode == 38 && (canJump || jumpCount < maxJumpCount) ||
                   keyCode == 87 && (canJump || jumpCount < maxJumpCount) ||
                   keyCode == 32 && (canJump || jumpCount < maxJumpCount)) {
            // UP ARROW or W key or SPACE key
            canJump = false;
            fallingSpeed = 17;
            jumpCount++;
        }
    }

    // Method to handle player stopping movement based on key events
    public void playerStopped(KeyEvent evt) {
        int keyCode = evt.getKeyCode();

        // LEFT ARROW or A key
        if (keyCode == 37 || keyCode == 65) {
            moveLeft = false;
        } else if (keyCode == 39 || keyCode == 68) {
            // RIGHT ARROW or D key
            moveRight = false;
        }
    }

    // Method to handle player movement and collisions with platforms
    public void playerMovement(Platform[] platforms, Obstacle[] obstacles, Obstacle[] movingObstacles) {
        int originalX = this.x;

        // Update player position based on movement flags
        if (moveLeft && this.x > 0) {
            this.x -= 5;
        } else if (moveRight && this.x <= 1865) {
            this.x += 5;
        }

        // Set bounds for player left and right hitboxes
        playerLeft.setBounds(this.x, this.y + 1, 10, 48);
        playerRight.setBounds(this.x + 40, this.y + 1, 10, 48);

        // Check for collisions with platforms
        for (Platform platform : platforms) {
            Rectangle platformRect = new Rectangle(platform.x, platform.y, platform.width, platform.height);

            if (playerLeft.intersects(platformRect) || playerRight.intersects(platformRect)) {
                // If collision is detected, return to original position
                this.x = originalX;
                break;
            }
        }

        // Update hitboxes after checking collisions
        updateHitboxes();
    }

    // Method to handle gravity and collisions with platforms and obstacles
    public void applyGravity(Platform[] platforms, Obstacle[] obstacles, Obstacle[] movingObstacles) {
        // Apply gravity
        fallingSpeed -= gravitySpeed;
        this.y -= fallingSpeed;

        // Update hitboxes after applying gravity
        updateHitboxes();

        // Check for collisions with platforms
        boolean onGround = false;

        for (Platform platform : platforms) {
            Rectangle platformRect = new Rectangle(platform.x, platform.y, platform.width, platform.height);

            if (playerFeet.intersects(platformRect)) {
                // If the player's feet instersects with the platform, adjust position and reset jump-related variables
                onGround = true;
                this.y = platform.y - 49;
                fallingSpeed = 0;
                canJump = true;
                jumpCount = 0;
                break;
            }

            if (playerHead.intersects(platformRect)) {
                // If player intersects with platform head, adjust position
                this.y = (int) platformRect.getMaxY();
                fallingSpeed = 0;
                break;
            }
        }

        // Check for collisions with obstacles
        for (Obstacle obstacle : obstacles) {
            if (playerFeet.intersects(obstacle.getTriangleHitbox())) {
                // Handle collision with obstacle
                handleObstacleCollision(obstacle);
                return;
            }
        }

        // Check for collisions with moving obstacles
        for (Obstacle movingObstacle : movingObstacles) {
            Rectangle movingObstacleRect = new Rectangle(movingObstacle.x, movingObstacle.y, movingObstacle.width, movingObstacle.height);

            if (playerFeet.intersects(movingObstacleRect)) {
                // Handle collision with moving obstacle
                handleMovingObstacleCollision(movingObstacle);
                return;
            }
        }

        // Adjusted condition for setting canJump
        if (onGround && fallingSpeed == 0) {
            canJump = true;
        } else {
            canJump = false;
        }
    }

    // Method to check for coin collisions and update game state
    public void checkCoins(Coin[] coins) {
        for (Coin coin : coins) {
            if (coin.collected == false) {
                if (playerHitbox.intersects(coin.getCoinHitbox())) {
                    // If player intersects with the coin's hitbox, the player collects the coin, a coin sound effect will be played, and the coin image will disappear from the game screen
                    coin.collected = true;
                    coin.coinsCollected++;
                    ching.setFramePosition(0); // Restart the sound back to the beginning of the clip
                    ching.start(); // Play the clip
                }
            }
        }
    }

    // Method to handle collisions with moving obstacles and update game state
    private void handleMovingObstacleCollision(Obstacle movingObstacle) {
        // If the player hits a moving obstacle, the player goes back to the start, loses a life, and loses all of their collected coins
        this.x = 0;
        this.y = 705;
        fallingSpeed = 0;
        canJump = true;
        jumpCount = 0;
        level.numLives = level.numLives - 1;
        
        // Reset collected coins
        for (Coin coin : level.coins) {
            coin.collected = false;
            Coin.coinsCollected = 0;
        }
    }

    // Method to handle collisions with obstacles and update game state
    private void handleObstacleCollision(Obstacle obstacle) {
        // If the player hits an obstacle, the player goes back to the start, loses a life, and loses all of their collected coins
        this.x = 0;
        this.y = 705;
        fallingSpeed = 0;
        canJump = true;
        jumpCount = 0;
        level.numLives = level.numLives - 1;
        
        // Reset collected coins
        for (Coin coin : level.coins) {
            coin.collected = false;
            Coin.coinsCollected = 0;
        }
    }

    // Method to update player hitboxes
    private void updateHitboxes() {
        playerFeet.setBounds(this.x, this.y + 40, 50, 10);
        playerHead.setBounds(this.x, this.y, 50, 10);
        playerLeft.setBounds(this.x, this.y + 1, 10, 48);
        playerRight.setBounds(this.x + 40, this.y + 1, 10, 48);
        playerHitbox.setBounds(x, y, width, height);
    }
}
