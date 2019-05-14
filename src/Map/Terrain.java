package src.Map;

import java.awt.image.BufferedImage;

public abstract class Terrain {
    abstract BufferedImage getImage();
    abstract void setImage(BufferedImage newImg);
}

