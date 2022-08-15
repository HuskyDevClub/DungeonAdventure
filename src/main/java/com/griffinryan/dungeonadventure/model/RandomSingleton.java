package com.griffinryan.dungeonadventure.model;

/**
 * @author Yudong Lin (ydlin@uw.edu)
 */
public final class RandomSingleton {

    private static final java.util.Random myRANDOM = new java.util.Random();

    /**
     * check to see whether one is lucky enough to perform an action
     *
     * @param theChance the chance, for 80%, your input should be 80, not 0.8
     * @return boolean
     */
    public static boolean isSuccessful(final int theChance) {
        // ensure not out of bound
        if (0 > theChance || theChance > 100) {
            throw new IndexOutOfBoundsException("Chance has to be 0>= and <= 100");
        }
        return myRANDOM.nextInt(100) < theChance;
    }


    /**
     * generate a random number between the min and max
     *
     * @param theMin the minimum (inclusive)
     * @param theMax the maximum (inclusive)
     * @return int
     */
    public static int nextInt(final int theMin, final int theMax) {
        if (theMin == theMax) {
            return theMin;
        }
        return myRANDOM.nextInt(theMin, theMax + 1);
    }

    /**
     * generate a random number between the 0 and max
     *
     * @param theMax the maximum (inclusive)
     * @return int
     */
    public static int nextInt(final int theMax) {
        return myRANDOM.nextInt(theMax + 1);
    }
}
