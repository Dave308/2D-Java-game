package Main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;

public class Game extends Canvas implements Runnable{

    private boolean isRunning = false;
    private Thread thread;
    private ObjectHandler ObjHandler;
    public Camera camera;
    private SpriteSheet ss;

    private BufferedImage level = null;
    private BufferedImage sprite_sheet = null;
    private BufferedImage floor = null;
    public File gunshot = new File("res/gun.WAV");
    public File hitmarker = new File("res/HITMARKER.WAV");
    public countdown gametimer = new countdown(this, 100);
    private countdown resettimer = new countdown(this, 3);
    private ImageLoader loader = new ImageLoader();

    public int ammo = 120;
    public int hp = 100;
    public int level_num = 1;
    public int enemyCounter = 1;
    private boolean dead = false;
    public boolean reset = false;
    public int mouseX, mouseY;

    //Game construction
    public Game(){

        //makes a new window
        new Window(1000, 563, "Shooter game", this);

        //start game loop thread
        start();

        //Create handler object. This will keep track of all game objects
        ObjHandler = new ObjectHandler();

        //Creates the camera that will follow the player character around
        camera = new Camera(100,100);

        //allows canvas to listen for key input
        this.addKeyListener(new KeyInput(ObjHandler));

        //load level background

        level = loader.loadImage("/level.png"); //method returns image object

        sprite_sheet = loader.loadImage("/Sprite_Sheet.png");
        ss = new SpriteSheet(sprite_sheet);
        floor = ss.grabImage(4,2,32,32);

        //allows canvas to listen for mouse input
        this.addMouseListener(new MouseInput(ObjHandler, camera, ss, this));



        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent me){
                mouseX = me.getX();
                mouseY = me.getY();
                for (int i = 0; i < ObjHandler.objects.size(); i++){
                    GameObject tempObject = ObjHandler.objects.get(i);
                    if(tempObject.getId() == ID.Crosshairs){
                        tempObject.x = mouseX;
                        tempObject.y = mouseY;
                    }
                }
            }
        });

        LoadLevel(level);

        gametimer.start_timer();
    }

    //Thread methods
    private synchronized void start(){
        isRunning = true;
        thread = new Thread(this);
        thread.start(); //calls the run method
    }

    //stop thread
    private synchronized void stop(){
        isRunning = false;
        try{
            thread.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }


    //main game loop run by thread. Calls the update method as fast as possible and render 60 time per second
    public void run(){
        /* --- Game loop made by Notch --- */
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(isRunning){
            /* --- Game loop --- */
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            while(delta >= 1){
                update();
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frames = 0;
            }
            /* --- Game loop --- */
        }
        stop();
    }

    //update screen with graphics 60fps
    public void update(){
        //update camera with player object in parameters
        for(int i = 0; i < ObjHandler.objects.size(); i++){
            if(ObjHandler.objects.get(i).getId() == ID.Player){
                //"ObjHandler.object.get(i)" is the player and is used to get the current coordinates of the player
                camera.update(ObjHandler.objects.get(i));
            }
        }
        //loops through each object and updates them
        ObjHandler.update();
    }

    //draw graphics as fast as possible
    public void render(){
        if(ammo < 0){
            ammo = 0;
        }
        //level completed
        if(enemyCounter == 0){
            reset = true;
            level_num++;
            enemyCounter = 1;
            gametimer.stop_timer();
            resettimer.start_timer();
        }
        //die
        if(hp <= 0){
            reset = true;
            level_num = 1;
            gametimer.stop_timer();
            resettimer.start_timer();
            ammo = 75;
            enemyCounter = 1;
            hp = 100;
            gametimer.time = 100;
            dead = true;
        }
        //timer runs out
        if(gametimer.interval == -1 && !reset){
            gametimer.stop_timer();
            resettimer.start_timer();
            reset = true;
            level_num = 1;
            ammo = 75;
            enemyCounter = 1;
            gametimer.time = 100;
            hp = 100;
            dead = true;
        }

        //pre-load frames behind the actual window. A frame queue in effect
        BufferStrategy buffer_s = this.getBufferStrategy();
        if(buffer_s == null){
            this.createBufferStrategy(3);
            return;
        }
        //Creates a link between Graphics, to which you can write data and the BufferStrategy
        Graphics g = buffer_s.getDrawGraphics();
        //cast g to graphics 2d for camera
        Graphics2D g2d = (Graphics2D) g;

        /* --- draw area --- */
        {
            //elements are drawn in the order they are written here.

            //solid color for floor
            g.setColor(new Color(0, 0,0));
            g.fillRect(0,0,1000, 563);

            //don't all camera to move during reset screen
            if(!reset) {
                g2d.translate(-camera.getX(), -camera.getY());
            }

            /* --- draw area within camera view --- */
            {

                ObjHandler.render(g);

            }
            /* --- draw area within camera view --- */

            if(reset){
                //black screen
                g.setColor(new Color(0, 0,0));
                g.fillRect(0,0,1100, 563);
                ObjHandler.objects.clear();

            }else{
                //don't all camera to move during reset screen
                g2d.translate(camera.getX(), camera.getY());
            }

            /* Heads up display */

            //don't show HUD in reset screen
            if(!reset) {
                g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                g.setColor(Color.gray);
                g.fillRect(5, 5, 200, 32);

                g.setColor(Color.green);
                g.fillRect(5, 5, hp * 2, 32);

                g.setColor(Color.black);
                g.drawRect(5, 5, 200, 32);

                g.setColor(Color.white);
                g.drawString("Ammo: " + ammo, 60, 60);

                g.setColor(Color.white);
                g.drawString("Enemies: " + enemyCounter, 880, 25);

                g.setColor(Color.white);
                g.drawString("Level: " + level_num, 880, 45);

                g.setColor(Color.white);
                g.drawString("Time left: " + gametimer.interval, 780/2, 25);

            }else{
                g.setColor(Color.white);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                if(dead){
                    g.drawString("Game over... " + resettimer.interval, 900/2, 563/2);
                }else if (!dead && level_num%10 != 0){
                    g.drawString("Loading next level... " + resettimer.interval, 900/2, 563/2);
                }
                if(level_num == 5){
                    g.drawString("Shotgun unlocked!", 900/2, 653/2);
                }else if(level_num == 2){
                    g.drawString("Press escape to play/stop background music", 12, 18);
                }
                if(level_num%10 == 0){
                    level = loader.loadImage("/boss level.png");
                    ammo = 227;
                    hp = 100;
                    g.drawString("Loading boss level... " + resettimer.interval, 900/2, 563/2);
                }else{
                    level = loader.loadImage("/level.png");
                }
                g.drawString("Level: " + level_num, 900/2, 613/2);
                //if reset timer finished
                if(resettimer.interval < 1){
                    LoadLevel(level);
                    reset = false;
                    resettimer.stop_timer();
                    if(level_num != 1 && gametimer.time >= 20){
                        gametimer.time -= 10;
                    }
                    ObjHandler.setUp(false);
                    ObjHandler.setDown(false);
                    ObjHandler.setLeft(false);
                    ObjHandler.setRight(false);
                    gametimer.start_timer();
                    dead = false;
                    //reset health after boss round
                    if(level_num%10 == 1){
                        hp = 100;
                    }
                }
            }
            /* Heads up display */

        }
        /* --- draw area --- */


        g.dispose(); //releases system resources, delete old graphics
        buffer_s.show(); //makes next available buffer visible

    }




    //load the level image and turn black pixels into "wall" objects and blue pixels into "Space_Marine" objects
    private void LoadLevel(BufferedImage image){
        int w = image.getWidth();
        int h = image.getHeight();

        int playerCounter = 0;

        //generate blocks from image by looping through each pixel in image
        for(int xx = 0; xx<w; xx++){
            for(int yy = 0; yy<h; yy++){
                //get pixel color values
                int pixelRGB = image.getRGB(xx,yy);

                //pixelRGB contains the int value of the Red, Green, Blue components of the color.
                int blue = pixelRGB & 0xff;
                int green = (pixelRGB & 0xff00) >> 8;
                int red = (pixelRGB & 0xff0000) >> 16;

                //if pixel is red turn it into a wall
                if(red == 255) {
                    ObjHandler.addObject(new Wall(xx * 32, yy * 32, ID.Wall, ss));
                }
                //if pixel is blue turn it into a Space_Marine
                if(blue == 255 && green == 0){
                    if(playerCounter > 1){
                        System.out.println("Error: multiple player characters detected");
                        System.out.println("Exiting...");
                        stop(); //stop thread
                        System.exit(0);
                    }
                    //add player to the list of objects in the object handler
                    ObjHandler.addObject(new Space_Marine(xx * 32,yy * 32, ID.Player, ObjHandler, ss, this));
                    playerCounter++;
                }

                if(green == 255 && blue == 0) {
                    if(enemyCounter <= level_num*2){
                        ObjHandler.addObject(new Enemy(xx * 32, yy * 32, ID.Enemy, ObjHandler, ss, this));
                        enemyCounter++;
                    }else if (level_num%10 == 0){
                        ObjHandler.addObject(new Enemy(xx * 32, yy * 32, ID.Enemy, ObjHandler, ss, this));
                        enemyCounter++;
                    }
                }

                if(green == 255 && blue == 255) {
                    ObjHandler.addObject(new AmmoCrate(xx * 32, yy * 32, ID.Crate, ss));
                }

            }
        }
        enemyCounter-=1;
        ObjHandler.addObject(new Crosshairs(0, 0, ID.Crosshairs, ObjHandler, ss, this));
    }


    //main method
    public static void main(String args[]){
        //creates new game object
        new Game();
    }


}
