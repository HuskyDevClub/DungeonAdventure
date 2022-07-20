package com.griffinryan.dungeonadventure.dungeon;

import java.util.Random;

public class Dungeon {
    private final Room[][] rooms;
    private int myHeroCurrentX;
    private int myHeroCurrentY;

    public Dungeon(int width, int height) {
        rooms = new Room[height][width];
        final Random rand = new Random();
        for (int y = 0; y < rooms.length; y++) {
            for (int x = 0; x < rooms[y].length; x++) {
                if (rand.nextInt(100) < 85) {
                    rooms[y][x] = new Room();
                }
            }
        }
        myHeroCurrentX = width / 2;
        myHeroCurrentY = height / 2;
    }

    public boolean move(Direction theDirection) {
        switch (theDirection) {
            case UP -> {
                return moveTo(myHeroCurrentX, myHeroCurrentY - 1);
            }
            case DOWN -> {
                return moveTo(myHeroCurrentX, myHeroCurrentY + 1);
            }
            case LEFT -> {
                return moveTo(myHeroCurrentX - 1, myHeroCurrentY);
            }
            case RIGHT -> {
                return moveTo(myHeroCurrentX + 1, myHeroCurrentY);
            }
        }
        return false;
    }

    private boolean moveTo(int theX, int theY) {
        // check the coordinate to ensure that the player can move
        // which means no out of bound
        if (this.canMoveTo(theX, theY)) {
            myHeroCurrentX = theX;
            myHeroCurrentY = theY;
            return true;
        }
        return false;
    }

    public boolean canMoveTo(int theX, int theY) {
        return 0 <= theY && theY < rooms.length && 0 <= theX && theX < rooms[theY].length && rooms[theY][theX] != null;
    }

    @Override
    public String toString() {
        final StringBuilder theInfo = new StringBuilder();
        for (int y = 0; y < rooms.length; y++) {
            theInfo.append("[");
            for (int x = 0; x < rooms[y].length; x++) {
                if (y == myHeroCurrentY && x == myHeroCurrentX) {
                    theInfo.append("*");
                } else if (rooms[y][x] != null) {
                    theInfo.append(".");
                } else {
                    theInfo.append("X");
                }
            }
            theInfo.append("]\n");
        }
        return theInfo.toString();
    }

    public int getCurrentX() {
        return myHeroCurrentX;
    }

    public int getCurrentY() {
        return myHeroCurrentY;
    }

    public Room getCurrentRoom() {
        return rooms[myHeroCurrentY][myHeroCurrentX];
    }
}
