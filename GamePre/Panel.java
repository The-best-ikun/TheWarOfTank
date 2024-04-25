package GamePre;

import javazoom.jl.decoder.JavaLayerException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.Vector;


public class Panel extends JPanel implements KeyListener,Runnable {
    Vector<Node> nodes=new Vector<>();
    Player_Tank player_tank=null;
    Vector<EnemyTank> enemyTank=new Vector<>();//用集合存储多个相同类型的对象，这里用Vector是为了后面多线程做准备
    Vector<Boom> booms=new Vector<>();
    //Vector<Boom> booms_for_player=new Vector<>();
    /*Vector<Button> buttons=new Vector<>();*///以后很多个按钮可以用这个
    Button button01=null;
    Button button02=null;
    Boom player_boom = null;

    Image image01=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("./start01.png"));
    Image image02=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("./start02.png"));
    Image image03=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("./start03.png"));
    Image image04=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("./start04.png"));
    Image image05=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("./start05.png"));
    Image image06=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("./start06.png"));
    Boolean Check_while=true;//让一段代码只执行一次的临时变量
    public Panel(boolean isContinue){//是否进行上局游戏做一个判断

//        button01=new Button();
//        button02=new Button();
        if(isContinue){
            System.out.println("继续业务的代码");
            Recorder.ReadRecordFile();
            Recorder.ReadRecordPlayerFile();
            player_tank=Recorder.getPlayer_tank();
            nodes=Recorder.getNodes();
            for (int i = 0; i < nodes.size(); i++) {
                Node node=nodes.get(i);
                EnemyTank tmp=new EnemyTank(node.x,node.y);
                tmp.setDirectory(node.directory);
                tmp.setHeath_value(node.health_value);
                enemyTank.add(tmp);
            }
            for (int i = 0; i < enemyTank.size(); i++) {
                new Thread(enemyTank.get(i)).start();
            }
            Recorder.setEnemyTanks(enemyTank);
            Recorder.setPlayer_tank(player_tank);


        }else {
            player_tank=new Player_Tank();
            for (int i = 0; i < 3; i++) {
                enemyTank.add(i, new EnemyTank(200 * (i + 1), 100));
                enemyTank.get(i).setEnemyTanks(enemyTank);
                new Thread(enemyTank.get(i)).start();//启动敌方坦克线程
                Recorder.setEnemyTanks(enemyTank);
                Recorder.setPlayer_tank(player_tank);
//            for (int j = 0; j <  enemyTank.get(i).bullet_containers.size(); j++) {
//               Bullet bullet= enemyTank.get(i).bullet_containers.get(j);
//            }

            }
        }

           // MusicPlayer.play("D:\\javaworkspace\\TheWarOfTank\\out\\production\\TheWarOfTank\\BGM.mp3",60000);
    }



    //总绘图函数应该写在这个函数里
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 2000, 1200);//绘制游戏背景（默认黑色）

//        DrawButton01(g,button01.button_State);//画个按钮
//        DrawButton02(g,button02.button_State);
//       button01.LifeDown();
//       button02.LifeDown();暂时不会java自带的button，自己写的没那味
        DrawRecorder(g);//画个记录器
        player_tank.Check_isLive();

        if (player_tank.isLive() == false) {
            g.drawString("玩家坦克已死", 60, 60);
            if(player_boom==null) {
                player_boom = new Boom(player_tank.getX(), player_tank.getY());
            }
        } else {
            DrawTank(player_tank.getX(), player_tank.getY(), g, player_tank.getDirectory(), player_tank.getType());
        }
        // DrawBullets(player_tank.bullet.getX(),player_tank.bullet.getY(),player_tank.bullet.getSpeed(),g);
        for (int i = 0; i < enemyTank.size(); i++) {
            EnemyTank tmp = enemyTank.get(i);
            tmp.Check_isLive();
            //tmp.Check_Tank(player_tank);追踪逻辑
            int count = 0;
            if (tmp.isLive() == false) {
                booms.add(new Boom(tmp.getX(), tmp.getY()));
                enemyTank.remove(tmp);
                Recorder.increaseYour_destroyed_enemyTank();
                MusicPlayer.play("D:\\javaworkspace\\TheWarOfTank\\out\\production\\TheWarOfTank\\boom.mp3",1600);
            } else {
                enemyTank.get(i).Check_bullet(player_tank.bullet_containers);
                DrawTank(tmp.getX(), tmp.getY(), g, tmp.getDirectory(), tmp.getType());
                player_tank.Check_bullet(tmp.bullet_containers);//检测是否被敌方坦克击中
//            new Thread(tmp).start();//启动敌方坦克线程不能把启动线程放进Paint中，因为paint在以20Hz的速度不断被调用
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }//休眠0.5s（冷却时间）
                if (tmp.bullet_containers.size() > 0) {
                    for (int j = 0; j < tmp.bullet_containers.size(); j++) {
                        Bullet enemy_bullet = tmp.bullet_containers.get(j);
                        //g.drawString("玩家坦克生命值" + player_tank.getHeath_value(), 30, 30);
                        //g.drawString("敌方坦克生命值" + enemyTank.get(0).getHeath_value(), 40, 40);
                        if (enemy_bullet.IsLive()) {
                            DrawBullets(enemy_bullet.getX(), enemy_bullet.getY(), tmp.getSpeed(), g, tmp.getType());
                        } else {
                            tmp.bullet_containers.remove(enemy_bullet);
                        }
                    }
                }
                //g.drawString("敌方集合内炮弹数量：" + enemyTank.get(0).bullet_containers.size(), 20, 50);//获取第二辆坦克集合内的数量
            }
        }
        if (player_tank.bullet_containers.size() > 0) {//判断存在集合中有炮弹
            for (int i = 0; i < player_tank.bullet_containers.size(); i++) {
                Bullet bullet_temp = player_tank.bullet_containers.get(i);
                System.out.println();
                if (bullet_temp.IsLive()) {//判断线程是否存在
                    DrawBullets(bullet_temp.getX(), bullet_temp.getY(), bullet_temp.getSpeed(), g, player_tank.getType());
                } else {
                    player_tank.bullet_containers.remove(bullet_temp);//移除死炮弹，避免集合中炮弹堆积
                }

            }

        }

        //爆炸绘图
        if (booms.size() != 0 && booms != null) {
            for (Boom e : booms
            ) {
                if (e.life > 15) {
                    g.drawImage(image01, e.x, e.y, this);
                } else if (e.life > 12) {
                    g.drawImage(image02, e.x, e.y, this);
                } else if (e.life > 9) {
                    g.drawImage(image03, e.x, e.y, this);
                } else if (e.life > 6) {
                    g.drawImage(image04, e.x, e.y, this);
                } else if (e.life > 3) {
                    g.drawImage(image05, e.x, e.y, this);
                } else if (e.life > 0) {
                    g.drawImage(image06, e.x, e.y, this);
                }
                e.lifeDown();
            }
        }

        if (player_boom != null&&player_boom.isLive!=false) {
                if (player_boom.life > 15) {
                    g.drawImage(image01, player_boom.x, player_boom.y, this);
                } else if (player_boom.life > 12) {
                    g.drawImage(image02, player_boom.x, player_boom.y, this);
                } else if (player_boom.life > 9) {
                    g.drawImage(image03, player_boom.x, player_boom.y, this);
                } else if (player_boom.life > 6) {
                    g.drawImage(image04, player_boom.x, player_boom.y, this);
                } else if (player_boom.life > 3) {
                    g.drawImage(image05, player_boom.x, player_boom.y, this);
                } else if (player_boom.life > 0) {
                    g.drawImage(image06, player_boom.x, player_boom.y, this);
                }
            player_boom.lifeDown();

        }
    }
    public void DrawTank(int x,int y,Graphics g,int directory,int type){
        switch (type){
            case 0:g.setColor(Color.ORANGE);
            break;
            case 1:g.setColor(Color.RED);
            break;
        }
        switch(directory){
            case 0://向上
                g.fill3DRect(x,y,30,100,false);//轮子
                g.fill3DRect(x+30,y+20,50,60,false);//机体
                g.fill3DRect(x+80,y,30,100,false);//轮子
                g.fill3DRect(x+50,y,10,32,false);//炮管
                g.fillOval(x+35,y+30,40,40);//炮台
            break;
            case 1://向右
                g.fill3DRect(x,y,100,30,false);//轮子
                g.fill3DRect(x,y+80,100,30,false);//轮子
                 g.fill3DRect(x+20,y+30,60,50,false);//机体
                g.fill3DRect(x+48,y+50,52,10,false);//炮管
                g.fillOval(x+25,y+35,40,40);//炮台
                break;
            case 2://left
                g.fill3DRect(x,y,100,30,false);//轮子
                g.fill3DRect(x,y+80,100,30,false);//轮子
                g.fill3DRect(x+20,y+30,60,50,false);//机体
                g.fill3DRect(x,y+50,52,10,false);//炮管
                g.fillOval(x+25,y+35,40,40);//炮台
                break;
            case 3://down
                g.fill3DRect(x,y,30,100,false);//轮子
                g.fill3DRect(x+30,y+20,50,60,false);//机体
                g.fill3DRect(x+80,y,30,100,false);//轮子
                g.fill3DRect(x+50,y+48,10,52,false);//炮管
                g.fillOval(x+35,y+30,40,40);//炮台
                break;
            default:
                System.out.println("暂时未处理！！！");

        }
    }
    public void DrawBullets( int bullet_x,int bullet_y,int bullet_speed,Graphics g,int type){//形参为要进行该函数的对象
        if(type==0){
        g.setColor(Color.GREEN);
        }else{
            g.setColor(Color.magenta);
        }

        g.fill3DRect(bullet_x,bullet_y,10,10,false);//得到子弹初始位置
    }
    public  void DrawRecorder (Graphics g){
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑",Font.BOLD,30));
        //name:字体名称
        //style:风格(Font.PLAIN,粗体：Font.BOLD,斜体：Font.ITALIC)
        //size:大小
        g.drawString("累计击毁敌方坦克",2020,50);
        DrawTank(2020,100,g,0,1);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.your_destroyed_enemyTank+"",2160,160);
        g.drawString("玩家坦克剩余血量"+player_tank.getHeath_value(),2020,250);
        g.setColor(Color.BLUE);
        for (int i=1;i<player_tank.getHeath_value();i++){
        g.fill3DRect(2040,300,20*i,20,true);
        }
        DrawTank(2020,350,g,0,0);
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑",Font.BOLD,30));
        g.drawString("W：上 A:左 S:下 D:右",2020,500);
        g.drawString("J:攻击",2020,550);
    }
    public void DrawButton01(Graphics g,Boolean exit/*鼠标是否经过该按键*/){
        //绘制按钮01
        //按钮01区域：x:2020-2200
        //          y:800-880
        if(exit){
        g.setColor(Color.BLUE);
        }else{
        g.setColor(Color.ORANGE);
        }
        g.fill3DRect(2020,800,180,80,true);
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑",Font.BOLD,30));
        g.drawString("新游戏(0)",2040,850);
    }
    public void DrawButton02(Graphics g,Boolean exit){
        //绘制按钮02
        //按钮02区域：x:2020-2200
        //          y:900-980
        if(exit){
            g.setColor(Color.BLUE);
        }else{
            g.setColor(Color.ORANGE);
        }
        g.fill3DRect(2020,900,180,80,true);
        g.setColor(Color.BLACK);
        g.setFont(new Font("微软雅黑",Font.BOLD,30));
        g.drawString("继续(1)",2060,960);
    }
//    public   void DrawExplosion(Graphics g,Vector<Boom> booms){//爆炸动画方法,直接用图片画算了，坐标像素画比较繁琐
//        for (int i=0;i<booms.size();i++)
//       if(booms.get(i).life>15)
//            g.drawImage(,e.x,e.y,100,e.100,this);
//
//    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyChar()){
            case 'a':
            case 'A':
                player_tank.moveLeft();
                //this.repaint();//后面学了多线程就可以直接定义面板刷新率了，如果是图片的话可能要用到双缓冲
                break;
            case 'w':
            case 'W':
                player_tank.moveUp();
                //this.repaint();
                break;
            case 's':
            case 'S':
                player_tank.moveDown();
                //this.repaint();
                break;
            case'D':
            case 'd':
                player_tank.moveRight();
                //this.repaint();
                break;
            case'J':
            case'j':
                player_tank.Fire();
                MusicPlayer.play("D:\\javaworkspace\\TheWarOfTank\\out\\production\\TheWarOfTank\\biu.mp3",1000);
                break;
            case '0':
                //button01.setButton_State(true);
                break;
            case '1':
                //button02.setButton_State(true);
                break;
            default:
                System.out.println("使用WASD--J操纵！！！");

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void run() {
        while(true) {
            repaint();
            try {
                Thread.sleep(50);//相当于20帧的刷新率
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
