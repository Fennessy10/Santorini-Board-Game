import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    // screen settings
    private final int originalTileSize = 16;
    private final int scale = 12;

    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 5;
    private final int maxScreenRow = 5;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); //drawing is done in an offscreen painting buffer thereby increasing performance
    }

}
