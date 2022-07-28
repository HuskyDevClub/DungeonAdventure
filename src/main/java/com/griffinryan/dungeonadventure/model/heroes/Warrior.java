package com.griffinryan.dungeonadventure.model.heroes;

import com.griffinryan.dungeonadventure.model.dungeon.AbstractRoom;
import com.griffinryan.dungeonadventure.model.monsters.Monster;

/**
 * Warrior is a child object that
 * is abstracted by Hero.
 *
 * @see Hero
 * @author Yudong Lin (ydlin@uw.edu)
 */
public class Warrior extends Hero {

    /**
     * @param theName
     * @param theHealth
     * @param theMinDamage
     * @param theMaxDamage
     * @param theAttackSpeed
     * @param theChanceToHit
     * @param theChanceToBlock
     */
    public Warrior(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToBlock);
    }

    /**
     * @param theName
     */
    public Warrior(final String theName) {
        super(theName, 125, 35, 60, 4, (byte) 80, (byte) 20);
    }

    /**
     * @param theTarget
     */
    @Override
    public void skill(final Monster theTarget) {
        final byte theChance = 40;
        if (isLuckyToAct(theChance)) {
            theTarget.injury(generateRandomValue(75, 175));
        }
    }
}
