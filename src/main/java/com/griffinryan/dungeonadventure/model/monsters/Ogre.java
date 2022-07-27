package com.griffinryan.dungeonadventure.model.monsters;

/**
 * 
 * @author Yudong Lin (ydlin@uw.edu) 
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class Ogre extends Monster {

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
    public Ogre(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

    /**
     * @param theName
     */
    public Ogre(final String theName) {
        super(theName, 200, 30, 60, 2, (byte) 60, (byte) 10, 30, 60);
    }
}
