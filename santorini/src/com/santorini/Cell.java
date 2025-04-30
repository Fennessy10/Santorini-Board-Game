package com.santorini;

public class Cell {
    private int x;
    private int y;
    // need to implement
    private Worker worker;
    private boolean has_dome;
    private int build_lvl;



    // contructor initialises the cell
    public Cell(int x, int y) {
        this.x=x;
        this.y=y;
        // again... need to implement
        this.worker=null;
        this.has_dome=false;
        this.build_lvl=0;
    }

    // check if the cell contains a worker
    public boolean containsWorker(){
        return this.worker!=null;
    }

    // adds dome to the cell
    public boolean addDome(){
        if(!this.has_dome){
            this.has_dome=true;
            return true;
        }
        return false;
    }


}