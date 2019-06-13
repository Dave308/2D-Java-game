package Main;

import java.awt.*;

public class Bullet extends GameObject{

    private ObjectHandler ObjHandler;

    int mx, my;

    public Bullet(int x, int y, ID id, ObjectHandler ObjHandler, int mx, int my, SpriteSheet sprite_sheet) {
        super(x, y, id, sprite_sheet);
        this.ObjHandler = ObjHandler;

        velX = ((mx - x) / 10);
        velY = ((my - y) / 10);
        this.mx = mx;
        this.my = my;
    }

    public void update() {
        x += velX;
        y += velY;
        for(int i = 0; i < ObjHandler.objects.size(); i++){
            GameObject tempObject = ObjHandler.objects.get(i);
            if(tempObject.getId() == ID.Wall){
                if(getBounds().intersects(tempObject.getBounds())){
                    ObjHandler.removeObject(this);
                }
            }else if (velX == 0 & velY == 0){
                ObjHandler.removeObject(this);
            }
        }

    }


    public void render(Graphics g) {
        g.setColor(new Color(235,69,0));
        g.fillOval(x,y,8,8);
        /*
        for (int i = 0; i < ObjHandler.objects.size(); i++){
            GameObject tempObject = ObjHandler.objects.get(i);
            if(tempObject.getId() == ID.Player)
                g.drawLine(tempObject.getX()+16, tempObject.getY()+16, mx, my);
        }
        */
    }

    public Rectangle getBounds() {
        return new Rectangle(x,y,8,8);
    }
}
