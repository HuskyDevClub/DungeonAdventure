package com.griffinryan.dungeonadventure.model.dungeon;

/**
 * 
 * @author Yudong Lin (ydlin@uw.edu) 
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
class PathFinder {

    private final AbstractRoom[][] myMaze;
    private final int myEntranceX;
    private final int myEntranceY;
    private boolean[][] myTravelMark;
    private int myTargetX;
    private int myTargetY;

    /**
	 * 
	 */
    PathFinder(final AbstractRoom[][] theMaze, final int theEntranceX, final int theEntranceY) {
        this.myEntranceX = theEntranceX;
        this.myEntranceY = theEntranceY;
        this.myMaze = theMaze;
    }

    
    /** 
     * @param theTargetX
     * @param theTargetY
     * @return boolean
     */
    boolean isReachable(final int theTargetX, final int theTargetY) {
        this.myTravelMark = new boolean[this.myMaze.length][this.myMaze[0].length];
        myTargetX = theTargetX;
        myTargetY = theTargetY;
        return this.check(myEntranceX, myEntranceY);
    }

    
    /** 
     * @param theX
     * @param theY
     * @return boolean
     */
    private boolean check(final int theX, final int theY) {
        if (theY < 0 || theY >= this.myTravelMark.length || theX < 0 || theX >= this.myTravelMark[theY].length || this.myTravelMark[theY][theX]) {
            return false;
        }
        this.myTravelMark[theY][theX] = true;
        if (this.myMaze[theY][theX] == null) {
            return false;
        } else if (theX == this.myTargetX && theX == this.myTargetY) {
            return true;
        }
        return check(theX - 1, theY) || check(theX + 1, theY) || check(theX, theY - 1) || check(theX, theY + 1);
    }
}
