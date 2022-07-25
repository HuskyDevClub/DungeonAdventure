package com.griffinryan.dungeonadventure.model.monsters;

public class Ogre extends Monster {


    public Ogre(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

    public Ogre(final String theName) {
        super(theName, 200, 30, 60, 2, (byte) 60, (byte) 10, 30, 60);
    }
}
