package com.griffinryan.dungeonadventure.model.monsters;

public class Gremlin extends Monster {

    public Gremlin(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit, byte theChanceToHeal, int theMinHealing, int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

    public Gremlin(String theName) {
        super(theName, 70, 15, 30, 5, (byte) 80, (byte) 40, 20, 40);
    }

}
