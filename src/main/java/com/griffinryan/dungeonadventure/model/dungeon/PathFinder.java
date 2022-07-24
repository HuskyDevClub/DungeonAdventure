package com.griffinryan.dungeonadventure.model.dungeon;

class PathFinder {

    final private AbstractRoom[][] myMaze;
    final private int myEntranceX;
    final private int myEntranceY;
    private boolean[][] myTravelMark;
    private int myTargetX;
    private int myTargetY;

    PathFinder(AbstractRoom[][] theMaze, int theEntranceX, int theEntranceY) {
        this.myEntranceX = theEntranceX;
        this.myEntranceY = theEntranceY;
        this.myMaze = theMaze;
    }

    boolean isReachable(int theTargetX, int theTargetY) {
        this.myTravelMark = new boolean[this.myMaze.length][this.myMaze[0].length];
        myTargetX = theTargetX;
        myTargetY = theTargetY;
        return this.check(myEntranceX, myEntranceY);
    }

    private boolean check(int theX, int theY) {
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
