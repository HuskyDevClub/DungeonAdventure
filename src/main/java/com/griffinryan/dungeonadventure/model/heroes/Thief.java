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
     * @param theName
     * @param theHealth
     * @param theMinDamage
     * @param theMaxDamage
     * @param theAttackSpeed
     * @param theChanceToHit
     * @param theChanceToBlock
     */
    public Thief(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToBlock);
    }

    /**
     * @param theName
     */
    public Thief(final String theName) {
        super(theName, 75, 20, 40, 6, (byte) 80, (byte) 40);
    }

    /**
     * @param theTarget
     */
    @Override
    public void skill(final Monster theTarget) {

    }

}
