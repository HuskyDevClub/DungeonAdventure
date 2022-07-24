package com.griffinryan.dungeonadventure.model.dungeon;

final public class Pillar {
    private final String myName;
    private boolean myHasBeenFound;
    private int myX;
    private int myY;

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

    int[] getPos() {
        return new int[]{myX, myY};
    }

    void setPos(int theX, int theY) {
        myX = theX;
        myY = theY;
    }

    char getFlag() {
        return myName.charAt(0);
    }
}
