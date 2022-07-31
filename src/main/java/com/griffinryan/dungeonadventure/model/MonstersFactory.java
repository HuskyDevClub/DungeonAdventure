package com.griffinryan.dungeonadventure.model;

import com.griffinryan.dungeonadventure.model.monsters.Gremlin;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;
import com.griffinryan.dungeonadventure.model.sql.MonsterSqliteInterface;

import java.security.InvalidParameterException;
import java.sql.SQLException;

/**
 * a Factory class that generate dungeon characters using default parameters loaded form a sqlite database
 */
public final class MonstersFactory {

    /**
     * create a new monster using default parameters loaded form a sqlite database
     *
     * @param theType which type of monster
     * @param theName the name of the monster
     * @return a Monster
     */
    public static Monster spawn(final String theType, final String theName) throws SQLException {
        final int[] defaultInfoArray;
        switch (theType) {
            case "Ogre" -> {
                defaultInfoArray = MonsterSqliteInterface.load(Ogre.class.getSimpleName());
                return new Ogre(
                        theName, defaultInfoArray[0], defaultInfoArray[1], defaultInfoArray[2], defaultInfoArray[3],
                        defaultInfoArray[4], defaultInfoArray[5], defaultInfoArray[6], defaultInfoArray[7]
                );
            }
            case "Gremlin" -> {
                defaultInfoArray = MonsterSqliteInterface.load(Gremlin.class.getSimpleName());
                return new Gremlin(
                        theName, defaultInfoArray[0], defaultInfoArray[1], defaultInfoArray[2], defaultInfoArray[3],
                        defaultInfoArray[4], defaultInfoArray[5], defaultInfoArray[6], defaultInfoArray[7]
                );
            }
            case "Skeleton" -> {
                defaultInfoArray = MonsterSqliteInterface.load(Skeleton.class.getSimpleName());
                return new Skeleton(
                        theName, defaultInfoArray[0], defaultInfoArray[1], defaultInfoArray[2], defaultInfoArray[3],
                        defaultInfoArray[4], defaultInfoArray[5], defaultInfoArray[6], defaultInfoArray[7]
                );
            }
            default ->
                    throw new InvalidParameterException(String.format("The monster does not have type '%s'", theType));
        }
    }
}
