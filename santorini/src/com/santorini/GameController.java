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
    private main.GamePanel gamepanel;
    private JFrame frame;


    public GameController(String player_1_name, String player_2_name) {
        this.board = new Board();
        this.players = new ArrayList<>();
        this.gamephase = GamePhase.SETUP_WORKERS;
        this.game_over = false;
    }




}
