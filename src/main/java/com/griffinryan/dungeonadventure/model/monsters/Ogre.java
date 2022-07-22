package com.griffinryan.dungeonadventure.model.monsters;

public class Ogre extends Monster {


    public Ogre(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit, byte theChanceToHeal, int theMinHealing, int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

    public Ogre(String theName) {
        super(theName, 200, 30, 60, 2, (byte) 60, (byte) 10, 30, 60);
    }
}
