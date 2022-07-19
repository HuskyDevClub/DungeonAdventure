package com.griffinryan.dungeonadventure.heroes;

import com.griffinryan.dungeonadventure.basic.DungeonCharacter;
import com.griffinryan.dungeonadventure.monsters.Monster;

abstract public class Hero extends DungeonCharacter {

    private final byte myChanceToBlock;

    protected Hero(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit, byte theChanceToHeal, int theMinHealing, int theMaxHealing, byte theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
        this.myChanceToBlock = theChanceToBlock;
    }

    protected Hero(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit, byte theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit);
        this.myChanceToBlock = theChanceToBlock;
    }

    public void injury(int value) {
        if (isLuckyToAct(this.myChanceToBlock)) {
            super.injury(value);
            System.out.println("the hero does not block");
        } else {
            System.out.println("the hero block the damage");
        }
    }

    abstract public void skill(final Monster theTarget);
}
