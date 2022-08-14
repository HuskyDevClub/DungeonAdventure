package com.griffinryan.dungeonadventure.model.dungeon;

import java.io.Serializable;

/**
 * Pillar is an object for
 * maze "Pillars of OO."
 *
 * @author Yudong Lin (ydlin@uw.edu)
 */
public final class Pillar implements Serializable {

    public static final String Abstraction = "Abstraction";
    public static final String Encapsulation = "Encapsulation";
    public static final String Inheritance = "Inheritance";
    public static final String Polymorphism = "Polymorphism";

    private final String myName;
    private boolean myHasBeenFound;
    private int myX = 0;
    private int myY = 0;

    /**
     * @param theName the name of the Pillar
     */
    public Pillar(final String theName) {
        if (theName.isEmpty()) {
            throw new IllegalArgumentException("The name for pillar cannot be empty");
        }
        this.myName = theName;
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
    public int[] getPos() {
        return new int[]{myX, myY};
    }

    /**
     * set the position of the room that this pillar was placed
     *
     * @param theX the X of the room
     * @param theY the Y of the room
     */
    public void setPos(final int theX, final int theY) {
        myX = theX;
        myY = theY;
    }

    /**
     * @return the char flag that represent the Pillar (the Capital letter)
     */
    public char getFlag() {
        return myName.charAt(0);
    }
}
