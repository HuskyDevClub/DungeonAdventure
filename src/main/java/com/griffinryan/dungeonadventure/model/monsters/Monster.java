package com.griffinryan.dungeonadventure.model.monsters;

import com.griffinryan.dungeonadventure.model.DungeonCharacter;

/**
 * Monster is the parent object
 * that abstracts in the model.heroes
 * and model.monsters packages.
 *
 * @see DungeonCharacter
 * @author Yudong Lin (ydlin@uw.edu)
 */
public abstract class Monster extends DungeonCharacter {

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
    protected Monster(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

}
