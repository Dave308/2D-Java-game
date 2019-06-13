package Main;

import java.awt.*;

public class Crosshairs extends GameObject {

    private ObjectHandler ObjHandler;
    private Game game;

    public int x, y;

    public Crosshairs(int x, int y, ID id, ObjectHandler handler, SpriteSheet sprite_sheet, Game game) {
        super(x, y, id, sprite_sheet);
        this.ObjHandler = handler;
        this.game = game;
    }

    @Override
    public void update() {
        x = game.mouseX + (int)game.camera.getX();
        y = game.mouseY + (int)game.camera.getY();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(235,69,0));
        //circle with cross
        g.drawOval(x-13,y-12,30,30);
        g.drawLine(x-11, y+3,x+15, y+3);
        g.drawLine(x+3, y+15, x+3, y-10);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
