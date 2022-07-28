package com.griffinryan.dungeonadventure.model.heroes;

import com.griffinryan.dungeonadventure.model.dungeon.AbstractRoom;
import com.griffinryan.dungeonadventure.model.monsters.Monster;

/**
 * Priestess is a child object that
 * is abstracted by Hero.
 *
 * @see Hero
 * @author Yudong Lin (ydlin@uw.edu)
 */
public class Priestess extends Hero {

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
     * @param theChanceToBlock
     */
    public Priestess(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToHeal, final int theMinHealing, final int theMaxHealing, final byte theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing, theChanceToBlock);
    }

    /**
     * @param theName
     */
    public Priestess(final String theName) {
        super(theName, 75, 25, 45, 5, (byte) 70, (byte) 100, 10, 20, (byte) 30);
    }

    /**
     * @param theTarget
     */
    @Override
    public void skill(final Monster theTarget) {
        super.selfHeal();
    }
}
