package com.griffinryan.dungeonadventure.model.monsters;

import com.griffinryan.dungeonadventure.model.dungeon.AbstractRoom;

/**
 * Skeleton is a child object that
 * is abstracted by Monster.
 *
 * @see Monster
 * @author Yudong Lin (ydlin@uw.edu)
 */
public class Skeleton extends Monster {

    /**
     * @param theName
     * @param theHealth
     * @param theMinDamage
     * @param theMaxDamage
     * @param theAttackSpeed
     * @param theChanceToHit
     * @param theChanceToHeal
     * @param theMinHealing
     * @param theMaxHealing
     */
    public Skeleton(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

    /**
     * @param theName
     */
    public Skeleton(final String theName) {
        super(theName, 100, 30, 50, 3, (byte) 80, (byte) 30, 30, 50);
    }
}
