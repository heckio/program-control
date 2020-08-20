package Resources;

import Main.Handler;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ArrayList;

/**
 * Created by AlexVR on 1/24/2020.
 */

public class MusicHandler {

    Handler handler;

    public MusicHandler(Handler handler) {
        this.handler = handler;
    }

    public void startMusic(String fileName) {
        if (!handler.isMute()) {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Music/Background/" + fileName));
                clip.open(inputStream);
                clip.loop(-1);
                clip.start();
                handler.setBackgroundMusic(clip);
                handler.setMute(false);
            } catch (Exception e) {
               // System.err.println(e.getMessage());
            }
        }
    }

    public void playEffect(String fileName){
        if (!handler.isMute()) {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Music/Effects/" + fileName));
                clip.open(inputStream);
                clip.loop(0);
                clip.start();
                handler.getEffects().add(clip);
            } catch (Exception e) {
               // System.err.println(e.getMessage());
            }
        }
    }

    public void triggerGalaga(){
        changeMusic("space.wav");
    }

    //Untested
    public void stopAllEffects(){
        try {
            for (Clip clip: handler.getEffects()){
                clip.stop();
                clip.close();
            }
            handler.setEffects(new ArrayList<>());
        } catch (Exception e) {
            //System.err.println(e.getMessage());
        }
    }

    public void changeMusic(String fileNmae){
        stopMusic();
        startMusic(fileNmae);
    }

    public void stopMusic() {
        try {
            handler.getBackgroundMusic().stop();
            handler.getBackgroundMusic().close();
        } catch (Exception e) {
           // System.err.println(e.getMessage());
        }
    }

    public void pauseMusic() {
        try {
            handler.getBackgroundMusic().stop();
            handler.setMute(true);
        } catch (Exception e) {
            //System.err.println(e.getMessage());
        }
    }

    public void resumeMusic() {
        try {
            handler.getBackgroundMusic().start();
            handler.setMute(false);
        } catch (Exception e) {
            //System.err.println(e.getMessage());
        }
    }


}
