package GamePre;/*Author:l
Explain:用于进行IO流的交互
Version:1.0*/

import java.io.*;
import java.util.Vector;

public class Recorder {
    static Vector<EnemyTank> enemyTanks=null;
    static Player_Tank player_tank=null;
    static Vector<Node> nodes=new Vector<>();
    static int your_destroyed_enemyTank=0;
    static String record_FilePath="D:\\demo\\record.txt";
    static String record_playerFilePath="D:\\demo\\player_record.txt";
    static FileReader fr=null;
    static BufferedReader br=null;
    static BufferedWriter bw=null;
    static FileWriter fw=null;

    public static Player_Tank getPlayer_tank() {
        return player_tank;
    }

    public static void setPlayer_tank(Player_Tank player_tank) {
        Recorder.player_tank = player_tank;
    }

    public static Vector<Node> getNodes() {
        if(nodes==null){
            System.out.println("上局游戏已结束，只能进行新游戏");
        }
        return nodes;
    }

    public static void setNodes(Vector<Node> nodes) {
        Recorder.nodes = nodes;
    }

    public int getYour_destroyed_enemyTank() {
        return your_destroyed_enemyTank;
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static void setYour_destroyed_enemyTank(int n) {
        your_destroyed_enemyTank = n;
    }
    public static void increaseYour_destroyed_enemyTank(){
        your_destroyed_enemyTank+=1;
    }
    public static void ReadRecordFile(){
        try {
            br=new BufferedReader(new FileReader(record_FilePath));
            String str=null;
            your_destroyed_enemyTank=Integer.parseInt(br.readLine());
            while((str=br.readLine())!=null ){
                String[] tmp= str.split(" ");
                Node e = new Node(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]),Integer.parseInt(tmp[3]));
                nodes.add(e);
            }
            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void SaveRecordFile(){
        try {
            bw=new BufferedWriter(new FileWriter(record_FilePath));
            bw.write(your_destroyed_enemyTank+"");
            for (EnemyTank e:
                 enemyTanks) {
                bw.newLine();
                bw.write(e.getX()+" "+e.getY()+" "+e.getDirectory()+" "+e.getHeath_value());
            }
            if(bw!=null)
                bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
                System.out.println("记录文件保存函数执行完毕");
        }

    }
    public static void SaveRecordPlayerFile(){
        try {
            bw=new BufferedWriter(new FileWriter(record_playerFilePath));
           bw.write(player_tank.getX()+" "+player_tank.getY()+" "+player_tank.getDirectory()+" "+player_tank.getHeath_value());
            if(bw!=null)
                bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void ReadRecordPlayerFile(){
        try {
            br=new BufferedReader(new FileReader(record_playerFilePath));

            String str=null;
            while((str=br.readLine())!=null ){
                String[] tmp= str.split(" ");
                player_tank = new Player_Tank(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1]));
                player_tank.setDirectory(Integer.parseInt(tmp[2]));
                player_tank.setHeath_value(Integer.parseInt(tmp[3]));

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
