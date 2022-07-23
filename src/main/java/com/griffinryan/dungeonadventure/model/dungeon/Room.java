package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;

public class Room extends AbstractRoom {

    public Room(ArrayList<Monster> theMonsters, int theNumberOfHealingPotions, int theNumberOfVisionPotions) {
        super(theMonsters, theNumberOfHealingPotions, theNumberOfVisionPotions);
    }

}
