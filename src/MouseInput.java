package Main;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class MouseInput extends MouseAdapter{

    private ObjectHandler ObjHandler;
    private Camera camera;
    private SpriteSheet sprite_sheet;
    private Game game;

    public MouseInput(ObjectHandler ObjHandler, Camera camera, SpriteSheet sprite_sheet, Game game){
        this.ObjHandler = ObjHandler;
        this.camera = camera;
        this.sprite_sheet = sprite_sheet;
        this.game = game;
    }

    public void mousePressed(MouseEvent e){
        if(!game.reset){
            int mx = (int)(e.getX() + camera.getX());
            int my = (int)(e.getY() + camera.getY());
            double grad;
            double bx1, by1, bx2, by2;
            double spread;
            for (int i = 0; i < ObjHandler.objects.size(); i++){
                GameObject tempObject = ObjHandler.objects.get(i);
                if(tempObject.getId() == ID.Player && game.ammo >= 1){
                    if(game.level_num > 4){
                        double distance = Math.sqrt( ( (mx - (tempObject.getX()+16))*(mx - (tempObject.getX()+16)) ) + ( (my - (tempObject.getY()+16))*(my - (tempObject.getY()+16)) ) );
                        if(distance > 35){
                            //direct bullet
                            ObjHandler.addObject(new Bullet(tempObject.getX()+16, tempObject.getY()+6, ID.Bullet, ObjHandler, mx, my, sprite_sheet));
                            grad = -((mx-tempObject.getX())/(my-tempObject.getY()+6));
                            if(distance < 160){
                                spread = distance/5;
                            }else{
                                spread = distance/10;
                            }
                            //shotgun bullet1
                            bx1 = mx + spread * Math.sqrt(1/(1+(grad*grad)));
                            by1 = my + grad * spread * Math.sqrt(1/(1+(grad*grad)));
                            //shotgun bullet2
                            bx2 = mx - spread * Math.sqrt(1/(1+(grad*grad)));
                            by2 = my - grad * spread * Math.sqrt(1/(1+(grad*grad)));
                            //shotgun direct bullets
                            ObjHandler.addObject(new Bullet(tempObject.getX()+16, tempObject.getY()+6, ID.Bullet, ObjHandler, (int)bx2, (int)by2, sprite_sheet));
                            ObjHandler.addObject(new Bullet(tempObject.getX()+16, tempObject.getY()+6, ID.Bullet, ObjHandler, (int)bx1, (int)by1, sprite_sheet));
                            playsound(game.gunshot);
                            game.ammo -= 3;
                        }
                    }else{
                        ObjHandler.addObject(new Bullet(tempObject.getX()+16, tempObject.getY()+6, ID.Bullet, ObjHandler, mx, my, sprite_sheet));
                        playsound(game.gunshot);
                        game.ammo--;
                    }
                }
            }
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


}
