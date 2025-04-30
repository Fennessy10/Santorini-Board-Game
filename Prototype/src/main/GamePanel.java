package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // screen settings
    private final int originalTileSize = 16;
    private final int scale = 8;

    private final int tileSize = originalTileSize * scale;

    // hardcoded panel size based on default 5x5 board Santorini normally uses
    private final int maxScreenCol = 5;
    private final int maxScreenRow = 5;

    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    // FPS
    int FPS = 60;

    Controls controls = new Controls();
    Thread gameThread;

    // set workers default position
    private int workerX = 0;
    private int workerY = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //drawing is done in an offscreen painting buffer thereby increasing performance
        this.addKeyListener(controls);
        this.addMouseListener(controls);
        this.addMouseMotionListener(controls);
        this.setFocusable(true); // focused to recieve key input
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        // TODO: draw the board on screen

        double drawInterval = (double) 1000000000 / FPS; // ~0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }
    public void update() {
        // if worker clicked
        if (controls.MouseIsClicked()) {
            // light up the adjacent spaces around thw worker
            System.out.println("click again to stop");

        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);



        g2.fillRect(0, 300, tileSize, tileSize);

        g2.fillRect(workerX, workerY, tileSize, tileSize);


        g2.dispose(); //release once done (safer + better performance)
    }


}
