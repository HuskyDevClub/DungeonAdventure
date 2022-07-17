package com.griffinryan.dungeonadventure;

abstract class DungeonCharacter implements DungeonCharacterInterface {
    private final String myName;
    private final int myHealth;
    private final int myMinDamage;
    private final int myMaxDamage;
    private final int myAttackSpeed;

    private final int myChanceToHit;

    protected DungeonCharacter(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit) {
        this.myName = theName;
        this.myHealth = theHealth;
        this.myMinDamage = theMinDamage;
        this.myMaxDamage = theMaxDamage;
        this.myAttackSpeed = theAttackSpeed;
        this.myChanceToHit = theChanceToHit;
    }


}
