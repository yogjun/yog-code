package main.java.sierpinski;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import main.java.code.Location;

/**
 * @author
 * @date 2020/4/20
 */
public class StandardPointSierpinskiFrame extends JFrame {
    private int len = 199;
    private int maxDeep = 8;
    private int width = len;
    private int heigh = len;
    private Location B = new Location(heigh-1,0);
    private Location C = new Location(heigh-1,width-1);
    private Location A = new Location(0,(width+1)/2);
    private List<Location> locations = Arrays.asList(A,B,C);
    private int size = width*heigh;
    private boolean[][] life = new boolean[width][heigh];
    private JLabel[] label = new JLabel[size];
    public StandardPointSierpinskiFrame() {
        con();
    }
    private void con(){
        initLife();
        // 添加组件
        this.setLayout(new GridLayout(width, heigh));
        for (int i = 0; i < size; i++) {
            this.add(label[i]);
        }
        // 设置窗体属性
        this.setTitle("生命游戏");
        this.setSize(300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(200, 200);
        this.setVisible(true);
    }

    private void generate(Location a,Location b,Location c,int deep){
        if(deep>=maxDeep) {
            return;
        }
        Location x = new Location((a.getX()+b.getX()+1)/2,(a.getY()+b.getY()+1)/2);
        Location y = new Location((a.getX()+c.getX()+1)/2,(a.getY()+c.getY()+1)/2);
        Location z = new Location((b.getX()+c.getX()+1)/2,(b.getY()+c.getY()+1)/2);
        setColor(x.getX()*width+x.getY(),true);
        setColor(y.getX()*width+y.getY(),true);
        setColor(z.getX()*width+z.getY(),true);
        generate(a,x,y,deep+1);
        generate(x,b,z,deep+1);
        generate(y,z,c,deep+1);
    }

    public void run() throws InterruptedException {
        while (true){
            Thread.sleep(200);
            generate(A,B,C,0);
        }
    }

    private void initLife(){
        A = new Location(new Random().nextInt(heigh),new Random().nextInt(width));
        B = new Location(new Random().nextInt(heigh),new Random().nextInt(width));
        C = new Location(new Random().nextInt(heigh),new Random().nextInt(width));


        for (int i = 0;i<3;i++){
            life[locations.get(i).getX()][locations.get(i).getY()]=true;
        }
        for (int i = 0; i < heigh; i++) {
            for (int j = 0; j < width; j++) {
                label[i*width+j] = new JLabel();
                label[i*width+j].setOpaque(true);
                setColor(i*width+j,life[i][j]);
            }
        }
    }

    //设置指定格子颜色
    private void setColor(int labelId , boolean exist){
        if (exist) {
            label[labelId].setBackground(Color.BLACK);
        } else {
            label[labelId].setBackground(Color.WHITE);
        }
    }
}
