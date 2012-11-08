/**
 * 
 */
package com.arguments.functional.store.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.arguments.Deployment;

/**
 * @author mirleau
 *
 */
class ArgsSQLStoreConnection
{
    private static Connection theConnection;
    
    // ------------------------------------------------------------------------
    public static synchronized PreparedStatement getPreparedQuery(String anSqlString)
            throws SQLException
    {
        return getConnection().prepareStatement(anSqlString);
    }
    
    // ------------------------------------------------------------------------
    private static Connection getConnection() throws SQLException
    {
        if (theConnection == null)
        {
            theConnection = newConnection();
        }
        
        if(theConnection.isClosed())
            renewConnection();

        return theConnection;
    }
    
    // ------------------------------------------------------------------------
    public static void renewConnection()
    {
        if (theConnection != null)
            try
            {
                theConnection.close();
            } catch (SQLException anException)
            {
                // Don't bail out on close failings.
                anException.printStackTrace();
            }
        theConnection = newConnection();
    }
    
    // ------------------------------------------------------------------------
    private static Connection newConnection() throws ArgsSQLStoreException
    {
        String myDbUrl = Deployment.i().dbUrl;
        String myDbName = Deployment.i().dbName;
        String myDriver = Deployment.i().dbDriver;
        String myUserName = Deployment.i().dbUserName;
        String myPassword = Deployment.i().dbPassword;

        Connection myConnection = null;
        try
        {
            Class.forName(myDriver).newInstance();
            myConnection = DriverManager.getConnection(myDbUrl + myDbName, myUserName,
                    myPassword);
        } catch (Exception anException)
        {
            throw new ArgsSQLStoreException(anException);
        }
        return myConnection;
    }
}
