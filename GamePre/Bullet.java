package GamePre;/*Author:l
Explain:子弹
Version:1.0*/

public class Bullet implements Runnable  {
    private Boolean live=false;//判断线程是否还存活，本意不是想用通知线程
    public boolean IsLive(){//得到线程状态
        return live;
    }


    private int x;
    private int y;
    private int speed=10;
    private int directory;//子弹移动方向

//    public void setMp(Panel mp) {
//        this.mp = mp;
//    }

//    Panel mp=null;//接受画板，不然无法在该类里实现repaint方法

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirectory() {
        return directory;
    }

    public Bullet(int x,int y,int directory) {

        this.directory=directory;
        switch (directory) {
            case 0://方向向上
            this.x = x + 50;
            this.y =y ;
            break;
            case 1://向右
                this.x = x + 100;
                this.y =y+50;
                break;
            case 2://向左
                this.x=x;
                this.y=y+50;
                break;
            case 3:
                this.x=x+50;
                this.y=y+100;
                break;
            default:
        }
    }

    public void BulletMove()  {
        switch (directory){
            case 0://向上
                live=true;
                while(y>=0&&y<=1200&&x>=0&&x<=2000) {
                    y -= speed;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                live=false;
                break;
            case 1://向右
                live=true;
                while(y>=0&&y<=1200&&x>=0&&x<=2000) {
                    x+= speed;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                live=false;
                break;
            case 2://向左
                live=true;
                while(y>=0&&y<=1200&&x>=0&&x<=2000) {
                    x-= speed;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                live=false;
                break;
            case 3://向下
                live=true;
                while(y>=0&&y<=1200&&x>=0&&x<=2000) {
                    y+= speed;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                live=false;
                break;
            default:
                System.out.println("暂时还未处理！！！");
        }
    }
    public void Check_bullet(){//检测子弹是否触碰到坦克碰撞模型

    }



    @Override
    public void run() {

        //System.out.println("执行到BulletMove前" );
        this.BulletMove();
        //System.out.println("执行过BulletMove" );

    }
}
