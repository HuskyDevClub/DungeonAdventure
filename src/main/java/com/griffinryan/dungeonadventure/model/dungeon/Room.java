package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
final class Room extends AbstractRoom {
    /**
     * @param theMonsters               the monsters in this room
     * @param theNumberOfHealingPotions the number of healing potion(s) in this room
     * @param theNumberOfVisionPotions  the number of vision potion(s) in this room
     */
    Room(final ArrayList<Monster> theMonsters, final int theNumberOfHealingPotions, final int theNumberOfVisionPotions) {
        super(theMonsters, theNumberOfHealingPotions, theNumberOfVisionPotions);
    }
}
