package GamePre;/*Author:l
Explain:
Version:1.0*/

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MusicPlayer {

    static Player player = null;
    static Boolean stop_play=false;

//    public static void PlayMusic(String FilePath)throws FileNotFoundException, JavaLayerException {
//        File file = new File(FilePath);
//        FileInputStream fis = new FileInputStream(file);
//        BufferedInputStream stream = new BufferedInputStream(fis);
//        Player player = new Player(stream);
//        player.play();
//    }

    /**
     * 播放 20 秒并结束播放
     */
    public static  void play(String FilePath,int time_for_music) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = new File(FilePath);
                    FileInputStream fis = new FileInputStream(file);
                    BufferedInputStream stream = new BufferedInputStream(fis);
                    player = new Player(stream);
                    player.play();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }).start();
//        try {
//            Thread.sleep(time_for_music);//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//            player.close();

    }


}
