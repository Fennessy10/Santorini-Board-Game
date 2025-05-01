package com.santorini;

import com.santorini.GameController;
import com.santorini.GamePanelFrame;
import com.santorini.GamePhase;
import com.santorini.Player;

import java.awt.event.*;

public class Controls {
    private GamePanelFrame panel;
    private MouseAdapter boardMouseListener;
    private KeyAdapter keyListener;


    public Controls(GamePanelFrame panel) {
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
                if (currentPlayer != null && currentPlayer.getUtilisedWorker() != null) {
                    currentPlayer.setUtilisedWorker(null);
                    if (controller.getGamePhase() == GamePhase.MOVE_WORKER) {
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