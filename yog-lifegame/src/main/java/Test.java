package main.java;

import main.java.lifegame.ParentFrame;
import main.java.sierpinski.StandardPointSierpinskiFrame;

/**
 * @author
 * @date 2020/4/20
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        // 生命游戏
        ParentFrame de = new ParentFrame();
        de.nextGeneration();

//        //谢尔宾斯基三角形
//        StandardPointSierpinskiFrame s = new StandardPointSierpinskiFrame();
//        s.run();
    }
}
