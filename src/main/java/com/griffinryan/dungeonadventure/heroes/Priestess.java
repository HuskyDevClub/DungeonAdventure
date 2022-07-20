package com.griffinryan.dungeonadventure.heroes;

import com.griffinryan.dungeonadventure.monsters.Monster;

public class Priestess extends Hero {


    public Priestess(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit, byte theChanceToHeal, int theMinHealing, int theMaxHealing, byte theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing, theChanceToBlock);
    }

    public Priestess(String theName) {
        super(theName, 75, 25, 45, 5, (byte) 70, (byte) 100, 10, 20, (byte) 30);
    }

    @Override
    public void skill(Monster theTarget) {
        super.selfHeal();
    }
}
