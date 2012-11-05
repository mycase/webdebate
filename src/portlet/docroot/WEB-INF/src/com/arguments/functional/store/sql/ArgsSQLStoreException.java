package com.arguments.functional.store.sql;

import com.arguments.functional.store.StoreException;
import com.arguments.functional.store.sql.ArgsDB.ArgsQuery;

@SuppressWarnings("serial")
class ArgsSQLStoreException extends StoreException
{
    private static boolean theUnlocking = false;
    // ------------------------------------------------------------------------
    public ArgsSQLStoreException(Exception anException)
    {
        super(anException);
        tryUnlockTables();
    }

    // ------------------------------------------------------------------------
    public ArgsSQLStoreException(String aMessage, Exception anException)
    {
        super(aMessage, anException);
        tryUnlockTables();
    }

    // ------------------------------------------------------------------------
    public ArgsSQLStoreException(String aString)
    {
        super(aString);
        tryUnlockTables();
    }
    
    // ------------------------------------------------------------------------
    private static void tryUnlockTables()
    {
        if (theUnlocking) return; 
        theUnlocking = true;
        try
        {
            ArgsQuery.UNLOCK_TABLES.ps().executeQuery();            
        }
        catch (Exception | Error anException)
        {
            System.err.println("Repeat exception: " + anException.getClass());
            // anException.printStackTrace(System.err);
        }
        theUnlocking = false;
    }
}
