package main;

import java.awt.event.*;

public class Controls {
    private GamePanel panel;
    private MouseAdapter boardMouseListener;
    private KeyAdapter keyListener;


    public Controls(GamePanel panel) {
        this.panel = panel;
        initListeners();
    }

    // method to initialise the listeners for mouse and keyboard
    private void initListeners() {
        boardMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.handleCellClick(e.getX(), e.getY());
            }
        };
        keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        };
    }

    // method handles key events
    private void handleKeyPress(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                GameController controller = panel.getController();
                Player currentPlayer = controller.getCurrentPlayer();
                if (currentPlayer != null && currentPlayer.getSelectedWorker() != null) {
                    currentPlayer.setSelectedWorker(null);
                    if (controller.getGamePhase() == GameController.GamePhase.MOVE_WORKER) {
                        panel.repaint();
                    }
                }
                break;
        }
    }


// getters
    public MouseAdapter getBoardMouseListener() {
        return boardMouseListener;
    }
    public KeyAdapter getKeyListener() {
        return keyListener;
    }
}