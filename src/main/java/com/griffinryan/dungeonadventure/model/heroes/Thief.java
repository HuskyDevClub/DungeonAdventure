package com.griffinryan.dungeonadventure.model.heroes;


import com.griffinryan.dungeonadventure.model.monsters.Monster;

public class Thief extends Hero {
    public Thief(final String theName, final int theHealth, final int theMinDamage, final int theMaxDamage, final int theAttackSpeed, final byte theChanceToHit, final byte theChanceToBlock) {
        super(theName, theHealth, theMinDamage, theMaxDamage, theAttackSpeed, theChanceToHit, theChanceToBlock);
    }

    public Thief(final String theName) {
        super(theName, 75, 20, 40, 6, (byte) 80, (byte) 40);
    }

    @Override
    public void skill(final Monster theTarget) {

    }

}
