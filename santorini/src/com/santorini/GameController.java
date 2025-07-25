package com.santorini;

import com.santorini.gods.ArtemisCard;
import com.santorini.gods.DemeterCard;
import com.santorini.gods.ZeusCard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

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
    private boolean firstDome = true;
    private int player1Age;
    private int player2Age;


    // main constructor for class
    public GameController(String p1name, String p2name, int p1age, int p2age, GodCard p1godCard, GodCard p2GodCard) {
        this.board = new Board();
        this.players = new ArrayList<>();
        this.gamePhase = GamePhase.SETUP_WORKERS;
        this.gameOver = false;
        this.player1Age = p1age;
        this.player2Age = p2age;

        initializePlayers(p1name, p1age, p2name, p2age, p1godCard, p2GodCard);
        this.currentPlayer = players.get(0);



    }

    public void setGamePanel(GamePanelFrame gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void resetGame() {
        // Reset board
        this.board = new Board();

        // Get player names from existing players
        String player1Name = players.get(0).getName();
        String player2Name = players.get(1).getName();

        GodCard player1GodCard = players.get(0).getGodCard();
        GodCard player2GodCard = players.get(1).getGodCard();

        // Clear players list and recreate players
        this.players.clear();
        initializePlayers(player1Name, player1Age, player2Name, player2Age, player1GodCard, player2GodCard);

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
    private void initializePlayers(String p1_name, int p1_age, String p2_name, int p2_age, GodCard p1_godCard, GodCard p2_godCard) {
        Player p1 = new Player(p1_name, p1_age);
        Player p2 = new Player(p2_name, p2_age);
        players.add(p1);
        players.add(p2);
        p1.setGodCard(p1_godCard);
        p2.setGodCard(p2_godCard);

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
                // Check if the build level is now 3, add a dome
                if (cell.getBuildLvl() == 3) {
                    if (firstDome == true) {
                        firstDome = false;
                    } else {
                        cell.addDome();
                    }
                }
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

    private boolean isValidBuild(Worker worker, Cell targetCell) {
        boolean isZeus = currentPlayer.getGodCard() instanceof ZeusCard;

        if (!isZeus && (targetCell.containsWorker() || targetCell.getDome())) {
            return false;
        }

        if (targetCell.getDome()) return false;

        int workerX = worker.getX();
        int workerY = worker.getY();
        int targetX = targetCell.getX();
        int targetY = targetCell.getY();

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

    public void saveGame(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(board.getBoardSize() + " " + board.getBoardSize());

            for (int x = 0; x < board.getBoardSize(); x++) {
                for (int y = 0; y < board.getBoardSize(); y++) {
                    writer.print(board.grabCell(x, y).getBuildLvl());
                    if (y < board.getBoardSize() - 1) writer.print(" ");
                }
                writer.println();
            }

            for (int x = 0; x < board.getBoardSize(); x++) {
                for (int y = 0; y < board.getBoardSize(); y++) {
                    writer.print(board.grabCell(x, y).getDome() ? 1 : 0);
                    if (y < board.getBoardSize() - 1) writer.print(" ");
                }
                writer.println();
            }

            writer.println(players.size());

            for (Player player : players) {
                if (player == null) {
                    writer.println("NULL_PLAYER");
                    writer.println("NONE");
                    writer.println("-1 -1 -1");
                    writer.println("-1 -1 -1");
                    continue;
                }

                writer.println(player.getName());
                writer.println(player.getGodCard() != null ? player.getGodCard().getName() : "NONE");

                Worker w1 = player.getWorker1();
                if (w1 != null) {
                    writer.println(w1.getWorkerId() + " " + w1.getX() + " " + w1.getY());
                } else {
                    writer.println("-1 -1 -1");
                }

                Worker w2 = player.getWorker2();
                if (w2 != null) {
                    writer.println(w2.getWorkerId() + " " + w2.getX() + " " + w2.getY());
                } else {
                    writer.println("-1 -1 -1");
                }
            }

            writer.println(gamePhase);

            System.out.println("Game saved successfully");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
        }
    }

    public static GodCard createGodCardFromName(String name) {
        switch (name) {
            case "Artemis": return new ArtemisCard();
            case "Demeter": return new DemeterCard();
            case "Zeus": return new ZeusCard();
            default: return null;
        }
    }

    public void loadGame(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String[] boardDims = reader.readLine().split(" ");
            int rows = Integer.parseInt(boardDims[0]);
            int cols = Integer.parseInt(boardDims[1]);

            for (int x = 0; x < rows; x++) {
                String[] levels = reader.readLine().split(" ");
                for (int y = 0; y < cols; y++) {
                    board.grabCell(x, y).setBuildLvl(Integer.parseInt(levels[y]));
                }
            }

            for (int x = 0; x < rows; x++) {
                String[] domes = reader.readLine().split(" ");
                for (int y = 0; y < cols; y++) {
                    if (Integer.parseInt(domes[y]) == 1) {
                        board.grabCell(x, y).addDome();
                    }
                }
            }

            players.clear();
            board.resetWorkers(board);

            int numPlayers = Integer.parseInt(reader.readLine());

            for (int i = 0; i < numPlayers; i++) {
                String playerName = reader.readLine();
                if (playerName.equals("NULL_PLAYER")) {
                    reader.readLine();
                    reader.readLine();
                    reader.readLine();
                    continue;
                }

                String godCardName = reader.readLine();
                Player player = new Player(playerName, 1);
                if (!godCardName.equals("NONE")) {
                    player.setGodCard(createGodCardFromName(godCardName));
                }
                players.add(player);

                String[] w1Data = reader.readLine().split(" ");
                int w1Id = Integer.parseInt(w1Data[0]);
                int w1X = Integer.parseInt(w1Data[1]);
                int w1Y = Integer.parseInt(w1Data[2]);
                if (w1Id != -1) {
                    Worker w1 = new Worker(w1X, w1Y, w1Id, player);
                    player.setWorker1(w1);
                    board.grabCell(w1X, w1Y).setWorker(w1);
                }

                String[] w2Data = reader.readLine().split(" ");
                int w2Id = Integer.parseInt(w2Data[0]);
                int w2X = Integer.parseInt(w2Data[1]);
                int w2Y = Integer.parseInt(w2Data[2]);
                if (w2Id != -1) {
                    Worker w2 = new Worker(w2X, w2Y, w2Id, player);
                    player.setWorker2(w2);
                    board.grabCell(w2X, w2Y).setWorker(w2);
                }
            }

            // Current player is now correctly set after loading players list
            this.currentPlayer = players.get(0);

            this.gamePhase = GamePhase.valueOf(reader.readLine());
            this.gameOver = false;

            if (gamePanel != null) {
                gamePanel.repaint();
                gamePanel.updateUI();
            }

            System.out.println("Game loaded successfully from " + filePath);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }
}