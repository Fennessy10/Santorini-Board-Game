package com.santorini;

import java.awt.*;

public class Board {
    // attribute for board double array bcuz grid of cells = board
    private Cell[][] cells;
    /* NOTE: ideally we should have this in a configuration file for extensibility
        although we need to prioritise functionality at this point. This won't be hard to move
     */
    private static final int BOARD_SIZE = 5;

    // board constructor
    public Board() {
        initBoard();
    }

    // init method for board
    private void initBoard() {
        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        // nested for loop to initialise cells in the board
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    // grabs cell from the board based on the given coordinates
    public Cell grabCell(int x, int y) {
        if (isPosAllowed(x, y)) {
            return cells[x][y];
        }
        return null;
    }

    // checks if coords are within bounds of board
    public boolean isPosAllowed(int x, int y) {
        // this condition checks that coords are less than the board size and greater than zero
        return x<BOARD_SIZE&&y<BOARD_SIZE&&x>=0&&y>=0;
    }


    /* NOTE: for a lot of this graphic stuff we could probably move to SpriteManager
        and rename the class to GraphicManager to better represent its purpose.

        at the moment we are just trying to get this to work and we can refactor later.
    */

    // Method's purpose is for creating rudimentary graphics to differentiate between the different lvls
    // Will probably replace this testing method with actual graphics using SpriteManager if we have time
    public void createCellLvlGraphics(int x, int y, int w, int h, Cell cell, Graphics graphics) {
        Color colour;
        // Assign colour based on the build level of the cell for differentiation.
        switch (cell.getBuildLvl()) {
            case 0:
                colour = Color.WHITE;
                break;
            case 1:
                colour = Color.YELLOW;
                break;
            case 2:
                colour = Color.GREEN;
                break;
            case 3:
                colour = Color.BLUE;
                break;
            default:
                colour=Color.LIGHT_GRAY;
        }
        graphics.setColor(colour);
        // create rectangle with colour, represents build level for the meantime
        graphics.fillRect(x+1, y+1, w-2, h-2);
        graphics.setColor(Color.BLACK);
        if (cell.getBuildLvl()>0) {
            // Write text for the level as more clear visual indication. Could help for people that are colourblind as well I guess.
            graphics.drawString("Lvl"+cell.getBuildLvl(), x+2+3, y+5+10);
        }
    }

    // Also likely to be a temp method just for testing purposes to represent a dome.
    // Probably still good enough for final version, just depends on how fancy we want our graphics to be
    public void createDomeGraphics(int x, int y, int w, int h, Graphics graphics) {
        // generates the dome dimensions based on the cell size and substracts 10. To make sure it fits in the cell.
        int domeDimension = Math.min(w,h)-10;
        int dome_x=x+(w-domeDimension)/2;
        int dome_y=y+(h-domeDimension)/2;
        graphics.setColor(Color.MAGENTA);
        graphics.fillOval(dome_x, dome_y, domeDimension, domeDimension);
        graphics.setColor(Color.BLACK);
        graphics.drawOval(dome_x, dome_y, domeDimension, domeDimension);
    }


    public void createWorkerGraphics(int x, int y, int w, int h, Graphics graphics, Worker worker) {
        int workerSize = Math.min(w,h)-20;
        int worker_x=x+(w-workerSize)/2;
        int worker_y=y+(h-workerSize)/2;
        // draws the worker based on the worker id for differentiating different workers.
        if (worker.getWorkerId()==1) {
            graphics.setColor(Color.RED);
        } else {
            graphics.setColor(Color.BLUE);
        }
        graphics.fillOval(worker_x, worker_y, workerSize, workerSize);
        graphics.setColor(Color.BLACK);
        graphics.drawOval(worker_x, worker_y, workerSize, workerSize);
        graphics.drawString(String.valueOf(worker.getWorkerId()), (worker_x+workerSize/2), (worker_y+workerSize/2));

    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }

    /**
     * Resets all workers on the board by setting the worker reference in each cell to null.
     * This is useful before loading a new game state to ensure no old worker data persists.
     */
    public void resetWorkers(Board board) {
        for (int x = 0; x < board.getBoardSize(); x++) {
            for (int y = 0; y < board.getBoardSize(); y++) {
                if (cells[x][y].containsWorker()) {
                    cells[x][y].setWorker(null);
                }
            }
        }
    }

}