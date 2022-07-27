package com.griffinryan.dungeonadventure.model.monsters;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class Skeleton extends Monster {

    /**
     * @param theName         the name of the Skeleton
     * @param theHealth       the health/hit point of the Skeleton
     * @param theMinDamage    the minimum damage that the Skeleton will do
     * @param theMaxDamage    the maximum damage that the Skeleton will do
     * @param theAttackSpeed  the attack speed of the Skeleton
     * @param theChanceToHit  the chance that Skeleton will hit
     * @param theChanceToHeal the chance that Skeleton will heal himself/herself
     * @param theMinHealing   the minimum healing that the Skeleton will do
     * @param theMaxHealing   the minimum healing that the Skeleton will do
     */
    public Skeleton(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

    /**
     * @param theName the name of the Skeleton
     */
    public Skeleton(final String theName) {
        this(theName, 100, 30, 50, 3, 80, 30, 30, 50);
    }
}
