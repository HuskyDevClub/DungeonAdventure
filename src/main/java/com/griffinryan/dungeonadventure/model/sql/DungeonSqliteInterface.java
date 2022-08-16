package com.griffinryan.dungeonadventure.model.sql;

import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;

public final class DungeonSqliteInterface {

    public static final String DATABASE_PATH = "jdbc:sqlite:save.sqlite";
    private static final String TABLE_NAME = Dungeon.class.getSimpleName();
    private static final String[][] INSTANCES_TYPE = {
        {"NAME", SqliteInterface.TEXT}, {"DATA", SqliteInterface.BLOB}, {"HERO_TYPE", SqliteInterface.TEXT}, {"CREATED_AT", SqliteInterface.TEXT}
    };

    /**
     * @param theName    the name of the Dungeon
     * @param theDungeon the Dungeon object that needs to be saved
     * @return the id location of the object in that table
     */
    public static String save(final String theName, final Dungeon theDungeon) throws SQLException, IOException {
        return save(theName, theDungeon, DATABASE_PATH);
    }

    /**
     * @param theName         the name of the Dungeon
     * @param theDungeon      the Dungeon object that needs to be saved
     * @param theDatabasePath the custom path of the database
     * @return the id location of the object in that table
     */
    public static String save(final String theName, final Dungeon theDungeon, final String theDatabasePath) throws SQLException, IOException {
        //establish connection
        final Connection theConnection = SqliteInterface.connectDatabase(theDatabasePath);
        //create a table if it does not exist
        SqliteInterface.ensureTableExist(theConnection, TABLE_NAME, INSTANCES_TYPE);
        /*
         * insert the value into the database
         * the basic idea is, convert the object into byte array, and then save the byte array into the database
         */
        final PreparedStatement thePreparedStatement = theConnection.prepareStatement(SqliteInterface.getCommandForSavingModel(TABLE_NAME, INSTANCES_TYPE));
        thePreparedStatement.setString(1, theName);
        // convert the object into ByteArray and save it under the DATA colum
        thePreparedStatement.setBytes(2, SqliteInterface.objectToByteArray(theDungeon));
        thePreparedStatement.setString(3, theDungeon.getHero().getClass().getSimpleName());
        thePreparedStatement.setString(4, LocalDateTime.now().toString());
        // save the data
        thePreparedStatement.executeUpdate();
        // obtain the id of the save
        ResultSet rs = thePreparedStatement.getGeneratedKeys();
        final String itemId = rs.next() ? rs.getString(1) : "-1";
        // close the connections
        rs.close();
        thePreparedStatement.close();
        theConnection.close();
        // return the id
        return itemId;
    }

    /**
     * @param theId the id of the Dungeon in that database
     * @return the Dungeon loaded from the database according to given id
     */
    public static Dungeon load(final String theId) throws SQLException, IOException, ClassNotFoundException {
        return load(theId, DATABASE_PATH);
    }

    /**
     * @param theId           the id of the Dungeon in that database
     * @param theDatabasePath the path of the database
     * @return the Dungeon loaded from the database according to given id
     */
    public static Dungeon load(final String theId, final String theDatabasePath) throws SQLException, IOException, ClassNotFoundException {
        //establish connection
        final Connection theConnection = SqliteInterface.connectDatabase(theDatabasePath);
        //find the object based on give table
        final PreparedStatement thePreparedStatement = theConnection.prepareStatement(String.format("SELECT DATA FROM %s WHERE ID = ?", TABLE_NAME));
        thePreparedStatement.setString(1, theId);
        final ResultSet rs = thePreparedStatement.executeQuery();
        rs.next();
        // load the object
        final Dungeon resultObject = (Dungeon) new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(1))).readObject();
        // it is always a good practice to close all the connection at the end
        rs.close();
        thePreparedStatement.close();
        theConnection.close();
        return resultObject;
    }

    /**
     * @return a HashMap that stores information regarding exiting saves,
     * key is id, value[0] is the name of the save, and value[1] is a string represent when the save is created
     * value[1] can be converted into Data if needed
     */
    public static HashMap<String, String[]> getNamesOfExistingSaves() {
        return getNamesOfExistingSaves(DATABASE_PATH);
    }

    /**
     * @param theDatabasePath the path of the database
     * @return a HashMap that stores information regarding exiting saves,
     * key is id, value[0] is the name of the save, and value[1] is a string represent when the save is created
     * value[1] can be converted into Data if needed
     */
    public static HashMap<String, String[]> getNamesOfExistingSaves(final String theDatabasePath) {
        /*
         * The key is the id, the value is the name of the save
         * The reason of using string id is that the save can be deleted
         * In that case, using array will only cause more problem
         */
        final HashMap<String, String[]> existSaves = new HashMap<>();
        try {
            // try to establish connection
            final Connection theConnection = SqliteInterface.connectDatabase(theDatabasePath);
            //find the object based on give table
            final PreparedStatement thePreparedStatement = theConnection.prepareStatement(String.format("SELECT * FROM %s", TABLE_NAME));
            final ResultSet rs = thePreparedStatement.executeQuery();
            while (rs.next()) {
                existSaves.put(rs.getString(1), new String[]{rs.getString(2), rs.getString(4), rs.getString(5)});
            }
        } catch (SQLException e) {
            // assume something must be going wrong I guess
        }
        return existSaves;
    }

    /**
     * delete a save record from the database
     *
     * @param theId the id of the save
     */
    public static void delete(final String theId) throws SQLException {
        delete(theId, DATABASE_PATH);
    }

    /**
     * delete a save record from the database
     *
     * @param theDatabasePath the path of the database
     * @param theId           the id of the save
     */
    public static void delete(final String theId, final String theDatabasePath) throws SQLException {
        // try to establish connection
        final Connection theConnection = SqliteInterface.connectDatabase(theDatabasePath);
        final PreparedStatement thePreparedStatement = theConnection.prepareStatement(String.format("DELETE FROM %s WHERE ID = ?", TABLE_NAME));
        thePreparedStatement.setString(1, theId);
        thePreparedStatement.executeUpdate();
    }
}
