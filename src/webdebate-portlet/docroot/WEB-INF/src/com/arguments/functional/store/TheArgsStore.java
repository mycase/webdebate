/**
 * 
 */
package com.arguments.functional.store;

import com.arguments.functional.datamodel.ArgsStore;
import com.arguments.functional.datamodel.ArgsWriteStore;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.store.sql.ArgsSQLStore;
import com.arguments.functional.store.sql.SecureArgsSQLStore;

/**
 * @author mirleau
 *
 */
public class TheArgsStore
{
    // This instance can be used for reading, but not for writing since
    // it doesn't know who's writing.
    private static ArgsStore theInstance;

    // ------------------------------------------------------------------------
    public static ArgsStore i()
    {
        if (theInstance == null)
            theInstance = new ArgsSQLStore();
        return theInstance;
    }

    // ------------------------------------------------------------------------
    public static ArgsWriteStore i(ArgumentsUser aUser)
    {
        return new SecureArgsSQLStore(aUser);
    }

    // ------------------------------------------------------------------------
    public static void setDB(ArgsStore anArgsStore)
    {
        theInstance = anArgsStore;
    }
}
