package com.griffinryan.dungeonadventure.model.monsters;

public class Gremlin extends Monster {

    public Gremlin(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

    public Gremlin(final String theName) {
        super(theName, 70, 15, 30, 5, (byte) 80, (byte) 40, 20, 40);
    }

}
