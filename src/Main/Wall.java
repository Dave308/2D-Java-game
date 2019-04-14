package Main;

import java.awt.*;
import java.awt.image.BufferedImage;


/*

    Used to create in-game elements created from the png level background file.
    This allows for collision checking with the level boundaries etc...

 */

public class Wall extends GameObject{


    private BufferedImage wall_image;

    SpriteSheet sprite_sheet;

    public Wall(int x, int y, ID id, SpriteSheet sprite_sheet) {
        super(x, y, id, sprite_sheet);         //col 5 for normal wall texture
        wall_image = sprite_sheet.grabImage(4,2,32,32);
    }


    public void update(){ /* unused */ }


    public void render(Graphics g) {
        g.drawImage(wall_image, x, y, null);
    }


    public Rectangle getBounds() {
        return new Rectangle(x,y,32,32);
    }
}
