package com.griffinryan.dungeonadventure.model.rooms;

import com.griffinryan.dungeonadventure.model.dungeon.Direction;
import com.griffinryan.dungeonadventure.model.dungeon.Pillar;
import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * AbstractRoom is the parent of all rooms
 *
 * @author Yudong Lin (ydlin@uw.edu)
 */
public abstract class AbstractRoom implements Serializable {
    private final ArrayList<Monster> myMonsters;
    private Pillar myPillar = null;
    private int myNumberOfHealingPotions;
    private int myNumberOfVisionPotions;

    /* update doors */
    private boolean myCanMoveUp = false;
    private boolean myCanMoveDown = false;
    private boolean myCanMoveLeft = false;
    private boolean myCanMoveRight = false;


    /**
     * @param theMonsters               the monsters in this room
     * @param theNumberOfHealingPotions the number of healing potion(s) in this room
     * @param theNumberOfVisionPotions  the number of vision potion(s) in this room
     */
    protected AbstractRoom(final ArrayList<Monster> theMonsters, final int theNumberOfHealingPotions, final int theNumberOfVisionPotions) {
        this.myMonsters = theMonsters;
        if (theNumberOfHealingPotions < 0 || theNumberOfVisionPotions < 0) {
            throw new IllegalArgumentException(" The number of potion(s) cannot be negative!");
        }
        this.myNumberOfHealingPotions = theNumberOfHealingPotions;
        this.myNumberOfVisionPotions = theNumberOfVisionPotions;
    }

    /**
     * pick up all healing potion(s) in this room
     *
     * @return the number of healing potion(s) were picked up
     */
    public int pickUpHealingPotions() {
        final int num = myNumberOfHealingPotions;
        myNumberOfHealingPotions = 0;
        return num;
    }

    /**
     * update the references for surrounding rooms
     *
     * @param theUp    can move upward
     * @param theDown  can move downward
     * @param theLeft  can move to the left
     * @param theRight can move to the right
     */
    public void updateSurrounding(boolean theUp, boolean theDown, boolean theLeft, boolean theRight) {
        myCanMoveUp = theUp;
        myCanMoveDown = theDown;
        myCanMoveLeft = theLeft;
        myCanMoveRight = theRight;
    }

    /**
     * pick up all vision potion(s) in this room
     *
     * @return the number of vision potion(s) were picked up
     */
    public int pickUpVisionPotions() {
        final int num = myNumberOfVisionPotions;
        myNumberOfVisionPotions = 0;
        return num;
    }

    /**
     * if the room has no pillar in it, the give one
     * this method should be used only during the initialization
     *
     * @param thePillar the pillar to set
     */
    public void placePillar(final Pillar thePillar) {
        if (this.hasPillar()) {
            throw new IllegalStateException("The Pillar cannot be modified after been set.");
        }
        this.myPillar = thePillar;
    }

    /**
     * @return whether this room has a pillar
     */
    public boolean hasPillar() {
        return myPillar != null;
    }

    /**
     * pick up the pillar that has been placed inside this room
     *
     * @return the name of the pillar
     */
    public String pickUpPillar() {
        // ensure there is pillar inside this room
        if (myPillar == null) {
            throw new NullPointerException("There is no pillar in this room for the player to pick up");
        }
        final String thePillarName = myPillar.toString();
        // change the Pillar status
        myPillar.found();
        // remove the reference
        myPillar = null;
        // return the name
        return thePillarName;
    }

    /**
     * @param index the index of monster that you want to remove and return from the monsters list
     * @return Monster
     */
    public Monster removeMonster(final int index) {
        return myMonsters.remove(index);
    }

    /**
     * @return the information regarding this room
     */
    public String getInfo() {
        if (!this.hasPillar()) {
            return String.format(
                "Monsters: %d\nHealing Potions: %d\nVision Potions: %d",
                this.getNumberOfMonsters(), this.getNumberOfHealingPotions(), this.getNumberOfVisionPotions()
            );
        }
        return String.format(
            "Monsters: %d\nHealing Potions: %d\nVision Potions: %d\nPillar: [%s]",
            this.getNumberOfMonsters(), this.getNumberOfHealingPotions(), this.getNumberOfVisionPotions(), this.myPillar.toString()
        );
    }

    /**
     * get whether there is a door on given direction
     *
     * @param theDirection the direction
     * @return whether there is a door on given direction
     */
    public boolean isThereDoorOn(Direction theDirection) {
        switch (theDirection) {
            case UP -> {
                return myCanMoveUp;
            }
            case DOWN -> {
                return myCanMoveDown;
            }
            case LEFT -> {
                return myCanMoveLeft;
            }
            case RIGHT -> {
                return myCanMoveRight;
            }
            default -> throw new IllegalArgumentException("Invalid Direction");
        }
    }

    /**
     * get the overview of current room
     *
     * @return a string that contain the information
     */
    @Override
    public String toString() {
        final char[][] roomOverView = new char[3][3];
        for (final char[] row : roomOverView) {
            Arrays.fill(row, '*');
        }
        roomOverView[1][1] = this.getFlag();
        if (myCanMoveUp) {
            roomOverView[0][1] = '-';
        }
        if (myCanMoveDown) {
            roomOverView[2][1] = '-';
        }
        if (myCanMoveLeft) {
            roomOverView[1][0] = '|';
        }
        if (myCanMoveRight) {
            roomOverView[1][2] = '|';
        }
        final StringBuilder theInfo = new StringBuilder();
        for (final char[] row : roomOverView) {
            theInfo.append(String.valueOf(row));
            theInfo.append('\n');
        }
        theInfo.deleteCharAt(theInfo.length() - 1);
        return theInfo.toString();
    }

    /**
     * @return the current number of monster(s) in this room
     */
    public int getNumberOfMonsters() {
        return myMonsters != null ? myMonsters.size() : 0;
    }

    /**
     * @return the current number of healing potion(s) in this room
     */
    public int getNumberOfHealingPotions() {
        return myNumberOfHealingPotions;
    }

    /**
     * @return the current number of vision potion(s) in this room
     */
    public int getNumberOfVisionPotions() {
        return myNumberOfVisionPotions;
    }

    /**
     * @return the char flag that represent the room type and info
     */
    public char getFlag() {
        if (!this.hasPillar()) {
            if (myNumberOfHealingPotions > 0 && myNumberOfVisionPotions > 0) {
                return 'M';
            } else if (myNumberOfHealingPotions > 0) {
                return 'H';
            } else if (myNumberOfVisionPotions > 0) {
                return 'V';
            }
            return ' ';
        }
        return this.myPillar.getFlag();
    }
}
