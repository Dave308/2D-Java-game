package main;

import java.awt.Graphics;
import java.awt.Rectangle;

//abstract creates an idea basically
public abstract class GameObject {

    protected int x, y; //Location of an object
    protected float velX = 0, velY = 0; //Speed of an object
    protected ID id;
    protected SpriteSheet sprite_sheet;

    public GameObject(int x, int y, ID id, SpriteSheet sprite_sheet){
        this.x = x;
        this.y = y;
        this.id = id;
        this.sprite_sheet = sprite_sheet;
    }


    //methods that need to be implemented with each game object

    public abstract void update(); //an object needs to update (to move, animate, etc...)

    public abstract void render(Graphics g); //an object needs to be rendered

    public abstract Rectangle getBounds(); //every object needs bounds for collision detection

    /* --- Getters and Setters --- */
    public ID getId(){
        return id;
    }
    public void setId(ID id){
        this.id = id;
    }
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }
    public int getY(){
        return y;
    }
    public void setY(){
        this.y = y;
    }

}
