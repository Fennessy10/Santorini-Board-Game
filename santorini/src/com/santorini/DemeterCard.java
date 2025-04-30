package com.santorini;

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
}
