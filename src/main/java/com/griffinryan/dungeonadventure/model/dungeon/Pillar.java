package com.griffinryan.dungeonadventure.model.dungeon;

/**
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
     * @param myName the name of the Pillar
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

    /**
     * mark the pillar as found
     */
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
     * @return the position of the room that this pillar was placed
     */
    int[] getPos() {
        return new int[]{myX, myY};
    }

    /**
     * set the position of the room that this pillar was placed
     *
     * @param theX the X of the room
     * @param theY the Y of the room
     */
    void setPos(final int theX, final int theY) {
        myX = theX;
        myY = theY;
    }

    /**
     * @return the char flag that represent the Pillar (the Capital letter)
     */
    char getFlag() {
        return myName.charAt(0);
    }
}
