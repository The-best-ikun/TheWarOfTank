package GamePre;/*Author:l
Explain:敌方坦克
Version:1.0*/

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{
    private int discover=0;//判断是否发现玩家坦克,0表示未发现，1234，分别代表四种情况
    Vector<EnemyTank> enemyTanks=new Vector<>();

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    public int isDiscover() {
        return discover;
    }

    public void setDiscover(int discover) {
        this.discover = discover;
    }

    public EnemyTank(int x, int y) {
        super(x, y);
        super.setHeath_value(5);//敌人坦克生命值尽量小一点
        super.setType(1);//将类型设置为敌方坦克
        super.setDirectory(3);//默认方向为向下
    }

    @Override
    public void Fire()  {
//        try {
//            Thread.sleep(500);//休眠0.5s
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        Bullet bullet=new Bullet(getX(),getY(),getDirectory());
        bullet_containers.add(bullet);
        Thread t=new Thread(bullet);
        t.start();//启动炮弹进程
    }
    public void Auto(){//用来总结线程所用到的所有方法
        Fire();
        Check_isLive();
        AutoMove_02();
        //AutoMove();



    }

    @Override
    public void run() {

        while (true) {
            if(isLive()==false){
                break;//坦克死亡线程结束
            }
            Auto();
//            try {
//                Thread.sleep(500);//每0.1秒休眠一次，避免子弹过于密集
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        }

    }
//    public void AutoMove(){
//        //敌方坦克移动逻辑：自己的想法是敌方坦克随机移动，在自身坐标系400范围内搜索，如果检测到玩家坦克（可以在父类坦克以中心坐标加个检测点），
//        // 则主动向玩家靠近（还得写个简单点的寻路算法），这里要求敌方坦克的速度慢于玩家坦克，碰撞模型也得做做（得亏原来画的就是近似正方形）
//        //
//        if(isDiscover()!=0) {
//            return;//发现玩家坦克终止自动随机移动
//        }
//        Random r=new Random();
//       int change_directory= r.nextInt(4);//这个是方向 随机数的bound是开区间
//        setDirectory(change_directory);
//        int duration=r.nextInt(50)+1;//随机移动speed（5）*50及以下的像素位置，也就是最多一坤的身位
//        for (int i=0;i<duration;i++) {
//            try {
//                Thread.sleep(50);//这个休眠要随帧率改变而改变
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//            switch (getDirectory()) {
//                case 0:
//
//                  moveUp();
//                    break;
//                case 1:
//
//                    moveRight();
//                    break;
//                case 2:
//                    moveLeft();
//                    break;
//                case 3:
//                    moveDown();
//                    break;
//                default:
//                    System.out.println("敌方坦克随机改变方向出现异常");
//            }
//            if(isLap()){
//                setLap(false);
//                return;
//            }
//        }
//    }

    @Override
    public void Check_isLive() {
        super.Check_isLive();
    }

    @Override
    public void Check_bullet(Vector<Bullet> bullets) {
        for (int i=0;i<bullets.size();i++) {
            Bullet bullet=bullets.get(i);
            int bullet_x=bullet.getX();
            int bullet_y=bullet.getY();//得到子弹坐标
            //int bullet_directory=bullet.getDirectory();
            switch (getDirectory()) {
                case 0:
                case 3://向上向下
                    if (bullet_x > getX() && bullet_x < (getX() + 110 - 10) && bullet_y > getY() && bullet_y < (getY()+100 - 10)) {
                        System.out.println("敌方子弹触碰（上下方向）");
                        int tmp = getHeath_value();
                        setHeath_value(--tmp);//生命值-1;
                        bullets.remove(bullet);
                    }
                    break;
                case 1:
                case 2://向左向右
                    if (bullet_x > getX() && bullet_x < (getX() + 100 - 10) && bullet_y > getY() && bullet_y < (getY() + 110-10)) {
                        System.out.println("敌方子弹触碰（左右方向！！）");
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
    public void Check_Tank(Tank player_tank){//检测玩家坦克是否在自动追踪范围内
        int player_tank_x=player_tank.getX();
        int player_tank_y=player_tank.getY();
        int tank_x=getX();
        int tank_y=getY();
        if(player_tank_x>tank_x&&player_tank_y>tank_y){//判断玩家坦克在坦克第一象限内，注意这里的坐标系是倒着的，右下为第一象限
            if(player_tank_x-tank_x<=400&&player_tank_y-tank_y<=400){

                System.out.println("追踪的业务逻辑");//可能还需要一个方法，并且再添加一个布尔类型，
                // 用于在20Hz的paint方法中检测坦克是否发现玩家坦克
                setDiscover(1);
                AutoTrack(player_tank);
            }
        }else
        if(player_tank_x>tank_x&&player_tank_y<tank_y){//右上
            if(player_tank_x-tank_x<=400&&player_tank_y-tank_y<400+100){
                setDiscover(2);

            }

        } else if (player_tank_x<tank_x&&player_tank_y<tank_y) {//左上
            if(tank_x-player_tank_x<400+100&&tank_y-player_tank_y<=400+100){
                setDiscover(3);
            }

        } else if (player_tank_x<tank_x&&player_tank_y>tank_y) {//右下
            if(tank_x-player_tank_x<=400+100&&tank_x-player_tank_x<=400){
                setDiscover(4);
            }

        }else{
            setDiscover(0);
        }
    }
    public void AutoTrack(Tank player_tank){
        int player_tank_x=player_tank.getX();
        int player_tank_y=player_tank.getY();
        int tank_x=getX();
        int tank_y=getY();
        switch (isDiscover()){
            case 0:
                return;//未发现坦克，直接终止该方法，不需要加break来防止穿透

            case 1:
                while(true) {
                    if(tank_x-player_tank_x<=50){
                        moveDown();
                        break;
                    }
                        moveRight();
                }
                break;
            case 2:
                break;
                case 3:
            break;
            case 4:
                break;
            default:
                System.out.println("自动追踪判定情况出现异常");
        }
    }

//    public void Check_Overlap_Enemy(Vector<EnemyTank> enemyTanks){
//        for (EnemyTank tmp:
//                enemyTanks) {
//            if(tmp!=this){//先把自己排除，只要检测与其他个体的坐标重叠
//                int tmp_x=tmp.getX();
//                int tmp_y=tmp.getY();
//                int tmp_directory=tmp.getDirectory();
//                switch (tmp_directory){
//                    case 0:
//                    case 3:if((tmp_y>=getY()-100&&tmp_y<=getY()+100)&&(tmp_x<=getX()+110&&tmp_x>=getX()-110)) {
//                        //检验this坦克上方有无坦克向下向此碰撞
////                            Random random=new Random();取消是因为自动移动的代码已经可以随机改变方向了，不过如果实行自动追踪
////                            tmp.setDirectory(random.nextInt(2));//不让tmp坦克下来，其他方向随机
//                        setLap(true);
//                    } else if((tmp_y<=getY()-110&&tmp_y<=getY()+110)&&(tmp_x<=getX()+100&&tmp_x>=getX()-100)){
//                        setLap(true);
//                    }
//                        break;
//                    case 1:
//                    case 2:if((tmp_x>=getX()-100&&tmp_x<=getX()+100)&&(tmp_y>=getY()-110&&tmp_y<=getY()+110)){
//                        setLap(true);
//                    }else if((tmp_y<=getY()-110&&tmp_y<=getY()+110)&&(tmp_x<=getX()+100&&tmp_x>=getX()-100)){
//                        setLap(true);
//                    }
//                }
//
//            }
//            else {continue;}
//
//        }
//    }
    public void AutoMove_02(){
        if(isOverLap()){
            setDirectory((int)(Math.random()*4));
        }else{
            adjustDirectory();
            setDirectory((int)(Math.random()*4));
        }
    }
    public void adjustDirectory(){
        switch (getDirectory()){
            case 0:
                for (int i = 0; i < 50; i++) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if(!isOverLap()&&getY()>0){
                        moveUp();
                    }
                }
                break;
            case 1:
                for (int i = 0; i < 50; i++) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if(!isOverLap()&&getX()<2000-100){
                        moveRight();
                    }
                }
                break;
            case 2:
                for (int i = 0; i < 50; i++) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if(!isOverLap()&&getX()>0){
                        moveLeft();
                    }
                }
                break;
            case 3:
            for (int i = 0; i < 50; i++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if(!isOverLap()&&getY()<1200-100){
                    moveDown();
                }
            }
            break;
        }
    }
    public boolean isOverLap(){
        for (EnemyTank tmp:
             enemyTanks) {
            if(tmp==this){continue;}//先把自己排除
            switch (getDirectory()){//根据方向分为4类8种情况，后续其实可以用对称优化，但现在先写完
                /*确定完执行坦克方向后，确定待检测坦克方向*/
                case 0:
                    /*执行坦克（方向为上时）左上角的坐标：getX，getY
                    右上角的坐标：getX+110，getY
                    待检测（方向为上下时）坦克区域tmp.getX---tmp.getX+110
                                tmp.getY---tmp.getY+100
                     待检测（方向为左右时）坦克区域tmp.getX----tmp.getX+100
                                            tmp.getY---tmp.getY+110
                     */
                    if(tmp.getDirectory()==0||tmp.getDirectory()==3){//待检测坦克方向为上下

                       if(getX()>=tmp.getX()//左上角情况
                            &&getX()<=tmp.getX()+110
                            &&getY()>=tmp.getY()
                            &&getY()<=tmp.getY()+100){
                           setLap(true);
                        return true;
                         }else if(getX()+110>=tmp.getX()//右上角情况
                            &&getX()+110<=tmp.getX()+110
                            &&getY()>=tmp.getY()
                            &&getY()<=tmp.getY()+100){
                        setLap(true);
                           return true;
                       }
                    }

                    else if (tmp.getDirectory()==1||tmp.getDirectory()==2) {//待检测坦克方向为左右
                        if(getX()>=tmp.getX()//左上角情况
                                &&getX()<=tmp.getX()+100
                                &&getY()>=tmp.getY()
                                &&getY()<=tmp.getY()+110){
                            setLap(true);
                            return true;
                        }else if(getX()+110>=tmp.getX()//右上角情况
                                &&getX()+110<=tmp.getX()+100
                                &&getY()>=tmp.getY()
                                &&getY()<=tmp.getY()+110){
                            setLap(true);
                            return true;
                        }
                    }
                    break;
                case 1:
                    /*执行坦克（方向为右时）右上角的坐标：getX+100，getY
                    右下角的坐标：getX+100，getY+110
                    待检测（方向为上下时）坦克区域tmp.getX---tmp.getX+110
                                tmp.getY---tmp.getY+100
                     待检测（方向为左右时）坦克区域tmp.getX----tmp.getX+100
                                            tmp.getY---tmp.getY+110
                     */
                    if(tmp.getDirectory()==0||tmp.getDirectory()==3){//待检测坦克方向为上下
                        if(getX()+100>=tmp.getX()//右上角情况
                                &&getX()+100<=tmp.getX()+110
                                &&getY()>=tmp.getY()
                                &&getY()<=tmp.getY()+100){
                            setLap(true);
                            return true;
                        }else if(getX()+100>=tmp.getX()//右下角情况
                                &&getX()+100<=tmp.getX()+110
                                &&getY()+110>=tmp.getY()
                                &&getY()+110<=tmp.getY()+100){
                            setLap(true);
                            return true;
                        }
                    }

                    else if (tmp.getDirectory()==1||tmp.getDirectory()==2) {//待检测坦克方向为左右
                        if(getX()+100>=tmp.getX()//右上角情况
                                &&getX()+100<=tmp.getX()+100
                                &&getY()>=tmp.getY()
                                &&getY()<=tmp.getY()+110){
                            setLap(true);
                            return true;
                        }else if(getX()+100>=tmp.getX()//右下角情况
                                &&getX()+100<=tmp.getX()+100
                                &&getY()+110>=tmp.getY()
                                &&getY()+110<=tmp.getY()+110){
                            setLap(true);
                            return true;
                        }
                    }
                    break;
                case 2:
                    /*执行坦克（方向为左时）左上角的坐标：getX，getY
                    左下角的坐标：getX，getY+100
                    待检测（方向为上下时）坦克区域tmp.getX---tmp.getX+110
                                tmp.getY---tmp.getY+100
                     待检测（方向为左右时）坦克区域tmp.getX----tmp.getX+100
                                            tmp.getY---tmp.getY+110
                     */
                    if(tmp.getDirectory()==0||tmp.getDirectory()==3){//待检测坦克方向为上下
                        if(getX()>=tmp.getX()//左上角情况
                                &&getX()<=tmp.getX()+110
                                &&getY()>=tmp.getY()
                                &&getY()<=tmp.getY()+100){
                            setLap(true);
                            return true;
                        }else if(getX()>=tmp.getX()//左下角情况
                                &&getX()<=tmp.getX()+110
                                &&getY()+100>=tmp.getY()
                                &&getY()+100<=tmp.getY()+100){
                            setLap(true);
                            return true;
                        }
                    }

                    else if (tmp.getDirectory()==1||tmp.getDirectory()==2) {//待检测坦克方向为左右
                        if(getX()>=tmp.getX()//左上角情况
                                &&getX()<=tmp.getX()+100
                                &&getY()>=tmp.getY()
                                &&getY()<=tmp.getY()+110){
                            setLap(true);
                            return true;
                        }else if(getX()>=tmp.getX()//左下角情况
                                &&getX()<=tmp.getX()+100
                                &&getY()+100>=tmp.getY()
                                &&getY()+100<=tmp.getY()+110){
                            setLap(true);
                            return true;
                        }
                    }
                    break;
                case 3:
                    /*执行坦克（方向为下时）左下角的坐标：getX，getY+100
                    右下角的坐标：getX+110，getY+100
                    待检测（方向为上下时）坦克区域tmp.getX---tmp.getX+110
                                tmp.getY---tmp.getY+100
                     待检测（方向为左右时）坦克区域tmp.getX----tmp.getX+100
                                            tmp.getY---tmp.getY+110
                     */
                    if(tmp.getDirectory()==0||tmp.getDirectory()==3){//待检测坦克方向为上下
                        if(getX()>=tmp.getX()//左下角情况
                                &&getX()<=tmp.getX()+110
                                &&getY()+100>=tmp.getY()
                                &&getY()+100<=tmp.getY()+100){
                            setLap(true);
                            return true;
                        }else if(getX()+110>=tmp.getX()//右下角情况
                                &&getX()+110<=tmp.getX()+110
                                &&getY()+100>=tmp.getY()
                                &&getY()+100<=tmp.getY()+100){
                            setLap(true);
                            return true;
                        }
                    }

                    else if (tmp.getDirectory()==1||tmp.getDirectory()==2) {//待检测坦克方向为左右
                        if(getX()>=tmp.getX()//左下角情况
                                &&getX()<=tmp.getX()+100
                                &&getY()+100>=tmp.getY()
                                &&getY()+100<=tmp.getY()+110){
                            setLap(true);
                            return true;
                        }else if(getX()+110>=tmp.getX()//右下角情况
                                &&getX()+110<=tmp.getX()+100
                                &&getY()+100>=tmp.getY()
                                &&getY()+100<=tmp.getY()+110){
                            setLap(true);
                            return true;
                        }
                    }
                    break;
            }
        }return false;
    }

}
