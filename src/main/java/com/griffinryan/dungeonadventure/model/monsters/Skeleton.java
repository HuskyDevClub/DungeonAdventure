package com.griffinryan.dungeonadventure.model.monsters;

public class Skeleton extends Monster {
    public Skeleton(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

    public Skeleton(final String theName) {
        super(theName, 100, 30, 50, 3, (byte) 80, (byte) 30, 30, 50);
    }
}
