package com.griffinryan.dungeonadventure.basic;

public abstract class DungeonCharacter extends Random implements DungeonCharacterInterface {
    private final String myName;
    private final int myMinDamage;
    private final int myMaxDamage;
    private final int myAttackSpeed;
    private final byte myChanceToHit;
    private final byte myChanceToHeal;
    private final int myMinHealing;
    private final int myMaxHealing;
    private int myHealth;
    private int myLastDamageDone = 0;

    protected DungeonCharacter(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit, byte theChanceToHeal, int theMinHealing, int theMaxHealing) {
        this.myName = theName;
        this.myHealth = theHealth;
        this.myMinDamage = theMinDamage;
        this.myMaxDamage = theMaxDamage;
        this.myAttackSpeed = theAttackSpeed;
        this.myChanceToHit = theChanceToHit;
        this.myChanceToHeal = theChanceToHeal;
        this.myMinHealing = theMinHealing;
        this.myMaxHealing = theMaxHealing;
    }

    protected DungeonCharacter(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit) {
        this.myName = theName;
        this.myHealth = theHealth;
        this.myMinDamage = theMinDamage;
        this.myMaxDamage = theMaxDamage;
        this.myAttackSpeed = theAttackSpeed;
        this.myChanceToHit = theChanceToHit;
        this.myChanceToHeal = 0;
        this.myMinHealing = 0;
        this.myMaxHealing = 0;
    }

    public void injury(int value) {
        this.myHealth = Integer.max(this.myHealth - value, 0);
    }

    public void selfHeal() {
        if (isLuckyToAct(this.myChanceToHeal)) {
            this.injury(generateRandomValue(this.myMinHealing, this.myMaxHealing));
        }
    }

    public void attack(DungeonCharacter theTarget) {
        if (isLuckyToAct(this.myChanceToHit)) {
            this.myLastDamageDone = generateRandomValue(this.myMinDamage, this.myMaxDamage);
            theTarget.injury(this.myLastDamageDone);
        } else {
            this.myLastDamageDone = 0;
        }
    }

    public int getMyHealth() {
        return myHealth;
    }

    public int getMyAttackSpeed() {
        return myAttackSpeed;
    }

    public int getMyLastDamageDone() {
        return myLastDamageDone;
    }

    public String getMyName() {
        return myName;
    }
}
