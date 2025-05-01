package com.santorini;

public class Player {
    private String name;
    private Worker worker_1;
    private Worker worker_2;
    private Worker utilised_worker;
    // need to implement placeholder atm
    private GodCard godCard;

    // init class
    public Player(String name) {
        this.name=name;
        this.worker_1=null;
        this.worker_2=null;
        this.utilised_worker=null;
        this.godCard=null;
    }

    // method grabs the worker with the given id num
    public Worker fetchWorkerById(int id) {
        if (id==1) {
            return this.worker_1;
        } else if (id==2) {
            return this.worker_2;
        }
        return null;

    }

    // getters&setters
    public String getName() {
        return this.name;
    }
    public Worker getWorker1() {
        return this.worker_1;
    }
    public void setWorker1(Worker worker1) {
        this.worker_1 = worker1;
    }
    public Worker getWorker2() {
        return this.worker_2;
    }
    public void setWorker2(Worker worker2) {
        this.worker_2 = worker2;
    }
    public GodCard getGodCard() {
        return this.godCard;
    }
    public void setGodCard(GodCard godCard) {
        this.godCard = godCard;
    }
    public Worker getUtilisedWorker() {
        return utilised_worker;
    }
    public void setUtilisedWorker(Worker utilised_worker) {
        this.utilised_worker = utilised_worker;
    }

}
