package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.MonstersFactory;
import com.griffinryan.dungeonadventure.model.RandomSingleton;
import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.rooms.*;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The Dungeon class instantiates
 * the AbstractRoom and Pillar objects
 * and determines the layout of the
 * dungeon's maze.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 * @see Room
 */
public class Dungeon implements Serializable {
    // the minimum amount of monster(s) that will exist in a room
    private static final int MIN_MONSTER_NUM = 0;
    // the maximum amount of monster(s) that will exist in a room
    private static final int MAX_MONSTER_NUM = 6;
    // the chance that it will generate a room instead of a wall
    private static final int CHANCE_TO_GENERATE_ROOM = 80;
    // if a room needs to be generated, chance that it is a normal room instead of a pit
    private static final int CHANCE_TO_BE_ROOM = 80;

    private final AbstractRoom[][] my2dMaze2dArray;
    private final Pillar[] myPillars = {new Pillar(Pillar.Abstraction), new Pillar(Pillar.Encapsulation), new Pillar(Pillar.Inheritance), new Pillar(Pillar.Polymorphism)};
    private final Hero myHero;
    private int myHeroCurrentX;
    private int myHeroCurrentY;

    /**
     * @param theWidth  the height of the Dungeon
     * @param theHeight the height of the Dungeon
     */
    public Dungeon(final int theWidth, final int theHeight, final Hero theHero) throws IllegalAccessException, SQLException {
        AbstractRoom[][] the2dMaze2dArrayTemp;
        myHero = theHero;
        while (true) {
            the2dMaze2dArrayTemp = new AbstractRoom[theHeight][theWidth];
            for (int y = 0; y < the2dMaze2dArrayTemp.length; y++) {
                for (int x = 0; x < the2dMaze2dArrayTemp[y].length; x++) {
                    if (RandomSingleton.isSuccessful(CHANCE_TO_GENERATE_ROOM)) {
                        if (RandomSingleton.isSuccessful(CHANCE_TO_BE_ROOM)) {
                            final int theNumOfMonsters = RandomSingleton.nextInt(MIN_MONSTER_NUM, MAX_MONSTER_NUM);
                            final ArrayList<Monster> theMonsters = new ArrayList<>(theNumOfMonsters);
                            for (int i = 0; i < theNumOfMonsters; i++) {
                                switch (RandomSingleton.nextInt(0, 2)) {
                                    case 0 -> theMonsters.add(MonstersFactory.spawn("Gremlin", "G1"));
                                    case 1 -> theMonsters.add(MonstersFactory.spawn("Ogre", "G1"));
                                    default -> theMonsters.add(MonstersFactory.spawn("Skeleton", "S1"));
                                }
                            }
                            the2dMaze2dArrayTemp[y][x] = new Room(theMonsters, RandomSingleton.nextInt(0, 3), RandomSingleton.nextInt(0, 2));
                        } else {
                            the2dMaze2dArrayTemp[y][x] = new Pit();
                        }
                    }
                }
            }
            final int theExitX = RandomSingleton.nextInt(theWidth);
            final int theExitY = RandomSingleton.nextInt(theHeight);
            the2dMaze2dArrayTemp[theExitY][theExitX] = new Exit();
            myHeroCurrentX = theWidth / 2;
            myHeroCurrentY = theHeight / 2;
            the2dMaze2dArrayTemp[myHeroCurrentY][myHeroCurrentX] = new Entrance();
            final PathFinder theFinder = new PathFinder(the2dMaze2dArrayTemp, myHeroCurrentX, myHeroCurrentY);
            // check whether the player can reach the
            if (theFinder.isReachable(theExitX, theExitY)) {
                int pillarPlaced = 0;
                for (final Pillar thePillar : myPillars) {
                    boolean placed = false;
                    // the system will try to place the Pillar several times before regenerating a new Dungeon
                    for (int i = 0; i < (theWidth + theHeight) / 2; i++) {
                        final int thePillarX = RandomSingleton.nextInt(theWidth);
                        final int thePillarY = RandomSingleton.nextInt(theHeight);
                        if (the2dMaze2dArrayTemp[thePillarY][thePillarX] instanceof Room && !the2dMaze2dArrayTemp[thePillarY][thePillarX].hasPillar() && theFinder.isReachable(thePillarX, thePillarY)) {
                            thePillar.setPos(thePillarX, thePillarY);
                            the2dMaze2dArrayTemp[thePillarY][thePillarX].placePillar(thePillar);
                            placed = true;
                            break;
                        }
                    }
                    if (!placed) {
                        break;
                    }
                    pillarPlaced++;
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
     * @return whether current room is a Pit
     */
    public boolean isCurrentRoomPit() {
        return getCurrentRoom() instanceof Pit;
    }

    /**
     * @return the current room that the player is in
     */
    public AbstractRoom getCurrentRoom() {
        return my2dMaze2dArray[myHeroCurrentY][myHeroCurrentX];
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

    public Hero getHero() {
        return myHero;
    }
}
