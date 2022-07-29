package com.griffinryan.dungeonadventure.model.heroes;


import com.griffinryan.dungeonadventure.model.monsters.Monster;

/**
 * Thief is a child object that
 * is abstracted by Hero.
 *
 * @see Hero
 * @author Yudong Lin (ydlin@uw.edu)
 */
public class Thief extends Hero {

    /**
     * @param theName          the name of the Thief
     * @param theHealth        the health/hit point of the Thief
     * @param theMinDamage     the minimum damage that the Thief will do
     * @param theMaxDamage     the maximum damage that the Thief will do
     * @param theAttackSpeed   the attack speed of the Thief
     * @param theChanceToHit   the chance that Thief will hit
     * @param theChanceToBlock the chance that hero will block the damage
     */
    public Thief(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToBlock);
    }

    /**
     * @param theName the name of the Thief
     */
    public Thief(final String theName) {
        this(theName, 75, 20, 40, 6, 80, 40);
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
