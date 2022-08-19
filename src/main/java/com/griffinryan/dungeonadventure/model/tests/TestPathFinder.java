package com.griffinryan.dungeonadventure.model.tests;

import com.griffinryan.dungeonadventure.model.dungeon.PathFinder;
import com.griffinryan.dungeonadventure.model.rooms.AbstractRoom;
import com.griffinryan.dungeonadventure.model.rooms.Entrance;
import com.griffinryan.dungeonadventure.model.rooms.Pit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPathFinder {

    @Test
    public void testConstruction() {

        final AbstractRoom[][] EMPTY_MAZE_1 = new AbstractRoom[0][0];
        final AbstractRoom[][] EMPTY_MAZE_2 = new AbstractRoom[1][0];
        final AbstractRoom[][] EMPTY_MAZE_3 = new AbstractRoom[10][0];

        final AbstractRoom[][] NORMAL_MAZE_1 = new AbstractRoom[1][1];
        final AbstractRoom[][] NORMAL_MAZE_2 = new AbstractRoom[3][3];
        final AbstractRoom[][] NORMAL_MAZE_3 = new AbstractRoom[10][10];
        final AbstractRoom[][] NORMAL_MAZE_4 = new AbstractRoom[1000][1000];

        // invalid maze parameter
        assertThrows(IllegalArgumentException.class, () -> new PathFinder(EMPTY_MAZE_1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new PathFinder(EMPTY_MAZE_2, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new PathFinder(EMPTY_MAZE_3, 0, 0));
        // valid maze
        assertDoesNotThrow(() -> new PathFinder(NORMAL_MAZE_1, 0, 0));
        assertDoesNotThrow(() -> new PathFinder(NORMAL_MAZE_2, 0, 0));
        assertDoesNotThrow(() -> new PathFinder(NORMAL_MAZE_3, 0, 0));
        assertDoesNotThrow(() -> new PathFinder(NORMAL_MAZE_4, 0, 0));
        // valid maze but entrance coordinates are out of bound
        assertThrows(IndexOutOfBoundsException.class, () -> new PathFinder(NORMAL_MAZE_1, -1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> new PathFinder(NORMAL_MAZE_1, 0, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> new PathFinder(NORMAL_MAZE_3, -5, 9));
        assertThrows(IndexOutOfBoundsException.class, () -> new PathFinder(NORMAL_MAZE_4, 0, 1000));
    }

    @Test
    public void testPathFindingOn1x1Maze() {
        final AbstractRoom[][] MAZE_1x1 = {{new Entrance()}};
        final PathFinder PATH_FINDER_FOR_MAZE_1x1 = new PathFinder(MAZE_1x1, 0, 0);
        assertTrue(PATH_FINDER_FOR_MAZE_1x1.isReachable(0, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> PATH_FINDER_FOR_MAZE_1x1.isReachable(-10, -10));
        assertThrows(IndexOutOfBoundsException.class, () -> PATH_FINDER_FOR_MAZE_1x1.isReachable(10, 10));
    }

    @Test
    public void testPathFindingOn5x5Maze() {
        final AbstractRoom[][] MAZE_5x5 = {
            {new Entrance(), new Pit(), null, null, null},
            {null, new Pit(), null, null, null},
            {null, new Pit(), new Pit(), null, null},
            {null, null, new Pit(), null, null},
            {null, null, new Pit(), new Pit(), new Pit()},
        };
        final PathFinder PATH_FINDER_FOR_MAZE_5x5 = new PathFinder(MAZE_5x5, 0, 0);
        assertTrue(PATH_FINDER_FOR_MAZE_5x5.isReachable(0, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> PATH_FINDER_FOR_MAZE_5x5.isReachable(-10, 10));
        assertThrows(IndexOutOfBoundsException.class, () -> PATH_FINDER_FOR_MAZE_5x5.isReachable(5, 5));
        assertTrue(PATH_FINDER_FOR_MAZE_5x5.isReachable(1, 1));
        assertFalse(PATH_FINDER_FOR_MAZE_5x5.isReachable(1, 3));
        assertFalse(PATH_FINDER_FOR_MAZE_5x5.isReachable(0, 1));
        assertTrue(PATH_FINDER_FOR_MAZE_5x5.isReachable(4, 4));
        MAZE_5x5[3][2] = null;
        assertFalse(PATH_FINDER_FOR_MAZE_5x5.isReachable(4, 4));
    }

}
