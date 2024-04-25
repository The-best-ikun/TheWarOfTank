package GamePre;/*Author:l
Explain:
Version:1.0*/

import java.util.Random;
import java.util.Vector;

public class Tank {

        private int x;
        private int y;//坐标
        private int heath_value=10;//直接设置默认生命值
        private String name="普通坦克";//默认名字
        private int directory=0;//方向


    /*0：向上
    * 1：向右
    * 2：向左
    * 3：向右*/
    private int speed=5;//坐标值增减速度
    private int type;/*
    0:玩家坦克
    1：敌方坦克*/
    Vector<Bullet> bullet_containers=new Vector<>();
    private  boolean isLive=true;//坦克生命值为0时，改为false
    private boolean isLap=false;

    public boolean isLap() {
        return isLap;
    }

    public void setLap(boolean lap) {
        isLap = lap;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirectory() {
        return directory;
    }

    public void setDirectory(int directory) {
        this.directory = directory;
    }

    public int getHeath_value() {
        return heath_value;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setHeath_value(int heath_value) {
        this.heath_value = heath_value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Tank() {
    }

    public Tank(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void moveUp(){
        directory=0;
        if(y<=0){return;}else {//撞墙逻辑
            y -= speed;
        }
        }
        public void moveDown(){
            directory=3;
            if(y>=(1200-170)){//正常情况应该是减100的，这里好像出问题了
                return;}
            else {
                y += speed;
            }
        }
        public void moveLeft(){
        directory=2;
        if(x<=0){return;}else {
            x -= speed;
        }
        }
        public void moveRight(){
        directory=1;
            if(x>=(2000-110-10)){return;}else {
        x+=speed;}
        }


    public void Fire(){}
    public void Check_bullet(Vector<Bullet> bullets){}
    public void Check_isLive(){
        if (getHeath_value()<=0){
            setLive(false);
        }
    }

}
