package com.griffinryan.dungeonadventure.controller;

import org.sqlite.SQLiteDataSource;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

final class SqlInterface {
    private static final String myDatabasePath = "jdbc:sqlite:save.sqlite";

    /**
     * try to make connection to the database
     *
     * @return the Connection reference to the database
     */
    private static Connection connectDatabase() throws SQLException {
        //establish connection (creates database file if it does not exist :-)
        final SQLiteDataSource ds = new SQLiteDataSource();
        try {
            ds.setUrl(myDatabasePath);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new SQLException("Fail to connect to the database.");
        }
        return ds.getConnection();
    }

    /**
     * save an object into the database
     * @param tableName the table of the database
     * @param theName
     * @param theData the object data to save
     * @return the id location of the object in that table
     */
    static int save(final String tableName, final String theName, final Object theData) throws SQLException, IOException {
        //establish connection
        final Connection theConnection = connectDatabase();
        //create a table if it does not exist
        theConnection.createStatement().executeUpdate(String.format("CREATE TABLE IF NOT EXISTS %s ( ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT NOT NULL, DATA BYTES NOT NULL)", tableName));
        /*
         * insert the value into the database
         * the basic idea is, convert the object into byte array, and then save the byte array into the database
         */
        final PreparedStatement thePreparedStatement = theConnection.prepareStatement(String.format("INSERT INTO %s ( NAME, DATA ) VALUES ( ?, ? )", tableName));
        thePreparedStatement.setString(1, theName);
        // prepare for the conversion;
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(theData);
        out.flush();
        // convert the object into ByteArray and save it under the DATA colum
        thePreparedStatement.setBytes(2, bos.toByteArray());
        // obtain the id of the save
        final int rv = thePreparedStatement.executeUpdate();
        // close the connections
        out.close();
        bos.close();
        thePreparedStatement.close();
        theConnection.close();
        // return the id
        return rv;
    }

    /**
     * @param tableName the table of the database
     * @param theId     the id of the object in that database
     * @return the Object (that needs to be cast)
     */
    static Object load(final String tableName, final int theId) throws SQLException, IOException, ClassNotFoundException {
        //establish connection
        final Connection theConnection = connectDatabase();
        //find the object based on give table
        final PreparedStatement thePreparedStatement = theConnection.prepareStatement(String.format("SELECT DATA FROM %s WHERE ID = ?", tableName));
        thePreparedStatement.setInt(1, theId);
        final ResultSet rs = thePreparedStatement.executeQuery();
        rs.next();
        // load the object
        final Object resultObject = new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(1))).readObject();
        // it is always a good practice to close all the connection at the end
        rs.close();
        thePreparedStatement.close();
        theConnection.close();
        return resultObject;
    }
}
