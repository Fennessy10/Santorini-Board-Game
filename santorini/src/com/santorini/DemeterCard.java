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
    public boolean isMoveAllowed()
}
