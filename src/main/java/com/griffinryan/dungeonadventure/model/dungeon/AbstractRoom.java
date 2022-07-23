package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;

abstract public class AbstractRoom {
    private final ArrayList<Monster> myMonsters;
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

    public int getNumberOfHealingPotions() {
        return myNumberOfHealingPotions;
    }

    public int getNumberOfVisionPotions() {
        return myNumberOfVisionPotions;
    }

    public int getNumberOfMonsters() {
        return myMonsters != null ? myMonsters.size() : 0;
    }

    public Monster removeMonster(int index) {
        return myMonsters.remove(index);
    }

    @Override
    public String toString() {
        return String.format("Monsters: %d\nHealing Potions: %d\nVision Potions: %d", this.getNumberOfMonsters(), this.getNumberOfHealingPotions(), this.getNumberOfVisionPotions());
    }
}
