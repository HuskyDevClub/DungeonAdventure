package com.griffinryan.dungeonadventure.model.monsters;

import com.griffinryan.dungeonadventure.model.DungeonCharacter;

public abstract class Monster extends DungeonCharacter {

    protected Monster(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
    }

}
