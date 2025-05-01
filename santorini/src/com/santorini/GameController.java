package com.santorini;

import com.santorini.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// class is responsible for the core logic of the game.
public class GameController {
    // Game state
    private Board board;
    private List<Player> players;
    private Player currentPlayer;
    private GamePhase gamePhase;
    private boolean gameOver;
    private GamePanelFrame gamePanel;
    private JFrame frame;


    // main constructor for class
    public GameController(String p1name, String p2name) {
        this.board = new Board();
        this.players = new ArrayList<>();
        this.gamePhase = GamePhase.SETUP_WORKERS;
        this.gameOver = false;
        initializePlayers(p1name, p2name);
        this.currentPlayer = players.get(0);
        initUI();
    }

    // inits game UI frame
    public void initUI() {
        SwingUtilities.invokeLater(() -> {
            frame = new JFrame("Santorini");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gamePanel = new GamePanelFrame();
            frame.add(gamePanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public void resetGame() {
        // Reset board
        this.board = new Board();

        // Get player names from existing players
        String player1Name = players.get(0).getName();
        String player2Name = players.get(1).getName();

        // Clear players list and recreate players
        this.players.clear();
        initializePlayers(player1Name, player2Name);

        // Reset game state
        this.gamePhase = GamePhase.SETUP_WORKERS;
        this.gameOver = false;
        this.currentPlayer = players.get(0);

        // Reset UI
        if (gamePanel != null) {
            gamePanel.repaint();
        }
    }


    // method handles player init and calls method to assign the god cards.
    private void initializePlayers(String p1_name, String p2_name) {
        Player p1 = new Player(p1_name);
        Player p2 = new Player(p2_name);
        rndGodCards(p1, p2);
        players.add(p1);
        players.add(p2);
    }


    // randomly assign god cards
    private void rndGodCards(Player player1, Player player2) {
        List<GodCard> godCards = new ArrayList<>();
        godCards.add(new ArtemisCard());
        godCards.add(new DemeterCard());
        Random random = new Random();
        // randomly assign the god cards.
        int index1 = random.nextInt(godCards.size());
        player1.setGodCard(godCards.get(index1));
        godCards.remove(index1);
        int index2= random.nextInt(godCards.size());
        player2.setGodCard(godCards.get(index2));
    }

    public void run() {
        while (!gameOver) {
            try {
                // small delay to fix cpu hang
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // move depending on gamephase
    public void processCellClick(int x, int y) {
        Cell clickedCell =board.grabCell(x, y);
        switch (gamePhase) {
            case SETUP_WORKERS:
                handleWorkerPlacement(clickedCell);
                break;
            case SELECT_WORKER:
                handleWorkerSelection(clickedCell);
                break;
            case MOVE_WORKER:
                handleWorkerMovement(clickedCell);
                break;
            case BUILD:
                handleBuilding(clickedCell);
                break;
            case GAME_OVER:
                break;
        }
        gamePanel.repaint();
    }

    private void handleWorkerPlacement(Cell cell) {
        // check if cell is occupied
        if (cell.containsWorker()) {
            return;
        }
        Player player = currentPlayer;

        if (player.getWorker1() == null) {
            // Place first worker
            Worker worker = new Worker(cell.getX(), cell.getY(), 1, player);
            player.setWorker1(worker);
            cell.setWorker(worker);
        } else if (player.getWorker2() == null) {
            // Place second worker
            Worker worker = new Worker(cell.getX(), cell.getY(), 2, player);
            player.setWorker2(worker);
            cell.setWorker(worker);

            // Switch to next player
            switchPlayer();

            // Check if all workers are placed
            if (allWorkersPlaced()) {
                gamePhase = GamePhase.SELECT_WORKER;
            }
        }
    }

    // check to see if all workers have been placed.
    private boolean allWorkersPlaced() {
        for (Player player : players) {
            if (player.getWorker1() == null || player.getWorker2() == null) {
                return false;
            }
        }
        return true;
    }

    // handles the worker selection. as we have multiple workers on the board, associated with different players
    private void handleWorkerSelection(Cell cell) {
        if (cell.containsWorker()&&cell.getWorker().getPlayer() ==currentPlayer) {
            currentPlayer.setUtilisedWorker(cell.getWorker());
            gamePhase = GamePhase.MOVE_WORKER;
        }
    }
    
    private void handleWorkerMovement(Cell cell) {
        Worker selectedWorker = currentPlayer.getUtilisedWorker();
        if (selectedWorker != null) {
            if (isValidMove(selectedWorker, cell)) {
                // Execute move using player's god card
                GodCard godCard = currentPlayer.getGodCard();
                // Get current cell
                Cell currentCell = board.grabCell(selectedWorker.getX(), selectedWorker.getY());

                // Use god's move ability
                if (godCard.move(board, currentCell, cell)) {
                    // Update worker position
                    currentCell.setWorker(null);
                    selectedWorker.setPos(cell.getX(), cell.getY());
                    cell.setWorker(selectedWorker);

                    // Check win condition - moved to level 3
                    if (cell.getBuildLvl() == 3) {
                        handleWin();
                        return;
                    }
                    // Proceed to build phase
                    gamePhase = GamePhase.BUILD;
                }
            }
        }
    }

    private void handleBuilding(Cell cell) {
        Worker selectedWorker = currentPlayer.getUtilisedWorker();

        if (isValidBuild(selectedWorker, cell)) {
            // Execute build using player's god card
            GodCard godCard = currentPlayer.getGodCard();
            // Get current worker cell
            Cell workerCell = board.grabCell(selectedWorker.getX(), selectedWorker.getY());
            // Use god's build ability
            if (godCard.build(board, workerCell, cell)) {
                // End turn and switch players
                endTurn();
            }
        }
    }

    private boolean isValidMove(Worker worker, Cell targetCell) {
        // Basic move validation (will be enhanced by god card's move logic)
        if (targetCell.containsWorker() || targetCell.getDome()) {
            return false;
        }
        int workerX = worker.getX();
        int workerY = worker.getY();
        int targetX = targetCell.getX();
        int targetY = targetCell.getY();
        // Check if target is adjacent
        if (Math.abs(targetX - workerX)>1 || Math.abs(targetY - workerY) >1) {
            return false;
        }
        // Check height difference
        Cell currentCell = board.grabCell(workerX, workerY);
        return targetCell.getBuildLvl()-currentCell.getBuildLvl() <= 1;
    }

    // check is build is valid.
    private boolean isValidBuild(Worker worker, Cell targetCell) {
        // Basic build validation (will be enhanced by god card's build logic)
        if (targetCell.containsWorker() || targetCell.getDome()) {
            return false;
        }

        int workerX = worker.getX();
        int workerY = worker.getY();
        int targetX = targetCell.getX();
        int targetY = targetCell.getY();

        // Check if target is adjacent
        return Math.abs(targetX - workerX) <= 1 && Math.abs(targetY - workerY) <= 1;
    }

    // end turn & change player
    private void endTurn() {
        currentPlayer.setUtilisedWorker(null);
        switchPlayer();

        // Check if next player can make a move
        if (!playerCanMove(currentPlayer)) {
            // Current player cannot move, other player wins
            switchPlayer();
            handleWin();
        } else {
            gamePhase = GamePhase.SELECT_WORKER;
        }
    }

  // checks if player is able to move
    private boolean playerCanMove(Player player) {
        // Check if either worker can move
        return canWorkerMove(player.getWorker1()) || canWorkerMove(player.getWorker2());
    }

  // game logic checks for all cells and makes sure worker is allowed to move
    private boolean canWorkerMove(Worker worker) {
        int x = worker.getX();
        int y = worker.getY();

        // Check all adjacent cells
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int newX = x + dx;
                int newY = y + dy;

                if (board.isPosAllowed(newX, newY)) {
                    Cell targetCell = board.grabCell(newX, newY);
                    if (isValidMove(worker, targetCell)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // swap player
    private void switchPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        currentPlayer = players.get((currentIndex + 1) % players.size());
    }

    private void handleWin() {
        gamePhase = GamePhase.GAME_OVER;
        gameOver = true;
        // to implmement
        gamePanel.showWinMessage(currentPlayer.getName());
    }

    // Getters
    public Board getBoard() {
        return board;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public GamePhase getGamePhase() {
        return gamePhase;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public List<Player> getPlayers() {
        return players;
    }
}