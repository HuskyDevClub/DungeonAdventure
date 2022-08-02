package com.griffinryan.dungeonadventure.model.sql;

import org.sqlite.SQLiteDataSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.SQLException;

final class SqliteInterface {

    public static final String TEXT = "TEXT";
    public static final String INTEGER = "INTEGER";
    public static final String BLOB = "BLOB";


    /**
     * try to make connection to the database
     *
     * @return the Connection reference to the database
     */
    static Connection connectDatabase(final String theDatabasePath) throws SQLException {
        //establish connection
        final SQLiteDataSource ds = new SQLiteDataSource();
        try {
            ds.setUrl(theDatabasePath);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new SQLException("Fail to connect to the database.");
        }
        return ds.getConnection();
    }

    /**
     * generate a sql command that will create a table (if not exist) if is executed
     *
     * @param tableName        the name of the table
     * @param theInstancesType the instances of the model and their SQL types in string
     * @return the sql command
     */
    private static String getCommandForTableCreation(final String tableName, final String[][] theInstancesType) {
        final StringBuilder theCommand = new StringBuilder();
        theCommand.append(String.format("CREATE TABLE IF NOT EXISTS %s ( ID INTEGER PRIMARY KEY AUTOINCREMENT, ", tableName));
        for (final String[] pairs : theInstancesType) {
            theCommand.append(pairs[0]);
            theCommand.append(' ');
            theCommand.append(pairs[1]);
            theCommand.append(" NOT NULL, ");
        }
        theCommand.deleteCharAt(theCommand.length() - 2);
        theCommand.append(')');
        return theCommand.toString();
    }

    /**
     * generate a sql command template that will insert data into database if is executed
     *
     * @param tableName        the name of the table
     * @param theInstancesType the instances of the model and their SQL types in string
     * @return the sql command
     */
    static String getCommandForSavingModel(final String tableName, final String[][] theInstancesType) {
        final StringBuilder theCommand = new StringBuilder();
        theCommand.append(String.format("INSERT INTO %s ( ", tableName));
        for (final String[] pairs : theInstancesType) {
            theCommand.append(pairs[0]);
            theCommand.append(", ");
        }
        theCommand.deleteCharAt(theCommand.length() - 2);
        theCommand.append(") VALUES ( ");
        theCommand.append("?, ".repeat(theInstancesType.length));
        theCommand.deleteCharAt(theCommand.length() - 2);
        theCommand.append(')');
        return theCommand.toString();
    }

    /**
     * Convert an object into byte array
     *
     * @param theObject the object to convert
     * @return the object in byte array form
     */
    static byte[] objectToByteArray(final Object theObject) throws IOException {
        // prepare for the conversion;
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(theObject);
        out.flush();
        final byte[] result = bos.toByteArray();
        // close the output streams
        out.close();
        bos.close();
        // return result
        return result;
    }

    /**
     * ensure the specified table exists in the database
     *
     * @param theConnection    the connection to the database
     * @param tableName        the name of the table
     * @param theInstancesType the instances of the model and their SQL types in string
     */
    static void ensureTableExist(final Connection theConnection, final String tableName, final String[][] theInstancesType) throws SQLException {
        theConnection.prepareStatement(getCommandForTableCreation(tableName, theInstancesType)).executeUpdate();
    }
}
