package com.griffinryan.dungeonadventure.entity;

public class Warrior extends Hero {


    public Warrior(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToBlock);
    }

    public Warrior(final String theName) {
        super(theName, 125, 35, 60, 4, 80, 40);
    }

    @Override
    void skill(DungeonCharacter theTarget) {
        if (rand.nextInt(100) < 40) {
            theTarget.injury(rand.nextInt(75, 175));
        }
    }
}
