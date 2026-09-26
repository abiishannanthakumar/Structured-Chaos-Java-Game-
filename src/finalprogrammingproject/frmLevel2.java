/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalprogrammingproject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * Author: Abiishan and Munsif
 * Date: Jan. 24, 2023 
 * Project Name: frmLevel2.java
 * Description: This class is the specific code needed for level 2.
 */

public class frmLevel2 extends LevelManager {
    

    //here we place the speical code for level 2
    
    
    @Override
    public void loadMusic(){
      //LOADING SOUND EFFECTS
      
      // https://pixabay.com/music/search/platformer/
        
        try {
        AudioInputStream song = AudioSystem.getAudioInputStream(new File("LVLTwoMusic.wav"));

            music = AudioSystem.getClip();
            music.loop(0);//This will loop forever once played/
            music.open(song);
            
        } catch (Exception e) {
            System.out.println(e + "Song file not found");
        }
        
        try {
            //First we load in the sound effects/music.
            coinSound = AudioSystem.getAudioInputStream(new File("chingSound.wav"));
            //https://freesound.org/s/415209/ InspectorJ
            ching = AudioSystem.getClip();
            ching.open(coinSound);
            
        } catch (Exception e) {
            System.out.println(e + "Meow file not found");
        }
        
    }
    
    @Override
    public void drawBKGD(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        // BACKGROUND IMAGE
        //We must have a try catch to account for missing files
        try {
            //the background image is loaded
            bkgdImage = ImageIO.read(new File("levelTwoBKGD.jpg"));
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND!");
        }
        
        //BKGD
        g2.drawImage(bkgdImage, bkgdX, bkgdY, 1920, 980, null);
    }
    
    @Override
    public void loadPlatforms() {
        //This color is used as a parameter to make the platforms pale green in level 2
        Color paleGreen = new Color(104, 158, 120);

    
        platforms = new Platform[18];
        platforms[0] = new Platform(0, 980, 1920, 50, paleGreen);
        platforms[1] = new Platform(0, 800, 100, 180, paleGreen);
        platforms[2] = new Platform(0, 755, 200, 50, paleGreen); 
        platforms[3] = new Platform(160, 630, 350, 50, paleGreen);
        platforms[4] = new Platform(43, 521, 100, 50, paleGreen);
        platforms[5] = new Platform(305, 398, 100, 50, paleGreen);
        platforms[6] = new Platform(1012, 905, 200, 75, paleGreen);
        platforms[7] = new Platform(1280, 175, 150, 50, paleGreen);
        platforms[8] = new Platform(285, 820, 50, 50, paleGreen);
        platforms[9] = new Platform(1555, 450, 325, 50, paleGreen);
        platforms[10] = new Platform(780, 820, 50, 50, paleGreen);
        platforms[11] = new Platform(1565, 735, 250, 50, paleGreen);
        platforms[12] = new Platform(1195, 420, 250, 50, paleGreen);
        platforms[13] = new Platform(909, 625, 250, 50, paleGreen);
        platforms[14] = new Platform(10, 285, 200, 50, paleGreen);
        platforms[15] = new Platform(1625, 175, 297, 50, paleGreen);
        platforms[16] = new Platform(525, 450, 425, 50, paleGreen);
        platforms[17] = new Platform(768, 185, 200, 50, paleGreen);

    }


    @Override
    public void loadObstacles() {
        obstacles = new Obstacle[24];
        obstacles[0] = new Obstacle(150, 755, 175, 730, 200, 755); 
        obstacles[1] = new Obstacle(350, 980, 375, 955, 400, 980);
        obstacles[2] = new Obstacle(475, 980, 500, 955, 525, 980);
        obstacles[3] = new Obstacle(1012, 905, 1037, 880, 1062, 905);
        obstacles[4] = new Obstacle(1280, 980, 1305, 955, 1330, 980);
        obstacles[5] = new Obstacle(1480, 980, 1505, 955, 1530, 980);
        obstacles[6] = new Obstacle(1725, 735, 1750, 705, 1775, 735);
        obstacles[7] = new Obstacle(1600, 735, 1625, 705, 1650, 735);
        obstacles[8] = new Obstacle(1680, 980, 1705, 955, 1730, 980);
        obstacles[9] = new Obstacle(1110, 625, 1135, 600, 1160, 625);
        obstacles[10] = new Obstacle(840, 185, 865, 160, 890, 185);
        obstacles[11] = new Obstacle(600, 980, 625, 955, 650, 980);
        obstacles[12] = new Obstacle(305, 400, 330, 375, 355, 400);
        obstacles[13] = new Obstacle(10, 285, 35, 260, 60, 285);
        obstacles[14] = new Obstacle(1325, 175, 1350, 150, 1375, 175);
        obstacles[15] = new Obstacle(1580, 980, 1605, 955, 1630, 980);
        obstacles[16] = new Obstacle(1625, 175, 1650, 150, 1675, 175);
        obstacles[17] = new Obstacle(1393, 420, 1418, 395, 1443, 420);
        obstacles[18] = new Obstacle(525, 450, 550, 425, 575, 450);
        obstacles[19] = new Obstacle(900, 450, 925, 425, 950, 450);
        obstacles[20] = new Obstacle(725, 980, 750, 955, 775, 980);
        obstacles[21] = new Obstacle(1770, 980, 1795, 955, 1820, 980);
        obstacles[22] = new Obstacle(1162, 905, 1187, 880, 1212, 905);
        obstacles[23] = new Obstacle(1380, 980, 1405, 955, 1430, 980);

    }

    @Override
    public void movingObstacles() {
        //This color is used as a parameter to make the platforms red in level 2
        Color red = new Color(255, 51, 0);

        movingObstacles = new Obstacle[8];
        movingObstacles[0] = new Obstacle(450, 845, 40, 10, red);
        movingObstacles[1] = new Obstacle(326, 620, 40, 10, red);
        movingObstacles[2] = new Obstacle(100, 275, 40, 10, red);
        movingObstacles[3] = new Obstacle(575, 440, 40, 10, red);
        movingObstacles[4] = new Obstacle(777, 970, 40, 10, red);
        movingObstacles[5] = new Obstacle(1626, 440, 40, 10, red);
        movingObstacles[6] = new Obstacle(100, 970, 40, 10, red);
        movingObstacles[7] = new Obstacle(49, 511, 40, 10, red);


    }

    @Override
    public void loadCoins() {
        coins = new Coin[10];
        coins[0] = new Coin(30, 350, 50, 50);
        coins[1] = new Coin(906, 352, 50, 50);
        coins[2] = new Coin(670, 760, 50, 50);
        coins[3] = new Coin(848, 42, 50, 50);
        coins[4] = new Coin(1326, 52, 50, 50);
        coins[5] = new Coin(145, 840, 50, 50);
        coins[6] = new Coin(0, 50, 50, 50);
        coins[7] = new Coin(342, 144, 50, 50);
        coins[8] = new Coin(1662, 592, 50, 50);
        coins[8] = new Coin(1662, 592, 50, 50);
        coins[9] = new Coin(1840, 830, 50, 50);

    }
}
