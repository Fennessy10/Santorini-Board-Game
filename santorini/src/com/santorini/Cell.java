package com.santorini;

public class Cell {
    private int x;
    private int y;
    // need to implement
    private Worker worker;
    private boolean has_dome;
    private int build_lvl;


    // constructor initialises the cell
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


    // check if we can add a dome to this cell. Do a conditional check to make sure there isn't a dome or worker
    public boolean canAddDome() {
        return !containsWorker() && !getDome();
    }

    // increase the build level of the cell
    public boolean incLvl() {
        if (this.build_lvl < 3 && !has_dome) {
            this.build_lvl++;
            return true;
        }
        return false;
    }


    // method for attesting if we can build a building in this cell.
    public boolean canBuild() {
        // run a conditional check to make sure there isn't a worker, dome and the build level is less than 3.
        return !this.containsWorker() && !getDome() && this.build_lvl < 3;
    }




    // get&set stuff
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Worker getWorker() {
        return worker;
    }
    public void setWorker(Worker worker) {
        this.worker = worker;
    }
    public boolean getDome() {
        return has_dome;
    }
    public void setHasDome(boolean has_dome) {
        this.has_dome = has_dome;
    }
    public int getBuildLvl() {
        return build_lvl;
    }
    public void setBuildLvl(int build_lvl) {this.build_lvl = build_lvl;}

}