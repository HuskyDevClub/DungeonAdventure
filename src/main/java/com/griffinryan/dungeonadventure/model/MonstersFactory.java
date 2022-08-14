package com.griffinryan.dungeonadventure.model;

import com.griffinryan.dungeonadventure.model.monsters.Gremlin;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;
import com.griffinryan.dungeonadventure.model.sql.MonsterSqliteInterface;

import java.sql.SQLException;

/**
 * a Factory class that generate dungeon characters using default parameters loaded form a sqlite database
 */
public final class MonstersFactory {

    /*
     * try to cache all the data before the game loading phase
     */

    static final int[] OGRE_DEFAULT_INFO_ARRAY;
    static final int[] GREMLIN_DEFAULT_INFO_ARRAY;
    static final int[] SKELETON_DEFAULT_INFO_ARRAY;

    static {
        try {
            OGRE_DEFAULT_INFO_ARRAY = MonsterSqliteInterface.load(Ogre.class.getSimpleName());
            GREMLIN_DEFAULT_INFO_ARRAY = MonsterSqliteInterface.load(Gremlin.class.getSimpleName());
            SKELETON_DEFAULT_INFO_ARRAY = MonsterSqliteInterface.load(Skeleton.class.getSimpleName());
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * create a new monster using default parameters loaded form a sqlite database
     *
     * @param theType which type of monster
     * @param theName the name of the monster
     * @return a Monster
     */
    public static Monster spawn(final String theType, final String theName) throws SQLException {
        switch (theType.toLowerCase()) {
            case "ogre" -> {
                return new Ogre(
                    theName, OGRE_DEFAULT_INFO_ARRAY[0], OGRE_DEFAULT_INFO_ARRAY[1],
                    OGRE_DEFAULT_INFO_ARRAY[2], OGRE_DEFAULT_INFO_ARRAY[3], OGRE_DEFAULT_INFO_ARRAY[4],
                    OGRE_DEFAULT_INFO_ARRAY[5], OGRE_DEFAULT_INFO_ARRAY[6], OGRE_DEFAULT_INFO_ARRAY[7]
                );
            }
            case "gremlin" -> {
                return new Gremlin(
                    theName, GREMLIN_DEFAULT_INFO_ARRAY[0], GREMLIN_DEFAULT_INFO_ARRAY[1],
                    GREMLIN_DEFAULT_INFO_ARRAY[2], GREMLIN_DEFAULT_INFO_ARRAY[3], GREMLIN_DEFAULT_INFO_ARRAY[4],
                    GREMLIN_DEFAULT_INFO_ARRAY[5], GREMLIN_DEFAULT_INFO_ARRAY[6], GREMLIN_DEFAULT_INFO_ARRAY[7]
                );
            }
            case "skeleton" -> {
                return new Skeleton(
                    theName, SKELETON_DEFAULT_INFO_ARRAY[0], SKELETON_DEFAULT_INFO_ARRAY[1],
                    SKELETON_DEFAULT_INFO_ARRAY[2], SKELETON_DEFAULT_INFO_ARRAY[3], SKELETON_DEFAULT_INFO_ARRAY[4],
                    SKELETON_DEFAULT_INFO_ARRAY[5], SKELETON_DEFAULT_INFO_ARRAY[6], SKELETON_DEFAULT_INFO_ARRAY[7]
                );
            }
            default -> throw new SQLException(String.format("The monster does not have type '%s'", theType));
        }
    }
}
