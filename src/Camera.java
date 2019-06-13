package Main;

public class Camera {

    private float x ,y;

    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void update(GameObject player){
        x += ((player.getX() - x) - 1000/2) * 0.5f;
        y += ((player.getY() - y) - 563/2) * 0.5f;

        //stop camera from leaving the game area
        if(x <= 12)     x = 12;
        if(x >= 1042)   x = 1042;
        if(y <= 7)      y = 7;
        if(y >= 607)    y = 607;
    }

    //Getters and Setters for x and y values of the camera
    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(){
        this.y = y;
    }


}
