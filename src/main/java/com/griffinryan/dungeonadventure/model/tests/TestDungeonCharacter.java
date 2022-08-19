package com.griffinryan.dungeonadventure.model.tests;

import com.griffinryan.dungeonadventure.model.DungeonCharacter;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TestDungeonCharacter {

    protected final static String myDummyName = "dummy";

    protected static final int defaultHealth = 100;
    protected static final int defaultMinDamage = 50;
    protected static final int defaultMaxDamage = 90;
    protected static final int defaultAttackSpeed = 5;
    protected static final int defaultChanceToHit = 80;
    protected static final int defaultChanceToHeal = 100;
    protected static final int defaultMinHealing = 10;
    protected static final int defaultMaxHealing = 50;
    protected static final int defaultChanceToBlock = 0;

    protected static final int weakDamage = 20;
    protected static final int superDamage = 2000;

    protected static void checkConstantInstanceField(final DungeonCharacter theCharacter) {
        assertEquals(defaultMinDamage, theCharacter.getMinDamage());
        assertEquals(defaultMaxDamage, theCharacter.getMaxDamage());
        assertEquals(defaultAttackSpeed, theCharacter.getMaxAttackSpeed());
        assertEquals(defaultChanceToHit, theCharacter.getChanceToHit());
        assertEquals(defaultChanceToHeal, theCharacter.getChanceToHeal());
        assertEquals(defaultMinHealing, theCharacter.getMinHealing());
        assertEquals(defaultMaxHealing, theCharacter.getMaxHealing());
    }
}
