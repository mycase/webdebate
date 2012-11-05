/**
 * 
 */
package com.arguments.support.sql;

/**
 * @author mirleau
 *
 */
public class DBColumn
{
    public final String theColumnName;
    public final String theTableName;
    
    // ------------------------------------------------------------------------
    public DBColumn(String aColumnName)
    {
        this(aColumnName, null);
    }

    // ------------------------------------------------------------------------
    public DBColumn(String aColumnName, String aTableName)
    {
        theColumnName = aColumnName;
        theTableName = aTableName;
    }

    // ------------------------------------------------------------------------
    public String getName()
    {
        return theColumnName;
    }

    // ------------------------------------------------------------------------
    public String getLongName() // full
    {
        return theTableName + "." + theColumnName;
    }

    // ------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return theColumnName;
    }
}
