package src.Map;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class Moon extends Terrain {
    private BufferedImage img;
    int xcord;
    int ycord;
    boolean mailDelivered;
    boolean shipLanded;
    public static ArrayList<Moon> moonHolder = new ArrayList<>();


    public Moon(BufferedImage itself, int x, int y){
        this.img = itself;
        this.xcord = x;
        this.ycord = y;
        this.mailDelivered = false;
        this.shipLanded = false;
        moonHolder.add(this);
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

    public void drawImage(Graphics2D g){
//        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x,this.y);
//        rotation.rotate(Math.toRadians(this.angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        for(int i = 0; i < moonHolder.size(); i++){
            Moon temp = moonHolder.get(i);
            g2d.drawImage(temp.img, temp.xcord, temp.ycord, null);
        }
    }
}
