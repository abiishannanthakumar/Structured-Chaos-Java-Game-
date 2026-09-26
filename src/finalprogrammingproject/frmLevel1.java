
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalprogrammingproject;

import static finalprogrammingproject.LevelManager.ching;
import static finalprogrammingproject.LevelManager.coinSound;
import static finalprogrammingproject.LevelManager.music;
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
 * Project Name: frmLevel1.java
 * Description: This class is the specific code needed for level 1.
 */

public class frmLevel1 extends LevelManager {

    //here we place the speical code for level 1


    @Override
    public void loadMusic(){
      //LOADING SOUND EFFECTS
      
      // https://pixabay.com/music/search/platformer/
        
        try {
        AudioInputStream song = AudioSystem.getAudioInputStream(new File("LVLOneMusic.wav"));

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
            bkgdImage = ImageIO.read(new File("levelOneBKGD.jpg"));
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND!");
        }
        
        //BKGD
        g2.drawImage(bkgdImage, bkgdX, bkgdY, 1920, 980, null);
    }

    @Override
    public void loadPlatforms() {
        //This color is used as a parameter to make the platforms dark red in level 1
        Color darkRedPlatforms = new Color(139, 0, 0);

        platforms[0] = new Platform(0, 980, 1920, 50, darkRedPlatforms);
        platforms[1] = new Platform(0, 800, 100, 180, darkRedPlatforms);
        platforms[2] = new Platform(0, 755, 250, 50, darkRedPlatforms);
        platforms[3] = new Platform(325, 630, 350, 50, darkRedPlatforms);
        platforms[4] = new Platform(775, 780, 100, 50, darkRedPlatforms);
        platforms[5] = new Platform(925, 630, 175, 50, darkRedPlatforms);
        platforms[6] = new Platform(1012, 905, 200, 75, darkRedPlatforms);
        platforms[7] = new Platform(1212, 930, 150, 50, darkRedPlatforms);
        platforms[8] = new Platform(1362, 955, 100, 25, darkRedPlatforms);
        platforms[9] = new Platform(400, 855, 325, 125, darkRedPlatforms);
        platforms[10] = new Platform(1525, 855, 250, 50, darkRedPlatforms);
        platforms[11] = new Platform(1375, 705, 250, 50, darkRedPlatforms);
        platforms[12] = new Platform(1225, 555, 250, 50, darkRedPlatforms);
        platforms[13] = new Platform(1075, 405, 250, 50, darkRedPlatforms);
        platforms[14] = new Platform(0, 175, 950, 50, darkRedPlatforms);
        platforms[15] = new Platform(1250, 175, 670, 50, darkRedPlatforms);
        platforms[16] = new Platform(525, 450, 425, 50, darkRedPlatforms);
        platforms[17] = new Platform(1625, 450, 200, 50, darkRedPlatforms);
    }



    @Override
    public void loadObstacles() {
        obstacles[0] = new Obstacle(200, 755, 225, 730, 250, 755);
        obstacles[1] = new Obstacle(400, 855, 425, 830, 450, 855);
        obstacles[2] = new Obstacle(675, 855, 700, 830, 725, 855);
        obstacles[3] = new Obstacle(1012, 905, 1037, 880, 1062, 905);
        obstacles[4] = new Obstacle(1212, 930, 1237, 905, 1262, 930);
        obstacles[5] = new Obstacle(1362, 955, 1387, 930, 1412, 955);
        obstacles[6] = new Obstacle(1725, 855, 1750, 830, 1775, 855);
        obstacles[7] = new Obstacle(1575, 705, 1600, 680, 1625, 705);
        obstacles[8] = new Obstacle(1425, 555, 1450, 530, 1475, 555);
        obstacles[9] = new Obstacle(1275, 405, 1300, 380, 1325, 405);
        obstacles[10] = new Obstacle(825, 175, 850, 150, 875, 175);
        obstacles[11] = new Obstacle(675, 175, 700, 150, 725, 175);
        obstacles[12] = new Obstacle(525, 175, 550, 150, 575, 175);
        obstacles[13] = new Obstacle(375, 175, 400, 150, 425, 175);
        obstacles[14] = new Obstacle(1325, 175, 1350, 150, 1375, 175);
        obstacles[15] = new Obstacle(1475, 175, 1500, 150, 1525, 175);
        obstacles[16] = new Obstacle(1625, 175, 1650, 150, 1675, 175);
        obstacles[17] = new Obstacle(1775, 175, 1800, 150, 1825, 175);
        obstacles[18] = new Obstacle(525, 450, 550, 425, 575, 450);
        obstacles[19] = new Obstacle(900, 450, 925, 425, 950, 450);
        obstacles[20] = new Obstacle(1870, 980, 1895, 955, 1920, 980);
        obstacles[21] = new Obstacle(1820, 980, 1845, 955, 1870, 980);
        obstacles[22] = new Obstacle(1770, 980, 1795, 955, 1820, 980);
        obstacles[23] = new Obstacle(1162, 905, 1187, 880, 1212, 905);
        obstacles[24] = new Obstacle(1312, 930, 1337, 905, 1362, 930);

    }



    @Override
    public void movingObstacles() {
        //This color is used as a parameter to make the platforms light green in level 1
        Color lightGreen = new Color(144, 238, 144);

        movingObstacles[0] = new Obstacle(450, 845, 40, 10, lightGreen);
        movingObstacles[1] = new Obstacle(326, 620, 40, 10, lightGreen);
        movingObstacles[2] = new Obstacle(335, 165, 40, 10, lightGreen);
        movingObstacles[3] = new Obstacle(575, 440, 40, 10, lightGreen);
        movingObstacles[4] = new Obstacle(725, 970, 40, 10, lightGreen);
        movingObstacles[5] = new Obstacle(1626, 440, 40, 10, lightGreen);
        movingObstacles[6] = new Obstacle(100, 970, 40, 10, lightGreen);
        movingObstacles[7] = new Obstacle(1462, 970, 40, 10, lightGreen);

    }

    @Override
    public void loadCoins() {
        coins[0] = new Coin(1262, 730, 50, 50);
        coins[1] = new Coin(100, 805, 50, 50);
        coins[2] = new Coin(725, 820, 50, 50);
        coins[3] = new Coin(1870, 350, 50, 50);
        coins[4] = new Coin(1162, 605, 50, 50);
        coins[5] = new Coin(538, 725, 50, 50);
        coins[6] = new Coin(0, 120, 50, 50);
        coins[7] = new Coin(714, 250, 50, 50);

    }
}
