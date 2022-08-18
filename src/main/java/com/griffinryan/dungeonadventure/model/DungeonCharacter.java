package com.griffinryan.dungeonadventure.model;

import com.griffinryan.dungeonadventure.controller.DevelopmentTool;

import java.io.Serializable;

/**
 * DungeonCharacter is the parent class
 * extended by the model. heroes
 * model.monsters packages.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see com.griffinryan.dungeonadventure.model.heroes.Hero
 * @see RandomSingleton
 */
public abstract class DungeonCharacter implements Serializable {
    private final String myName;
    private final int myMinDamage;
    private final int myMaxDamage;
    private final int myMaxAttackSpeed;
    private final int myChanceToHit;
    private final int myChanceToHeal;
    private final int myMinHealing;
    private final int myMaxHealing;
    private final int myChanceToBlock;
    private final int myOriginalHealth;
    private int myCurrentAttackSpeed;
    private int myHealth;
    private int myLastDamageDone = 0;
    private boolean myIsLastAttackBlocked = false;

    /**
     * @param theName         the name of the Dungeon Character
     * @param theHealth       the health/hit point of the Dungeon Character
     * @param theMinDamage    the minimum damage that the Dungeon Character will do
     * @param theMaxDamage    the maximum damage that the Dungeon Character will do
     * @param theAttackSpeed  the attack speed of the Dungeon Character
     * @param theChanceToHit  the chance that Dungeon Character will hit
     * @param theChanceToHeal the chance that Dungeon Character will heal himself/herself
     * @param theMinHealing   the minimum healing that the Dungeon Character will do
     * @param theMaxHealing   the minimum healing that the Dungeon Character will do
     */
    protected DungeonCharacter(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToHeal, final int theMinHealing, final int theMaxHealing, final int theChanceToBlock) {
        this.myName = theName;
        this.myHealth = theHealth;
        this.myMinDamage = theMinDamage;
        this.myMaxDamage = theMaxDamage;
        this.myMaxAttackSpeed = theAttackSpeed;
        this.myCurrentAttackSpeed = theAttackSpeed;
        this.myChanceToHit = theChanceToHit;
        this.myChanceToHeal = theChanceToHeal;
        this.myMinHealing = theMinHealing;
        this.myMaxHealing = theMaxHealing;
        this.myChanceToBlock = theChanceToBlock;

        // ensure maximum is greater than minimum
        if (this.myMinDamage > this.myMaxDamage) {
            throw new IllegalArgumentException("The minimum damage cannot be greater than the maximum damage.");
        } else if (this.myMinHealing > this.myMaxHealing) {
            throw new IllegalArgumentException("The minimum healing cannot be greater than the maximum healing.");
        } else if (this.myMaxAttackSpeed < 1) {
            throw new IllegalArgumentException("The attack speed has to be greater than 1.");
        } else if (this.myHealth < 0) {
            throw new IllegalArgumentException("The health has to be positive!");
        }

        // store the original health
        myOriginalHealth = myHealth;
    }

    /**
     * if the Dungeon Character has chance to heal himself/herself, then try to do so
     */
    public void selfHeal() {
        if (RandomSingleton.isSuccessful(this.myChanceToHeal)) {
            this.heal(RandomSingleton.nextInt(this.myMinHealing, this.myMaxHealing));
        }
    }

    /**
     * heal the Dungeon Character
     *
     * @param value the amount of health Dungeon Character heal
     */
    public void heal(final int value) {
        // ensure the value is not negative
        if (value < 0) {
            throw new IllegalArgumentException("You cannot heal a negative number!");
        }
        this.myHealth = (int) Long.min((long) this.myHealth + value, Integer.MAX_VALUE);
    }

    /**
     * try to attack another Dungeon Character
     *
     * @param theTarget the Dungeon Character to attack
     */
    public void attack(final DungeonCharacter theTarget, int theCost) {
        this.myLastDamageDone = RandomSingleton.isSuccessful(this.myChanceToHit) ? RandomSingleton.nextInt(this.myMinDamage, this.myMaxDamage) : 0;
        theTarget.injury(this.myLastDamageDone);
        this.myCurrentAttackSpeed -= theCost;
    }

    /**
     * take away health point from the Dungeon Character
     *
     * @param damage the amount of health Dungeon Character lost
     */
    public void injury(final int damage) {
        // ensure the damage is not negative
        if (damage < 0) {
            throw new IllegalArgumentException("The damage cannot be negative!");
        }
        this.myIsLastAttackBlocked = DevelopmentTool.isInvincible() || RandomSingleton.isSuccessful(this.getChanceToBlock());
        if (!this.myIsLastAttackBlocked) {
            this.reduceHealth(damage);
        }
    }

    /**
     * reduce a certain amount of health
     * unlike injury(), this method will not be affected by anything
     * it assumes who calls this method will know what he is doing
     * otherwise, do use it!
     *
     * @param damage the damage that will be taken away
     */
    public void reduceHealth(final int damage) {
        this.myHealth = Integer.max(this.myHealth - damage, 0);
    }

    /**
     * get the Dungeon Character's chance to block incoming damage
     *
     * @return int
     */
    public int getChanceToBlock() {
        return myChanceToBlock;
    }

    /**
     * @return whether the character is dead (heath <= 0)
     */
    public boolean isDead() {
        return myHealth <= 0;
    }

    /**
     * revive a dead character by resting its health to its original health
     */
    public void revive() {
        if (this.isAlive()) {
            throw new IllegalStateException("You cannot revive a still alive character!");
        } else {
            myHealth = myOriginalHealth;
        }
    }

    /**
     * @return whether the character is alive (heath > 0)
     */
    public boolean isAlive() {
        return myHealth > 0;
    }

    /**
     * get the current health point of the Dungeon Character
     *
     * @return int
     */
    public int getHealth() {
        return myHealth;
    }


    /**
     * set the current health point of the Dungeon Character
     *
     * @param value the health to set
     */
    public void setHealth(int value) {
        myHealth = value;
    }

    /**
     * get the current health point of the Dungeon Character
     *
     * @return int
     */
    public int getMaxAttackSpeed() {
        return myMaxAttackSpeed;
    }

    /**
     * get the latest damage that the Dungeon Character tried to do
     *
     * @return int
     */
    public int getLastDamageDone() {
        return myLastDamageDone;
    }

    /**
     * get the name of the Dungeon Character
     *
     * @return String
     */
    public String getName() {
        return myName;
    }

    /**
     * get the Dungeon Character's minimum damage
     *
     * @return int
     */
    public int getMinDamage() {
        return myMinDamage;
    }

    /**
     * get the Dungeon Character's maximum damage
     *
     * @return int
     */
    public int getMaxDamage() {
        return myMaxDamage;
    }

    /**
     * get the Dungeon Character's chance to successfully do the damage
     *
     * @return int
     */
    public int getChanceToHit() {
        return myChanceToHit;
    }

    /**
     * get the Dungeon Character's chance to heal him/herself
     *
     * @return int
     */
    public int getChanceToHeal() {
        return myChanceToHeal;
    }

    /**
     * get the Dungeon Character's minimum healing
     *
     * @return int
     */
    public int getMinHealing() {
        return myMinHealing;
    }

    /**
     * get the Dungeon Character's maximum healing
     *
     * @return int
     */
    public int getMaxHealing() {
        return myMaxHealing;
    }

    /**
     * @return whether the character block the damage that was last done to him/her
     */
    public boolean isLastAttackBlocked() {
        return myIsLastAttackBlocked;
    }

    /**
     * get the current attack speed
     *
     * @return the current attack speed
     */
    public int getCurrentAttackSpeed() {
        return myCurrentAttackSpeed;
    }

    /**
     * add a value to the current attack speed
     *
     * @param theCurrentAttackSpeed the value to add to the current attack speed
     */
    public void addCurrentAttackSpeed(int theCurrentAttackSpeed) {
        this.myCurrentAttackSpeed += theCurrentAttackSpeed;
    }

    /**
     * subtract a value from the current attack speed
     *
     * @param theCurrentAttackSpeed the value to subtract from the current attack speed
     */
    public void subtractCurrentAttackSpeed(int theCurrentAttackSpeed) {
        this.myCurrentAttackSpeed -= theCurrentAttackSpeed;
    }

    /**
     * reset the current attack speed to its original
     */
    public void resetCurrentAttackSpeed() {
        this.myCurrentAttackSpeed = this.myMaxAttackSpeed;
    }

    /**
     * well... the character will kill itself
     */
    public void suicide() {
        this.myHealth = 0;
    }
}
