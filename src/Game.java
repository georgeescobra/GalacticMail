package src;
import src.Map.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/*
INFO

You play an intergalactic mail carrier who must deliver mail to a number of inhabited moons. He
must safely steer a course from moon to moon while avoiding dangerous asteroids. The mail
carrier is paid for each delivery he makes, but pay is deducted for time spent hanging around on
moons. This adds pressure to the difficult task of orienting his rickety, old rocket, which he
cannot steer very well in space.
When the rocket is on a moon, the arrow keys will rotate it to allow the launch direction to be
set. The spacebar will launch the rocket, and the moon will be removed from the screen to show
that its mail has been delivered. In flight, the rocket will keep moving in the direction it is pointing
in, with only a limited amount of control over its steering using the arrow keys. When things
move outside the playing area, they reappear on the other side to give the impression of a
continuous world. The player will gain points for delivering mail, but points will be deducted
while waiting on a moon. This will encourage the player to move as quickly as possible from
moon to moon. There will be different levels, with more asteroids to avoid. The game is over if
the rocket is hit by an asteroid, and a high-score table will be displayed. Figure 3-1 shows an
impression of what the final game will look like.

 */
public class Game extends JPanel {
    public static final int screenWidth = 1280;
    public static final int screenHeight = 720;
    private boolean running = false;

    private JFrame gameFrame;
    private JFrame highScores;

    private Graphics2D buffer;
    private Graphics2D bufferM;
    private Graphics2D bufferH;
    private Thread thread;

    private SpaceMailMan player;

    //All the Images
        private BufferedImage world;
        private BufferedImage background;
        private BufferedImage lastSavedWorld;
        private BufferedImage menuBg;
        private BufferedImage menuWorld;
        private BufferedImage hsBG;
        private BufferedImage hsWorld;

        private BufferedImage landedShip;
        private BufferedImage flyingShip;
        private BufferedImage title;

        private BufferedImage asteroidimg;
        //this is the actual planet
        private BufferedImage moon;


        private Moon moonBase;
        private Asteroid asteroid;
        private Points highScore;
        private double level;
        private int numOfMoons;
        private int numOfAsteroids;
        private boolean menu = true;

        private JButton Start;
        private JButton HighScore;
        static protected Game newGame;
        private MouseInput mouse = new MouseInput();

    private void gameMenu(){
        this.menu = true;
        this.running = false;
        this.gameFrame = new JFrame("****GALACTIC MAIL****");
        this.menuWorld = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        this.bufferM = this.menuWorld.createGraphics();
        this.bufferM.drawImage(this.menuBg.getScaledInstance(screenWidth, screenHeight, Image.SCALE_AREA_AVERAGING), 0, 0, null);
        this.bufferM.drawImage(this.title.getScaledInstance(700, 400, Image.SCALE_SMOOTH), screenWidth / 2 - (700 / 2), 50, null);

        this.Start = new JButton("START");
        Start.setName("Start");
        Start.setBounds(550, 450, 140, 50);
        Start.setFont(new Font("Arial", Font.BOLD, 28));
        Start.setEnabled(true);

        //got this function from https://alvinalexander.com/java/jbutton-listener-pressed-actionlistener
        Start.addMouseListener(mouse);
        this.gameFrame.add(Start);

        this.HighScore = new JButton("HIGHSCORES");
        HighScore.setName("HighScore");
        HighScore.setBounds(522, 525, 195, 55);
        HighScore.setFont(new Font("Arial", Font.BOLD, 22));
        HighScore.addMouseListener(mouse);
        HighScore.setEnabled(true);

        this.gameFrame.add(HighScore);
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.add(this);
        this.gameFrame.setSize(screenWidth, screenHeight);
        this.gameFrame.setResizable(false);
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
        this.gameFrame.setVisible(true);

        //for some reason this has to print or do something
        //in order the buttons to take necessary action
        while(menu){ System.out.print("");}


    }

    public void startGame(){
        this.menu = false;
        this.running = true;
        Start.setFocusable(false);
        HighScore.setFocusable(false);
        this.highScores = null;
        this.gameFrame.dispose();

    }

    private void init(){
        this.gameFrame = new JFrame(String.format("***%s***     LEVEL:  %01d       SCORE:  %-2d", "GALACTIC MAIL", (int) this.level, (int) this.highScore.getPoints() ));
        this.Start.setFocusable(false);
        this.HighScore.setFocusable(false);

        this.world = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        this.buffer = this.world.createGraphics();

        //draws background
        this.buffer.drawImage(this.background.getScaledInstance(screenWidth, screenHeight, Image.SCALE_AREA_AVERAGING), 0 , 0, null);

        this.player = new SpaceMailMan(0, 0, 0, 0, 270, this.flyingShip);
        PlayerControls playerCntrl = new PlayerControls(this.player,  KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT);

        Random random = new Random();

        numOfMoons = 7;
        while(Moon.moonHolder.size() < numOfMoons) {
            int x = random.nextInt(screenWidth - 200);
            int y = random.nextInt(screenHeight - 150) + 40;
            this.moonBase = new Moon(moon, x, y);
        }
            //this resets the player to a random moon
            Moon temp = Moon.moonHolder.get(0);
            this.player.setX(temp.getX() + temp.getImage().getWidth() / 2);
            this.player.setY(temp.getY() + temp.getImage().getHeight() / 2);

        //I want the asteroids to be randomly generated
        //doesn't matter if it collides with moon & spaceship if on moon
        //Randomly generated angle
        //have this constantly update/increment
        //reset the x and y anytime it goes beyond the borders of the screen (infinite screen)

        numOfAsteroids = 2;
        while(Asteroid.asteroidHolder.size() < numOfAsteroids){
            int x  = random.nextInt(screenWidth - 200);
            int y = random.nextInt(screenHeight - 150);
            int direction = random.nextInt(2);
            int angle = random.nextInt(361);
            double speed = 1;
            this.asteroid = new Asteroid(x, y, direction, speed, angle, this.asteroidimg);

        }
//            if(Start.isEnabled()){
//
//            }
        //for JFrame
        this.gameFrame.addKeyListener(playerCntrl);
        this.gameFrame.add(HighScore);
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.add(this);
        this.gameFrame.setSize(screenWidth, screenHeight);
        this.gameFrame.setResizable(false);
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
        this.gameFrame.setVisible(true);

    }
    private void loadImages(){
        try{

            this.background = ImageIO.read(getClass().getResource("../resources/Background.bmp"));
            this.lastSavedWorld = ImageIO.read(getClass().getResource("../resources/Background.bmp"));
            this.menuBg = ImageIO.read(getClass().getResource("../resources/Background.bmp"));
            this.hsBG =  ImageIO.read(getClass().getResource("../resources/Background.bmp"));

            this.title = ImageIO.read(getClass().getResource("../resources/Title.png"));

            this.landedShip = ImageIO.read(getClass().getResource("../resources/LandedShip.png"));
            this.flyingShip = ImageIO.read(getClass().getResource("../resources/FlyingShip.png"));

            this.asteroidimg = ImageIO.read(getClass().getResource("../resources/Asteroid.png"));

            //may or may not ending up using all these planets/bases we shall see
            this.moon = ImageIO.read(getClass().getResource("../resources/Moon.png"));

            BufferedImage base0 = ImageIO.read(getClass().getResource("../resources/Base0.png"));
            BufferedImage base1 = ImageIO.read(getClass().getResource("../resources/Base1.png"));
            BufferedImage base2 = ImageIO.read(getClass().getResource("../resources/Base2.png"));
            BufferedImage base3 = ImageIO.read(getClass().getResource("../resources/Base3.png"));
            BufferedImage base4 = ImageIO.read(getClass().getResource("../resources/Base4.png"));
            Moon.bases.add(base0);
            Moon.bases.add(base1);
            Moon.bases.add(base2);
            Moon.bases.add(base3);
            Moon.bases.add(base4);


        }catch(IOException e){
            System.out.println("***COULD NOT LOAD IMAGES***");
            System.out.println(e);
        }
    }
    public void showLeaderBoard(){
        this.highScore.loadHighScores();
        int windowWidth = 500;
        int windowHeight = 500;
        this.highScores = new JFrame("****HighScores****");
        this.hsWorld = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_ARGB);

        this.bufferH = this.hsWorld.createGraphics();
        this.bufferH.drawImage(this.hsBG.getScaledInstance(windowWidth, windowHeight, Image.SCALE_AREA_AVERAGING), 0, 0, null);

        this.bufferH.setFont(new Font("Arial", Font.BOLD, 50));
        this.bufferH.setColor(Color.WHITE);

        int row = 55;
        int column = 15;
        for(int i = 0; i < Points.pointHolder.size(); i++){
            Points temp = Points.pointHolder.get(i);
//            if(temp.name.equals("new")){
//                JTextField input = new JTextField();
//                input.setLocation(row, column);
//                TextListener tfListener = new TextListener();
//                input.addActionListener(tfListener);
//
//            }
                this.bufferH.drawString(temp.getName(), column, row);
                column += 160;
                this.bufferH.drawString(Integer.toString((int) temp.getPoints()), column, row);
                column -= 160;
                row += 50;

        }

        this.highScores.setLayout(new BorderLayout());
        this.highScores.setSize(windowWidth, windowHeight);
        this.highScores.setResizable(false);
        this.highScores.setLocationRelativeTo(null);
        this.highScores.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.highScores.setVisible(true);
        this.highScores.add(this);
//        ME 1
//        ME 2
//        ME 3
//        ME 4
//        ME 5
//        ME 6
//        ME 7
//        ME 8
//        ME 9
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        //this has to be constantly updated when the map is updated
        if(running && !menu && buffer != null) {
            super.paintComponent(g2);

            this.buffer.drawImage(this.lastSavedWorld.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH), 0, 0, null);
            this.moonBase.drawImage(this.buffer, this.player, this.landedShip);
            this.asteroid.drawImage(this.buffer);
            this.player.drawImage(this.buffer);
            g2.drawImage(this.world, 0 , 0, null);

        }
        if(menu && !running && this.highScores == null){
            super.paintComponent(g2);
            g2.drawImage(this.lastSavedWorld.getScaledInstance(500, 500, Image.SCALE_SMOOTH),0, 0, null);
            g2.drawImage(this.menuWorld, 0 , 0, null);
        }

        if(this.highScores != null){
            super.paintComponent(g2);
            g2.drawImage(this.hsWorld, 0 , 0, null);
        }

    }

    private synchronized void start(){
        thread = new Thread(this.thread);
        thread.start();
    }

    public static void main(String[] args){
        newGame = new Game();
        newGame.loadImages();
        newGame.highScore = new Points(0);
        newGame.highScore.loadHighScores();
        newGame.level = 1;
        newGame.gameMenu();
        newGame.init();
        Random random = new Random();
        try{
            while(newGame.running){
                newGame.start();
                newGame.repaint();
                if(newGame.player.launchPressed() && !newGame.player.flyingStatus()){
                    double newScore = newGame.highScore.getPoints();
                    newScore = newScore +(newGame.level * 30);
                    newGame.highScore.setPoints(newScore);
                }
                if(!newGame.player.flyingStatus() && newGame.highScore.getPoints() > 0){
                    double newScore = newGame.highScore.getPoints();
                    newScore = newScore - newGame.level * .03;
                    if(newScore <= 0){
                        newGame.gameFrame.dispose();
                        newGame.level = 1;
                        newGame.gameMenu();
                        newGame.init();
                    }
                    newGame.highScore.setPoints((newScore));
                }
                if(src.Map.Moon.moonHolder.size() == 1){
                    if(!newGame.player.flyingStatus()) {
                        while(Moon.moonHolder.size() < newGame.numOfMoons) {
                            int x = random.nextInt(screenWidth - 200);
                            int y = random.nextInt(screenHeight - 150) + 40;
                            newGame.moonBase = new Moon(newGame.moon, x, y);
                        }
                        newGame.level++;
                        src.Map.Asteroid.asteroidHolder.clear();
                        System.out.println(Asteroid.asteroidHolder.size());
                        int size = newGame.numOfAsteroids + (int)newGame.level;
                        if(size > 5) {
                            size = 5;
                        }
                        for(int i = 0; i < size; i++){
                            int x  = random.nextInt(screenWidth - 200);
                            int y = random.nextInt(screenHeight - 150);
                            int direction = random.nextInt(2);
                            int angle = random.nextInt(361);
                            double speed = 1 +  newGame.level * .25;
                            newGame.asteroid = new Asteroid(x, y, direction, speed, angle, newGame.asteroidimg);
                        }
                    }
                }

                for(int i = 0; i < Asteroid.asteroidHolder.size(); i++){
                    Asteroid temp = Asteroid.asteroidHolder.get(i);
                    temp.update();
                }
                newGame.player.update();
                //if this returns 0, continue
                //else add restart button and exit
                //display Scores
                int cont = newGame.asteroid.check(newGame.player);
                if(cont != 0){
                    int indx = newGame.highScore.newHighScore(newGame.highScore);
                    System.out.println(indx);
                    System.out.println(Points.pointHolder.size());
                    if( indx >= 0 ){
                        newGame.highScore.setName("new");
                        newGame.highScore.setHighScore(newGame.highScore, indx);
                        newGame.gameFrame.setTitle("****CRASHED****CHECK-HIGHSCORES****");
                        TimeUnit.SECONDS.sleep(2);
                    }
                    newGame.gameFrame.dispose();
                    newGame.highScore = new Points(0);
                    newGame.level = 1;
                    newGame.gameMenu();
                    newGame.init();

                }
                newGame.gameFrame.setTitle(String.format("***%s***     LEVEL:  %01d       SCORE:  %-2d", "GALACTIC MAIL", (int)newGame.level, (int) newGame.highScore.getPoints() ));
                Thread.sleep(1000/144);

            }
        }catch(InterruptedException ignored){

        }
    }

}