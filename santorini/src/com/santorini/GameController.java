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
    private JFrame frame;


    public GameController() {
        this.board = new Board();
        this.players = new ArrayList<>();
        this.gamephase = GamePhase.SETUP_WORKERS;
        this.game_over = false;
    }

    public void initPlayers(String p1name, String p2name) {
        Player p1 = new Player(p1name);
        Player p2 = new Player(p2name);
        this.players.add(p1);
        this.players.add(p2);
    }

}
