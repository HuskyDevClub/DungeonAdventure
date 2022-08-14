package com.griffinryan.dungeonadventure.model.tests;

import com.griffinryan.dungeonadventure.model.MonstersFactory;
import com.griffinryan.dungeonadventure.model.dungeon.Pillar;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;
import com.griffinryan.dungeonadventure.model.rooms.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestRooms {

    @Test
    public void testEmptyRoom() {
        final Room theEmptyRoom = new Room(new ArrayList<>(), 0, 0);
        assertEquals(' ', theEmptyRoom.getFlag());
        assertRoomIsEmpty(theEmptyRoom);

        testPillarPlacement(theEmptyRoom);

        // check if the room is empty again
        assertRoomIsEmpty(theEmptyRoom);
        assertEquals(' ', theEmptyRoom.getFlag());
    }

    public void assertRoomIsEmpty(AbstractRoom theEmptyRoom) {
        assertEquals(0, theEmptyRoom.getNumberOfMonsters());
        assertEquals(0, theEmptyRoom.getNumberOfHealingPotions());
        assertEquals(0, theEmptyRoom.pickUpHealingPotions());
        assertEquals(0, theEmptyRoom.getNumberOfVisionPotions());
        assertEquals(0, theEmptyRoom.pickUpVisionPotions());
        assertFalse(theEmptyRoom.hasPillar());
        assertThrows(IndexOutOfBoundsException.class, () -> theEmptyRoom.removeMonster(0));
        assertEquals(0, theEmptyRoom.getNumberOfMonsters());
        assertThrows(NullPointerException.class, theEmptyRoom::pickUpPillar);
    }

    public void testPillarPlacement(AbstractRoom theEmptyRoom) {
        assertDoesNotThrow(() -> theEmptyRoom.placePillar(new Pillar("Abstract")));
        assertTrue(theEmptyRoom.hasPillar());
        assertEquals('A', theEmptyRoom.getFlag());
        assertThrows(IllegalStateException.class, () -> theEmptyRoom.placePillar(new Pillar("Magic")));
        assertTrue(theEmptyRoom.hasPillar());
        assertEquals('A', theEmptyRoom.getFlag());
        assertEquals("Abstract", theEmptyRoom.pickUpPillar());
    }

    @Test
    public void testRoomWithOnlyHealingPotions() {
        final int numOfHealingPotions = 10;
        final Room theRoom = new Room(new ArrayList<>(), numOfHealingPotions, 0);

        // check whether the flag is correct
        assertEquals('H', theRoom.getFlag());
        testPillarPlacement(theRoom);
        assertEquals('H', theRoom.getFlag());

        // try to pick up potions
        assertEquals(numOfHealingPotions, theRoom.getNumberOfHealingPotions());
        assertEquals(0, theRoom.getNumberOfVisionPotions());
        assertEquals(numOfHealingPotions, theRoom.pickUpHealingPotions());
        assertEquals(0, theRoom.pickUpVisionPotions());
        assertEquals(0, theRoom.getNumberOfHealingPotions());
        assertEquals(0, theRoom.pickUpHealingPotions());

        // check if the room is empty again
        assertRoomIsEmpty(theRoom);
        assertEquals(' ', theRoom.getFlag());
    }

    @Test
    public void testRoomWithOnlyVisionPotions() {
        final int numOfVisionPotions = 20;
        final Room theRoom = new Room(new ArrayList<>(), 0, numOfVisionPotions);

        // check whether the flag is correct
        assertEquals('V', theRoom.getFlag());
        testPillarPlacement(theRoom);
        assertEquals('V', theRoom.getFlag());

        // try to pick up potions
        assertEquals(numOfVisionPotions, theRoom.getNumberOfVisionPotions());
        assertEquals(0, theRoom.getNumberOfHealingPotions());
        assertEquals(numOfVisionPotions, theRoom.pickUpVisionPotions());
        assertEquals(0, theRoom.pickUpVisionPotions());
        assertEquals(0, theRoom.getNumberOfVisionPotions());
        assertEquals(0, theRoom.getNumberOfHealingPotions());

        // check if the room is empty again
        assertRoomIsEmpty(theRoom);
        assertEquals(' ', theRoom.getFlag());
    }

    @Test
    public void testRoomWithBothPotions() {
        final int numOfHealingPotions = 31;
        final int numOfVisionPotions = 45;
        final Room theRoom = new Room(new ArrayList<>(), numOfHealingPotions, numOfVisionPotions);

        // check whether the flag is correct
        assertEquals('M', theRoom.getFlag());
        testPillarPlacement(theRoom);
        assertEquals('M', theRoom.getFlag());

        // try to pick up potions
        assertEquals(numOfHealingPotions, theRoom.getNumberOfHealingPotions());
        assertEquals(numOfVisionPotions, theRoom.getNumberOfVisionPotions());
        assertEquals(numOfHealingPotions, theRoom.pickUpHealingPotions());
        assertEquals('V', theRoom.getFlag());
        assertEquals(numOfVisionPotions, theRoom.pickUpVisionPotions());
        assertEquals(0, theRoom.getNumberOfVisionPotions());
        assertEquals(0, theRoom.getNumberOfHealingPotions());

        // check if the room is empty again
        assertRoomIsEmpty(theRoom);
        assertEquals(' ', theRoom.getFlag());
    }

    @Test
    public void testRoomWithMonstersOnly() {
        final ArrayList<Monster> monstersArr = new ArrayList<>(3);

        assertDoesNotThrow(() -> monstersArr.add(MonstersFactory.spawn("skeleton", "minecraft_skeleton")));
        assertDoesNotThrow(() -> monstersArr.add(MonstersFactory.spawn("gremlin", "gremlin_the_third")));
        assertDoesNotThrow(() -> monstersArr.add(MonstersFactory.spawn("ogre", "ogre_is_here")));

        final Room theRoom = new Room(monstersArr, 0, 0);
        assertEquals(0, theRoom.getNumberOfVisionPotions());
        assertEquals(0, theRoom.getNumberOfHealingPotions());
        assertEquals(3, theRoom.getNumberOfMonsters());
        assertEquals(' ', theRoom.getFlag());

        Monster monster = theRoom.removeMonster(0);
        assertEquals(2, theRoom.getNumberOfMonsters());
        assertEquals("minecraft_skeleton", monster.getName());
        assertEquals(Skeleton.class, monster.getClass());

        theRoom.removeMonster(0);
        theRoom.removeMonster(0);

        // check if the room is empty again
        assertRoomIsEmpty(theRoom);
        assertEquals(' ', theRoom.getFlag());
    }

    @Test
    public void testRoomWithEverything() {
        final ArrayList<Monster> monstersArr = new ArrayList<>(3);
        assertDoesNotThrow(() -> monstersArr.add(MonstersFactory.spawn("skeleton", "minecraft_skeleton")));
        assertDoesNotThrow(() -> monstersArr.add(MonstersFactory.spawn("gremlin", "gremlin_the_third")));

        final int numOfHealingPotions = 31;
        final int numOfVisionPotions = 45;

        final Room theRoom = new Room(monstersArr, numOfHealingPotions, numOfVisionPotions);
        assertEquals(numOfVisionPotions, theRoom.getNumberOfVisionPotions());
        assertEquals(numOfHealingPotions, theRoom.getNumberOfHealingPotions());
        assertEquals(2, theRoom.getNumberOfMonsters());
        assertEquals('M', theRoom.getFlag());

        testPillarPlacement(theRoom);

        Monster monster = theRoom.removeMonster(0);
        assertEquals(1, theRoom.getNumberOfMonsters());
        assertEquals("minecraft_skeleton", monster.getName());
        assertEquals(Skeleton.class, monster.getClass());
        theRoom.removeMonster(0);

        assertEquals(numOfVisionPotions, theRoom.pickUpVisionPotions());
        assertEquals(numOfHealingPotions, theRoom.pickUpHealingPotions());

        // check if the room is empty again
        assertRoomIsEmpty(theRoom);
        assertEquals(' ', theRoom.getFlag());
    }

    @Test
    public void testPit() {
        final Pit thePit = new Pit();
        assertEquals('X', thePit.getFlag());
        assertRoomIsEmpty(thePit);
    }

    @Test
    public void testEntrance() {
        final Entrance theEntrance = new Entrance();
        assertEquals('i', theEntrance.getFlag());
        assertRoomIsEmpty(theEntrance);
    }

    @Test
    public void testExit() {
        final Exit theExit = new Exit();
        assertEquals('O', theExit.getFlag());
        assertRoomIsEmpty(theExit);
    }

}
