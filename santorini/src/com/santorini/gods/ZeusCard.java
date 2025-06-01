package com.santorini.gods;

import com.santorini.Board;
import com.santorini.Cell;
import com.santorini.GodCard;
import com.santorini.Worker;

public class ZeusCard extends GodCard {

    public ZeusCard() {
        super("Zeus", "Your Worker may build a block underneath itself, as long as it is not a third-level block that would result in a win");
    }

    @Override
    public boolean isMoveAllowed(Board board, Cell originalCell, Cell targetCell) {
        if (originalCell == null || targetCell == null) {
            return false;
        }

        int dx = Math.abs(targetCell.getX() - originalCell.getX());
        int dy = Math.abs(targetCell.getY() - originalCell.getY());

        if (dx > 1 || dy > 1) {
            return false;
        }

        if (targetCell.containsWorker() || targetCell.getDome()) {
            return false;
        }

        int heightDiff = targetCell.getBuildLvl() - originalCell.getBuildLvl();
        return heightDiff <= 1;
    }

    @Override
    public boolean move(Board board, Cell originalCell, Cell targetCell) {
        if (!isMoveAllowed(board, originalCell, targetCell)) {
            return false;
        }

        Worker worker = originalCell.getWorker();
        originalCell.setWorker(null);
        worker.setPos(targetCell.getX(), targetCell.getY());
        targetCell.setWorker(worker);
        return true;
    }

    @Override
    public boolean isBuildAllowed(Board board, Cell workerCell, Cell buildCell) {
        if (workerCell == null || buildCell == null) {
            return false;
        }

        int build_x = buildCell.getX();
        int build_y = buildCell.getY();
        int worker_x = workerCell.getX();
        int worker_y = workerCell.getY();

        boolean isUnderSelf = (worker_x == build_x && worker_y == build_y);

        System.out.println("ZeusCard: isBuildAllowed check:");
        System.out.println("Worker at: (" + worker_x + "," + worker_y + ")");
        System.out.println("Build at: (" + build_x + "," + build_y + ")");
        System.out.println("Is building under self? " + isUnderSelf);
        System.out.println("BuildCell dome? " + buildCell.getDome() + ", level: " + buildCell.getBuildLvl());

        if (isUnderSelf) {
            return !buildCell.getDome() && buildCell.getBuildLvl() < 3;
        } else {
            int dx = Math.abs(worker_x - build_x);
            int dy = Math.abs(worker_y - build_y);

            if (dx > 1 || dy > 1) {
                return false;
            }

            return buildCell.canBuild();
        }
    }

    @Override
    public boolean build(Board board, Cell workerCell, Cell buildCell) {
        if (!isBuildAllowed(board, workerCell, buildCell)) {
            return false;
        }

        System.out.println("ZeusCard: attempting build...");


        buildCell.incLvl();
        return true;
    }
}
