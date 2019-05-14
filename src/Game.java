package src;
import src.Map.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.text.DecimalFormat;

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

    private Graphics2D buffer;
    private Thread thread;

    private SpaceMailMan player;

    //All the Images
        private BufferedImage world;
        private BufferedImage background;
        private BufferedImage lastSavedWorld;

        private BufferedImage landedShip;
        private BufferedImage flyingShip;
        private BufferedImage title;

        private BufferedImage asteroidimg;
        //this is the actual planet
        private BufferedImage moon;
        private BufferedImage moon0;
        private BufferedImage moon1;
        private BufferedImage moon2;
        private BufferedImage moon3;

        private Moon moonBase;
        private Asteroid asteroid;
        private Points highScore;
        private double level;
        private int numOfMoons;
        private int numOfAsteroids;

    private void gameMenu(){


        this.gameFrame = new JFrame("Galactic Mail!!!");
        this.world = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        this.buffer = this.world.createGraphics();
        buffer.drawImage(this.background.getScaledInstance(screenWidth, screenHeight, Image.SCALE_AREA_AVERAGING), 0 , 0, null);
        buffer.drawImage(this.title.getScaledInstance(700, 400, Image.SCALE_SMOOTH), screenWidth / 2 - (700 / 2),50, null);

       // this.gameFrame.addKeyListener(MouseEvent click);
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.add(this);
        this.gameFrame.setSize(screenWidth, screenHeight);
        this.gameFrame.setResizable(false);
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setVisible(true);

        running = false;

    }

    private void init(){
        running = true;

        this.world = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        buffer = this.world.createGraphics();

        //draws background
        buffer.drawImage(this.background.getScaledInstance(screenWidth, screenHeight, Image.SCALE_AREA_AVERAGING), 0 , 0, null);

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

        //for JFrame
        this.gameFrame.addKeyListener(playerCntrl);
//        this.gameFrame.setLayout(new BorderLayout());
//        this.gameFrame.add(this);
//        this.gameFrame.setSize(screenWidth, screenHeight);
//        this.gameFrame.setResizable(false);
//        this.gameFrame.setLocationRelativeTo(null);
//        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.gameFrame.setVisible(true);


    }
    private void loadImages(){
        try{

            this.background = ImageIO.read(getClass().getResource("../resources/Background.bmp"));
            this.lastSavedWorld = ImageIO.read(getClass().getResource("../resources/Background.bmp"));
            this.title = ImageIO.read(getClass().getResource("../resources/Title.png"));

            this.landedShip = ImageIO.read(getClass().getResource("../resources/LandedShip.png"));
            this.flyingShip = ImageIO.read(getClass().getResource("../resources/FlyingShip.png"));

            this.asteroidimg = ImageIO.read(getClass().getResource("../resources/Asteroid.png"));

            //may or may not ending up using all these planets/bases we shall see
            this.moon = ImageIO.read(getClass().getResource("../resources/Moon.png"));
            this.moon0 = ImageIO.read(getClass().getResource("../resources/Planet0.png"));
            this.moon1 = ImageIO.read(getClass().getResource("../resources/Planet1.png"));
            this.moon2 = ImageIO.read(getClass().getResource("../resources/Planet2.png"));
            this.moon3 = ImageIO.read(getClass().getResource("../resources/Planet3.png"));


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

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        buffer = this.world.createGraphics();
        super.paintComponent(g2);

        //this has to be constantly updated when the map is updated
        if(running) {
            buffer.drawImage(this.lastSavedWorld.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH), 0, 0, null);

            this.moonBase.drawImage(buffer, this.player, this.landedShip);
            this.asteroid.drawImage(buffer);
            this.player.drawImage(buffer);
        }

        g2.drawImage(this.world, 0 , 0, null);

    }

    private synchronized void start(){
        thread = new Thread(this.thread);
        thread.start();
    }

    public static void main(String[] args){
        Game newGame = new Game();
        newGame.loadImages();
        newGame.gameMenu();
        newGame.highScore = new Points(0);
        newGame.level = 1;
        if(newGame.running) {
            newGame.init();
        }
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
                        break;
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
                newGame.gameFrame.setTitle(String.format("***%s***     LEVEL:  %01d       SCORE:  %-2d", "GALACTIC MAIL", (int)newGame.level, (int) newGame.highScore.getPoints() ));
                Thread.sleep(1000/144);

            }
        }catch(InterruptedException ignored){

        }
    }

}