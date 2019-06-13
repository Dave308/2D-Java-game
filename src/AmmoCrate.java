package Main;

import java.awt.*;

public class AmmoCrate extends GameObject {

    public AmmoCrate(int x, int y, ID id, SpriteSheet sprite_sheet) {
        super(x, y, id, sprite_sheet);
    }

    public void update() { }

    public void render(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(x, y, 32, 32);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
}
