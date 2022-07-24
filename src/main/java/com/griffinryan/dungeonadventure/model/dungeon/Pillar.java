package com.griffinryan.dungeonadventure.model.dungeon;

final public class Pillar {
    private final String myName;
    private boolean myHasBeenFound;

    public Pillar(String myName) {
        this.myName = myName;
        this.myHasBeenFound = false;
    }

    @Override
    public String toString() {
        return myName;
    }

    public void found() {
        this.myHasBeenFound = true;
    }

    public boolean hasBeenFound() {
        return myHasBeenFound;
    }
}
