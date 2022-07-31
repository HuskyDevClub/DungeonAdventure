package com.griffinryan.dungeonadventure.model.sql;

import com.griffinryan.dungeonadventure.model.DungeonCharacter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

final class DungeonCharacterSqliteInterface {

    private static final String DATABASE_PATH = "jdbc:sqlite:DungeonCharacters.sqlite";
    private static final String[][] INSTANCES_TYPE = {
            {"TYPE", SqliteInterface.TEXT}, {"HEALTH", SqliteInterface.INTEGER}, {"MIN_DAMAGE", SqliteInterface.INTEGER},
            {"MAX_DAMAGE", SqliteInterface.INTEGER}, {"ATTACK_SPEED", SqliteInterface.INTEGER}, {"CHANCE_TO_HIT", SqliteInterface.INTEGER},
            {"CHANCE_TO_HEAL", SqliteInterface.INTEGER}, {"MIN_HEALING", SqliteInterface.INTEGER}, {"MAX_HEALING", SqliteInterface.INTEGER},
            {"CHANCE_TO_BLOCK", SqliteInterface.INTEGER}
    };

    /**
     * @param theDungeonCharacter the DungeonCharacter that will be saved
     * @param TABLE_NAME          the name of the table
     * @return the id location of the DungeonCharacter in that table
     */
    static int save(final DungeonCharacter theDungeonCharacter, final String TABLE_NAME) throws SQLException {
        //establish connection
        final Connection theConnection = SqliteInterface.connectDatabase(DATABASE_PATH);
        //create a table if it does not exist
        SqliteInterface.ensureTableExist(theConnection, TABLE_NAME, INSTANCES_TYPE);
        // update the statement
        final PreparedStatement thePreparedStatement = theConnection.prepareStatement(SqliteInterface.getCommandForSavingModel(TABLE_NAME, INSTANCES_TYPE));
        thePreparedStatement.setString(1, theDungeonCharacter.getClass().getSimpleName());
        thePreparedStatement.setInt(2, theDungeonCharacter.getHealth());
        thePreparedStatement.setInt(3, theDungeonCharacter.getMinDamage());
        thePreparedStatement.setInt(4, theDungeonCharacter.getMaxDamage());
        thePreparedStatement.setInt(5, theDungeonCharacter.getAttackSpeed());
        thePreparedStatement.setInt(6, theDungeonCharacter.getChanceToHit());
        thePreparedStatement.setInt(7, theDungeonCharacter.getChanceToHeal());
        thePreparedStatement.setInt(8, theDungeonCharacter.getMinHealing());
        thePreparedStatement.setInt(9, theDungeonCharacter.getMaxHealing());
        thePreparedStatement.setInt(10, theDungeonCharacter.getChanceToBlock());
        // obtain the id of the save
        final int rv = thePreparedStatement.executeUpdate();
        // close the connections
        thePreparedStatement.close();
        theConnection.close();
        // return the id
        return rv;
    }

    /**
     * @param theMonsterType the type of the DungeonCharacter that will be loaded
     * @param TABLE_NAME     the name of the table
     * @return a ResultSet that contain the information reading that DungeonCharacter
     */
    static int[] load(final String theMonsterType, final String TABLE_NAME) throws SQLException {
        //establish connection
        final Connection theConnection = SqliteInterface.connectDatabase(DATABASE_PATH);
        //find the object based on give table
        final PreparedStatement thePreparedStatement = theConnection.prepareStatement(String.format("SELECT * FROM %s WHERE TYPE = ?", TABLE_NAME));
        thePreparedStatement.setString(1, theMonsterType);
        final ResultSet rs = thePreparedStatement.executeQuery();
        rs.next();
        // store the data into an array
        final int[] result = new int[9];
        for (int i = 2; i <= 10; i++) {
            result[i - 2] = rs.getInt(i);
        }
        // it is always a good practice to close all the connection at the end
        rs.close();
        thePreparedStatement.close();
        theConnection.close();
        return result;
    }
}
