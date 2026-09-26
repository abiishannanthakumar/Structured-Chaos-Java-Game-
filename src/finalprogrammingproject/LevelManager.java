/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package finalprogrammingproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 * Author: Abiishan and Munsif
 * Date: Jan. 24, 2023 
 * Project Name: LevelManager.java
 * Description: This level manager stores all related code between levels one and two so there are no sections of duplicated code anywhere. Levels One and Two use this code when they start and they override the necessary code that the specific level needs. 
 */


public class LevelManager extends JFrame{

    // Variables to store game-related information
    long startTime = System.currentTimeMillis();  // To store the start time of the game
    long pausedTimeElapsed = 0;

    int bkgdX = 0;
    int bkgdY = 0;

    int livesX = 1820;
    int livesY = 10;
    int livesWidth = 50;
    int livesHeight = 50;

    // Image Variables
    Image bkgdImage;
    Image heartImage;

    // Boolean flags to control game state
    boolean allCoinsCollected = false;
    boolean pauseGame = false;
    boolean showHB = false;
    boolean gameOver = false;
    boolean lvlComplete = false;

    int score;
    int timerCounter = 0;
    int numLives = 3;

    // Sound effect variables and levels 1 and 2 background music
    public static Clip music;
    public static long songTime = 0;
    public static AudioInputStream coinSound;
    public static Clip ching;
    
    Color darkRed = new Color(139, 0, 0);

    // Timer to update the game at a fixed rate
    Timer timer = new Timer(1000 / 60, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Update the game logic
            updateGame();
            // Increment the timer counter
            timerCounter++;

            // Calculate the current time elapsed since the start of the game
            long currentTime = System.currentTimeMillis() - startTime;

            // Convert the elapsed time to minutes, seconds, and milliseconds
            long minutes = (currentTime / 1000) / 60;      // Calculate total minutes
            long seconds = (currentTime / 1000) % 60;      // Calculate remaining seconds after removing minutes
            long milliseconds = currentTime % 1000;        // Calculate remaining milliseconds after removing seconds

            // Update a label to display the elapsed time in the format: "minutes : seconds : milliseconds"
            lblTime.setText((minutes) + " : " + (seconds) + " : " + (milliseconds));

            // Update a label to display the number of coins collected
            lblCoinsCollected.setText("Number of Coins: " + (Coin.coinsCollected));
        }
    });

    /**
     * Creates new form frmGamePlay
     */
    public LevelManager() {
        initComponents();  // Initialize GUI components
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        lblPause.setVisible(false);
        lblGameOver.setVisible(false);
        lblLVLComplete.setVisible(false);
        lblScore.setVisible(true);
        btnResume.setVisible(false);
        btnNextLevel.setVisible(false);
        btnRestart.setVisible(false);
        btnBack.setVisible(false);

        timer.start();  // Start the game timer

        loadMusic();  // Load background music and sound effects
        loadPlatforms();  // Load static platforms
        loadObstacles();  // Load static obstacles and moving obstacles
        movingObstacles();  // Load moving obstacles
        loadCoins();  // Load coins

        music.start();  // Start playing background music
        music.setMicrosecondPosition(songTime);
        music.loop(-1);

        // Load heart image for player lives
        try {
            heartImage = ImageIO.read(new File("heart.png"));
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND!");
        }
    }
    
    // Method to draw the background
    public void drawBKGD(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        try {
            // Loads the background image
            bkgdImage = ImageIO.read(new File("levelOneBKGD.jpg"));
        } catch (IOException e) {
            System.out.println("FILE NOT FOUND!");
        }

        // Draws the background
        g2.drawImage(bkgdImage, bkgdX, bkgdY, 1920, 980, null);
    }
    
    public void loadMusic() {
        // LOADING GAME MUSIC AND SOUND EFFECTS

        // Load the background music for the game level
        // Source: https://mixkit.co/free-sound-effects/coin/
        // Source: https://pixabay.com/music/search/platformer/
        try {
            // Load the audio file for the background music
            AudioInputStream song = AudioSystem.getAudioInputStream(new File("LVLOneMusic.wav"));

            // Create and configure a Clip to play the background music
            music = AudioSystem.getClip();
            music.loop(0);  // This will play the music once (no looping)
            music.open(song);

        } catch (Exception e) {
            // Handle exceptions related to loading or playing the background music
            System.out.println(e + "Song file not found");
        }

        // Load the sound effect for collecting coins
        // Source: https://freesound.org/s/415209/ InspectorJ
        try {
            // Load the audio file for the coin collecting sound effect
            coinSound = AudioSystem.getAudioInputStream(new File("chingSound.wav"));

            // Create and configure a Clip to play the coin collecting sound effect
            ching = AudioSystem.getClip();
            ching.open(coinSound);

        } catch (Exception e) {
            // Handle exceptions related to loading or playing the coin collecting sound effect
            System.out.println(e + "Coin file not found");
        }
    }

    //loads the player object from the player class
    public Player player = new Player(0, 705, 50, 50,this);

    // Used to store all of the platforms used in a level
    public Platform platforms[] = new Platform[18];

    //Method to load platforms
    public void loadPlatforms() {
        platforms[0] = new Platform(0, 980, 1920, 50, darkRed);
        platforms[1] = new Platform(0, 800, 100, 180, darkRed);
        platforms[2] = new Platform(0, 755, 250, 50, darkRed);
        platforms[3] = new Platform(325, 630, 350, 50, darkRed);
        platforms[4] = new Platform(775, 780, 100, 50, darkRed);
        platforms[5] = new Platform(925, 630, 175, 50, darkRed);
        platforms[6] = new Platform(1012, 905, 200, 75, darkRed);
        platforms[7] = new Platform(1212, 930, 150, 50, darkRed);
        platforms[8] = new Platform(1362, 955, 100, 25, darkRed);
        platforms[9] = new Platform(400, 855, 325, 125, darkRed);
        platforms[10] = new Platform(1525, 855, 250, 50, darkRed);
        platforms[11] = new Platform(1375, 705, 250, 50, darkRed);
        platforms[12] = new Platform(1225, 555, 250, 50, darkRed);
        platforms[13] = new Platform(1075, 405, 250, 50, darkRed);
        platforms[14] = new Platform(0, 175, 950, 50, darkRed);
        platforms[15] = new Platform(1250, 175, 670, 50, darkRed);
        platforms[16] = new Platform(525, 450, 425, 50, darkRed);
        platforms[17] = new Platform(1625, 450, 200, 50, darkRed);
    }

    // Used to store all of the obstacles used in a level
    public Obstacle obstacles[] = new Obstacle[25];

    //Method to load obstacles
    public void loadObstacles() {
        obstacles[0] = new Obstacle(200, 755, 225, 730, 250, 755); //Horizontal
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

    // Used to store all of the moving obstacles used in a level
    public Obstacle movingObstacles[] = new Obstacle[8];

    //Method to load moving obstacles
    public void movingObstacles() {
        Color darkGreen = new Color(2, 48, 32);

        movingObstacles[0] = new Obstacle(450, 845, 40, 10, darkGreen);
        movingObstacles[1] = new Obstacle(326, 620, 40, 10, darkGreen);
        movingObstacles[2] = new Obstacle(335, 165, 40, 10, darkGreen);
        movingObstacles[3] = new Obstacle(575, 440, 40, 10, darkGreen);
        movingObstacles[4] = new Obstacle(725, 970, 40, 10, darkGreen);
        movingObstacles[5] = new Obstacle(1626, 440, 40, 10, darkGreen);
        movingObstacles[6] = new Obstacle(100, 970, 40, 10, darkGreen);
        movingObstacles[7] = new Obstacle(1462, 970, 40, 10, darkGreen);
    }

    // Used to store all of the coins used in a level
    public Coin coins[] = new Coin[8];

    //Method to load coins    
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

    //loads the final door (finish line) from the door class
    public Door finalDoor = new Door(1870, 100, 50, 75);

    // Method to move all the moving obstacles left and right in the game
    private void moveMovingObstacles() {
        // Repeats through each moving obstacle in the array
        for (Obstacle movingObstacle : movingObstacles) {

            // Move the obstacle horizontally based on its direction and speed
            movingObstacle.x += 2 * movingObstacle.getDirection();

            // Update the hitbox position to match the new obstacle position
            movingObstacle.getRectangleHitbox().setLocation(movingObstacle.x, movingObstacle.y);

            // Check if the obstacle reaches the edge of a platform
            if (checkPlatformEdge(movingObstacle)) {

                // Reverse the direction of the obstacle if it reaches the edge
                movingObstacle.setDirection(-movingObstacle.getDirection());
            }
        }
    }

private boolean checkPlatformEdge(Obstacle movingObstacle) {
    // Check if the movingObstacle collides with any platform
    for (Platform platform : platforms) {
        // Check for collision with the platform's main hitbox
        if (movingObstacle.getRectangleHitbox().intersects(platform.getPlatHitbox())
                // Check for collision with the left barrier of the platform
                || movingObstacle.getRectangleHitbox().intersects(platform.leftBarrier())
                // Check for collision with the right barrier of the platform
                || movingObstacle.getRectangleHitbox().intersects(platform.rightBarrier())) {
            return true; // MovingObstacle collides with a platform
        }
    }

    // Check if the movingObstacle collides with any triangular obstacle
    for (Obstacle obstacle : obstacles) {
        if (movingObstacle.getRectangleHitbox().intersects(obstacle.getTriangleHitbox())) {
            return true; // MovingObstacle collides with a triangular obstacle
        }
    }

    // MovingObstacle does not collide with any platform or obstacle
    return false;
}

    public void updateGame() {
        // Handles the player's gravity method based on platform and obstacle collisions
        player.applyGravity(platforms, obstacles, movingObstacles);

        // Handles player movement based on user input and collisions
        player.playerMovement(platforms, obstacles, movingObstacles);

        // Repaints the game panel to reflect the updated game state
        panDraw.repaint();

        // Updates the positions of moving obstacles
        moveMovingObstacles();

        // Check for coin collisions and updates the coin count
        player.checkCoins(coins);

        // Checks if all coins are collected
        if (Coin.coinsCollected == coins.length) {
            allCoinsCollected = true;
        } else {
            allCoinsCollected = false;
        }

        // Checks for collision with the door and whether all coins are collected
        if (allCoinsCollected && player.getPlayerHitbox().intersects(finalDoor.doorHitbox)) {
            lvlComplete = true;
            timer.stop(); // Stop the timer to freeze the game
        }

        // Checks if the player has run out of lives
        if (numLives == 0) {
            gameOver = true;
            timer.stop(); // Stops the timer to pause the game
        }
    }

    public void draw(Graphics g) {
        
        // Convert Graphics object to Graphics2D for advanced drawing operations
        Graphics2D g2 = (Graphics2D) g;

        // Draws the image background
        drawBKGD(g);

        // Draws PLATFORMS from their arrays
        for (Platform platform : platforms) {
            platform.draw(g2);
        }

        // Draws OBSTACLES from their arrays
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g2);
        }

        // Draws MOVING OBSTACLES from their arrays
        for (Obstacle movingObstacle : movingObstacles) {
            movingObstacle.draw(g2);
        }

        // Draws COINS from their arrays
        for (Coin coin : coins) {
            coin.draw(g2);
        }

        // Draws PLAYER
        player.draw(g2);

        // Draws LIVES
        for (int x = 0; x < numLives; x++) {
            int currentLivesX = livesX - (x * (livesWidth + 5));  // Adjusts the spacing between the hearts
            g2.drawImage(heartImage, currentLivesX, livesY, livesWidth, livesHeight, null);
        }

        // Draws the pause screen if the game is paused
        if (pauseGame) {
            drawPauseScreen(g2);
        }

        // Draws hitboxes if enabled
        if (showHB) {
            showHitboxes(g2);
        }

        // Draws the FINAL DOOR if all coins are collected
        if (allCoinsCollected) {
            finalDoor.draw(g2);
        }

        // Draws the "Game Over" screen if the game is over
        if (gameOver) {
            drawGameOverScreen(g2);
        }

        // Draws the level completion screen if the level is completed
        if (lvlComplete) {
            drawLevelCompleteScreen(g2);
        }
    }

    private void drawPauseScreen(Graphics2D g2) {
        g2.setColor(darkRed);
        g2.fillRect(1120 / 2, 580 / 2, 800, 400);

        g2.setColor(Color.BLACK);
        g2.fillRect((1120 / 2) + 20, (580 / 2) + 20, 800 - 40, 400 - 40);
    }

    private void drawGameOverScreen(Graphics2D g2) {
        g2.setColor(darkRed);
        g2.fillRect(1120 / 2, 580 / 2, 800, 400);

        g2.setColor(Color.BLACK);
        g2.fillRect((1120 / 2) + 20, (580 / 2) + 20, 800 - 40, 400 - 40);

        // Displays "Game Over" label and buttons
        lblGameOver.setVisible(true);
        btnNextLevel.setVisible(true);
        btnRestart.setVisible(true);
        btnBack.setVisible(true);
        panDraw.repaint();
    }


    private void drawLevelCompleteScreen(Graphics2D g2) {
        g2.setColor(darkRed);
        g2.fillRect(1120 / 2, 580 / 2, 800, 400);

        g2.setColor(Color.BLACK);
        g2.fillRect((1120 / 2) + 20, (580 / 2) + 20, 800 - 40, 400 - 40);

        // Displays "Level Complete" label, score, and buttons
        lblLVLComplete.setVisible(true);
        score = (10000 - timerCounter) + (2500 * numLives);
        lblScore.setVisible(true);
        lblScore.setText("Your Score: " + score);

        btnNextLevel.setVisible(true);
        btnRestart.setVisible(true);
        btnBack.setVisible(true);
        panDraw.repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LevelManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LevelManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LevelManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LevelManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LevelManager().setVisible(true);
            }
        });
    }

    private void showHitboxes(Graphics2D g2) {

        // FINAL DOOR HITBOX
        g2.setColor(Color.PINK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(finalDoor.doorHitbox.x, finalDoor.doorHitbox.y, finalDoor.doorHitbox.width, finalDoor.doorHitbox.height);

        // PLAYER HITBOX
        g2.drawRect(player.playerHitbox.x, player.playerHitbox.y, player.playerHitbox.width, player.playerHitbox.height);
        g2.setColor(Color.GREEN);
        g2.fillRect(player.playerFeet.x, player.playerFeet.y, player.playerFeet.width, player.playerFeet.height);
        g2.fillRect(player.playerHead.x, player.playerHead.y, player.playerHead.width, player.playerHead.height);
        g2.setColor(Color.PINK);
        g2.fillRect(player.playerLeft.x, player.playerLeft.y, player.playerLeft.width, player.playerLeft.height);
        g2.fillRect(player.playerRight.x, player.playerRight.y, player.playerRight.width, player.playerRight.height);

        // PLATFORM HITBOX
        for (Platform platform : platforms) {
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(platform.platHitbox.x, platform.platHitbox.y, platform.platHitbox.width, platform.platHitbox.height);
            g2.drawRect(platform.leftBarrier.x, platform.leftBarrier.y, platform.leftBarrier.width, platform.leftBarrier.height);
            g2.drawRect(platform.rightBarrier.x, platform.rightBarrier.y, platform.rightBarrier.width, platform.rightBarrier.height);
        }

        // OBSTACLE HITBOX
        for (Obstacle obstacle : obstacles) {
            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(1));
            g2.drawRect(obstacle.triHitbox.x, obstacle.triHitbox.y, obstacle.triHitbox.width, obstacle.triHitbox.height);
        }

        // MOVING OBSTACLE HITBOX
        for (Obstacle movingObstacle : movingObstacles) {
            g2.setColor(Color.ORANGE);
            g2.drawRect(movingObstacle.rectHitbox.x, movingObstacle.rectHitbox.y, movingObstacle.rectHitbox.width, movingObstacle.rectHitbox.height);
        }

        //COIN HITBOX
        for (Coin coin : coins) {
            g2.setColor(Color.GREEN);
            g2.drawRect(coin.coinHitbox.x, coin.coinHitbox.y, coin.coinHitbox.width, coin.coinHitbox.height);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panDraw = new javax.swing.JPanel(){

            //paintComponent happens on object creation and when
            //any changes occur to the frame (size change, new objects added.
                // etc.)
            @Override // this means that we are replacing the old
            //method that is prebuilt with a new one.
            public void paintComponent(Graphics g) {
                super.paintComponent(g); //calls the old paintComponent method
                draw(g);//calls the draw method
            }
        };
        lblCoinsCollected = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblPause = new javax.swing.JLabel();
        btnResume = new javax.swing.JButton();
        btnRestart = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        lblGameOver = new javax.swing.JLabel();
        lblLVLComplete = new javax.swing.JLabel();
        lblScore = new javax.swing.JLabel();
        btnNextLevel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                formKeyReleased(evt);
            }
        });

        panDraw.setMaximumSize(new java.awt.Dimension(1920, 1080));
        panDraw.setMinimumSize(new java.awt.Dimension(1920, 1080));
        panDraw.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                panDrawMouseMoved(evt);
            }
        });
        panDraw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panDrawMouseClicked(evt);
            }
        });
        panDraw.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCoinsCollected.setFont(new java.awt.Font("Pixelify Sans", 1, 24)); // NOI18N
        lblCoinsCollected.setForeground(new java.awt.Color(255, 255, 255));
        panDraw.add(lblCoinsCollected, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 52, 250, 40));

        lblTime.setFont(new java.awt.Font("Pixelify Sans", 1, 24)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        panDraw.add(lblTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 6, 200, 40));

        lblPause.setFont(new java.awt.Font("Pixelify Sans", 1, 72)); // NOI18N
        lblPause.setForeground(new java.awt.Color(255, 51, 51));
        lblPause.setText("PAUSED");
        panDraw.add(lblPause, new org.netbeans.lib.awtextra.AbsoluteConstraints(834, 355, -1, 64));

        btnResume.setFont(new java.awt.Font("Pixelify Sans", 0, 48)); // NOI18N
        btnResume.setText("RESUME");
        btnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResumeActionPerformed(evt);
            }
        });
        panDraw.add(btnResume, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 470, 349, 46));

        btnRestart.setFont(new java.awt.Font("Pixelify Sans", 0, 48)); // NOI18N
        btnRestart.setText("RESTART");
        btnRestart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestartActionPerformed(evt);
            }
        });
        panDraw.add(btnRestart, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 530, 349, 46));

        btnBack.setFont(new java.awt.Font("Pixelify Sans", 0, 48)); // NOI18N
        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        panDraw.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 590, 349, 46));

        lblGameOver.setFont(new java.awt.Font("Pixelify Sans", 1, 72)); // NOI18N
        lblGameOver.setForeground(new java.awt.Color(255, 51, 51));
        lblGameOver.setText("GAME  OVER");
        panDraw.add(lblGameOver, new org.netbeans.lib.awtextra.AbsoluteConstraints(771, 355, -1, 64));

        lblLVLComplete.setFont(new java.awt.Font("Pixelify Sans", 1, 72)); // NOI18N
        lblLVLComplete.setForeground(new java.awt.Color(255, 51, 51));
        lblLVLComplete.setText("LEVEL COMPLETE");
        panDraw.add(lblLVLComplete, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 350, -1, 64));

        lblScore.setBackground(new java.awt.Color(0, 0, 0));
        lblScore.setFont(new java.awt.Font("Pixelify Sans", 1, 48)); // NOI18N
        lblScore.setForeground(new java.awt.Color(255, 255, 255));
        panDraw.add(lblScore, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 420, 580, 30));

        btnNextLevel.setFont(new java.awt.Font("Pixelify Sans", 0, 48)); // NOI18N
        btnNextLevel.setText("NEXT LEVEL");
        btnNextLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextLevelActionPerformed(evt);
            }
        });
        panDraw.add(btnNextLevel, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 470, 349, 46));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panDraw, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panDrawMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panDrawMouseMoved

    }//GEN-LAST:event_panDrawMouseMoved

    private void panDrawMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panDrawMouseClicked
        // Displays the coordinates of the screen through any mouse click
        System.out.println("(" + evt.getX() + ", " + evt.getY() + ")");
    }//GEN-LAST:event_panDrawMouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
    // Calls the playerMoved method to handle player movement based on the KeyEvent
    player.playerMoved(evt);

    // Retrieves the key code from the KeyEvent
    int keyCode = evt.getKeyCode();

    // Prints the key code to the console for debugging purposes
    System.out.println(keyCode);

    // Check for specific key codes and perform corresponding actions
    // ESC: 27
    // H: 72
    
    if (keyCode == 27 && !gameOver && !lvlComplete) {
        // If the ESC key is pressed and the game is not over or the level is not complete
        pauseGame = true; // Set the pauseGame flag to true

        // Check if the game is paused
        if (pauseGame) {
            // Calculate the time elapsed before pausing the game
            pausedTimeElapsed = System.currentTimeMillis() - startTime;

            // Stop the game timer
            timer.stop();

            // Display pause-related UI elements
            lblPause.setVisible(true);
            btnResume.setVisible(true);
            btnRestart.setVisible(true);
            btnBack.setVisible(true);

            // Repaint the drawing panel to reflect the changes
            panDraw.repaint();
        }
    } else if (keyCode == 72) {
        // If the H key is pressed
        showHB = !showHB; // Toggle the showHB flag to show or hide hitboxes
    }

    }//GEN-LAST:event_formKeyPressed

    private void formKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyReleased
        player.playerStopped(evt);
    }//GEN-LAST:event_formKeyReleased

    private void btnResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumeActionPerformed
        pauseGame = false;
        if (!pauseGame) {
            startTime = System.currentTimeMillis() - pausedTimeElapsed; // Adjust the start time
            timer.start();
            lblPause.setVisible(false);
            btnResume.setVisible(false);
            btnRestart.setVisible(false);
            btnBack.setVisible(false);
        }
        this.requestFocus();
    }//GEN-LAST:event_btnResumeActionPerformed

    private void btnRestartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestartActionPerformed
        pauseGame = false;
        if (!pauseGame) {
            timer.start();
            lblPause.setVisible(false);
            btnResume.setVisible(false);
            btnRestart.setVisible(false);
            btnBack.setVisible(false);
        }

        // Resets timer, player position, lives, and coins
        startTime = System.currentTimeMillis();
        gameOver = false;
        lvlComplete = false;
        lblGameOver.setVisible(false);
        lblLVLComplete.setVisible(false);
        lblScore.setVisible(false);
        btnNextLevel.setVisible(false);
        
        player.x = 0;
        player.y = 705;
        player.fallingSpeed = 0;
        player.canJump = true;
        player.jumpCount = 0;
        numLives = 3;
        
        for (Coin coin : coins) {
            coin.collected = false;
            Coin.coinsCollected = 0;
        }
        this.requestFocus();

    }//GEN-LAST:event_btnRestartActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        music.stop();
        frmMainMenu screen1 = new frmMainMenu();
        screen1.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnNextLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextLevelActionPerformed
        music.stop();
        frmLevel2 screen2 = new frmLevel2();
        screen2.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnNextLevelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnNextLevel;
    private javax.swing.JButton btnRestart;
    private javax.swing.JButton btnResume;
    private javax.swing.JLabel lblCoinsCollected;
    private javax.swing.JLabel lblGameOver;
    private javax.swing.JLabel lblLVLComplete;
    private javax.swing.JLabel lblPause;
    private javax.swing.JLabel lblScore;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel panDraw;
    // End of variables declaration//GEN-END:variables
}
