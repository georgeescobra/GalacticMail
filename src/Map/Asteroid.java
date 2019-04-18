package src.Map;

import java.awt.image.BufferedImage;
//Asteroid going to be moving from right to left
//It is a continuous screen so once the x or y value (still don't know the axis of screen) of Aasteroid reaches 0
//then it will go back to the opposite edge giving an illusion of infinite map

public class Asteroid extends Terrain{
    public Asteroid(){

    }
    @Override
    void movement() {

    }

    @Override
    BufferedImage getImage() {
        return null;
    }

    @Override
    void setImage() {

    }
}
