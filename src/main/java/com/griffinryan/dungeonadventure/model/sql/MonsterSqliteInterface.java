package com.griffinryan.dungeonadventure.model.sql;

import com.griffinryan.dungeonadventure.model.monsters.Gremlin;
import com.griffinryan.dungeonadventure.model.monsters.Monster;
import com.griffinryan.dungeonadventure.model.monsters.Ogre;
import com.griffinryan.dungeonadventure.model.monsters.Skeleton;

import java.sql.SQLException;

public final class MonsterSqliteInterface {

    private static final String TABLE_NAME = Monster.class.getSimpleName();

    /**
     * save default monsters information into the sqlite database
     */
    public static void main(final String[] args) throws SQLException {
        save(new Gremlin("default", 70, 15, 30, 5, 80, 40, 20, 40));
        save(new Ogre("default", 200, 30, 60, 2, 60, 10, 30, 60));
        save(new Skeleton("default", 100, 30, 50, 3, 80, 30, 30, 50));
    }

    /**
     * save a monster into the sqlite database
     *
     * @param theMonster the name to save into the sqlite database
     */
    public static void save(final Monster theMonster) throws SQLException {
        DungeonCharacterSqliteInterface.save(theMonster, TABLE_NAME);
    }

    /**
     * load a monster from the sqlite database
     *
     * @param theMonsterType the type of the monster to load from the sqlite database
     * @return the information regarding this monster
     */
    public static int[] load(final String theMonsterType) throws SQLException {
        return DungeonCharacterSqliteInterface.load(theMonsterType, TABLE_NAME);
    }

}
