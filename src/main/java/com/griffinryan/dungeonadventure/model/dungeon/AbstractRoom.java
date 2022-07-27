package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public abstract class AbstractRoom {
    private final ArrayList<Monster> myMonsters;
    Pillar myPillar = null;
    private int myNumberOfHealingPotions;
    private int myNumberOfVisionPotions;

    /**
     * @param theMonsters               the monsters in this room
     * @param theNumberOfHealingPotions the number of healing potion(s) in this room
     * @param theNumberOfVisionPotions  the number of vision potion(s) in this room
     */
    protected AbstractRoom(final ArrayList<Monster> theMonsters, final int theNumberOfHealingPotions, final int theNumberOfVisionPotions) {
        this.myMonsters = theMonsters;
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
     * @return the current number of monster(s) in this room
     */
    public int getNumberOfMonsters() {
        return myMonsters != null ? myMonsters.size() : 0;
    }

    /**
     * @return whether this room has a pillar
     */
    public boolean hasPillar() {
        return myPillar != null;
    }

    /**
     * @param index the index of monster that you want to remove and return from the monsters list
     * @return Monster
     */
    public Monster removeMonster(final int index) {
        return myMonsters.remove(index);
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        if (!this.hasPillar()) {
            return String.format(
                    "Monsters: %d\nHealing Potions: %d\nVision Potions: %d",
                    this.getNumberOfMonsters(), this.getNumberOfHealingPotions(), this.getNumberOfVisionPotions()
            );
        } else {
            return String.format(
                    "Monsters: %d\nHealing Potions: %d\nVision Potions: %d\nPillar: [%s]",
                    this.getNumberOfMonsters(), this.getNumberOfHealingPotions(), this.getNumberOfVisionPotions(), this.myPillar.toString()
            );
        }
    }

    /**
     * @return the char flag that represent the room type and info
     */
    char getFlag() {
        if (!this.hasPillar()) {
            if (myNumberOfHealingPotions > 0 && myNumberOfVisionPotions > 0) {
                return 'M';
            } else if (myNumberOfHealingPotions > 0) {
                return 'H';
            } else if (myNumberOfVisionPotions > 0) {
                return 'V';
            }
            return '*';
        }
        return this.myPillar.getFlag();
    }
}
