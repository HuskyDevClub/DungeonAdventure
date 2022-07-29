package com.griffinryan.dungeonadventure.model.rooms;

import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;

/**
 * Room is a child object that
 * takes arguments ArrayList<Monster> and
 * integers for number of potions
 * to create a room object.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see AbstractRoom
 * @see com.griffinryan.dungeonadventure.model.monsters.Ogre
 */
public final class Room extends AbstractRoom {
    /**
     * @param theMonsters               the monsters in this room
     * @param theNumberOfHealingPotions the number of healing potion(s) in this room
     * @param theNumberOfVisionPotions  the number of vision potion(s) in this room
     */
    public Room(final ArrayList<Monster> theMonsters, final int theNumberOfHealingPotions, final int theNumberOfVisionPotions) {
        super(theMonsters, theNumberOfHealingPotions, theNumberOfVisionPotions);
    }
}
