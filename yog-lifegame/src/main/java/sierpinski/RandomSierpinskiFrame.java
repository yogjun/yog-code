package main.java.sierpinski;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
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
public class RandomSierpinskiFrame extends JFrame {
    private int len = 100;
    private int width = len;
    private int heigh = len;
    private Location B = new Location(heigh-1,0);
    private Location C = new Location(heigh-1,width-1);
    private Location A = new Location(0,width/2);
    private List<Location> locations = Arrays.asList(A,B,C);
    private int size = width*heigh;
    private boolean[][] life = new boolean[width][heigh];
    private JLabel[] label = new JLabel[size];
    public RandomSierpinskiFrame() {
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

    private void generate(){
        int x = new Random().nextInt(heigh);
        int y = new Random().nextInt(width);
        if(judge(x,y)){
            //todo
        }
    }

    private boolean judge(int x,int y){
        //三条线段内部
        //       A
        //
        //B              C
        //1:在AB右下方，x+2y>=len
        //2:在AC左下方，2y-x<=len
        //3:在BC上方，x<=len,y<=len,y>=0
        return x+2*y>=len&&2*y-x<=len&&x<=len&&y<=len&&y>=0;
    }

    private void initLife(){
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
