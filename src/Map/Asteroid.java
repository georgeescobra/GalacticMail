package src.Map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
//Asteroid going to be moving from right to left
//It is a continuous screen so once the x or y value (still don't know the axis of screen) of Aasteroid reaches 0
//then it will go back to the opposite edge giving an illusion of infinite map

public class Asteroid extends Terrain{
    BufferedImage img;
    public static ArrayList<Moon> asteroidHolder = new ArrayList<>();

    public Asteroid(){

    }
    @Override
    public void movement() {

    }

    @Override
    public BufferedImage getImage() {
        return this.img;
    }

    @Override
    public void setImage(BufferedImage newImg) {
        this.img = newImg;
    }
}
