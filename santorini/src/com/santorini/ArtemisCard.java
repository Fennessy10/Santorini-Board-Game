package com.santorini;

import com.santorini.Board;
import com.santorini.Cell;
import com.santorini.GodCard;
import com.santorini.Worker;

public class ArtemisCard extends GodCard {
    private boolean alreadyMoved;
    private int initial_x;
    private int initial_y;

    // constructor init
    public ArtemisCard() {
        super("Artemis", "Your Worker may move one additional time, however not back to its initial space.");
        initMoveTracker();
    }



    // init function for move tracker
    private void initMoveTracker() {
        alreadyMoved = false;
        initial_x = -1;
        initial_y = -1;
    }

    @Override
    public boolean isMoveAllowed(Board board, Cell originalCell, Cell targetCell) {
        // make sure that original & target cells classes are actually initialised
        if (originalCell == null || targetCell == null) {
            return false;
        }

        int original_x = originalCell.getX();
        int original_y = originalCell.getY();
        int target_x = targetCell.getX();
        int target_y = targetCell.getY();

        // conditional check to see if worker has already moved. If not then record it.
        if (!alreadyMoved) {
            initial_x = original_x;
            initial_y = original_y;
        } else {
            // After first move, cannot move back to initial position
            if (target_x == initial_x && target_y == initial_y) {
                return false;
            }
        }

        // Check if target is adjacent
        if (Math.abs(target_x - original_x) > 1 || Math.abs(target_y - original_y) > 1) {
            return false;
        }

        // check to see if target cell contains a worker or has a dome.
        if (targetCell.containsWorker() || targetCell.getDome()) {
            return false;
        }

        if (targetCell.getBuildLvl() - originalCell.getBuildLvl() > 1) {
            return false;
        }

        return true;
    }

    @Override
    public boolean move(Board board, Cell originalCell, Cell targetCell) {
        if (!isMoveAllowed(board, originalCell, targetCell)) {
            return false;
        }

        Worker worker = originalCell.getWorker();

        // Move worker to target cell
        originalCell.setWorker(null);
        worker.setPos(targetCell.getX(), targetCell.getY());
        targetCell.setWorker(worker);

        if (!alreadyMoved) {
            alreadyMoved = true;
            return false; 
        } else {
            initMoveTracker();
            return true;
        }
    }

    @Override
    public boolean isBuildAllowed(Board board, Cell workerCell, Cell buildCell) {
        // Standard build validation (no special powers for building)
        if (workerCell == null || buildCell == null) {
            return false;
        }

        int workerX = workerCell.getX();
        int workerY = workerCell.getY();
        int buildX = buildCell.getX();
        int buildY = buildCell.getY();

        // Check if target is adjacent
        if (Math.abs(buildX - workerX) > 1 || Math.abs(buildY - workerY) > 1) {
            return false;
        }

        // Make sure we can build
        return buildCell.canBuild();
    }

    @Override
    public boolean build(Board board, Cell workerCell, Cell buildCell) {
        if (!isMoveAllowed(board, workerCell, buildCell)) {
            return false;
        }

        // increment level
        buildCell.incLvl();

        // reinit move tracking for next turn
        initMoveTracker();

        return true;
    }
}