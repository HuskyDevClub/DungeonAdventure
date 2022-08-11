package com.griffinryan.dungeonadventure.model.heroes;

import com.griffinryan.dungeonadventure.model.RandomSingleton;
import com.griffinryan.dungeonadventure.model.monsters.Monster;

/**
 * Warrior is a child object that
 * is abstracted by Hero.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see Hero
 */
public final class Warrior extends Hero {

    /**
     * @param theName          the name of the Warrior
     * @param theHealth        the health/hit point of the Warrior
     * @param theMinDamage     the minimum damage that the Warrior will do
     * @param theMaxDamage     the maximum damage that the Warrior will do
     * @param theAttackSpeed   the attack speed of the Warrior
     * @param theChanceToHit   the chance that Warrior will hit
     * @param theChanceToHeal  the chance that Warrior will heal himself/herself
     * @param theMinHealing    the minimum healing that the Warrior will do
     * @param theMaxHealing    the minimum healing that the Warrior will do
     * @param theChanceToBlock the chance that Warrior will block the damage
     */
    public Warrior(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToHeal, final int theMinHealing, final int theMaxHealing, final int theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing, theChanceToBlock);
    }

    /**
     * the skill of the Warrior
     *
     * @param theTarget the target
     */
    public void skill(final Monster theTarget) {
        if (RandomSingleton.isSuccessful(40)) {
            theTarget.injury(RandomSingleton.nextInt(75, 175));
        }
    }
}
