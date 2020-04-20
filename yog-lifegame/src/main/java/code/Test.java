package main.java.code;

import main.java.panel.FailParentFrame;
import main.java.panel.ParentFrame;

/**
 * @author
 * @date 2020/4/20
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        // 创建实例
        ParentFrame de = new ParentFrame();
//        ParentFrame de = new ParentFrame(60,60,15);
        de.nextGeneration();
    }
}
