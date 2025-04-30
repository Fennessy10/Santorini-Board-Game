package com.santorini;


// making use of abstraction because inheritance will be used to call this class through specific godcard classes like ArtemisCard for example
public abstract class GodCard {
    private String name;
    private String desc;

    public GodCard(String name, String desc) {
        this.name=name;
        this.desc=desc;
    }


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
