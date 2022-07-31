package com.griffinryan.dungeonadventure.model.heroes;


import com.griffinryan.dungeonadventure.model.monsters.Monster;

/**
 * Thief is a child object that
 * is abstracted by Hero.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see Hero
 */
public final class Thief extends Hero {

    /**
     * @param theName          the name of the Thief
     * @param theHealth        the health/hit point of the Thief
     * @param theMinDamage     the minimum damage that the Thief will do
     * @param theMaxDamage     the maximum damage that the Thief will do
     * @param theAttackSpeed   the attack speed of the Thief
     * @param theChanceToHit   the chance that Thief will hit
     * @param theChanceToHeal  the chance that Thief will heal himself/herself
     * @param theMinHealing    the minimum healing that the Thief will do
     * @param theMaxHealing    the minimum healing that the Thief will do
     * @param theChanceToBlock the chance that Thief will block the damage
     */
    public Thief(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToHeal, final int theMinHealing, final int theMaxHealing, final int theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing, theChanceToBlock);
    }

    /**
     * the skill of the Thief
     *
     * @param theTarget the target
     */
    public void skill(final Monster theTarget) {
        final int theValue = generateRandomValue(1, 100);
        if (theValue < 40) {
            this.attack(theTarget);
            this.attack(theTarget);
        } else if (theValue > 60) {
            this.attack(theTarget);
        }
    }

}
