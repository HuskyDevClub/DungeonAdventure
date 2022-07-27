package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;

/**
 * 
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
	 * 
	 */
    protected AbstractRoom(final ArrayList<Monster> theMonsters, final int theNumberOfHealingPotions, final int theNumberOfVisionPotions) {
        this.myMonsters = theMonsters;
        this.myNumberOfHealingPotions = theNumberOfHealingPotions;
        this.myNumberOfVisionPotions = theNumberOfVisionPotions;
    }

    
    /** 
     * @return int
     */
    public int pickUpHealingPotions() {
        final int num = myNumberOfHealingPotions;
        myNumberOfHealingPotions = 0;
        return num;
    }

    
    /** 
     * @return int
     */
    public int pickUpVisionPotions() {
        final int num = myNumberOfVisionPotions;
        myNumberOfVisionPotions = 0;
        return num;
    }

    
    /** 
     * @return String
     */
    public String pickUpPillar() {
        final String thePillarName = myPillar.toString();
        myPillar.found();
        myPillar = null;
        return thePillarName;
    }

    
    /** 
     * @return int
     */
    public int getNumberOfHealingPotions() {
        return myNumberOfHealingPotions;
    }

    
    /** 
     * @return int
     */
    public int getNumberOfVisionPotions() {
        return myNumberOfVisionPotions;
    }

    
    /** 
     * @return int
     */
    public int getNumberOfMonsters() {
        return myMonsters != null ? myMonsters.size() : 0;
    }

    
    /** 
     * @return boolean
     */
    public boolean hasPillar() {
        return myPillar != null;
    }

    
    /** 
     * @param index
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
     * @return char
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
