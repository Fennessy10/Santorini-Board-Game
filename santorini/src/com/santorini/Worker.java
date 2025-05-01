package com.santorini;

public class Worker {
    private int x;
    private int y;
    private int worker_id;
    private Player player;

    // contructor for worker. Init all attributes.
    public Worker(int x, int y, int worker_id, Player player) {
        this.x=x;
        this.y=y;
        this.worker_id=worker_id;
        this.player=player;
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

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setWorkerId(int worker_id) {
        this.worker_id = worker_id;
    }

    public Player getPlayer() {
        return player;
    }
}
