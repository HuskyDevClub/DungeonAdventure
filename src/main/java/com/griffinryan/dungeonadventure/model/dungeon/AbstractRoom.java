package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;

abstract public class AbstractRoom {
    private final ArrayList<Monster> myMonsters;
    Pillar myPillar = null;
    private int myNumberOfHealingPotions;
    private int myNumberOfVisionPotions;

    protected AbstractRoom(ArrayList<Monster> theMonsters, int theNumberOfHealingPotions, int theNumberOfVisionPotions) {
        this.myMonsters = theMonsters;
        this.myNumberOfHealingPotions = theNumberOfHealingPotions;
        this.myNumberOfVisionPotions = theNumberOfVisionPotions;
    }

    public int pickUpHealingPotions() {
        final var num = myNumberOfHealingPotions;
        myNumberOfHealingPotions = 0;
        return num;
    }

    public int pickUpVisionPotions() {
        final var num = myNumberOfVisionPotions;
        myNumberOfVisionPotions = 0;
        return num;
    }

    public String pickUpPillar() {
        final var thePillarName = myPillar.toString();
        myPillar.found();
        myPillar = null;
        return thePillarName;
    }

    public int getNumberOfHealingPotions() {
        return myNumberOfHealingPotions;
    }

    public int getNumberOfVisionPotions() {
        return myNumberOfVisionPotions;
    }

    public int getNumberOfMonsters() {
        return myMonsters != null ? myMonsters.size() : 0;
    }

    public boolean hasPillar() {
        return myPillar != null;
    }

    public Monster removeMonster(int index) {
        return myMonsters.remove(index);
    }

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
