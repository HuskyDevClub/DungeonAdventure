package com.griffinryan.dungeonadventure.model;

/**
 * 
 * @author Yudong Lin (ydlin@uw.edu) 
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
public abstract class DungeonCharacter extends Random {
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

    /**
     * @param theName
     * @param theHealth
     * @param theMinDamage
     * @param theMaxDamage
     * @param theAttackSpeed
     * @param theChanceToHit
     * @param theChanceToHeal
     * @param theMinHealing
     * @param theMaxHealing
     */
    protected DungeonCharacter(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
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

    /**
     * @param theName
     * @param theHealth
     * @param theMinDamage
     * @param theMaxDamage
     * @param theAttackSpeed
     * @param theChanceToHit
     */
    protected DungeonCharacter(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit) {
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

    /** 
     * @param value
     */
    public void injury(final int value) {
        this.myHealth = Integer.max(this.myHealth - value, 0);
    }

    /** 
     * @param value
     */
    public void heal(final int value) {
        this.myHealth = this.myHealth + value;
    }

    public void selfHeal() {
        if (isLuckyToAct(this.myChanceToHeal)) {
            this.heal(generateRandomValue(this.myMinHealing, this.myMaxHealing));
        }
    }

    /** 
     * @param theTarget
     */
    public void attack(final DungeonCharacter theTarget) {
        if (isLuckyToAct(this.myChanceToHit)) {
            this.myLastDamageDone = generateRandomValue(this.myMinDamage, this.myMaxDamage);
            theTarget.injury(this.myLastDamageDone);
        } else {
            this.myLastDamageDone = 0;
        }
    }

    /** 
     * @return int
     */
    public int getMyHealth() {
        return myHealth;
    }

    /** 
     * @return int
     */
    public int getMyAttackSpeed() {
        return myAttackSpeed;
    }

    /** 
     * @return int
     */
    public int getMyLastDamageDone() {
        return myLastDamageDone;
    }

    /** 
     * @return String
     */
    public String getMyName() {
        return myName;
    }
}
