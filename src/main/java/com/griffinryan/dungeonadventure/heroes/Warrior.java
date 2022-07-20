package com.griffinryan.dungeonadventure.heroes;

import com.griffinryan.dungeonadventure.monsters.Monster;

public class Warrior extends Hero {


    public Warrior(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToBlock);
    }

    public Warrior(final String theName) {
        super(theName, 125, 35, 60, 4, (byte) 80, (byte) 20);
    }

    @Override
    public void skill(Monster theTarget) {
        byte theChance = 40;
        if (isLuckyToAct(theChance)) {
            theTarget.injury(generateRandomValue(75, 175));
        }
    }
}
