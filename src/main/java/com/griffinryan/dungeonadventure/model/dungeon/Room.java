package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;

final class Room extends AbstractRoom {
    Room(ArrayList<Monster> theMonsters, int theNumberOfHealingPotions, int theNumberOfVisionPotions) {
        super(theMonsters, theNumberOfHealingPotions, theNumberOfVisionPotions);
    }
}
