package GamePre;/*Author:l
Explain:
Version:1.0*/

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;
import java.util.Vector;

public class Game_Main extends JFrame {
    Panel mp=null;


    public static void main(String[] args) {
        Game_Main game_main=new Game_Main();

    }
    public Game_Main(){
        int i;
        boolean isContinue=false;
        System.out.println("是否继续上局游戏");
        System.out.println("0:继续，1:新游戏");
        Scanner scanner=new Scanner(System.in);
        i=scanner.nextInt();
        if(i==0){
            isContinue=true;
        } else if (i==1) {
            isContinue=false;
        }
        mp=new Panel(isContinue);
        new Thread(mp).start();//启动绘制线程
        this.add(mp);
        this.setSize(2400,1200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(mp);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.SaveRecordPlayerFile();
                Recorder.SaveRecordFile();//监听关闭并保存
                System.exit(0);
            }
        });//窗口监听器

    }
}
