package com.griffinryan.dungeonadventure.model.monsters;

/**
 * Gremlin is a child object that
 * is abstracted by Monster.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see Monster
 */
public class Gremlin extends Monster {

    /**
     * @param theName         the name of the Gremlin
     * @param theHealth       the health/hit point of the Gremlin
     * @param theMinDamage    the minimum damage that the Gremlin will do
     * @param theMaxDamage    the maximum damage that the Gremlin will do
     * @param theAttackSpeed  the attack speed of the Gremlin
     * @param theChanceToHit  the chance that Gremlin will hit
     * @param theChanceToHeal the chance that Gremlin will heal himself/herself
     * @param theMinHealing   the minimum healing that the Gremlin will do
     * @param theMaxHealing   the minimum healing that the Gremlin will do
     */
    public Gremlin(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

    /**
     * @param theName the name of the Gremlin
     */
    public Gremlin(final String theName) {
        this(theName, 70, 15, 30, 5, 80, 40, 20, 40);
    }

}
