package com.griffinryan.dungeonadventure.entity;

public class Ogre extends Monster {

    protected Ogre(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, int theChanceToHit, int theChanceToHeal, int theMinHealing, int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

    protected Ogre(String theName) {
        super(theName, 200, 30, 60, 2, 60, 10, 30, 60);
    }

}
