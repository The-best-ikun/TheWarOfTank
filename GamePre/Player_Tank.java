package GamePre;

import java.util.Vector;

public class Player_Tank extends Tank {
    public Player_Tank(int x,int y) {
        super(x,y);
    }

    public Player_Tank(){

        super(945,1000);//无参初始化，方便测试
        super.setName("玩家");
        super.setDirectory(0);//默认向上
        super.setType(0);//将类型设置为玩家坦克
        //想办法把bullet初始化
        super.setSpeed(10);
    }

    @Override
    public void Fire() {
        Bullet bullet=new Bullet(getX(),getY(),getDirectory());
        bullet_containers.add(bullet);
        Thread t=new Thread(bullet);
        t.start();
    }

    @Override
    public void Check_bullet(Vector<Bullet> bullets) {
        for (int i=0;i<bullets.size();i++) {
            Bullet bullet=bullets.get(i);
        int bullet_x=bullet.getX();
        int bullet_y=bullet.getY();//得到子弹坐标
            if(getHeath_value()<=0){return;}
        //int bullet_directory=bullet.getDirectory();
        switch (getDirectory()) {
            case 0:
            case 3://向上向下
                if (bullet_x > getX() && bullet_x < (getX() + 110 - 10) && bullet_y > getY() && bullet_y < (getY()+100 - 10)) {
                    System.out.println("子弹触碰（上下方向）");
                    int tmp = getHeath_value();
                    setHeath_value(--tmp);//生命值-1;
                    bullets.remove(bullet);
                }
                break;
            case 1:
            case 2://向左向右
                if (bullet_x > getX() && bullet_x < (getX() + 100 - 10) && bullet_y > getY() && bullet_y < (getY() + 110-10)) {
                    System.out.println("子弹触碰（左右方向！！）");
                    int tmp = getHeath_value();
                    setHeath_value(--tmp);//生命值-1;
                    bullets.remove(bullet);
                }
                break;
            default:
                System.out.println("子弹触碰发生异常！！！");
            }

        }


    }

    @Override
    public void Check_isLive() {
        super.Check_isLive();
    }
    public void Check_Overlap_Enemy(Vector<EnemyTank> enemyTanks){
        for (EnemyTank tmp:
                enemyTanks) {

                int tmp_x=tmp.getX();
                int tmp_y=tmp.getY();
                int tmp_directory=tmp.getDirectory();
                switch (tmp_directory){
                    case 0:
                    case 3:if((tmp_y>=getY()-100&&tmp_y<=getY()+100)&&(tmp_x<=getX()+110&&tmp_x>=getX()-110)) {
                        setLap(true);
                       // setLive(false);
                       // int tmp_h=getHeath_value();
                       // setHeath_value(--tmp_h);
                    } else if((tmp_y<=getY()-110&&tmp_y<=getY()+110)&&(tmp_x<=getX()+100&&tmp_x>=getX()-100)){
                        //setLive(false);
//                        int tmp_h=getHeath_value();
//                        setHeath_value(--tmp_h);
                        setLap(true);
                    }
                        break;
                    case 1:
                    case 2:if((tmp_x>=getX()-100&&tmp_x<=getX()+100)&&(tmp_y>=getY()-110&&tmp_y<=getY()+110)){
                        //setLive(false);
//                        int tmp_h=getHeath_value();
//                        setHeath_value(--tmp_h);
                        setLap(true);
                    }else if((tmp_y>=getY()-110&&tmp_y<=getY()+110)&&(tmp_x<=getX()+100&&tmp_x>=getX()-100)){
                        //setLive(false);
//                        int tmp_h=getHeath_value();
//                        setHeath_value(--tmp_h);
                        setLap(true);
                    }
                    break;
                }

        }

    }

}
