package com.griffinryan.dungeonadventure.model.tests;

import com.griffinryan.dungeonadventure.model.HeroesFactory;
import com.griffinryan.dungeonadventure.model.dungeon.Direction;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.heroes.Thief;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.rooms.Entrance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestDungeon {

    @Test
    public void testDungeonBasicFunctionalities() {
        final String theHeroName = "TheHeroIsMe";
        final int theWidth = 10;
        final int theHeight = 10;
        final Dungeon theDungeon = new Dungeon(HeroesFactory.spawn(Thief.class.getSimpleName(), theHeroName), theWidth, theHeight);

        assertEquals(theWidth, theDungeon.getMazeWidth());
        assertEquals(theHeight, theDungeon.getMazeHeight());

        // check hero
        final Hero theHero = theDungeon.getHero();
        assertTrue(theHero instanceof Thief);
        assertEquals(theHeroName, theHero.getName());

        // check pillars
        testPillar(theDungeon);

        // test coordinate system
        assertTrue(theDungeon.canHeroMoveTo(theDungeon.getCurrentX(), theDungeon.getCurrentY()));
        assertFalse(theDungeon.canHeroMoveTo(-1, 0));
        assertFalse(theDungeon.canHeroMoveTo(10, 0));

        // check room
        assertTrue(theDungeon.getCurrentRoom() instanceof Entrance);
        assertFalse(theDungeon.isCurrentRoomExit());
        assertFalse(theDungeon.isCurrentRoomPit());

        // test move method
        testMoveMethod(theDungeon, theDungeon.getCurrentX(), theDungeon.getCurrentY());
        testMoveMethodAroundEdgesIfPossible(theDungeon);
    }

    // test pillars placement
    public void testPillar(final Dungeon theDungeon) {
        assertFalse(theDungeon.areAllPillarsFound());
        assertTrue(theDungeon.getPillarsFound().isEmpty());
        assertDoesNotThrow(theDungeon::pickUpAllPillars);
        assertTrue(theDungeon.areAllPillarsFound());
        assertEquals(4, theDungeon.getPillarsFound().size());
    }

    private void testMoveMethod(final Dungeon theDungeon, int theX, int theY) {
        if (theDungeon.canHeroMoveTo(theX, theY - 1)) {
            theDungeon.moveHero(Direction.UP);
            theY--;
            assertEquals(theX, theDungeon.getCurrentX());
            assertEquals(theY, theDungeon.getCurrentY());
        }
        if (theDungeon.canHeroMoveTo(theX - 1, theY)) {
            theDungeon.moveHero(Direction.LEFT);
            theX--;
            assertEquals(theX, theDungeon.getCurrentX());
            assertEquals(theY, theDungeon.getCurrentY());
        }
        if (theDungeon.canHeroMoveTo(theX, theY + 1)) {
            theDungeon.moveHero(Direction.DOWN);
            theY++;
            assertEquals(theX, theDungeon.getCurrentX());
            assertEquals(theY, theDungeon.getCurrentY());
        }
        if (theDungeon.canHeroMoveTo(theX + 1, theY)) {
            theDungeon.moveHero(Direction.RIGHT);
            theX++;
            assertEquals(theX, theDungeon.getCurrentX());
            assertEquals(theY, theDungeon.getCurrentY());
        }
    }

    private void testMoveMethodAroundEdgesIfPossible(final Dungeon theDungeon) {
        if (theDungeon.moveHeroTo(0, 0)) {
            testMoveMethod(theDungeon, 0, 0);
        }
        if (theDungeon.moveHeroTo(0, theDungeon.getMazeHeight())) {
            testMoveMethod(theDungeon, 0, theDungeon.getMazeHeight());
        }
        if (theDungeon.moveHeroTo(theDungeon.getMazeWidth(), theDungeon.getMazeHeight())) {
            testMoveMethod(theDungeon, theDungeon.getMazeWidth(), theDungeon.getMazeHeight());
        }
        if (theDungeon.moveHeroTo(theDungeon.getMazeWidth(), 0)) {
            testMoveMethod(theDungeon, theDungeon.getMazeWidth(), 0);
        }
    }

    @Test
    public void testDungeon() {
        final String theHeroName = "TheHeroIsUs";

        final int theX = 124;
        final int theY = 0;
        final int theWidth = 125;
        final int theHeight = 75;

        final Dungeon theDungeon = new Dungeon(HeroesFactory.spawn(Warrior.class.getSimpleName(), theHeroName), theWidth, theHeight, theX, theY);

        assertEquals(theWidth, theDungeon.getMazeWidth());
        assertEquals(theHeight, theDungeon.getMazeHeight());

        // check current room's doors
        assertEquals(theDungeon.canHeroMove(Direction.UP), theDungeon.getCurrentRoom().isThereDoorOn(Direction.UP));
        assertEquals(theDungeon.canHeroMove(Direction.LEFT), theDungeon.getCurrentRoom().isThereDoorOn(Direction.LEFT));
        assertEquals(theDungeon.canHeroMove(Direction.RIGHT), theDungeon.getCurrentRoom().isThereDoorOn(Direction.RIGHT));
        assertEquals(theDungeon.canHeroMove(Direction.DOWN), theDungeon.getCurrentRoom().isThereDoorOn(Direction.DOWN));

        // check hero
        final Hero theHero = theDungeon.getHero();
        assertTrue(theHero instanceof Warrior);
        assertEquals(theHeroName, theHero.getName());

        // check pillars
        testPillar(theDungeon);

        // check room
        assertTrue(theDungeon.getCurrentRoom() instanceof Entrance);
        assertFalse(theDungeon.isCurrentRoomExit());
        assertFalse(theDungeon.isCurrentRoomPit());

        // test move method
        testMoveMethod(theDungeon, theX, theY);
        testMoveMethodAroundEdgesIfPossible(theDungeon);
    }

    @Test
    public void testDungeonThrows() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new Dungeon(HeroesFactory.spawn(Warrior.class.getSimpleName(), "name"), 10, 10, 10, 10)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> new Dungeon(HeroesFactory.spawn(Warrior.class.getSimpleName(), "name"), 10, 10, -1, -1)
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> new Dungeon(HeroesFactory.spawn(Warrior.class.getSimpleName(), "name"), 2, 2, 0, 0)
        );
    }
}
