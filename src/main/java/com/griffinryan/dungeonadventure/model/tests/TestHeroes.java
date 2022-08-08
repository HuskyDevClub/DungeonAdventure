package com.griffinryan.dungeonadventure.model.tests;

import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.heroes.Priestess;
import com.griffinryan.dungeonadventure.model.heroes.Thief;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("DuplicatedCode")
public class TestHeroes {

    private final static String myHeroDummyName = "dummy";

    private static final int defaultHealth = 100;
    private static final int defaultMinDamage = 50;
    private static final int defaultMaxDamage = 90;
    private static final int defaultAttackSpeed = 5;
    private static final int defaultChanceToHit = 80;
    private static final int defaultChanceToHeal = 100;
    private static final int defaultMinHealing = 10;
    private static final int defaultMaxHealing = 50;
    private static final int defaultChanceToBlock = 0;

    private static final int weakSkeletonDamage = 20;
    private static final int superSkeletonDamage = 2000;

    private final static Skeleton WeakSkeleton = new Skeleton(
            myHeroDummyName, defaultHealth, weakSkeletonDamage, weakSkeletonDamage, defaultAttackSpeed,
            100, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing
    );

    private final static Skeleton SuperSkeleton = new Skeleton(
            myHeroDummyName, defaultHealth, superSkeletonDamage, superSkeletonDamage, defaultAttackSpeed,
            100, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing
    );

    private final static Skeleton GodLikeSkeleton = new Skeleton(
            myHeroDummyName, defaultHealth, Integer.MAX_VALUE, Integer.MAX_VALUE, defaultAttackSpeed,
            100, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing
    );

    private static void checkConstantInstanceField(Hero theHero) {
        assertEquals(defaultMinDamage, theHero.getMinDamage());
        assertEquals(defaultMaxDamage, theHero.getMaxDamage());
        assertEquals(defaultAttackSpeed, theHero.getAttackSpeed());
        assertEquals(defaultChanceToHit, theHero.getChanceToHit());
        assertEquals(defaultChanceToHeal, theHero.getChanceToHeal());
        assertEquals(defaultMinHealing, theHero.getMinHealing());
        assertEquals(defaultMaxHealing, theHero.getMaxHealing());
    }

    private static void testTheHeroThatCannotBlock(final Hero theHero) {
        // check if the right name has been set
        assertEquals(theHero.getName(), myHeroDummyName);
        // check if the correct data has been assigned
        assertEquals(theHero.getHealth(), defaultHealth);
        checkConstantInstanceField(theHero);
        assertEquals(defaultChanceToBlock, theHero.getChanceToBlock());

        // check being attack behavior
        WeakSkeleton.attack(theHero);
        assertFalse(theHero.isLastAttackBlocked());
        assertFalse(theHero.isDead());
        assertTrue(theHero.isAlive());
        assertEquals(theHero.getHealth(), defaultHealth - weakSkeletonDamage);
        // recheck whether the supposed constant instances has been modified
        checkConstantInstanceField(theHero);
        assertEquals(defaultChanceToBlock, theHero.getChanceToBlock());

        final int _heath = theHero.getHealth();
        theHero.selfHeal();
        assertTrue(theHero.getHealth() > _heath);

        // what if the monster received a critical hit from our super warrior?
        SuperSkeleton.attack(theHero);
        assertFalse(theHero.isLastAttackBlocked());
        assertTrue(theHero.isDead());
        assertFalse(theHero.isAlive());
        assertNotEquals(theHero.getHealth(), defaultHealth - weakSkeletonDamage);
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
        GodLikeSkeleton.attack(theHero);

        // should be dead at this point
        assertFalse(theHero.isLastAttackBlocked());
        assertTrue(theHero.isDead());
        assertFalse(theHero.isAlive());
        assertEquals(theHero.getHealth(), 0);

        // do it again?
        GodLikeSkeleton.attack(theHero);

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
        assertEquals(theHero.getName(), myHeroDummyName);
        // check if the correct data has been assigned
        assertEquals(theHero.getHealth(), defaultHealth);
        checkConstantInstanceField(theHero);
        assertEquals(100, theHero.getChanceToBlock());

        // check being attack behavior
        WeakSkeleton.attack(theHero);
        assertTrue(theHero.isLastAttackBlocked());
        assertFalse(theHero.isDead());
        assertTrue(theHero.isAlive());
        assertEquals(theHero.getHealth(), defaultHealth);
        // recheck whether the supposed constant instances has been modified
        checkConstantInstanceField(theHero);
        assertEquals(100, theHero.getChanceToBlock());

        final int _heath = theHero.getHealth();
        theHero.selfHeal();
        assertTrue(theHero.getHealth() > _heath);

        // what if the monster received a critical hit from our super warrior?
        SuperSkeleton.attack(theHero);
        assertTrue(theHero.isLastAttackBlocked());
        assertFalse(theHero.isDead());
        assertTrue(theHero.isAlive());
        assertNotEquals(theHero.getHealth(), defaultHealth);
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
        GodLikeSkeleton.attack(theHero);

        // should still be alive at this point
        assertTrue(theHero.isLastAttackBlocked());
        assertFalse(theHero.isDead());
        assertTrue(theHero.isAlive());
        assertEquals(theHero.getHealth(), Integer.MAX_VALUE);
        checkConstantInstanceField(theHero);
        assertEquals(100, theHero.getChanceToBlock());
    }

    @Test
    void testOgre() {
        testTheHeroThatCannotBlock(new Priestess(
                myHeroDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
                defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, defaultChanceToBlock
        ));
        testTheHeroThatCanAlwaysBlock(new Priestess(
                myHeroDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
                defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, 100
        ));
    }

    @Test
    void testGremlin() {
        testTheHeroThatCannotBlock(new Thief(
                myHeroDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
                defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, defaultChanceToBlock
        ));
        testTheHeroThatCanAlwaysBlock(new Thief(
                myHeroDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
                defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, 100
        ));
    }

    @Test
    void testSkeleton() {
        testTheHeroThatCannotBlock(new Warrior(
                myHeroDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
                defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, defaultChanceToBlock
        ));
        testTheHeroThatCanAlwaysBlock(new Warrior(
                myHeroDummyName, defaultHealth, defaultMinDamage, defaultMaxDamage, defaultAttackSpeed,
                defaultChanceToHit, defaultChanceToHeal, defaultMinHealing, defaultMaxHealing, 100
        ));
    }
}
