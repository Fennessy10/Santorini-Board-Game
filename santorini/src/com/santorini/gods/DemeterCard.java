package com.santorini.gods;

import com.santorini.Board;
import com.santorini.Cell;
import com.santorini.GodCard;
import com.santorini.Worker;

public class DemeterCard extends GodCard {
   private boolean alreadyBuilt;
   private int initialBuildX;
   private int initialBuildY;


   public DemeterCard() {
       super("Demeter", "The worker can build one additional time, however it cannot be on the same space.");
   }

   private void initBuildTracker() {
       this.alreadyBuilt=false;
       this.initialBuildX=-1;
       this.initialBuildY=-1;
   }


   @Override
    public boolean isMoveAllowed(Board board, Cell originalCell, Cell targetCell) {
       // don't even attempt to move if the cell is null
       if (originalCell==null || targetCell==null) {
        return false;
       }
       int original_x=originalCell.getX();
       int original_y=originalCell.getY();
       int target_x=targetCell.getX();
       int target_y=targetCell.getY();
       // Identify if given target is adjacent to original cell
       if (Math.abs(target_x-original_x)>1 || Math.abs(target_y-original_y)>1) {
           return false;
       }
       // Identify if the target cell is occupied by a worker or a dome
       if (targetCell.containsWorker() || targetCell.getDome()) {
           return false;
       }
       // Identify if the target cell is already occupied by a building
       if (targetCell.getBuildLvl()-originalCell.getBuildLvl()>1) {
           return false;
       }
       return true;
   }


    @Override
    public boolean move(Board board,Cell originalCell, Cell targetCell) {
        if (!isMoveAllowed(board, originalCell, targetCell)) {
            return false;
        }
        Worker worker = originalCell.getWorker();
        // Move worker to target cell
        originalCell.setWorker(null);
        worker.setPos(targetCell.getX(), targetCell.getY());
        targetCell.setWorker(worker);
        // reinitialise build tracker after move is done.
        initBuildTracker();
        return true;
    }

    @Override
    public boolean isBuildAllowed(Board board, Cell workerCell, Cell buildCell) {
        // Standard build validation with Demeter power (can build twice, but not on same space)
        if (workerCell == null || buildCell == null) {
            return false;
        }
        int worker_x = workerCell.getX();
        int worker_y = workerCell.getY();
        int build_x = buildCell.getX();
        int build_y = buildCell.getY();
        // Check if target is adjacent to worker
        if (Math.abs(build_x - worker_x) > 1 || Math.abs(build_y - worker_y) > 1) {
            return false;
        }
        // Check that target cell is buildable (no worker, no dome, level < 3)
        if (!buildCell.canBuild()) {
            return false;
        }
        // Conditional to make sure if its the second build its not the same space as the first.
        if (alreadyBuilt && build_x == initialBuildX && build_y == initialBuildY) {
            return false;
        }

        return true;
    }

    @Override
    public boolean build(Board board, Cell workerCell, Cell buildCell) {
        if (!isBuildAllowed(board, workerCell, buildCell)) {
            return false;
        }
        buildCell.incLvl();
        if (!alreadyBuilt) {
            // Take note of first build location
            alreadyBuilt = true;
            initialBuildX = buildCell.getX();
            initialBuildY = buildCell.getY();
            return false;
        } else {
            // reinit build tracking
            initBuildTracker();
            return true;
        }
    }
}
