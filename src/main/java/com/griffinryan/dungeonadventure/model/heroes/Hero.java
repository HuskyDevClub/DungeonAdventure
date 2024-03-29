package com.griffinryan.dungeonadventure.model.heroes;

import com.griffinryan.dungeonadventure.controller.DevelopmentTool;
import com.griffinryan.dungeonadventure.model.DungeonCharacter;
import com.griffinryan.dungeonadventure.model.RandomSingleton;
import com.griffinryan.dungeonadventure.model.monsters.Monster;

/**
 * Hero is the parent class of all heroes
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see DungeonCharacter
 */
public abstract class Hero extends DungeonCharacter {
    private int myNumberOfHealingPotions = 0;
    private int myNumberOfVisionPotions = 0;

    /**
     * @param theName          the name of the Hero
     * @param theHealth        the health/hit point of the Hero
     * @param theMinDamage     the minimum damage that the Hero will do
     * @param theMaxDamage     the maximum damage that the Hero will do
     * @param theAttackSpeed   the attack speed of the Hero
     * @param theChanceToHit   the chance that Hero will hit
     * @param theChanceToHeal  the chance that Hero will heal himself/herself
     * @param theMinHealing    the minimum healing that the Hero will do
     * @param theMaxHealing    the minimum healing that the Hero will do
     * @param theChanceToBlock the chance that hero will block the damage
     */
    protected Hero(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToHeal, final int theMinHealing, final int theMaxHealing, final int theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing, theChanceToBlock);
    }

    /**
     * the skill of the Hero (the children needs to implement)
     *
     * @param theTarget the target
     * @param theCost   the cost of using skill
     */
    public abstract void skill(final Monster theTarget, final int theCost);

    /**
     * get the description of hero's skill
     *
     * @return description of hero's skill
     */
    public abstract String getSkillDescription();

    /**
     * get the result of hero using his/her skill (succeed? and how?)
     *
     * @return the result of hero using his/her skill
     */
    public abstract String getSkillUsageResult();

    /**
     * gain health positions
     *
     * @param theNum the amount of health positions that hero gets
     */
    public void obtainHealingPotions(final int theNum) {
        this.myNumberOfHealingPotions += theNum;
    }

    /**
     * try to use 1 health position
     *
     * @return whether 1 health position is consumed
     */
    public boolean useOneHealingPotion() {
        if (this.myNumberOfHealingPotions > 0) {
            this.myNumberOfHealingPotions--;
            this.heal(RandomSingleton.nextInt(5, 15));
            return true;
        }
        return false;
    }

    /**
     * gain vision positions
     *
     * @param theNum the amount of vision positions that hero gets
     */
    public void obtainVisionPotions(final int theNum) {
        this.myNumberOfVisionPotions += theNum;
    }

    /**
     * try to use 1 vision position
     *
     * @return whether 1 vision position is consumed
     */
    public boolean useOneVisionPotion() {
        if (this.myNumberOfVisionPotions > 0) {
            this.myNumberOfVisionPotions--;
            return true;
        }
        return false;
    }

    /**
     * take away health point from the hero
     * overwrite to make invincible cheat work
     *
     * @param damage the amount of health hero lost
     */
    @Override
    public void injury(final int damage) {
        if (DevelopmentTool.isNotInvincible()) {
            super.injury(damage);
        } else {
            this.setMyIsLastAttackBlocked(true);
        }

    }

    /**
     * @return the status regarding the hero
     */
    @Override
    public String toString() {
        return String.format(
            "Name: %s\nHit Points: %d\nTotal Healing Potions: %d\nTotal Vision Potions: %d",
            this.getName(), this.getHealth(), this.myNumberOfHealingPotions, this.myNumberOfVisionPotions
        );
    }
}
