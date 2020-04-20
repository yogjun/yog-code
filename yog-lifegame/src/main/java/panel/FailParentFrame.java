package main.java.panel;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author
 * @date 2020/4/20
 */
public class FailParentFrame extends JFrame {
    private int width = 30;
    private int heigh = 30;
    private int size = width*heigh;

    private boolean[][] life = new boolean[width][heigh];
    private JLabel[] label = new JLabel[size];

    //设置指定格子颜色
    private void setColor(int labelId , boolean exist){
        if (exist) {
            label[labelId].setBackground(Color.BLACK);
        } else {
            label[labelId].setBackground(Color.WHITE);
        }
    }

    //初始化生命格子
    private void initLife(){
        for (int i = 0; i < heigh; i++) {
            for (int j = 0; j < width; j++) {
                if(new Random().nextBoolean()){
                    life[i][j]=true;
                }
//                if(i==heigh/2&&j==width/2){
//                    life[i][j]=true;
//                }
            }
        }
    }

    //给格子上色
    private void colorLife(){
        for (int i = 0; i < heigh; i++) {
            for (int j = 0; j < width; j++) {
                label[i*width+j] = new JLabel();
                label[i*width+j].setOpaque(true);
                setColor(i*width+j,life[i][j]);
            }
        }
    }

    public FailParentFrame() {
        initLife();
        colorLife();
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

    private void colorJLabel(){
        for (int i = 0; i < heigh; i++) {
            for (int j = 0; j < width; j++) {
                setColor(i*width+j,life[i][j]);
            }
        }
    }

    public void nextGeneration() throws InterruptedException {
        for(int g = 0; g < 10000000; g++){
            Thread.sleep(100);
            //计算隔代生存数
            generation();
            colorJLabel();
        }
    }

    private void generation(){
        for (int i = 0; i < heigh; i++) {
            for (int j = 0; j < width; j++) {
                if(life[i][j]){
                    int sum = sumLifeAround(i,j);
                    if(sum<=3||sum>=7) {
                        life[i][j] = false;
                    }
                    if(sum>=7){
                        bornLifeAround(i,j);
                    }
                }
            }
        }
    }

    private void bornLifeAround(int x,int y){
        List<Location> locations = new ArrayList<>();
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if(i>=0&&i<heigh&&j>=0&&j<width){
                    if(!life[i][j]){
                        locations.add(new Location(i,j));
                    }
                }
            }
        }
        if(locations.size()>0){
            Location l = locations.get(new Random().nextInt(locations.size()));
            life[l.getX()][l.getY()] = true;
        }
    }

    private int sumLifeAround(int x,int y){
        int sum = 0;
        for (int i = x-1; i <= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if(i>=0&&i<heigh&&j>=0&&j<width){
                    if(life[i][j]){
                        sum++;
                    }
                }
            }
        }
        return sum;
    }
}
