package GamePre;/*Author:l
Explain:
Version:1.0*/

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class test extends JFrame{
    Paneltset mp=null;
    public static void main(String[] args) {

        System.out.println("没问题");
        test pre=new GamePre.test();


    }
    public  test() {
        Paneltset mp = new Paneltset();
        this.add(mp);
        this.setSize(2000,1200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }



}
class Paneltset extends JPanel{
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Vector<Image> images=new Vector<>();
        Image image01=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/start01.png"));
        Image image02=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/start02.png"));
        Image image03=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/start03.png"));
        Image image04=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/start04.png"));
        Image image05=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/start05.png"));
        Image image06=Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/start06.png"));
        images.add(image01);
        images.add(image02);
        images.add(image03);
        images.add(image04);
        images.add(image05);
        images.add(image06);
        int count=100;
      g.setColor(Color.BLACK);
        g.fill3DRect(0,0,2000,1200,false);
        for (Image e:
                images) {
            g.drawImage(e,100+count,100+count,e.getWidth(this),e.getHeight(this),this);
            g.setColor(Color.WHITE);
            g.drawString("已经在画了！！！",700,700);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            count+=100;
        }
    }
}

