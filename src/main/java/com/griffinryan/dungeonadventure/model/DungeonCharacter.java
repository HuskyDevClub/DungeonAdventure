package com.griffinryan.dungeonadventure.model;

/**
 * DungeonCharacter is the parent class
 * extended by the model. heroes
 * model.monsters packages.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see com.griffinryan.dungeonadventure.model.heroes.Hero
 * @see com.griffinryan.dungeonadventure.model.Random
 */
public abstract class DungeonCharacter extends Random {
    private final String myName;
    private final int myMinDamage;
    private final int myMaxDamage;
    private final int myAttackSpeed;
    private final int myChanceToHit;
    private final int myChanceToHeal;
    private final int myMinHealing;
    private final int myMaxHealing;
    private int myHealth;
    private int myLastDamageDone = 0;

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
    protected DungeonCharacter(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToHeal, final int theMinHealing, final int theMaxHealing) {
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
     * @param theName        the name of the Dungeon Character
     * @param theHealth      the health/hit point of the Dungeon Character
     * @param theMinDamage   the minimum damage that the Dungeon Character will do
     * @param theMaxDamage   the maximum damage that the Dungeon Character will do
     * @param theAttackSpeed the attack speed of the Dungeon Character
     * @param theChanceToHit the chance that Dungeon Character will hit
     */
    protected DungeonCharacter(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit) {
        this(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, 0, 0, 0);
    }

    /**
     * take away health point from the Dungeon Character
     *
     * @param value the amount of health Dungeon Character lost
     */
    public void injury(final int value) {
        this.myHealth = Integer.max(this.myHealth - value, 0);
    }

    /**
     * heal the Dungeon Character
     *
     * @param value the amount of health Dungeon Character heal
     */
    public void heal(final int value) {
        this.myHealth = this.myHealth + value;
    }

    /**
     * if the Dungeon Character has chance to heal himself/herself, then try to do so
     */
    public void selfHeal() {
        if (isLuckyToAct(this.myChanceToHeal)) {
            this.heal(generateRandomValue(this.myMinHealing, this.myMaxHealing));
        }
    }

    /**
     * try to attack another Dungeon Character
     *
     * @param theTarget the Dungeon Character to attack
     */
    public void attack(final DungeonCharacter theTarget) {
        this.myLastDamageDone = isLuckyToAct(this.myChanceToHit) ? generateRandomValue(this.myMinDamage, this.myMaxDamage) : 0;
        theTarget.injury(this.myLastDamageDone);
    }

    /**
     * get the current health point of the Dungeon Character
     *
     * @return int
     */
    public int getMyHealth() {
        return myHealth;
    }

    /**
     * get the current health point of the Dungeon Character
     *
     * @return int
     */
    public int getMyAttackSpeed() {
        return myAttackSpeed;
    }

    /**
     * get the latest damage that the Dungeon Character tried to do
     *
     * @return int
     */
    public int getMyLastDamageDone() {
        return myLastDamageDone;
    }

    /**
     * get the name of the Dungeon Character
     *
     * @return String
     */
    public String getMyName() {
        return myName;
    }
}
