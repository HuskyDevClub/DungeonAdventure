package com.griffinryan.dungeonadventure.model.tests;

import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.heroes.Priestess;
import com.griffinryan.dungeonadventure.model.heroes.Thief;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestHeroes extends TestDungeonCharacter {

    private final static Skeleton WeakSkeleton = new Skeleton(
        myDummyName, defaultHealth, weakDamage, weakDamage, defaultAttackSpeed,
        100, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing
    );

    private final static Skeleton SuperSkeleton = new Skeleton(
        myDummyName, defaultHealth, superDamage, superDamage, defaultAttackSpeed,
        100, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing
    );

    private final static Skeleton GodLikeSkeleton = new Skeleton(
        myDummyName, defaultHealth, Integer.MAX_VALUE, Integer.MAX_VALUE, defaultAttackSpeed,
        100, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing
    );

    private static void testTheHeroThatCannotBlock(final Hero theHero) {
        // check if the right name has been set
        assertEquals(theHero.getName(), myDummyName);
        // check if the correct data has been assigned
        assertEquals(theHero.getHealth(), defaultHealth);
        checkConstantInstanceField(theHero);
        assertEquals(defaultChanceToBlock, theHero.getChanceToBlock());

        // check being attack behavior
        assertEquals(WeakSkeleton.getCurrentAttackSpeed(), WeakSkeleton.getMaxAttackSpeed());
        WeakSkeleton.attack(theHero, 4);
        assertEquals(WeakSkeleton.getCurrentAttackSpeed(), WeakSkeleton.getMaxAttackSpeed() - 4);
        WeakSkeleton.resetCurrentAttackSpeed();

        assertFalse(theHero.isLastAttackBlocked());
        assertFalse(theHero.isDead());
        assertTrue(theHero.isAlive());
        assertEquals(theHero.getHealth(), defaultHealth - weakDamage);

        // recheck whether the supposed constant instances has been modified
        checkConstantInstanceField(theHero);
        assertEquals(defaultChanceToBlock, theHero.getChanceToBlock());

        // what if the monster received a critical hit from our super warrior?
        SuperSkeleton.attack(theHero, 0);
        assertEquals(SuperSkeleton.getCurrentAttackSpeed(), SuperSkeleton.getMaxAttackSpeed());
        assertFalse(theHero.isLastAttackBlocked());
        assertTrue(theHero.isDead());
        assertFalse(theHero.isAlive());
        assertNotEquals(theHero.getHealth(), defaultHealth - weakDamage);
        assertEquals(theHero.getHealth(), 0);
        // recheck whether the supposed constant instances has been modified
        checkConstantInstanceField(theHero);
        assertEquals(defaultChanceToBlock, theHero.getChanceToBlock());

        // revive the monster
        theHero.revive();
        assertFalse(theHero.isDead());
        assertTrue(theHero.isAlive());
        // check if the correct data has been assigned
        assertEquals(theHero.getHealth(), defaultHealth);
        checkConstantInstanceField(theHero);
        assertEquals(defaultChanceToBlock, theHero.getChanceToBlock());

        // check overflow and underflow
        theHero.heal(Integer.MAX_VALUE);
        theHero.heal(Integer.MAX_VALUE);
        assertFalse(theHero.isDead());
        assertTrue(theHero.isAlive());
        assertEquals(theHero.getHealth(), Integer.MAX_VALUE);

        // attack by a god like warrior who can do Integer.MAX_VALUE amount of damage!
        GodLikeSkeleton.attack(theHero, 0);

        // should be dead at this point
        assertFalse(theHero.isLastAttackBlocked());
        assertTrue(theHero.isDead());
        assertFalse(theHero.isAlive());
        assertEquals(theHero.getHealth(), 0);

        // do it again?
        GodLikeSkeleton.attack(theHero, 0);

        // everything should remain the same
        assertFalse(theHero.isLastAttackBlocked());
        assertTrue(theHero.isDead());
        assertFalse(theHero.isAlive());
        assertEquals(theHero.getHealth(), 0);
        checkConstantInstanceField(theHero);
        assertEquals(defaultChanceToBlock, theHero.getChanceToBlock());
    }

    private static void testTheHeroThatCanAlwaysBlock(final Hero theHero) {
        // check if the right name has been set
        assertEquals(theHero.getName(), myDummyName);
        // check if the correct data has been assigned
        assertEquals(theHero.getHealth(), defaultHealth);
        checkConstantInstanceField(theHero);
        assertEquals(100, theHero.getChanceToBlock());

        // check being attack behavior
        WeakSkeleton.attack(theHero, 0);
        assertTrue(theHero.isLastAttackBlocked());
        assertFalse(theHero.isDead());
        assertTrue(theHero.isAlive());
        assertEquals(theHero.getHealth(), defaultHealth);
        // recheck whether the supposed constant instances has been modified
        checkConstantInstanceField(theHero);
        assertEquals(100, theHero.getChanceToBlock());

        // what if the monster received a critical hit from our super warrior?
        SuperSkeleton.attack(theHero, 0);
        assertTrue(theHero.isLastAttackBlocked());
        assertFalse(theHero.isDead());
        assertTrue(theHero.isAlive());
        assertEquals(theHero.getHealth(), defaultHealth);
        // recheck whether the supposed constant instances has been modified
        checkConstantInstanceField(theHero);
        assertEquals(100, theHero.getChanceToBlock());

        // check overflow and underflow
        theHero.heal(Integer.MAX_VALUE);
        theHero.heal(Integer.MAX_VALUE);
        assertFalse(theHero.isDead());
        assertTrue(theHero.isAlive());
        assertEquals(theHero.getHealth(), Integer.MAX_VALUE);

        // attack by a god like warrior who can do Integer.MAX_VALUE amount of damage!
        GodLikeSkeleton.attack(theHero, 0);

        // should still be alive at this point
        assertTrue(theHero.isLastAttackBlocked());
        assertFalse(theHero.isDead());
        assertTrue(theHero.isAlive());
        assertEquals(theHero.getHealth(), Integer.MAX_VALUE);
        checkConstantInstanceField(theHero);
        assertEquals(100, theHero.getChanceToBlock());
    }

    @Test
    void testPriestess() {
        testTheHeroThatCannotBlock(new Priestess(
            myDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
            defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, defaultChanceToBlock
        ));
        testTheHeroThatCanAlwaysBlock(new Priestess(
            myDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
            defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, 100
        ));
    }

    @Test
    void testThief() {
        testTheHeroThatCannotBlock(new Thief(
            myDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
            defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, defaultChanceToBlock
        ));
        testTheHeroThatCanAlwaysBlock(new Thief(
            myDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
            defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, 100
        ));
    }

    @Test
    void testWarrior() {
        testTheHeroThatCannotBlock(new Warrior(
            myDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
            defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, defaultChanceToBlock
        ));
        testTheHeroThatCanAlwaysBlock(new Warrior(
            myDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
            defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, 100
        ));
    }
}
