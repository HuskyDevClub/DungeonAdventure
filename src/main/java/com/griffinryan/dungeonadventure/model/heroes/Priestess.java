package com.griffinryan.dungeonadventure.model.heroes;

import com.griffinryan.dungeonadventure.model.RandomSingleton;
import com.griffinryan.dungeonadventure.model.monsters.Monster;

/**
 * Priestess is a child object that
 * is abstracted by Hero.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see Hero
 */
public final class Priestess extends Hero {

    private int myLastHealDoneByUsingSkill = 0;

    /**
     * @param theName          the name of the Priestess
     * @param theHealth        the health/hit point of the Priestess
     * @param theMinDamage     the minimum damage that the Priestess will do
     * @param theMaxDamage     the maximum damage that the Priestess will do
     * @param theAttackSpeed   the attack speed of the Priestess
     * @param theChanceToHit   the chance that Priestess will hit
     * @param theChanceToHeal  the chance that Priestess will heal himself/herself
     * @param theMinHealing    the minimum healing that the Priestess will do
     * @param theMaxHealing    the minimum healing that the Priestess will do
     * @param theChanceToBlock the chance that Priestess will block the damage
     */
    public Priestess(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToHeal, final int theMinHealing, final int theMaxHealing, final int theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing, theChanceToBlock);
    }

    /**
     * the skill of the Priestess:
     * heal self based on theMinHealing and theMaxHealing
     *
     * @param theTarget the target
     * @param theCost   the cost of using skill
     */
    public void skill(final Monster theTarget, final int theCost) {
        myLastHealDoneByUsingSkill = RandomSingleton.isSuccessful(this.getChanceToHeal()) ? RandomSingleton.nextInt(this.getMinHealing(), this.getMaxHealing()) : 0;
        this.heal(myLastHealDoneByUsingSkill);
        this.subtractCurrentAttackSpeed(theCost);
    }

    /**
     * get the description of hero's skill
     *
     * @return description of hero's skill
     */
    public String getSkillDescription() {
        return "Self Healing - She will recovering some health";
    }

    /**
     * get the result of hero using his/her skill (succeed? and how?)
     *
     * @return the result of hero using his/her skill
     */
    @Override
    public String getSkillUsageResult() {
        return myLastHealDoneByUsingSkill <= 0
            ? "But she is not lucky enough to do any healing."
            : String.format("She successfully heals herself %d points.", myLastHealDoneByUsingSkill);
    }
}
