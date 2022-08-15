package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.MonstersFactory;
import com.griffinryan.dungeonadventure.model.RandomSingleton;
import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.rooms.*;

import java.io.Serializable;
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

    private final AbstractRoom[][] myMazeArray;
    private final int myMazeWidth;
    private final int myMazeHeight;
    private final Pillar[] myPillars = {new Pillar(Pillar.Abstraction), new Pillar(Pillar.Encapsulation), new Pillar(Pillar.Inheritance), new Pillar(Pillar.Polymorphism)};
    private final Hero myHero;
    private final int myExitX;
    private final int myExitY;
    private int myHeroCurrentX;
    private int myHeroCurrentY;

    /**
     * construct a Dungeon with default entrance point
     *
     * @param theHero   the hero of the Dungeon
     * @param theWidth  the width of the Dungeon
     * @param theHeight the height of the Dungeon
     */
    public Dungeon(final Hero theHero, final int theWidth, final int theHeight) {
        this(theHero, theWidth, theHeight, theWidth / 2, theHeight / 2);
    }

    /**
     * @param theHero   the hero of the Dungeon
     * @param theWidth  the width of the Dungeon
     * @param theHeight the height of the Dungeon
     * @param theHeroX  the x coordinate of the hero
     * @param theHeroY  the y coordinate of the hero
     */
    public Dungeon(final Hero theHero, final int theWidth, final int theHeight, final int theHeroX, final int theHeroY) {
        if (theWidth < 3 || theHeight < 3) {
            throw new IllegalArgumentException("The size of the Dungeon cannot be less than 3!");
        }
        AbstractRoom[][] the2dMaze2dArrayTemp;
        myHero = theHero;
        myMazeWidth = theWidth;
        myMazeHeight = theHeight;
        if (0 > theHeroY || theHeroY >= theHeight || 0 > theHeroX || theHeroX >= theWidth) {
            throw new IllegalArgumentException("Invalid Hero coordinate as it is out of range!");
        }
        myHeroCurrentX = theHeroX;
        myHeroCurrentY = theHeroY;
        int theExitX;
        int theExitY;
        while (true) {
            the2dMaze2dArrayTemp = new AbstractRoom[myMazeHeight][myMazeWidth];
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
            theExitX = RandomSingleton.nextInt(theWidth - 1);
            theExitY = RandomSingleton.nextInt(theHeight - 1);
            // set Exit and Entrance
            the2dMaze2dArrayTemp[theExitY][theExitX] = new Exit();
            the2dMaze2dArrayTemp[myHeroCurrentY][myHeroCurrentX] = new Entrance();
            final PathFinder theFinder = new PathFinder(the2dMaze2dArrayTemp, myHeroCurrentX, myHeroCurrentY);
            // check whether the player can reach the
            if (theFinder.isReachable(theExitX, theExitY)) {
                int pillarPlaced = 0;
                for (final Pillar thePillar : myPillars) {
                    boolean placed = false;
                    // the system will try to place the Pillar several times before regenerating a new Dungeon
                    for (int i = 0; i < (theWidth + theHeight) / 2; i++) {
                        final int thePillarX = RandomSingleton.nextInt(theWidth - 1);
                        final int thePillarY = RandomSingleton.nextInt(theHeight - 1);
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
        myExitX = theExitX;
        myExitY = theExitY;
        myMazeArray = the2dMaze2dArrayTemp;
    }

    /**
     * @param theDirection the direction you want to move
     * @return whether you have moved or not
     */
    public boolean moveHero(final Direction theDirection) {
        switch (theDirection) {
            case UP -> {
                return moveHeroTo(myHeroCurrentX, myHeroCurrentY - 1);
            }
            case DOWN -> {
                return moveHeroTo(myHeroCurrentX, myHeroCurrentY + 1);
            }
            case LEFT -> {
                return moveHeroTo(myHeroCurrentX - 1, myHeroCurrentY);
            }
            case RIGHT -> {
                return moveHeroTo(myHeroCurrentX + 1, myHeroCurrentY);
            }
        }
        return false;
    }

    /**
     * @param theX the X of the room that you want to move to
     * @param theY the Y of the room that you want to move to
     * @return whether you have moved or not
     */
    public boolean moveHeroTo(final int theX, final int theY) {
        // check the coordinate to ensure that the player can move
        // which means no out of bound
        if (this.canHeroMoveTo(theX, theY)) {
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
    public boolean canHeroMoveTo(final int theX, final int theY) {
        return 0 <= theY && theY < myMazeArray.length && 0 <= theX && theX < myMazeArray[theY].length && myMazeArray[theY][theX] != null;
    }

    /**
     * whether you can move to a direction
     *
     * @param theDirection the direction towards
     * @return whether you can or not
     */
    public boolean canHeroMove(Direction theDirection) {
        switch (theDirection) {
            case UP -> {
                return canHeroMoveTo(myHeroCurrentX, myHeroCurrentY - 1);
            }
            case DOWN -> {
                return canHeroMoveTo(myHeroCurrentX, myHeroCurrentY + 1);
            }
            case LEFT -> {
                return canHeroMoveTo(myHeroCurrentX - 1, myHeroCurrentY);
            }
            case RIGHT -> {
                return canHeroMoveTo(myHeroCurrentX + 1, myHeroCurrentY);
            }
        }
        return false;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder theInfo = new StringBuilder();
        for (int y = 0; y < myMazeArray.length; y++) {
            theInfo.append('[');
            for (int x = 0; x < myMazeArray[y].length; x++) {
                if (y == myHeroCurrentY && x == myHeroCurrentX) {
                    theInfo.append('U');
                } else if (myMazeArray[y][x] != null) {
                    theInfo.append(myMazeArray[y][x].getFlag());
                } else {
                    theInfo.append(' ');
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
        for (int y = Integer.max(myHeroCurrentY - 1, 0); y < Integer.min(myHeroCurrentY + 2, myMazeArray.length); y++) {
            theInfo.append("[");
            for (int x = Integer.max(myHeroCurrentX - 1, 0); x < Integer.min(myHeroCurrentX + 2, myMazeArray[y].length); x++) {
                if (myMazeArray[y][x] != null) {
                    theInfo.append(myMazeArray[y][x].getFlag());
                } else {
                    theInfo.append("|");
                }
            }
            theInfo.append("]\n");
        }
        return theInfo.toString();
    }

    /**
     * @return get the names of Pillars that has been found
     */
    public ArrayList<String> getPillarsFound() {
        final ArrayList<String> thePillarsFound = new ArrayList<>(4);
        for (final Pillar thePillar : myPillars) {
            if (thePillar.hasBeenFound()) {
                thePillarsFound.add(thePillar.toString());
            }
        }
        return thePillarsFound;
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
        return myMazeArray[myHeroCurrentY][myHeroCurrentX];
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
     * pick up all pillars immediately (do not call this method unless you know what you are doing!!)
     */
    public void pickUpAllPillars() {
        for (final Pillar thePillar : myPillars) {
            if (!thePillar.hasBeenFound()) {
                thePillar.found();
                final int[] thePos = thePillar.getPos();
                myMazeArray[thePos[1]][thePos[0]].pickUpPillar();
            }
        }
    }

    /**
     * @return the hero
     */
    public Hero getHero() {
        return myHero;
    }

    /**
     * @return the maze width
     */
    public int getMazeWidth() {
        return myMazeWidth;
    }

    /**
     * @return the maze height
     */
    public int getMazeHeight() {
        return myMazeHeight;
    }

    /**
     * @return the exit x
     */
    public int getExitX() {
        return myExitX;
    }

    /**
     * @return the exit y
     */
    public int getExitY() {
        return myExitY;
    }
}
