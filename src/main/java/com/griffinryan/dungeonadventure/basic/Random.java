package com.griffinryan.dungeonadventure.basic;

abstract class Random {

    private static final java.util.Random rand = new java.util.Random();

    protected static boolean isLuckyToAct(byte theChance) {
        if (0 > theChance || theChance > 100) {
            throw new IndexOutOfBoundsException("Chance has to be 0>= and <= 100");
        }
        return rand.nextInt(100) < theChance;
    }

    protected static int generateRandomValue(int _min, int _max) {
        return rand.nextInt(_min, _max);
    }
}
