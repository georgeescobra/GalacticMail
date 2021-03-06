package src;

import src.Map.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;


public class SpaceMailMan {
    private int x;
    private int y;
    private float vx;
    private float vy;
    private int angle;

    private BufferedImage img;
    private BufferedImage landed;
    private boolean flying;
    private Rectangle bounds;
    private Moon moon;


    //size of SpaceShip
    private final int height = 44;
    private final int width = 40;

    private final double R = 1;
    private int rotationSpeed = 2;

    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean LaunchPressed;

    SpaceMailMan(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        this.flying = true;
        this.bounds = new Rectangle(x, y, width, height);
        this.bounds.setBounds(this.bounds);
    }
    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleLaunchPressed() {
        this.LaunchPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleLaunchPressed() {this.LaunchPressed = false;}


    public void update() {
        if (this.UpPressed && this.flying) {
            this.moveForwards();
        }
        if (this.DownPressed && this.flying) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.LaunchPressed && !this.flying){
            this.flying = true;
            this.angle -= 90;
            src.Map.Moon.moonHolder.remove(this.moon);
            Random random = new Random();
            rotationSpeed = random.nextInt(4) + 1;
        }


    }

    //this should only be activatable when on the moon
    //would have to rotate using the moons center and not the spaceships
    private void rotateLeft() {
        if(this.angle < 0) {
            this.angle += 360;
            this.angle -= this.rotationSpeed;
        }else{
            this.angle -= this.rotationSpeed;
        }
    }

    private void rotateRight() {
        if(this.angle > 360) {
            this.angle -= 360;
            this.angle += this.rotationSpeed;
        }else{
            this.angle += this.rotationSpeed;
        }
    }

    private void moveBackwards() {
        this.vx = (int) Math.round(this.R * Math.cos(Math.toRadians(this.angle)));
        this.vy = (int) Math.round(this.R * Math.sin(Math.toRadians(this.angle)));


        this.x -= this.vx;
        this.y -= this.vy;

        this.bounds.setLocation(this.x, this.y);

    }

    private void moveForwards() {
        this.vx = (int) Math.round(this.R * Math.cos(Math.toRadians(this.angle)));
        this.vy = (int) Math.round(this.R * Math.sin(Math.toRadians(this.angle)));


        this.x += this.vx;
        this.y += this.vy;

        this.bounds.setLocation(this.x, this.y);

    }


    public Rectangle getBounds(){return this.bounds;}
    public boolean flyingStatus(){return this.flying;}
    public void setFlyingStatus(boolean s){this.flying = s;}
    public void setImage(BufferedImage img){this.landed = img;}
    public void setMoon(Moon moon){this.moon = moon;}
    public void setAngle(int a){this.angle = a;}

    public void setX(int x){
        this.x = x;
        this.bounds.setLocation(this.x, this.y);

    }
    public void setY(int y){
        this.y = y;
        this.bounds.setLocation(this.x, this.y);
    }

    public void setRotaionSpeed(int s){this.rotationSpeed = s;}
    public boolean launchPressed(){return this.LaunchPressed;}
    public BufferedImage getImg(){return this.img;}
    @Override
    public String toString(){
        return "x=" + x + ", y=" + y + ",angle=" + angle;
    }

    public void drawImage(Graphics g){
        if(this.flying) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
            rotation.rotate(Math.toRadians(this.angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);
        }else{

            Graphics2D g2d = (Graphics2D) g;
            AffineTransform rotation = AffineTransform.getTranslateInstance(this.moon.getX() + 22, this.moon.getY());
            this.setX(this.moon.getX() + 22);
            this.setY(this.moon.getY());
            rotation.rotate(Math.toRadians(this.angle), (this.moon.getImage().getHeight() / 4),  (this.moon.getImage().getWidth() / 2));
            g2d.drawImage(this.landed, rotation, null);
        }
    }
}
