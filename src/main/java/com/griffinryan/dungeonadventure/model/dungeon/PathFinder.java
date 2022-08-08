package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.rooms.AbstractRoom;

/**
 * PathFinder determines the
 * path of a given dungeon maze
 * and whether it is reachable or not.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 */
public final class PathFinder {
    private final AbstractRoom[][] myMaze;
    private final int myEntranceX;
    private final int myEntranceY;
    private boolean[][] myTravelMark;
    private int myDestinationX;
    private int myDestinationY;

    /**
     * @param theMaze      the maze you want to traversal
     * @param theEntranceX the X of the Entrance
     * @param theEntranceY the Y of the Entrance
     */
    public PathFinder(final AbstractRoom[][] theMaze, final int theEntranceX, final int theEntranceY) {
        this.myEntranceX = theEntranceX;
        this.myEntranceY = theEntranceY;
        this.myMaze = theMaze;
        if (this.myMaze.length == 0) {
            throw new IllegalArgumentException("The maze cannot be empty!");
        } else if (this.myMaze[0].length == 0) {
            throw new IllegalArgumentException("The maze cannot be empty!");
        } else if (isOutOfRange(this.myEntranceX, this.myEntranceY)) {
            throw new IndexOutOfBoundsException("The coordinates for the entrance is out of bound!");
        }
    }

    /**
     * @param theX a X coordinate
     * @param theY a Y coordinate
     * @return whether the give coordinates are out of bound (not reachable)
     */
    private boolean isOutOfRange(final int theX, final int theY) {
        return theY < 0 || theY >= this.myMaze.length || theX < 0 || theX >= this.myMaze[theY].length;
    }

    /**
     * @param theDestinationX the X of the Destination
     * @param theDestinationY the Y of the Destination
     * @return boolean whether there is a path to reach it
     */
    public boolean isReachable(final int theDestinationX, final int theDestinationY) {
        this.myTravelMark = new boolean[this.myMaze.length][this.myMaze[0].length];
        myDestinationX = theDestinationX;
        myDestinationY = theDestinationY;
        if (isOutOfRange(theDestinationX, theDestinationY)) {
            throw new IndexOutOfBoundsException("The coordinates for the destination is out of bound!");
        }
        return this.check(myEntranceX, myEntranceY);
    }

    /**
     * @param theX the X of the room that needs to be checked
     * @param theY the Y of the room that needs to be checked
     * @return boolean whether there is a valid path comes out of here
     */
    private boolean check(final int theX, final int theY) {
        if (isOutOfRange(theX, theY) || this.myTravelMark[theY][theX]) {
            return false;
        }
        this.myTravelMark[theY][theX] = true;
        if (this.myMaze[theY][theX] == null) {
            return false;
        } else if (theX == this.myDestinationX && theY == this.myDestinationY) {
            return true;
        }
        return check(theX - 1, theY) || check(theX + 1, theY) || check(theX, theY - 1) || check(theX, theY + 1);
    }
}
