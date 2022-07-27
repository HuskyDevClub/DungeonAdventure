package com.griffinryan.dungeonadventure.model;

/**
 * 
 * @author Yudong Lin (ydlin@uw.edu) 
 * @author Griffin Ryan (glryan@uw.edu)
 * @author Elijah Amian (elijah25@uw.edu)
 */
abstract class Random {

    private static final java.util.Random rand = new java.util.Random();
    
    /** 
     * @param theChance
     * @return boolean
     */
    protected static boolean isLuckyToAct(final byte theChance) {
        if (0 > theChance || theChance > 100) {
            throw new IndexOutOfBoundsException("Chance has to be 0>= and <= 100");
        }
        return rand.nextInt(100) < theChance;
    }

    
    /** 
     * @param _min
     * @param _max
     * @return int
     */
    protected static int generateRandomValue(final int _min, final int _max) {
        return rand.nextInt(_min, _max);
    }
}
