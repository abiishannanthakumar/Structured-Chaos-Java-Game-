/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalprogrammingproject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Author: Abiishan and Munsif
 * Date: Jan. 24, 2023 
 * Project Name: Coin.java
 * Description: This class represents the coins in the game.
 */

public class Coin {

    int x;
    int y;
    int width;
    int height;
    
    // Used for the player to be able identify whether it collected a coin or not
    boolean collected = false;
    public static int coinsCollected = 0;
    
    Color yellow = new Color(255, 253, 175);
    Color darkYellow = new Color(219, 195, 0);


    public Rectangle coinHitbox;

    public Coin(int newX, int newY, int newWidth, int newHeight) {

        this.x = newX;
        this.y = newY;
        this.width = newWidth;
        this.height = newHeight;

        // Initialize the hitbox
        this.coinHitbox = new Rectangle(x, y, width, height);

    }

    public void draw(Graphics2D g2) {
        if (this.collected == false){
            g2.setColor(yellow);
            g2.fillOval(x, y, width, height);


            //Draw the inner circle
            drawInnerCircle(g2);
        }
    }

    public void drawInnerCircle(Graphics2D g2) {

        int innerX = x + 10;
        int innerY = y + 10;
        int innerWidth = width - 20;
        int innerHeight = height - 20;

        //Draw the inner circle
        g2.setColor(darkYellow);
        g2.fillOval(innerX, innerY, innerWidth, innerHeight);
    }

    // Gets the coin hitbox
    public Rectangle getCoinHitbox() {
        return coinHitbox;
    }

}
