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

    private BufferedImage world;
    private BufferedImage lastSavedWorld;
    private JFrame gameFrame;

    private Graphics2D buffer;
    private Thread thread;



    public static void main(String[] args){

    }
}