/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalprogrammingproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Author: Abiishan and Munsif
 * Date: Jan. 24, 2023 
 * Project Name: Door.java
 * Description: This class represents the final door (finish line) in the game.
 */

public class Door {

    int x;
    int y; 
    int width;
    int height;
    
    boolean finalDoor = false;

    public Rectangle doorHitbox;
    
    public Door(int newX, int newY, int newWidth, int newHeight){
        this.x = newX;
        this.y = newY;
        this.width = newWidth;
        this.height = newHeight;
        
        // Initialize the hitbox
        this.doorHitbox = new Rectangle(x, y, width, height);
    }
    
    public void draw(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.fillRect(x, y, width, height);
        
        // Draw the inner rectangle
        drawInnerRectangle(g2);    
    }
    
    private void drawInnerRectangle(Graphics2D g2) {
        
        // Calculate the dimensions for the inner rectangle
        int innerX = x + 10;
        int innerY = y + 10;
        int innerWidth = width - 20;
        int innerHeight = height - 20;

        // Draw the inner rectangle
        g2.setColor(Color.WHITE);
        g2.fillRect(innerX, innerY, innerWidth, innerHeight);
        
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(1882, 136, 1890, 136);
        
    }
    
    public Rectangle getDoorHitbox() {
        return doorHitbox;
    }
}
