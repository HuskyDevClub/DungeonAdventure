package com.griffinryan.dungeonadventure.model.monsters;

import com.griffinryan.dungeonadventure.model.DungeonCharacter;

/**
 * Monster is the parent object
 * that abstracts in the model.heroes
 * and model.monsters packages.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see DungeonCharacter
 */
public abstract class Monster extends DungeonCharacter {
    /**
     * @param theName         the name of the Monster
     * @param theHealth       the health/hit point of the Monster
     * @param theMinDamage    the minimum damage that the Monster will do
     * @param theMaxDamage    the maximum damage that the Monster will do
     * @param theAttackSpeed  the attack speed of the Monster
     * @param theChanceToHit  the chance that Monster will hit
     * @param theChanceToHeal the chance that Monster will heal himself/herself
     * @param theMinHealing   the minimum healing that the Monster will do
     * @param theMaxHealing   the minimum healing that the Monster will do
     */
    protected Monster(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }
}
