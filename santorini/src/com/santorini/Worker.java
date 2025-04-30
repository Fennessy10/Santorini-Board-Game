package com.santorini;

public class Worker {
    private int x;
    private int y;
    private int worker_id;


    // contructor for worker. Init all attributes.
    public Worker(int x, int y, int worker_id) {
        this.x=x;
        this.y=y;
        this.worker_id=worker_id;
    }

    // set position of worker
    public void setPos(int x, int y) {
        this.x=x;
        this.y=y;
    }

    // get&setters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getWorkerId() {
        return worker_id;
    }

}
