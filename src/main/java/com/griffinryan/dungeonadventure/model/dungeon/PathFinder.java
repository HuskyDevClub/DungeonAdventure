package com.griffinryan.dungeonadventure.model.dungeon;

import com.griffinryan.dungeonadventure.model.rooms.AbstractRoom;

/**
 * PathFinder determines the
 * path of a given dungeon maze
 * and whether it is reachable or not.
 *
 * @author Yudong Lin (ydlin@uw.edu)
 */
class PathFinder {
    private final AbstractRoom[][] myMaze;
    private final int myEntranceX;
    private final int myEntranceY;
    private boolean[][] myTravelMark;
    private int myTargetX;
    private int myTargetY;

    /**
     * @param theMaze      the maze you want to traversal
     * @param theEntranceX the X of the Entrance
     * @param theEntranceY the Y of the Entrance
     */
    PathFinder(final AbstractRoom[][] theMaze, final int theEntranceX, final int theEntranceY) {
        this.myEntranceX = theEntranceX;
        this.myEntranceY = theEntranceY;
        this.myMaze = theMaze;
    }

    /**
     * @param theTargetX the X of the Destination
     * @param theTargetY the Y of the Destination
     * @return boolean whether there is a path to reach it
     */
    boolean isReachable(final int theTargetX, final int theTargetY) {
        this.myTravelMark = new boolean[this.myMaze.length][this.myMaze[0].length];
        myTargetX = theTargetX;
        myTargetY = theTargetY;
        return this.check(myEntranceX, myEntranceY);
    }

    /**
     * @param theX the X of the room that needs to be checked
     * @param theY the Y of the room that needs to be checked
     * @return boolean whether there is a valid path comes out of here
     */
    private boolean check(final int theX, final int theY) {
        if (theY < 0 || theY >= this.myTravelMark.length || theX < 0 || theX >= this.myTravelMark[theY].length || this.myTravelMark[theY][theX]) {
            return false;
        }
        this.myTravelMark[theY][theX] = true;
        if (this.myMaze[theY][theX] == null) {
            return false;
        } else if (theX == this.myTargetX && theY == this.myTargetY) {
            return true;
        }
        return check(theX - 1, theY) || check(theX + 1, theY) || check(theX, theY - 1) || check(theX, theY + 1);
    }
}
