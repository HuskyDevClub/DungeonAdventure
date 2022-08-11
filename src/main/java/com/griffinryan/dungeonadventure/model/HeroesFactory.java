package com.griffinryan.dungeonadventure.model;

import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.heroes.Priestess;
import com.griffinryan.dungeonadventure.model.heroes.Thief;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.sql.HeroSqliteInterface;

import java.sql.SQLException;

public class HeroesFactory {

    /**
     * create a new hero using default parameters loaded form a sqlite database
     *
     * @param theType which type of hero
     * @param theName the name of the hero
     * @return a Monster
     */
    public static Hero spawn(final String theType, final String theName) throws SQLException {
        final int[] defaultInfoArray;
        switch (theType.toLowerCase()) {
            case "priestess" -> {
                defaultInfoArray = HeroSqliteInterface.load(Priestess.class.getSimpleName());
                return new Priestess(
                        theName, defaultInfoArray[0], defaultInfoArray[1], defaultInfoArray[2], defaultInfoArray[3],
                        defaultInfoArray[4], defaultInfoArray[5], defaultInfoArray[6], defaultInfoArray[7], defaultInfoArray[8]
                );
            }
            case "thief" -> {
                defaultInfoArray = HeroSqliteInterface.load(Thief.class.getSimpleName());
                return new Thief(
                        theName, defaultInfoArray[0], defaultInfoArray[1], defaultInfoArray[2], defaultInfoArray[3],
                        defaultInfoArray[4], defaultInfoArray[5], defaultInfoArray[6], defaultInfoArray[7], defaultInfoArray[8]
                );
            }
            case "warrior" -> {
                defaultInfoArray = HeroSqliteInterface.load(Warrior.class.getSimpleName());
                return new Warrior(
                        theName, defaultInfoArray[0], defaultInfoArray[1], defaultInfoArray[2], defaultInfoArray[3],
                        defaultInfoArray[4], defaultInfoArray[5], defaultInfoArray[6], defaultInfoArray[7], defaultInfoArray[8]
                );
            }
            default -> throw new SQLException(String.format("The hero does not have type '%s'", theType));
        }
    }
}
