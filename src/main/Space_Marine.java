package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Space_Marine extends GameObject{

    ObjectHandler ObjHandler;
    Game game;

    private BufferedImage[] spacemarine_image = new BufferedImage[2];

    ObjectAnimation animation;

    public Space_Marine(int x, int y, ID id, ObjectHandler ObjHandler, SpriteSheet sprite_sheet, Game game) {
        super(x, y, id, sprite_sheet);
        this.ObjHandler = ObjHandler;
        this.game = game;
                                                //sprite_sheet3 w = 32 h =32
        spacemarine_image[0] = sprite_sheet.grabImage(1,1,32, 48);
        spacemarine_image[1] = sprite_sheet.grabImage(2,1,32, 48);

        animation = new ObjectAnimation(3, spacemarine_image[0], spacemarine_image[1]);

    }

    public void update() {
        x += velX;
        y += velY;

        //check for collision
        collision();

        /* --- Movement logic --- */
        if(ObjHandler.isUp()){
            velY = -5;
        }
        else if(!ObjHandler.isDown()){
            velY = 0;
        }
        if(ObjHandler.isDown()){
            velY = 5;
        }
        else if(!ObjHandler.isUp()){
            velY = 0;
        }
        if(ObjHandler.isRight()){
            velX = 5;
        }
        else if(!ObjHandler.isLeft()){
            velX = 0;
        }
        if(ObjHandler.isLeft()){
            velX = -5;
        }
        else if(!ObjHandler.isRight()){
            velX = 0;
        }
        animation.runAnimation();
    }

    //collision detection
    private void collision(){
        //loop through each game object until you reach a wall object
        for(int i = 0; i < ObjHandler.objects.size(); i++){
            GameObject tempObject = ObjHandler.objects.get(i);
            //Colliding with a wall
            if(tempObject.getId() == ID.Wall){
                if(getBounds().intersects(tempObject.getBounds())){
                    x += velX * -1;
                    y += velY *-1;
                }
            }
            //Ammo crate pickup
            if(tempObject.getId() == ID.Crate){
                if(getBounds().intersects(tempObject.getBounds())){
                    game.ammo += 27;
                    game.gametimer.interval += 5;
                    ObjHandler.removeObject(tempObject);
                }
            }
            //Colliding with an enemy
            if(tempObject.getId() == ID.Enemy){
                if(getBounds().intersects(tempObject.getBounds())){
                    game.hp -= 5;
                }
            }
        }
    }



    public void render(Graphics g) {
        if(velX == 0 && velY == 0){
            g.drawImage(spacemarine_image[0], x, y, null);
        }else{
            animation.drawAnimation(g, x, y, 0);
        }
    }


    public Rectangle getBounds() {
        return new Rectangle(x,y,32,48);
    }
}
