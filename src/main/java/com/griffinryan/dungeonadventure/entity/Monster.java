package com.griffinryan.dungeonadventure.entity;

abstract class Monster extends DungeonCharacter {

    private final int myChanceToHeal;
    private final int myMinHealing;
    private final int myMaxHealing;


    protected Monster(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, int theChanceToHit, int theChanceToHeal, int theMinHealing, int theMaxHealing) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit);
        this.myChanceToHeal = theChanceToHeal;
        this.myMinHealing = theMinHealing;
        this.myMaxHealing = theMaxHealing;
    }

    public void heal(int value) {
        if (rand.nextInt(100) > this.myChanceToHeal) {
            super.injury(rand.nextInt(this.myMinHealing, this.myMaxHealing));
        }
    }

}
