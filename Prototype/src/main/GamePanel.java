package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    private final int originalTileSize = 16;
    private final int scale = 12;

    private final int tileSize = originalTileSize * scale;

    // hardcoded panel size based on default 5x5 board Santorini normally uses
    private final int maxScreenCol = 5;
    private final int maxScreenRow = 5;

    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    Controls controls = new Controls();
    Thread gameThread;

    // set workers default position
    private int workerX = 100;
    private int workerY = 100;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //drawing is done in an offscreen painting buffer thereby increasing performance
        this.addKeyListener(controls);
        this.setFocusable(true); // focused to recieve key input
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        // TODO: draw the board on screen

        while (gameThread != null) {
            update();
            repaint();
        }


    }
    public void update() {
        // if worker clicked
        if (controls.MouseIsPressed()) {
            // light up the adjacent spaces around thw worker

        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);

        g2.fillRect(workerX, workerY, tileSize, tileSize);

        g2.dispose(); //release once done (safer + better performance)
    }


}
