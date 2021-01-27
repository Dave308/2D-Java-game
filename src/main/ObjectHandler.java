package main;

import java.awt.Graphics;
import java.util.LinkedList;



public class ObjectHandler {

    LinkedList<GameObject> objects = new LinkedList<GameObject>(); //essentially array of objects

    // Player direction booleans
    private boolean up = false, down = false, right = false, left = false;


    public void update(){
        //loops through each object and updates them
        for (int i = 0; i< objects.size(); i++){
            GameObject tempObject = objects.get(i);
            //call update method on each object
            tempObject.update();
        }
    }

    public void render(Graphics g){
        //loops through each object and renders them
        for (int i = 0; i< objects.size(); i++){
            GameObject tempObject = objects.get(i);
            //calls the render method on each object in the object list
            tempObject.render(g);
        }
    }

    //add/create object in game by adding them to the linked list
    public void addObject(GameObject tempObject){
        objects.add(tempObject);
    }
    //remove/delete object from game by removing them to the linked list
    public void removeObject(GameObject tempObject){
        objects.remove(tempObject);
    }


    //Getters and Setters for direction key used on object
    //*** Bolean variables relate to the player object only ***
    public void setUp(boolean up){
        this.up = up;
    }
    public void setDown(boolean down){
        this.down = down;
    }
    public void setLeft(boolean left){
        this.left = left;
    }
    public void setRight(boolean right){
        this.right = right;
    }
    public boolean isUp(){
        return up;
    }
    public boolean isDown(){
        return down;
    }
    public boolean isLeft(){
        return left;
    }
    public boolean isRight(){
        return right;
    }



}

