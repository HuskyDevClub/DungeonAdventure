package com.griffinryan.dungeonadventure.model.tests;

import com.griffinryan.dungeonadventure.model.HeroesFactory;
import com.griffinryan.dungeonadventure.model.RandomSingleton;
import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;
import com.griffinryan.dungeonadventure.model.heroes.Warrior;
import com.griffinryan.dungeonadventure.model.sql.DungeonSqliteInterface;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TestDungeonSqliteInterface {
    @Test
    public void testDungeonLoadAndSave() throws SQLException, IOException, ClassNotFoundException {
        final String theHeroName = "TheHeroIsUs";

        final int theX = RandomSingleton.nextInt(10, 40);
        final int theY = RandomSingleton.nextInt(10, 40);
        final int theWidth = 50;
        final int theHeight = 50;
        final String theSaveName = "theTest";

        final Dungeon theDungeon = new Dungeon(HeroesFactory.spawn(Warrior.class.getSimpleName(), theHeroName), theWidth, theHeight, theX, theY);

        // save the Dungeon and get the id of the save
        final String saveId = DungeonSqliteInterface.save(theSaveName, theDungeon);

        // verify that the data has been saved into the database
        final HashMap<String, String[]> nameOfSaves = DungeonSqliteInterface.getNamesOfExistingSaves();
        assertTrue(nameOfSaves.containsKey(saveId));
        assertEquals(theSaveName, nameOfSaves.get(saveId)[0]);

        // now load the record from the sql database
        final Dungeon theDungeonFromSql = DungeonSqliteInterface.load(saveId);
        // check if two Dungeon are the same
        assertEquals(theDungeon.getMazeWidth(), theDungeonFromSql.getMazeWidth());
        assertEquals(theDungeon.getMazeHeight(), theDungeonFromSql.getMazeHeight());
        assertEquals(theDungeon.getCurrentX(), theDungeonFromSql.getCurrentX());
        assertEquals(theDungeon.getCurrentY(), theDungeonFromSql.getCurrentY());
        assertEquals(theDungeon.getPillarsFound(), theDungeonFromSql.getPillarsFound());
        assertEquals(theDungeon.getHero().toString(), theDungeonFromSql.getHero().toString());
        assertEquals(theDungeon.getHero().getClass().getName(), theDungeonFromSql.getHero().getClass().getName());
        assertEquals(theDungeon.toString(), theDungeonFromSql.toString());

        // remove record
        DungeonSqliteInterface.delete(saveId);
        assertFalse(DungeonSqliteInterface.getNamesOfExistingSaves().containsKey(saveId));

    }
}
