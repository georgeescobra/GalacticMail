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
    private final int imgWidth = 75;
    private final int imgHeight = 75;
    private boolean mailDelivered;
    private boolean shipLanded;
    private Rectangle bounds;
    private BufferedImage baseImg;
    private static Random random = new Random();
    public static ArrayList<Moon> moonHolder = new ArrayList<>();
    public static ArrayList<BufferedImage> bases = new ArrayList<>();


    public Moon(BufferedImage itself, int x, int y){
        this.img =  itself;
        this.xcord = x;
        this.ycord = y;
        this.mailDelivered = false;
        this.shipLanded = false;
        this.bounds = new Rectangle(x, y, imgWidth, imgHeight);
        //should add after it checks that there are no intersecting rectangles
        // moonHolder.add(this);
        this.baseImg = bases.get(random.nextInt(bases.size()));

        if(moonHolder.size() > 1){
            int counter = 0;
            for(int i = 0; i < moonHolder.size(); i++){
                Moon temp = moonHolder.get(i);
                if(!temp.bounds.intersects(this.bounds)){
                    counter++;
                }
            }
            if(counter == moonHolder.size()){
                moonHolder.add(this);
            }
        }else{
            moonHolder.add(this);
        }
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
            g2d.drawImage(temp.img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH), temp.xcord, temp.ycord, null);
            g2d.drawImage(temp.baseImg, temp.xcord + (imgWidth/2 - temp.baseImg.getWidth()/2), temp.ycord, null);
        }
    }
}
