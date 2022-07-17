package com.griffinryan.dungeonadventure.entity;


import java.util.Random;

abstract class DungeonCharacter implements DungeonCharacterInterface {
    protected static final Random rand = new Random();
    private final String myName;
    private final int myMinDamage;
    private final int myMaxDamage;
    private final int myAttackSpeed;
    private final int myChanceToHit;
    private int myHealth;

    protected DungeonCharacter(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit) {
        this.myName = theName;
        this.myHealth = theHealth;
        this.myMinDamage = theMinDamage;
        this.myMaxDamage = theMaxDamage;
        this.myAttackSpeed = theAttackSpeed;
        this.myChanceToHit = theChanceToHit;
    }

    public void injury(int value) {
        this.myHealth = Integer.max(this.myHealth - value, 0);
    }

    public void attack(DungeonCharacter theTarget) {
        if (rand.nextInt(100) >= this.myChanceToHit) {
            final int theDamage = rand.nextInt(this.myMinDamage, this.myMaxDamage);
            theTarget.injury(theDamage);
            System.out.printf("succeed attack with damage: %d\n", theDamage);
        } else {
            System.out.println("damage missed");
        }
    }

    public int getMyHealth() {
        return myHealth;
    }

    public String getMyName() {
        return myName;
    }
}
