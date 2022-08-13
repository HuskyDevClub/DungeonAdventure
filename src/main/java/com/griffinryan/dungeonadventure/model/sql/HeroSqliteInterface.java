package com.griffinryan.dungeonadventure.model.sql;

import com.griffinryan.dungeonadventure.model.heroes.Hero;
import com.griffinryan.dungeonadventure.model.heroes.Priestess;
import com.griffinryan.dungeonadventure.model.heroes.Thief;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;

import java.sql.SQLException;

public class HeroSqliteInterface {

    private static final String TABLE_NAME = Hero.class.getSimpleName();

    /**
     * save default heroes information into the sqlite database
     */
    public static void main(final String[] args) throws SQLException {
        save(new Priestess("default", 75, 25, 45, 5, 70, 100, 10, 20, 30));
        save(new Thief("default", 75, 20, 40, 6, 80, 0, 0, 0, 40));
        save(new Warrior("default", 125, 35, 60, 4, 80, 0, 0, 0, 20));
    }

    /**
     * save a hero into the sqlite database
     *
     * @param theHero the name to save into the sqlite database
     */
    public static void save(final Hero theHero) throws SQLException {
        DungeonCharacterSqliteInterface.save(theHero, TABLE_NAME);
    }

    /**
     * load a hero from the sqlite database
     *
     * @param theHeroType the type of the hero to load from the sqlite database
     * @return the information regarding this hero
     */
    public static int[] load(final String theHeroType) throws SQLException {
        return DungeonCharacterSqliteInterface.load(theHeroType, TABLE_NAME);
    }

}
