package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.monsters.Gremlin;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;
import com.griffinryan.dungeonadventure.model.rooms.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Dungeon class instantiates
 * the AbstractRoom and Pillar objects
 * and determines the layout of the
 * dungeon's maze.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 */
public class Dungeon {
    private static final int myChanceToGenerateRoom = 85;
    private final AbstractRoom[][] my2dMaze2dArray;
    private final Pillar[] myPillars = {new Pillar("Abstract"), new Pillar("Encapsulation"), new Pillar("Inheritance"), new Pillar("Polymorphism")};
    private int myHeroCurrentX;
    private int myHeroCurrentY;

    /**
     * @param width  the height of the Dungeon
     * @param height the height of the Dungeon
     */
    public Dungeon(final int width, final int height) throws IllegalAccessException {
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
            final PathFinder theFinder = new PathFinder(the2dMaze2dArrayTemp, myHeroCurrentX, myHeroCurrentY);
            // check whether the player can reach the
            if (!theFinder.isReachable(theExitX, theExitY)) {
                int pillarPlaced = 0;
                for (final Pillar thePillar : myPillars) {
                    final int thePillarX = theRandom.nextInt(width);
                    final int thePillarY = theRandom.nextInt(height);
                    boolean placed = false;
                    for (int i = 0; i < 5; i++) {
                        if (the2dMaze2dArrayTemp[thePillarY][thePillarX] instanceof Room && !the2dMaze2dArrayTemp[thePillarY][thePillarX].hasPillar() && theFinder.isReachable(thePillarX, thePillarY)) {
                            thePillar.setPos(thePillarX, thePillarY);
                            the2dMaze2dArrayTemp[thePillarY][thePillarX].newPillar(thePillar);
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

    /**
     * @param theDirection the direction you want to move
     * @return whether you have moved or not
     */
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

    /**
     * @param theX the X of the room that you want to move to
     * @param theY the Y of the room that you want to move to
     * @return whether you have moved or not
     */
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

    /**
     * @param theX the X of the room that you want to move to
     * @param theY the Y of the room that you want to move to
     * @return boolean whether you can move to that room or not
     */
    public boolean canMoveTo(final int theX, final int theY) {
        return 0 <= theY && theY < my2dMaze2dArray.length && 0 <= theX && theX < my2dMaze2dArray[theY].length && my2dMaze2dArray[theY][theX] != null;
    }

    /**
     * @return String
     */
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


    /**
     * @return the information regarding surrounding rooms
     */
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

    /**
     * @return get the Pillars (do not call this method unless you know what you are doing!!)
     */
    public Pillar[] getPillars() {
        return myPillars;
    }

    /**
     * @return the current X of the player
     */
    public int getCurrentX() {
        return myHeroCurrentX;
    }

    /**
     * @return the current Y of the player
     */
    public int getCurrentY() {
        return myHeroCurrentY;
    }

    /**
     * @return the current room that the player is in
     */
    public AbstractRoom getCurrentRoom() {
        return my2dMaze2dArray[myHeroCurrentY][myHeroCurrentX];
    }

    /**
     * @return whether current room is a Pit
     */
    public boolean isCurrentRoomPit() {
        return getCurrentRoom() instanceof Pit;
    }

    /**
     * @return whether current room is an Exit
     */
    public boolean isCurrentRoomExit() {
        return getCurrentRoom() instanceof Exit;
    }

    /**
     * @return whether all pillars have been found
     */
    public boolean areAllPillarsFound() {
        for (final Pillar thePillar : myPillars) {
            if (!thePillar.hasBeenFound()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the number of pillars that have been found
     */
    public int getNumOfPillarsFound() {
        int num = 0;
        for (final Pillar thePillar : myPillars) {
            if (thePillar.hasBeenFound()) {
                num++;
            }
        }
        return num;
    }

    /**
     * pick up all pillars immediately (do not call this method unless you know what you are doing!!)
     */
    public void pickUpAllPillars() {
        for (final Pillar thePillar : myPillars) {
            thePillar.found();
            final int[] thePos = thePillar.getPos();
            my2dMaze2dArray[thePos[0]][thePos[1]].pickUpPillar();
        }
    }
}
