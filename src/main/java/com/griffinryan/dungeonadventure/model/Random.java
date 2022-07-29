package com.griffinryan.dungeonadventure.model;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 */
abstract class Random {

    private static final java.util.Random myRANDOM = new java.util.Random();

    /**
     * check to see whether one is lucky enough to perform an action
     *
     * @param theChance the chance, for 80%, your input should be 80, not 0.8
     * @return boolean
     */
    protected static boolean isLuckyToAct(final int theChance) {
        // ensure not out of bound
        if (0 > theChance || theChance > 100) {
            throw new IndexOutOfBoundsException("Chance has to be 0>= and <= 100");
        }
        return myRANDOM.nextInt(100) < theChance;
    }


    /**
     * generate a random number between the min and max
     *
     * @param theMin the minimum
     * @param theMax the maximum
     * @return int
     */
    protected static int generateRandomValue(final int theMin, final int theMax) {
        return myRANDOM.nextInt(theMin, theMax);
    }
}
