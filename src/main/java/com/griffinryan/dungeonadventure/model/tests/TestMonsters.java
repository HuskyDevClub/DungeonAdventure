package com.griffinryan.dungeonadventure.model.tests;

import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.monsters.Gremlin;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMonsters extends TestDungeonCharacter {

    private final static Warrior WeakWarrior = new Warrior(
        myDummyName, defaultHealth, weakDamage, weakDamage, defaultAttackSpeed,
        100, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, 0
    );

    private final static Warrior SuperWarrior = new Warrior(
        myDummyName, defaultHealth, superDamage, superDamage, defaultAttackSpeed,
        100, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, 0
    );

    private final static Warrior GodLikeWarrior = new Warrior(
        myDummyName, defaultHealth, Integer.MAX_VALUE, Integer.MAX_VALUE, defaultAttackSpeed,
        100, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, 0
    );

    private static void checkConstantInstanceField(Monster theMonster) {
        TestDungeonCharacter.checkConstantInstanceField(theMonster);
        // check if hidden by useless data has been assigned
        assertEquals(0, theMonster.getChanceToBlock());

    }

    private static void testTheMonster(final Monster theMonster) {
        // check if the right name has been set
        assertEquals(theMonster.getName(), myDummyName);
        // check if the correct data has been assigned
        assertEquals(theMonster.getHealth(), defaultHealth);
        checkConstantInstanceField(theMonster);

        // check being attack behavior
        assertEquals(WeakWarrior.getCurrentAttackSpeed(), WeakWarrior.getMaxAttackSpeed());
        WeakWarrior.attack(theMonster, 3);
        assertEquals(WeakWarrior.getCurrentAttackSpeed(), WeakWarrior.getMaxAttackSpeed() - 3);
        WeakWarrior.resetCurrentAttackSpeed();
        assertFalse(theMonster.isLastAttackBlocked());
        assertFalse(theMonster.isDead());
        assertTrue(theMonster.isAlive());
        assertEquals(theMonster.getHealth(), defaultHealth - weakDamage);
        // recheck whether the supposed constant instances has been modified
        checkConstantInstanceField(theMonster);

        // what if the monster received a critical hit from our super warrior?
        SuperWarrior.attack(theMonster, 0);
        assertEquals(SuperWarrior.getCurrentAttackSpeed(), SuperWarrior.getMaxAttackSpeed());
        assertFalse(theMonster.isLastAttackBlocked());
        assertTrue(theMonster.isDead());
        assertFalse(theMonster.isAlive());
        assertNotEquals(theMonster.getHealth(), defaultHealth - weakDamage);
        assertEquals(theMonster.getHealth(), 0);
        // recheck whether the supposed constant instances has been modified
        checkConstantInstanceField(theMonster);

        // revive the monster
        theMonster.revive();
        assertFalse(theMonster.isDead());
        assertTrue(theMonster.isAlive());
        // check if the correct data has been assigned
        assertEquals(theMonster.getHealth(), defaultHealth);
        checkConstantInstanceField(theMonster);

        // check overflow and underflow
        theMonster.heal(Integer.MAX_VALUE);
        theMonster.heal(Integer.MAX_VALUE);
        assertFalse(theMonster.isDead());
        assertTrue(theMonster.isAlive());
        assertEquals(theMonster.getHealth(), Integer.MAX_VALUE);

        // attack by a god like warrior who can do Integer.MAX_VALUE amount of damage!
        GodLikeWarrior.attack(theMonster, 0);

        // should be dead at this point
        assertFalse(theMonster.isLastAttackBlocked());
        assertTrue(theMonster.isDead());
        assertFalse(theMonster.isAlive());
        assertEquals(theMonster.getHealth(), 0);

        // do it again?
        GodLikeWarrior.attack(theMonster, 0);

        // everything should remain the same
        assertFalse(theMonster.isLastAttackBlocked());
        assertTrue(theMonster.isDead());
        assertFalse(theMonster.isAlive());
        assertEquals(theMonster.getHealth(), 0);
        checkConstantInstanceField(theMonster);
    }


    @Test
    void testOgre() {
        testTheMonster(new Ogre(
            myDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
            defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing
        ));
    }

    @Test
    void testGremlin() {
        testTheMonster(new Gremlin(
            myDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
            defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing
        ));
    }

    @Test
    void testSkeleton() {
        testTheMonster(new Skeleton(
            myDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
            defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing
        ));
    }
}
