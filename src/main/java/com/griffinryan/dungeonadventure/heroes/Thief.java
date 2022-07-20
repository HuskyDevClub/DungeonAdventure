package com.griffinryan.dungeonadventure.heroes;


import com.griffinryan.dungeonadventure.monsters.Monster;

public class Thief extends Hero {
    public Thief(String theName, int theHealth, int theMinDamage, int theMaxDamage, int theAttackSpeed, byte theChanceToHit, byte theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToBlock);
    }

    public Thief(String theName) {
        super(theName, 75, 20, 40, 6, (byte) 80, (byte) 40);
    }

    @Override
    public void skill(Monster theTarget) {

    }

}
