package main;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Enemy extends GameObject{

    private ObjectHandler ObjHandler;
    Random r = new Random();
    int choose = 0;
    int hp = 100;
    Game game;

    private BufferedImage enemy_image;

    public Enemy(int x, int y, ID id, ObjectHandler handler, SpriteSheet sprite_sheet, Game game){
        super(x, y, id, sprite_sheet);
        this.ObjHandler = handler;
        this.game = game;
        enemy_image = sprite_sheet.grabImage(3, 1, 32, 32);
    }

    public void update() {

        x += velX;
        y += velY;
        choose = r.nextInt(10);

        for(int i = 0; i < ObjHandler.objects.size(); i++){
            GameObject tempObject = ObjHandler.objects.get(i);
            if (tempObject.getId() == ID.Wall){
                //if colliding with wall
                if (getBoundsBig().intersects(tempObject.getBounds())){
                    x += (velX*5) * -1;
                    y += (velY*5) * -1;
                    velX *= -1;
                    velY *= -1;
                }else if(choose == 0){
                    velX = (r.nextInt(4) - 1);
                    velY = (r.nextInt(4) - 1);
                }
            }
            if(tempObject.getId() == ID.Bullet){
                if (getBounds().intersects(tempObject.getBounds())){
                    hp -= 50;
                    playsound(game.hitmarker);
                    ObjHandler.removeObject(tempObject);
                }
            }

        }

        if(x >= 2000 || x <= 0 || y > 1068 || y <= 0){
            //if the enemy leaves the map bring them back to this point
            x = 847;
            y = 793;
        }
        if (hp <= 0) {
            ObjHandler.removeObject(this);
            game.enemyCounter--;
        }
    }

    static private void playsound(File sound){
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void render(Graphics g) {
        g.drawImage(enemy_image, x, y, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }
    public Rectangle getBoundsBig() {
        return new Rectangle(x-16, y-16, 64, 64);
    }
}
