package com.griffinryan.dungeonadventure.model.tests;

import com.griffinryan.dungeonadventure.model.MonstersFactory;
import com.griffinryan.dungeonadventure.model.monsters.Gremlin;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final public class TestMonstersFactory {

    private final static String myMonsterDummyName = "dummy";

    @Test
    void testDefaultOgre() throws SQLException {
        final Monster theOgre = MonstersFactory.spawn("Ogre", myMonsterDummyName);
        // check to see if the Monster return by the MonstersFactory is Gremlin
        assertTrue(theOgre instanceof Ogre);
        // check if the right name has been set
        assertEquals(theOgre.getName(), myMonsterDummyName);
        // check if the default information has been assigned
        assertEquals(theOgre.getHealth(), 200);
        assertEquals(theOgre.getMaxAttackSpeed(), 2);
        assertEquals(theOgre.getChanceToHit(), 60);
        assertEquals(theOgre.getMinDamage(), 30);
        assertEquals(theOgre.getMaxDamage(), 60);
        assertEquals(theOgre.getChanceToHeal(), 10);
        assertEquals(theOgre.getMinHealing(), 30);
        assertEquals(theOgre.getMaxHealing(), 60);
    }

    @Test
    void testDefaultGremlin() throws SQLException {
        final Monster theGremlin = MonstersFactory.spawn("Gremlin", myMonsterDummyName);
        // check to see if the Monster return by the MonstersFactory is Gremlin
        assertTrue(theGremlin instanceof Gremlin);
        // check if the right name has been set
        assertEquals(theGremlin.getName(), myMonsterDummyName);
        // check if the default information has been assigned
        assertEquals(theGremlin.getHealth(), 70);
        assertEquals(theGremlin.getMaxAttackSpeed(), 5);
        assertEquals(theGremlin.getChanceToHit(), 80);
        assertEquals(theGremlin.getMinDamage(), 15);
        assertEquals(theGremlin.getMaxDamage(), 30);
        assertEquals(theGremlin.getChanceToHeal(), 40);
        assertEquals(theGremlin.getMinHealing(), 20);
        assertEquals(theGremlin.getMaxHealing(), 40);
    }

    @Test
    void testDefaultSkeleton() throws SQLException {
        final Monster theSkeleton = MonstersFactory.spawn("Skeleton", myMonsterDummyName);
        // check to see if the Monster return by the MonstersFactory is Gremlin
        assertTrue(theSkeleton instanceof Skeleton);
        // check if the right name has been set
        assertEquals(theSkeleton.getName(), myMonsterDummyName);
        // check if the default information has been assigned
        assertEquals(theSkeleton.getHealth(), 100);
        assertEquals(theSkeleton.getMaxAttackSpeed(), 3);
        assertEquals(theSkeleton.getChanceToHit(), 80);
        assertEquals(theSkeleton.getMinDamage(), 30);
        assertEquals(theSkeleton.getMaxDamage(), 50);
        assertEquals(theSkeleton.getChanceToHeal(), 30);
        assertEquals(theSkeleton.getMinHealing(), 30);
        assertEquals(theSkeleton.getMaxHealing(), 50);
    }
}
