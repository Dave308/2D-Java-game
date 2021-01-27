package main;

import java.util.Timer;
import java.util.TimerTask;

public class countdown {

    public int interval;
    public Timer timer;
    private Game game;
    public int time;

    public countdown(Game game, int seconds){
        this.game = game;
        time = seconds;
    }


    public void start_timer(){
        int delay = 1000;
        int period = 1000;
        timer = new Timer();
        interval = time;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                setInterval();
            }
        }, delay, period);
    }

    public void stop_timer(){
        timer.cancel();
    }

    private final int setInterval() {
        if(interval < 1){
            return interval = -1;
        }
        return --interval;
    }


}

