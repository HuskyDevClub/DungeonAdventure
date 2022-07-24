package com.griffinryan.dungeonadventure.model;

abstract class Random {

    private static final java.util.Random rand = new java.util.Random();

    protected static boolean isLuckyToAct(final byte theChance) {
        if (0 > theChance || theChance > 100) {
            throw new IndexOutOfBoundsException("Chance has to be 0>= and <= 100");
        }
        return rand.nextInt(100) < theChance;
    }

    protected static int generateRandomValue(final int _min, final int _max) {
        return rand.nextInt(_min, _max);
    }
}
