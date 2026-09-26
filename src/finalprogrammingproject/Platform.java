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
 * Project Name: Platform.java
 * Description: This class represents the platforms in the game.
 */

public class Platform {
    
    // Position and size of the platform
    int x;
    int y; 
    int width;
    int height;

    // Color of the platform
    Color platformColor;
    
    // Hitbox and barriers for collision detection
    public Rectangle platHitbox;     // Main hitbox of the platform
    public Rectangle leftBarrier;    // Left barrier for collision with moving obstacles
    public Rectangle rightBarrier;   // Right barrier for collision with moving obstacles
    
    //Constructor to initialize platform and platform properties
    public Platform(int newX, int newY, int newWidth, int newHeight, Color newColor){
        this.x = newX;
        this.y = newY;
        this.width = newWidth;
        this.height = newHeight;
        this.platformColor = newColor;
        
        // Initialize the hitbox and barriers
        this.platHitbox = new Rectangle(x, y, width, height);
        this.leftBarrier = new Rectangle(x, y - 1, 1, 1);
        this.rightBarrier = new Rectangle(x + (width - 1), y - 1, 1, 1);
    }
    
    //Draws the platforms on the game screen
    public void draw(Graphics2D g2){
        // Draw the main platform
        g2.setColor(platformColor);
        g2.fillRect(x, y, width, height);
        
        // Draw the inner rectangle
        drawInnerRectangle(g2);    
    }
    
    // Draws the inner rectangle of the platform as a desgin (creating a two-tone effect)
    private void drawInnerRectangle(Graphics2D g2) {
        // Calculate the dimensions for the inner rectangle
        int innerX = x + 10;
        int innerY = y + 10;
        int innerWidth = width - 20;
        int innerHeight = height - 20;

        // Draw the inner rectangle
        g2.setColor(platformColor.darker());
        g2.fillRect(innerX, innerY, innerWidth, innerHeight);
    }
    
    // Returns the left barrier of the platform
    public Rectangle leftBarrier() {
        return leftBarrier;
    }
    
    // Returns the right barrier of the platform
    public Rectangle rightBarrier() {
        return rightBarrier;
    }
    

    // Returns the platform's main hitbox
    public Rectangle getPlatHitbox() {
        return platHitbox;
    }
    
}