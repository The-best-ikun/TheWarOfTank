package GamePre;/*Author:l
Explain:我不理解，为什么在paint方法里写循环调用显示图片（附带休眠时间）
他还是一股脑的冒出来，而且看得出来这样做很影响性能  ┭┮﹏┭┮
Version:1.0*/

public class Boom {
    int x,y;
    int life=18;//炸弹的生命周期，直接在paint循环好像不对
    boolean isLive=true;
    public Boom(int x,int y){
        this.x=x;
        this.y=y;
    }
    public void lifeDown() {
        if (life > 0) {
            life--;
        } else {
            isLive=false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLife() {
        return life;
    }
}
