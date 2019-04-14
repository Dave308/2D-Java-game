package Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/* --- Used to get image from storage --- */

public class ImageLoader {

    private BufferedImage image;

    public BufferedImage loadImage(String path){
        try {
            //getClass().getResource(path)) return a URL
            image = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
