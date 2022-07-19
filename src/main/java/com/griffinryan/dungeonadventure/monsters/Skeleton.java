package com.griffinryan.dungeonadventure.monsters;

public class Skeleton extends Monster {
    public Skeleton(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit, byte theChanceToHeal, int theMinHealing, int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

    public Skeleton(String theName) {
        super(theName, 100, 30, 50, 3, (byte) 80, (byte) 30, 30, 50);
    }
}
