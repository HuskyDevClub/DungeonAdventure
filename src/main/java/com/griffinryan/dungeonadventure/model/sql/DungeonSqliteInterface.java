package com.griffinryan.dungeonadventure.model.sql;

import com.griffinryan.dungeonadventure.model.dungeon.Dungeon;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;
import java.util.HashMap;

public final class DungeonSqliteInterface {

    private static final String TABLE_NAME = Dungeon.class.getSimpleName();
    private static final String[][] INSTANCES_TYPE = {{"NAME", SqliteInterface.TEXT}, {"DATA", SqliteInterface.BLOB}};

    /**
     * @param theDatabasePath the path of the database
     * @param theName         the name of the Dungeon
     * @param theDungeon      the Dungeon object that needs to be saved
     * @return the id location of the object in that table
     */
    public static int save(final String theDatabasePath, final String theName, final Dungeon theDungeon) throws SQLException, IOException {
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
        // obtain the id of the save
        final int rv = thePreparedStatement.executeUpdate();
        // close the connections
        thePreparedStatement.close();
        theConnection.close();
        // return the id
        return rv;
    }

    /**
     * @param theDatabasePath the path of the database
     * @param theId           the id of the Dungeon in that database
     * @return the Dungeon loaded from the database according to given id
     */
    public static Dungeon load(final String theDatabasePath, final String theId) throws SQLException, IOException, ClassNotFoundException {
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

    public static HashMap<String, String> getNamesOfExistingSaves(){
        /*
        * The key is the id, the value is the name of the save
        * The reason of using string id is that the save can be deleted
        * In that case, using array will only cause more problem
        */
        final HashMap<String, String> existSaves = new HashMap<>();
        try{
            // try to establish connection
            final Connection theConnection = SqliteInterface.connectDatabase("jdbc:sqlite:save.sqlite");
            //find the object based on give table
            final PreparedStatement thePreparedStatement = theConnection.prepareStatement(String.format("SELECT * FROM %s", TABLE_NAME));
            final ResultSet rs = thePreparedStatement.executeQuery();
            while (rs.next()) {
                existSaves.put(rs.getString(1), rs.getString(2));
            }
        } catch (SQLException e) {
            // assume something must be going wrong I guess
        }
        return existSaves;
    }
}
