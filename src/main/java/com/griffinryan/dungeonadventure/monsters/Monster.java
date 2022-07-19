package com.griffinryan.dungeonadventure.monsters;

import com.griffinryan.dungeonadventure.basic.DungeonCharacter;

public abstract class Monster extends DungeonCharacter {

    protected Monster(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit, byte theChanceToHeal, int theMinHealing, int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

}
