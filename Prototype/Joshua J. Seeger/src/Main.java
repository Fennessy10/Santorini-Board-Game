

import java.awt.*;
public class Main {
    public static void main(String[] args) {
        initFrame();
    }
    public static void initFrame() {
        Frame f = new Frame();
        Label l1 = new Label("Hello FIT3077 from group 25!");
        l1.setBounds(100, 50, 200, 80);
        f.add(l1);
        f.setSize(500, 500);
        f.setLayout(null);
        f.setVisible(true);
    }
}