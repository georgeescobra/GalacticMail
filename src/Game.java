package src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

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

        private BufferedImage asteroid;
        //this is the actual planet
        private BufferedImage moon0;
        private BufferedImage moon1;
        private BufferedImage moon2;
        private BufferedImage moon3;

        //this is to add variety to the bases on the planets
        private BufferedImage base0;
        private BufferedImage base1;
        private BufferedImage base2;
        private BufferedImage base3;
        private BufferedImage base4;


    private void init(){
        running = true;
        loadImages();
        this.gameFrame = new JFrame("Galactic Mail");
        this.world = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        buffer = this.world.createGraphics();

        //draws background
        buffer.drawImage(this.background.getScaledInstance(screenWidth, screenHeight, Image.SCALE_AREA_AVERAGING), 0 , 0, null);

        this.player = new SpaceMailMan(1000, 500, 0, 0, 270, this.flyingShip);
        PlayerControls playerCntrl = new PlayerControls(this.player,  KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SHIFT);

        this.gameFrame.addKeyListener(playerCntrl);
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.add(this);
        this.gameFrame.setSize(screenWidth, screenHeight);
        this.gameFrame.setResizable(false);
        this.gameFrame.setLocationRelativeTo(null);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.setVisible(true);


    }
    private void loadImages(){
        try{
            this.background = ImageIO.read(getClass().getResource("../resources/Background.bmp"));
            this.lastSavedWorld = ImageIO.read(getClass().getResource("../resources/Background.bmp"));

            this.landedShip = ImageIO.read(getClass().getResource("../resources/LandedShip.png"));
            this.flyingShip = ImageIO.read(getClass().getResource("../resources/FlyingShip.png"));

            this.asteroid = ImageIO.read(getClass().getResource("../resources/Asteroid.png"));

            //may or may not ending up using all these planets/bases we shall see
            this.moon0 = ImageIO.read(getClass().getResource("../resources/Planet0.png"));
            this.moon1 = ImageIO.read(getClass().getResource("../resources/Planet1.png"));
            this.moon2 = ImageIO.read(getClass().getResource("../resources/Planet2.png"));
            this.moon3 = ImageIO.read(getClass().getResource("../resources/Planet3.png"));

            this.base0 = ImageIO.read(getClass().getResource("../resources/Base0.png"));
            this.base1 = ImageIO.read(getClass().getResource("../resources/Base1.png"));
            this.base2 = ImageIO.read(getClass().getResource("../resources/Base2.png"));
            this.base3 = ImageIO.read(getClass().getResource("../resources/Base3.png"));
            this.base4 = ImageIO.read(getClass().getResource("../resources/Base4.png"));


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
        buffer.drawImage(this.lastSavedWorld.getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH), 0 , 0, null);

        this.player.drawImage(buffer);
        g2.drawImage(this.world, 0 , 0, null);

    }

    private synchronized void start(){
        thread = new Thread(this.thread);
        thread.start();
    }

    public static void main(String[] args){
        Game newGame = new Game();
        newGame.init();

        try{
            while(newGame.running){
                newGame.start();
                newGame.player.update();
                newGame.repaint();
                Thread.sleep(1000/144);
                newGame.player.toString();

            }
        }catch(InterruptedException ignored){

        }
    }

}