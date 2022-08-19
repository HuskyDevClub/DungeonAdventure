package com.griffinryan.dungeonadventure.model.heroes;


import com.griffinryan.dungeonadventure.model.RandomSingleton;
import com.griffinryan.dungeonadventure.model.monsters.Monster;

/**
 * Thief is a child object that
 * is abstracted by Hero.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see Hero
 */
public final class Thief extends Hero {

    private int mySkillResult = -1;

    /**
     * @param theName          the name of the Thief
     * @param theHealth        the health/hit point of the Thief
     * @param theMinDamage     the minimum damage that the Thief will do
     * @param theMaxDamage     the maximum damage that the Thief will do
     * @param theAttackSpeed   the attack speed of the Thief
     * @param theChanceToHit   the chance that Thief will hit
     * @param theChanceToHeal  the chance that Thief will heal himself/herself
     * @param theMinHealing    the minimum healing that the Thief will do
     * @param theMaxHealing    the minimum healing that the Thief will do
     * @param theChanceToBlock the chance that Thief will block the damage
     */
    public Thief(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final int theChanceToHit, final int theChanceToHeal, final int theMinHealing, final int theMaxHealing, final int theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing, theChanceToBlock);
    }

    /**
     * the skill of the Thief:
     * surprise attack - 40 percent chance it is successful.
     * If it is successful, Thief gets an attack and another turn (extra attack) in the current round.
     * There is a 20 percent chance the Thief is caught in which case no attack at all is rendered.
     * The other 40 percent is just a normal attack.
     *
     * @param theTarget the target
     * @param theCost   the cost of using skill
     */
    public void skill(final Monster theTarget, final int theCost) {
        final int theValue = RandomSingleton.nextInt(1, 101);
        if (theValue < 40) {
            this.attack(theTarget, 0);
            this.attack(theTarget, theCost);
            mySkillResult = 1;
        } else if (theValue > 60) {
            this.attack(theTarget, theCost);
            mySkillResult = 0;
        } else {
            this.subtractCurrentAttackSpeed(theCost);
            mySkillResult = -1;
        }
    }

    /**
     * get the description of hero's skill
     *
     * @return description of hero's skill
     */
    public String getSkillDescription() {
        return "Surprise Attack - If it is successful, he gets an attack and another turn (extra attack) in the current round.";
    }

    /**
     * get the result of hero using his/her skill (succeed? and how?)
     *
     * @return the result of hero using his/her skill
     */
    @Override
    public String getSkillUsageResult() {
        return mySkillResult < 0 ? "Unfortunately, his attack was noticed by the monster, so he was not able to do any damage to the monster"
            : mySkillResult == 0 ? "Although his attack somewhat surprised the monster, he was only able to do one attack to the monster"
            : "His attack caught monster completely off guard, so he was able to attack the monster twice during this round!";
    }

}
