package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.monsters.Gremlin;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;

import java.util.ArrayList;
import java.util.Random;

public class Dungeon {
    private final AbstractRoom[][] rooms;
    private int myHeroCurrentX;
    private int myHeroCurrentY;

    public Dungeon(int width, int height) {
        rooms = new AbstractRoom[height][width];
        final Random theRandom = new Random();
        for (int y = 0; y < rooms.length; y++) {
            for (int x = 0; x < rooms[y].length; x++) {
                if (theRandom.nextInt(100) < 85) {
                    if (theRandom.nextInt(0, 6) != 0) {
                        final int theNumOfMonsters = theRandom.nextInt(0, 6);
                        final ArrayList<Monster> theMonsters = new ArrayList<>(theNumOfMonsters);
                        for (int i = 0; i < theNumOfMonsters; i++) {
                            switch (theRandom.nextInt(0, 2)) {
                                case 0 -> theMonsters.add(new Gremlin("G1"));
                                case 1 -> theMonsters.add(new Ogre("O1"));
                                default -> theMonsters.add(new Skeleton("S1"));
                            }
                        }
                        rooms[y][x] = new Room(theMonsters, theRandom.nextInt(0, 3), theRandom.nextInt(0, 2));
                    } else {
                        rooms[y][x] = new Pit();
                    }
                }
            }
        }
        rooms[theRandom.nextInt(height)][theRandom.nextInt(width)] = new Exit();
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
                } else if (rooms[y][x] instanceof Pit) {
                    theInfo.append("P");
                } else if (rooms[y][x] instanceof Exit) {
                    theInfo.append("E");
                } else if (rooms[y][x] != null) {
                    theInfo.append(".");
                } else {
                    theInfo.append("|");
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

    public AbstractRoom getCurrentRoom() {
        return rooms[myHeroCurrentY][myHeroCurrentX];
    }
}
