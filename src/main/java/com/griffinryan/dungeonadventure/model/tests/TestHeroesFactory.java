package com.griffinryan.dungeonadventure.model.tests;

import com.griffinryan.dungeonadventure.model.HeroesFactory;
import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.heroes.Priestess;
import com.griffinryan.dungeonadventure.model.heroes.Thief;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestHeroesFactory {

    private final static String myHeroDummyName = "dummy";

    @Test
    void testDefaultWarrior() {
        final Hero theWarrior = HeroesFactory.spawn("Warrior", myHeroDummyName);
        // check to see if the Monster return by the MonstersFactory is Gremlin
        assertTrue(theWarrior instanceof Warrior);
        // check if the right name has been set
        assertEquals(theWarrior.getName(), myHeroDummyName);
        // check if the default information has been assigned
        assertEquals(theWarrior.getHealth(), 125);
        assertEquals(theWarrior.getMaxAttackSpeed(), 4);
        assertEquals(theWarrior.getChanceToHit(), 80);
        assertEquals(theWarrior.getMinDamage(), 35);
        assertEquals(theWarrior.getMaxDamage(), 60);
        assertEquals(theWarrior.getChanceToBlock(), 20);
        assertEquals(theWarrior.getChanceToHeal(), 0);
        assertEquals(theWarrior.getMinHealing(), 0);
        assertEquals(theWarrior.getMaxHealing(), 0);
    }

    @Test
    void testDefaultPriestess() {
        final Hero thePriestess = HeroesFactory.spawn("Priestess", myHeroDummyName);
        // check to see if the Monster return by the MonstersFactory is Gremlin
        assertTrue(thePriestess instanceof Priestess);
        // check if the right name has been set
        assertEquals(thePriestess.getName(), myHeroDummyName);
        // check if the default information has been assigned
        assertEquals(thePriestess.getHealth(), 75);
        assertEquals(thePriestess.getMaxAttackSpeed(), 5);
        assertEquals(thePriestess.getChanceToHit(), 70);
        assertEquals(thePriestess.getMinDamage(), 25);
        assertEquals(thePriestess.getMaxDamage(), 45);
        assertEquals(thePriestess.getChanceToBlock(), 30);
        assertEquals(thePriestess.getChanceToHeal(), 100);
        assertEquals(thePriestess.getMinHealing(), 10);
        assertEquals(thePriestess.getMaxHealing(), 20);
    }

    @Test
    void testDefaultThief() {
        final Hero theThief = HeroesFactory.spawn("Thief", myHeroDummyName);
        // check to see if the Monster return by the MonstersFactory is Gremlin
        assertTrue(theThief instanceof Thief);
        // check if the right name has been set
        assertEquals(theThief.getName(), myHeroDummyName);
        // check if the default information has been assigned
        assertEquals(theThief.getHealth(), 75);
        assertEquals(theThief.getMaxAttackSpeed(), 6);
        assertEquals(theThief.getChanceToHit(), 80);
        assertEquals(theThief.getMinDamage(), 20);
        assertEquals(theThief.getMaxDamage(), 40);
        assertEquals(theThief.getChanceToBlock(), 40);
        assertEquals(theThief.getChanceToHeal(), 0);
        assertEquals(theThief.getMinHealing(), 0);
        assertEquals(theThief.getMaxHealing(), 0);
    }
}
