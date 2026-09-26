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
 * Project Name: Obstacle.java
 * Description: This class represents the obstacles and moving obstacles in the game.
 */

public class Obstacle {
    
    // Triangle coordinates
    int x; // also used for rectangles
    int x2;
    int x3;
    int y; // also used for rectangles
    int y2;
    int y3;
    
    // Rectangle coordinates
    int width;
    int height;
        
    // Arrays to store triangle coordinates
    int xCoords[];
    int yCoords[];
    
    // Direction of movement (1 for right, -1 for left)
    private int direction = 1;
    
    // Boolean to check if it's a triangle
    boolean isTriangle;
    
    // Rectangle hitbox for moving obstacles
    public Rectangle rectHitbox;
    
    // Triangle hitbox for stationary obstacles
    public Rectangle triHitbox;
    
    // Colors for drawing
    Color pink = new Color(255, 105, 180);
    Color darkPurple = new Color(48, 25, 52);
    Color movingObstacleColor;
    

    // Constructor for triangle obstacles
    public Obstacle(int newX, int newY, int newX2, int newY2, int newX3, int newY3){
        this.x = newX;
        this.x2 = newX2;
        this.x3 = newX3;
        this.y = newY;
        this.y2 = newY2;
        this.y3 = newY3;
        
        xCoords = new int[] {x, x2, x3};
        yCoords = new int[] {y, y2, y3};
        
        isTriangle = true;
        
        width = 50;
        height = 25;
        
        this.triHitbox = new Rectangle(x, y - 25, width, height);
    }
    
    // Constructor for rectangular obstacles
    public Obstacle(int newX, int newY, int newWidth, int newHeight, Color newColor){
        this.x = newX;
        this.y = newY;
        this.width = newWidth;
        this.height = newHeight;
        this.movingObstacleColor = newColor;
        
        isTriangle = false;
        
        // Initialize the hitbox
        this.rectHitbox = new Rectangle(x, y, width, height);
    }
    
    // Draw method for the obstacle
    public void draw(Graphics2D g2){

        if (isTriangle == true) {
            drawTriangle(g2);
        } else {
            drawRectangle(g2);
        }
        
    }
    
    // Draw a triangle obstacle
    public void drawTriangle(Graphics2D g2) {
        g2.setColor(pink);

        g2.fillPolygon(xCoords, yCoords, 3);
        
        // Draw the inner triangle
        drawInnerTriangle(g2);
    }
    
    // Draw a rectangular obstacle
    public void drawRectangle(Graphics2D g2) {
        g2.setColor(movingObstacleColor);
        g2.fillRect(x, y, width, height);
        
        // Draw the inner rectangle
        drawInnerRectangle(g2);
    }
    
    // Draw the inner triangle with adjusted coordinates
    private void drawInnerTriangle(Graphics2D g2) {
        // Calculate the coordinates for the inner triangle
        
        int innerX = x;
        int innerY = y;
        int innerX2 = x2;
        int innerY2 = y2;
        int innerX3 = x3;
        int innerY3 = y3;
        
        if (y == y3) {
            innerX = x + 5;
            innerY = y;
            innerX2 = x2;
            innerY2 = y2 + 5;
            innerX3 = x3 - 5;
            innerY3 = y3;   
        } else if (x == x3 && x < 1000) {
            innerX = x;
            innerY = y + 5;
            innerX2 = x2 - 5;
            innerY2 = y2;
            innerX3 = x3;
            innerY3 = y3 - 5;            
        } else if (x == x3 && x > 1000) {
            innerX = x;
            innerY = y + 5;
            innerX2 = x2 + 5;
            innerY2 = y2;
            innerX3 = x3;
            innerY3 = y3 - 5; 
        }


        int[] innerXCoords = {innerX, innerX2, innerX3};
        int[] innerYCoords = {innerY, innerY2, innerY3};

        // Draw the inner triangle
        g2.setColor(darkPurple); 
        g2.fillPolygon(innerXCoords, innerYCoords, 3);
    }
    
    // Draw the inner rectangle
    private void drawInnerRectangle(Graphics2D g2) {
        // Calculate the dimensions for the inner rectangle
        int innerX = x + 2;
        int innerY = y + 2;
        int innerWidth = width - 4;
        int innerHeight = height - 4;

        // Draw the inner rectangle
        g2.setColor(movingObstacleColor.darker());
        g2.fillRect(innerX, innerY, innerWidth, innerHeight);
    }
    
    // Set the direction of movement
    public void setDirection(int direction) {
        this.direction = direction;
    }

    // Get the direction of movement
    public int getDirection() {
        return direction;
    }
    
    // Get the hitbox for moving obstacles
    public Rectangle getRectangleHitbox() {
        return rectHitbox;
    }

    // Get the hitbox for stationary obstacles
    public Rectangle getTriangleHitbox() {
        return triHitbox;
    }

}
