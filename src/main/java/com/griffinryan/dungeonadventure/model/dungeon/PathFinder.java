package com.griffinryan.dungeonadventure.model.dungeon;

class PathFinder {

    final private AbstractRoom[][] myMaze;
    final private boolean[][] myTravelMark;

    PathFinder(AbstractRoom[][] theMaze) {
        this.myMaze = theMaze;
        this.myTravelMark = new boolean[this.myMaze.length][this.myMaze[0].length];
    }

    boolean isReachable(int theEntranceX, int theEntranceY) {
        System.out.println("checking");
        return this.check(theEntranceX, theEntranceY);
    }

    private boolean check(int theX, int theY) {
        if (theY < 0 || theY >= this.myTravelMark.length || theX < 0 || theX >= this.myTravelMark[theY].length || this.myTravelMark[theY][theX]) {
            return false;
        }
        this.myTravelMark[theY][theX] = true;
        if (this.myMaze[theY][theX] == null) {
            return false;
        } else if (this.myMaze[theY][theX] instanceof Exit) {
            return true;
        }
        return check(theX - 1, theY) || check(theX + 1, theY) || check(theX, theY - 1) || check(theX, theY + 1);
    }
}
