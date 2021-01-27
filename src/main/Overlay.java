package main;

import java.awt.*;


//creates a game over screen
//ObjHandler.addObject(new Overlay(0,0,ID.Overylay, null));

public class Overlay extends GameObject{


    public Overlay(int x, int y, ID id, SpriteSheet sprite_sheet) {
        super(x, y, id, sprite_sheet);
    }

    public void update() { }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,1000, 563);
        g.setColor(Color.white);
        g.drawString("Game over", 1000/2,563/2);
    }

    public Rectangle getBounds() {
        return null;
    }
}
