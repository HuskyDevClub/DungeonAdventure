package com.griffinryan.dungeonadventure.model.monsters;

/**
 * Ogre is a child object that
 * is abstracted by Monster.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see Monster
 * @see com.griffinryan.dungeonadventure.model.heroes.Warrior
 */
public final class Ogre extends Monster {

    /**
     * @param theName         the name of the Ogre
     * @param theHealth       the health/hit point of the Ogre
     * @param theMinDamage    the minimum damage that the Ogre will do
     * @param theMaxDamage    the maximum damage that the Ogre will do
     * @param theAttackSpeed  the attack speed of the Ogre
     * @param theChanceToHit  the chance that Ogre will hit
     * @param theChanceToHeal the chance that Ogre will heal himself/herself
     * @param theMinHealing   the minimum healing that the Ogre will do
     * @param theMaxHealing   the minimum healing that the Ogre will do
     * @see com.griffinryan.dungeonadventure.model.rooms.Room
     */
    public Ogre(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }
}
