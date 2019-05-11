package src.Map;

import java.awt.image.BufferedImage;

public abstract class Terrain {
    abstract void movement();
    abstract BufferedImage getImage();
    abstract void setImage(BufferedImage newImg);
}

