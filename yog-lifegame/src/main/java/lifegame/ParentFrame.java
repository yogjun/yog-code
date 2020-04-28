package main.java.lifegame;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author
 * @date 2020/4/20
 */
public class ParentFrame extends JFrame {
    private int width = 180;
    private int heigh = 180;
    private int bornChance = 10;
    private int size = width*heigh;
    private boolean[][] life = new boolean[width][heigh];
    private JLabel[] label = new JLabel[size];
    public ParentFrame() {
        con();
    }
    public void nextGeneration() throws InterruptedException {
        while(true){
            Thread.sleep(1000);
            //计算隔代生存数
            generation();
            colorJLabel();
        }
    }

    private void con(){
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
//        this.setLocation(200, 200);
        this.setVisible(true);
    }

    //设置指定格子颜色
    private void setColor(int labelId , boolean exist){
        if (exist) {
            label[labelId].setBackground(Color.BLACK);
        } else {
            label[labelId].setBackground(Color.WHITE);
        }
    }

    private void initLife(){
        for (int i = 0; i < heigh; i++) {
            for (int j = 0; j < width; j++) {
                if(new Random().nextInt(bornChance)==0){
                    life[i][j]=true;
                }
            }
        }
    }

    private void colorLife(){
        for (int i = 0; i < heigh; i++) {
            for (int j = 0; j < width; j++) {
                label[i*width+j] = new JLabel();
                label[i*width+j].setOpaque(true);
                setColor(i*width+j,life[i][j]);
            }
        }
    }

    private void colorJLabel(){
        for (int i = 0; i < heigh; i++) {
            for (int j = 0; j < width; j++) {
                setColor(i*width+j,life[i][j]);
            }
        }
    }

    private void generation(){
        boolean[][] tmp = new boolean[life.length][life[0].length];
        for (int i = 0; i < heigh; i++) {
            for (int j = 0; j < width; j++) {
                int sum = sumLifeAround(i,j);
                if(sum==3){
                    tmp[i][j] = true;
                }else if(sum==2){
                    tmp[i][j] = life[i][j];
                }else {
                    tmp[i][j] = false;
                }
            }
        }
        life = tmp;
//        for (int i = 0; i < heigh; i++) {
//            for (int j = 0; j < width; j++) {
//                    int sum = sumLifeAround(i,j);
//                    if(sum==3){
//                        life[i][j] = true;
//                    }else if(sum==2){
//                    }else {
//                        life[i][j] = false;
//                    }
//            }
//        }
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
        if(life[x][y]){
            sum--;
        }
        return sum;
    }
}
