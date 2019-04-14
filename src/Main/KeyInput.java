package Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



/* --- handles user input controls --- */

public class KeyInput extends KeyAdapter {

    ObjectHandler ObjHandler;

    private int counter = 1;

    //constructor
    public KeyInput(ObjectHandler handler){
        //pass object handler object to this class instead of instantiating a new one
        //if a new one is made we would lose the linked list of objects, etc...
        this.ObjHandler = handler;
    }

    public void keyPressed(KeyEvent e){

        int key = e.getKeyCode();

        //loop through all game objects
        for(int i = 0; i < ObjHandler.objects.size(); i++){
            GameObject tempObject = ObjHandler.objects.get(i);
            //once the player object is found, the movement direction bools for that object can be
            //modified
            if(tempObject.getId() == ID.Player){
                if(key == KeyEvent.VK_UP)
                    ObjHandler.setUp(true);

                if(key == KeyEvent.VK_DOWN)
                    ObjHandler.setDown(true);

                if(key == KeyEvent.VK_LEFT)
                    ObjHandler.setLeft(true);

                if(key == KeyEvent.VK_RIGHT)
                    ObjHandler.setRight(true);

                if(key == KeyEvent.VK_ESCAPE){
                    if(counter%2 == 1){
                        System.out.println("Play");
                        PlayMusic.play("res/BackgroundMusic.wav");
                        counter+=1;
                    }
                    else if(counter%2 == 0){
                        System.out.println("Stop");
                        PlayMusic.stop();
                        counter+=1;
                    }
                }
            }
        }
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        for(int i = 0; i < ObjHandler.objects.size(); i++){
            GameObject tempObject = ObjHandler.objects.get(i);
            if(tempObject.getId() == ID.Player){
                if(key == KeyEvent.VK_UP)
                    ObjHandler.setUp(false);

                if(key == KeyEvent.VK_DOWN)
                    ObjHandler.setDown(false);

                if(key == KeyEvent.VK_LEFT)
                    ObjHandler.setLeft(false);

                if(key == KeyEvent.VK_RIGHT)
                    ObjHandler.setRight(false);
            }
        }
    }
}

