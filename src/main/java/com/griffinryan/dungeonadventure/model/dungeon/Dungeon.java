package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.monsters.Gremlin;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;

import java.util.ArrayList;
import java.util.Random;

public class Dungeon {
    final static private int myChanceToGenerateRoom = 85;
    private final AbstractRoom[][] my2dMaze2dArray;
    final private Pillar[] myPillars = {new Pillar("Abstract"), new Pillar("Encapsulation"), new Pillar("Inheritance"), new Pillar("Polymorphism")};
    private int myHeroCurrentX;
    private int myHeroCurrentY;

    public Dungeon(final int width, final int height) {
        AbstractRoom[][] the2dMaze2dArrayTemp;
        final Random theRandom = new Random();
        while (true) {
            the2dMaze2dArrayTemp = new AbstractRoom[height][width];
            for (int y = 0; y < the2dMaze2dArrayTemp.length; y++) {
                for (int x = 0; x < the2dMaze2dArrayTemp[y].length; x++) {
                    if (theRandom.nextInt(100) < myChanceToGenerateRoom) {
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
                            the2dMaze2dArrayTemp[y][x] = new Room(theMonsters, theRandom.nextInt(0, 3), theRandom.nextInt(0, 2));
                        } else {
                            the2dMaze2dArrayTemp[y][x] = new Pit();
                        }
                    }
                }
            }
            final int theExitX = theRandom.nextInt(width);
            final int theExitY = theRandom.nextInt(height);
            the2dMaze2dArrayTemp[theExitY][theExitX] = new Exit();
            myHeroCurrentX = width / 2;
            myHeroCurrentY = height / 2;
            the2dMaze2dArrayTemp[myHeroCurrentY][myHeroCurrentX] = new Entrance();
            final var theFinder = new PathFinder(the2dMaze2dArrayTemp, myHeroCurrentX, myHeroCurrentY);
            // check whether the player can reach the
            if (!theFinder.isReachable(theExitX, theExitY)) {
                int pillarPlaced = 0;
                for (final Pillar thePillar : myPillars) {
                    final int thePillarX = theRandom.nextInt(width);
                    final int thePillarY = theRandom.nextInt(height);
                    boolean placed = false;
                    for (int i = 0; i < 5; i++) {
                        if (the2dMaze2dArrayTemp[thePillarY][thePillarX] instanceof Room && the2dMaze2dArrayTemp[thePillarY][thePillarX].myPillar == null && theFinder.isReachable(thePillarX, thePillarY)) {
                            thePillar.setPos(thePillarX, thePillarY);
                            the2dMaze2dArrayTemp[thePillarY][thePillarX].myPillar = thePillar;
                            placed = true;
                            break;
                        }
                    }
                    if (placed) {
                        pillarPlaced++;
                    } else {
                        break;
                    }
                }
                if (pillarPlaced == this.myPillars.length) {
                    break;
                }
            }
        }
        my2dMaze2dArray = the2dMaze2dArrayTemp;
    }

    public boolean move(final Direction theDirection) {
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

    private boolean moveTo(final int theX, final int theY) {
        // check the coordinate to ensure that the player can move
        // which means no out of bound
        if (this.canMoveTo(theX, theY)) {
            myHeroCurrentX = theX;
            myHeroCurrentY = theY;
            return true;
        }
        return false;
    }

    public boolean canMoveTo(final int theX, final int theY) {
        return 0 <= theY && theY < my2dMaze2dArray.length && 0 <= theX && theX < my2dMaze2dArray[theY].length && my2dMaze2dArray[theY][theX] != null;
    }

    @Override
    public String toString() {
        final StringBuilder theInfo = new StringBuilder();
        for (int y = 0; y < my2dMaze2dArray.length; y++) {
            theInfo.append("[");
            for (int x = 0; x < my2dMaze2dArray[y].length; x++) {
                if (y == myHeroCurrentY && x == myHeroCurrentX) {
                    theInfo.append("U");
                } else if (my2dMaze2dArray[y][x] != null) {
                    theInfo.append(my2dMaze2dArray[y][x].getFlag());
                } else {
                    theInfo.append("|");
                }
            }
            theInfo.append("]\n");
        }
        return theInfo.toString();
    }

    public String getSurroundingRooms() {
        final StringBuilder theInfo = new StringBuilder();
        for (int y = Integer.max(myHeroCurrentY - 1, 0); y < Integer.min(myHeroCurrentY + 2, my2dMaze2dArray.length); y++) {
            theInfo.append("[");
            for (int x = Integer.max(myHeroCurrentX - 1, 0); x < Integer.min(myHeroCurrentX + 2, my2dMaze2dArray[y].length); x++) {
                if (my2dMaze2dArray[y][x] != null) {
                    theInfo.append(my2dMaze2dArray[y][x].getFlag());
                } else {
                    theInfo.append("|");
                }
            }
            theInfo.append("]\n");
        }
        return theInfo.toString();
    }

    public Pillar[] getPillars() {
        return myPillars;
    }


    public int getCurrentX() {
        return myHeroCurrentX;
    }

    public int getCurrentY() {
        return myHeroCurrentY;
    }

    public AbstractRoom getCurrentRoom() {
        return my2dMaze2dArray[myHeroCurrentY][myHeroCurrentX];
    }

    public boolean isCurrentRoomPit() {
        return getCurrentRoom() instanceof Pit;
    }

    public boolean isCurrentRoomExit() {
        return getCurrentRoom() instanceof Exit;
    }

    public boolean areAllPillarsFound() {
        for (final Pillar thePillar : myPillars) {
            if (!thePillar.hasBeenFound()) {
                return false;
            }
        }
        return true;
    }

    public int getNumOfPillarsFound() {
        int num = 0;
        for (final Pillar thePillar : myPillars) {
            if (thePillar.hasBeenFound()) {
                num++;
            }
        }
        return num;
    }

    public void pickUpAllPillars() {
        for (final Pillar thePillar : myPillars) {
            thePillar.found();
            final var thePos = thePillar.getPos();
            my2dMaze2dArray[thePos[0]][thePos[1]].myPillar = null;
        }
    }
}
