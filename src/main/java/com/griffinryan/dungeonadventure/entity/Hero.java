package com.griffinryan.dungeonadventure.entity;

abstract class Hero extends DungeonCharacter {

    private final int myChanceToBlock;

    protected Hero(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit);
        this.myChanceToBlock = theChanceToBlock;
    }

    public void injury(int value) {
        if (rand.nextInt(100) < this.myChanceToBlock) {
            super.injury(value);
            System.out.println("the hero does not block");
        } else {
            System.out.println("the hero block the damage");
        }
    }

    abstract void skill(final DungeonCharacter theTarget);
}
