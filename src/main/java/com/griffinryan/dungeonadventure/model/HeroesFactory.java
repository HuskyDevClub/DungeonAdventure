package com.griffinryan.dungeonadventure.model;

import com.griffinryan.dungeonadventure.menu.HeroType;
import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.heroes.Priestess;
import com.griffinryan.dungeonadventure.model.heroes.Thief;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.sql.HeroSqliteInterface;

import java.sql.SQLException;

public class HeroesFactory {

    /*
     * try to cache all the data before the game loading phase
     */
    private static final int[] PRIESTESS_DEFAULT_INFO_ARRAY;
    private static final int[] THIEF_DEFAULT_INFO_ARRAY;
    private static final int[] WARRIOR_DEFAULT_INFO_ARRAY;

    static {
        try {
            PRIESTESS_DEFAULT_INFO_ARRAY = HeroSqliteInterface.load(Priestess.class.getSimpleName());
            THIEF_DEFAULT_INFO_ARRAY = HeroSqliteInterface.load(Thief.class.getSimpleName());
            WARRIOR_DEFAULT_INFO_ARRAY = HeroSqliteInterface.load(Warrior.class.getSimpleName());
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * create a new hero using default parameters loaded form a sqlite database
     *
     * @param theType which type of hero
     * @param theName the name of the hero
     * @return a Monster
     */
    public static Hero spawn(final String theType, final String theName) {
        switch (theType.toLowerCase()) {
            case "priestess" -> {
                return new Priestess(
                    theName, PRIESTESS_DEFAULT_INFO_ARRAY[0], PRIESTESS_DEFAULT_INFO_ARRAY[1], PRIESTESS_DEFAULT_INFO_ARRAY[2],
                    PRIESTESS_DEFAULT_INFO_ARRAY[3], PRIESTESS_DEFAULT_INFO_ARRAY[4], PRIESTESS_DEFAULT_INFO_ARRAY[5],
                    PRIESTESS_DEFAULT_INFO_ARRAY[6], PRIESTESS_DEFAULT_INFO_ARRAY[7], PRIESTESS_DEFAULT_INFO_ARRAY[8]
                );
            }
            case "thief" -> {
                return new Thief(
                    theName, THIEF_DEFAULT_INFO_ARRAY[0], THIEF_DEFAULT_INFO_ARRAY[1], THIEF_DEFAULT_INFO_ARRAY[2],
                    THIEF_DEFAULT_INFO_ARRAY[3], THIEF_DEFAULT_INFO_ARRAY[4], THIEF_DEFAULT_INFO_ARRAY[5],
                    THIEF_DEFAULT_INFO_ARRAY[6], THIEF_DEFAULT_INFO_ARRAY[7], THIEF_DEFAULT_INFO_ARRAY[8]
                );
            }
            case "warrior" -> {
                return new Warrior(
                    theName, WARRIOR_DEFAULT_INFO_ARRAY[0], WARRIOR_DEFAULT_INFO_ARRAY[1], WARRIOR_DEFAULT_INFO_ARRAY[2],
                    WARRIOR_DEFAULT_INFO_ARRAY[3], WARRIOR_DEFAULT_INFO_ARRAY[4], WARRIOR_DEFAULT_INFO_ARRAY[5],
                    WARRIOR_DEFAULT_INFO_ARRAY[6], WARRIOR_DEFAULT_INFO_ARRAY[7], WARRIOR_DEFAULT_INFO_ARRAY[8]
                );
            }
            default -> throw new RuntimeException(String.format("The hero does not have type '%s'", theType));
        }
    }

    /**
     * create a new hero using default parameters loaded form a sqlite database
     *
     * @param theType which type of hero
     * @param theName the name of the hero
     * @return a Monster
     */
    public static Hero spawn(final HeroType theType, final String theName) {
        switch (theType) {
            case PRIEST -> {
                return new Priestess(
                    theName, PRIESTESS_DEFAULT_INFO_ARRAY[0], PRIESTESS_DEFAULT_INFO_ARRAY[1], PRIESTESS_DEFAULT_INFO_ARRAY[2],
                    PRIESTESS_DEFAULT_INFO_ARRAY[3], PRIESTESS_DEFAULT_INFO_ARRAY[4], PRIESTESS_DEFAULT_INFO_ARRAY[5],
                    PRIESTESS_DEFAULT_INFO_ARRAY[6], PRIESTESS_DEFAULT_INFO_ARRAY[7], PRIESTESS_DEFAULT_INFO_ARRAY[8]
                );
            }
            case THIEF -> {
                return new Thief(
                    theName, THIEF_DEFAULT_INFO_ARRAY[0], THIEF_DEFAULT_INFO_ARRAY[1], THIEF_DEFAULT_INFO_ARRAY[2],
                    THIEF_DEFAULT_INFO_ARRAY[3], THIEF_DEFAULT_INFO_ARRAY[4], THIEF_DEFAULT_INFO_ARRAY[5],
                    THIEF_DEFAULT_INFO_ARRAY[6], THIEF_DEFAULT_INFO_ARRAY[7], THIEF_DEFAULT_INFO_ARRAY[8]
                );
            }
            case WARRIOR -> {
                return new Warrior(
                    theName, WARRIOR_DEFAULT_INFO_ARRAY[0], WARRIOR_DEFAULT_INFO_ARRAY[1], WARRIOR_DEFAULT_INFO_ARRAY[2],
                    WARRIOR_DEFAULT_INFO_ARRAY[3], WARRIOR_DEFAULT_INFO_ARRAY[4], WARRIOR_DEFAULT_INFO_ARRAY[5],
                    WARRIOR_DEFAULT_INFO_ARRAY[6], WARRIOR_DEFAULT_INFO_ARRAY[7], WARRIOR_DEFAULT_INFO_ARRAY[8]
                );
            }
            default -> throw new RuntimeException(String.format("The hero does not have type '%s'", theType));
        }
    }
}
