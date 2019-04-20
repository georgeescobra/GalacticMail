package src;

import java.awt.image.BufferedImage;

public class SpaceMailMan {
    private int x;
    private int y;
    private float vx;
    private float vy;
    private int angle;
    //helper variables to know which direction the tank is moving
    private int savex;
    private int savey;
    private BufferedImage img;

    //size of SpaceShip
    private int height = 16;
    private int width = 16;

    private final double R = 1;
    private final int rotationSpeed = 2;

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
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
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
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));

        savex = x;
        savey = y;

        x -= vx;
        y -= vy;

    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));

        savex = x;
        savey = y;

        x += vx;
        y += vy;

    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

}
