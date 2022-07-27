package com.griffinryan.dungeonadventure.model.dungeon;

/**
 * 
 * @author Yudong Lin (ydlin@uw.edu) 
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public final class Pillar {
    private final String myName;
    private boolean myHasBeenFound;
    private int myX;
    private int myY;

    /**
     * 
     * @param myName
     */
    public Pillar(final String myName) {
        this.myName = myName;
        this.myHasBeenFound = false;
    }

    /** 
     * @return String
     */
    @Override
    public String toString() {
        return myName;
    }

    public void found() {
        this.myHasBeenFound = true;
    }

    /** 
     * @return boolean
     */
    public boolean hasBeenFound() {
        return myHasBeenFound;
    }

    /** 
     * @return int[]
     */
    int[] getPos() {
        return new int[]{myX, myY};
    }
    
    /** 
     * @param theX
     * @param theY
     */
    void setPos(final int theX, final int theY) {
        myX = theX;
        myY = theY;
    }

    /** 
     * @return char
     */
    char getFlag() {
        return myName.charAt(0);
    }
}
