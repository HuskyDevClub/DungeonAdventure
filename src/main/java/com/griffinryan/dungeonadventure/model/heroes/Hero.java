package com.griffinryan.dungeonadventure.model.heroes;

import com.griffinryan.dungeonadventure.model.DungeonCharacter;
import com.griffinryan.dungeonadventure.model.dungeon.Pillar;
import com.griffinryan.dungeonadventure.model.monsters.Monster;

import java.util.ArrayList;

abstract public class Hero extends DungeonCharacter {

    private final byte myChanceToBlock;
    private final ArrayList<Pillar> myPillarsFound = new ArrayList<>(4);
    private int myNumberOfHealingPotions = 0;
    private int myNumberOfVisionPotions = 0;

    protected Hero(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit, byte theChanceToHeal, int theMinHealing, int theMaxHealing, byte theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToHeal, theMinHealing, theMaxHealing);
        this.myChanceToBlock = theChanceToBlock;
    }

    protected Hero(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit, byte theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit);
        this.myChanceToBlock = theChanceToBlock;
    }

    public void injury(int value) {
        if (isLuckyToAct(this.myChanceToBlock)) {
            super.injury(value);
            System.out.println("the hero does not block");
        } else {
            System.out.println("the hero block the damage");
        }
    }

    abstract public void skill(final Monster theTarget);

    public int getNumberOfHealingPotions() {
        return this.myNumberOfHealingPotions;
    }

    public void gainHealingPotions(int theNum) {
        this.myNumberOfHealingPotions += theNum;
    }

    public boolean useHealingPotion() {
        if (this.myNumberOfHealingPotions > 0) {
            this.myNumberOfHealingPotions--;
            this.heal(generateRandomValue(5, 15));
            return true;
        }
        return false;
    }

    public int getNumberOfVisionPotions() {
        return myNumberOfVisionPotions;
    }

    public void gainVisionPotions(int theNum) {
        this.myNumberOfVisionPotions += theNum;
    }

    public boolean useVisionPotion() {
        if (this.myNumberOfVisionPotions > 0) {
            this.myNumberOfVisionPotions--;

            /*
             *
             * put the affect here!!!!
             *
             */

            return true;
        }
        return false;
    }

    public ArrayList<Pillar> getPillarsFound() {
        return myPillarsFound;
    }

    @Override
    public String toString() {
        return String.format(
                "Name: %s\nHit Points: %d\nTotal Healing Potions: %d\nTotal Vision Potions: %d\nList of Pillars Pieces Found: %s",
                this.getMyName(), this.getMyHealth(), this.myNumberOfHealingPotions, this.myNumberOfVisionPotions, this.myPillarsFound
        );
    }
}
