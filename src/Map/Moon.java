package src.Map;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class Moon extends Terrain {
    private BufferedImage img;
    private int xcord;
    private int ycord;
    private boolean mailDelivered;
    private boolean shipLanded;
    private BufferedImage baseImg;
    private static Random random = new Random();
    public static ArrayList<Moon> moonHolder = new ArrayList<>();
    public static ArrayList<BufferedImage> bases = new ArrayList<>();


    public Moon(BufferedImage itself, int x, int y){
        this.img = itself;
        this.xcord = x;
        this.ycord = y;
        this.mailDelivered = false;
        this.shipLanded = false;
        moonHolder.add(this);
        this.baseImg = bases.get(random.nextInt(bases.size()));
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
            g2d.drawImage(temp.baseImg, temp.xcord + (temp.img.getWidth()/2 - temp.baseImg.getWidth()/2), temp.ycord, null);
        }
    }
}
