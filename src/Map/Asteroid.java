package src.Map;

import src.Game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;
//Asteroid going to be moving from right to left
//It is a continuous screen so once the x or y value (still don't know the axis of screen) of Aasteroid reaches 0
//then it will go back to the opposite edge giving an illusion of infinite map

public class Asteroid extends Terrain{
    BufferedImage img;
    public static ArrayList<Asteroid> asteroidHolder = new ArrayList<>();

    private int x;
    private int y;
    private final int width = 60;
    private final int height = 60;
    private int direction;
    private double speed;
    private int angle;
    private int rotationSpeed = 2;
    private Rectangle bounds;

    public Asteroid(int x, int y, int direction, double speed, int angle, BufferedImage img){
        this.img = img;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed= speed;
        this.angle = angle;
        this.bounds = new Rectangle(x, y, img.getWidth(), img.getHeight());
        asteroidHolder.add(this);
    }

    public void update(){
        //0 means that it is moving forward
        //1 means that it is moving backwards
        if(this.direction == 0){
            rotateRight();
            moveForwards();
        }
        if(this.direction == 1) {
            rotateLeft();
            moveBackwards();
        }

    }
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
        int increment = 1;


        this.x -= increment * this.speed;
        this.y -= increment * this.speed;



        if(this.x < 0){
            this.x += Game.screenWidth;
        }
        if(this.y < 0){
            this.y += Game.screenHeight;
        }
        this.bounds.setLocation(this.x, this.y);

    }

    private void moveForwards() {

        int increment = 1;
        this.x += increment * this.speed;
        this.y += increment * this.speed;

        if(this. x > Game.screenWidth){
            this.x -= Game.screenWidth;

        }
        if(this.y > Game.screenHeight){
            this.y -= Game.screenHeight;
        }

        this.bounds.setLocation(this.x, this.y);

    }
    @Override
    public BufferedImage getImage() {
        return this.img;
    }

    @Override
    public void setImage(BufferedImage newImg) {
        this.img = newImg;
    }

    public void drawImage(Graphics2D g){
        for(int i = 0; i < asteroidHolder.size(); i++){
            Asteroid temp = asteroidHolder.get(i);
            AffineTransform rotation = AffineTransform.getTranslateInstance(temp.x, temp.y);
            rotation.rotate(Math.toRadians(temp.angle), (height / 2),  (width/ 2));
            g.drawImage(temp.img.getScaledInstance(width, height, Image.SCALE_SMOOTH), rotation, null);
        }
    }
}
