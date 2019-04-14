package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class PlayMusic {

    private static Clip clip;

    public static void play(String filepath){
        try{
            File musicPath = new File(filepath);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(clip.LOOP_CONTINUOUSLY);

            }else{
                System.out.println("cant file file");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public static void stop(){
        clip.stop();
    }
}
