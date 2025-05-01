package com.santorini;


// making use of abstraction because inheritance will be used to call this class through specific godcard classes like ArtemisCard for example
public abstract class GodCard {
    private String name;
    private String desc;

    // init attributes
    public GodCard(String name, String desc) {
        this.name=name;
        this.desc=desc;
    }

    // abstract method will be used to check if move is allowed on the basis of the God's abilities
    public abstract boolean isMoveAllowed(Board board,Cell originalCell, Cell targetCell);
    // abstract method for move execution on the basis of god power
    public abstract boolean move(Board board,Cell originalCell, Cell targetCell);
    // abstract method for checking if build is valid based on god abilities
    public abstract boolean isBuildAllowed(Board board,Cell workerCell, Cell buildCell);
    // abstract method for execution of build
    public abstract boolean build(Board board,Cell workerCell, Cell buildCell);
    // getters&setters
    public String getName() {
        return this.name;
    }
    public String getDesc() {
        return this.desc;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
