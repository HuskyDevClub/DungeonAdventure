package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.monsters.Gremlin;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;

import java.util.ArrayList;
import java.util.Random;

public class Room {
    private final ArrayList<Monster> myMonsters;
    private final boolean myHasPit;
    private int myNumberOfHealingPotions;
    private int myNumberOfVisionPotions;

    public Room() {
        final Random theRandom = new Random();
        final int theNumOfMonsters = theRandom.nextInt(0, 6);
        myMonsters = new ArrayList<>(theNumOfMonsters);
        for (int i = 0; i < theNumOfMonsters; i++) {
            switch (theRandom.nextInt(0, 2)) {
                case 0 -> myMonsters.add(new Gremlin("G1"));
                case 1 -> myMonsters.add(new Ogre("O1"));
                default -> myMonsters.add(new Skeleton("S1"));
            }
        }
        myHasPit = theRandom.nextInt(0, 3) == 0;
        myNumberOfHealingPotions = theRandom.nextInt(0, 3);
        myNumberOfVisionPotions = theRandom.nextInt(0, 2);

    }

    @Override
    public String toString() {
        return String.format("Monsters: %d\nHealing Potions: %d\nVision Potions: %d", myMonsters.size(), myNumberOfHealingPotions, myNumberOfVisionPotions);
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
        return myMonsters.size();
    }

    public Monster removeMonster(int index) {
        return myMonsters.remove(index);
    }

    public boolean hasPit() {
        return myHasPit;
    }
}
