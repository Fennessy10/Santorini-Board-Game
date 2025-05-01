package com.santorini;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Board board;
    private List<Player> players;
    private Player curPlayer;
    private GamePhase gamephase;
    private boolean game_over;
    private GamePanelFrame gamePanel;
    private JFrame frame;


    public GameController(String player_1_name, String player_2_name) {
        this.board = new Board();
        this.players = new ArrayList<>();
        this.gamephase = GamePhase.SETUP_WORKERS;
        this.game_over = false;
    }

    private void initPlayers(String p1name, String p2name) {
        Player p1 = new Player(p1name);
        Player p2 = new Player(p2name);
        this.players.add(p1);
        this.players.add(p2);
    }

    private void initUI() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Santorini");
            gamePanel = new GamePanelFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(1000, 1000);
            frame.pack();
            frame.add(gamePanel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });


    }



}
