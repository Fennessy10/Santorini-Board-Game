package com.santorini;

public class Player {
    private String name;
    private Worker worker_1;
    private Worker worker_2;
    private Worker utilised_worker;

    public Player(String name) {
        this.name=name;
        this.worker_1=null;
        this.worker_2=null;
        this.utilised_worker=null;
    }
}
