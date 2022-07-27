package com.griffinryan.dungeonadventure.model.heroes;

import com.griffinryan.dungeonadventure.model.monsters.Monster;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public class Warrior extends Hero {

    /**
     * @param theName          the name of the Warrior
     * @param theHealth        the health/hit point of the Warrior
     * @param theMinDamage     the minimum damage that the Warrior will do
     * @param theMaxDamage     the maximum damage that the Warrior will do
     * @param theAttackSpeed   the attack speed of the Warrior
     * @param theChanceToHit   the chance that Warrior will hit
     * @param theChanceToBlock the chance that hero will block the damage
     */
    public Warrior(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToBlock);
    }

    /**
     * @param theName the name of the Warrior
     */
    public Warrior(final String theName) {
        this(theName, 125, 35, 60, 4, 80, 20);
    }

    /**
     * the skill of the Warrior
     *
     * @param theTarget the target
     */
    public void skill(final Monster theTarget) {
        if (isLuckyToAct(40)) {
            theTarget.injury(generateRandomValue(75, 175));
        }
    }
}
