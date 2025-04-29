import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

    Thread gameThread;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //drawing is done in an offscreen painting buffer thereby increasing performance
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        // TODO: draw the board on screen

    }
}
