package com.arguments.functional.store;

import com.arguments.functional.datamodel.ArgumentsException;

@SuppressWarnings("serial")
public class StoreException extends ArgumentsException
{
    // ------------------------------------------------------------------------
    public StoreException(Exception anException)
    {
        super(anException);
    }

    // ------------------------------------------------------------------------
    public StoreException(String aMessage, Exception anException)
    {
        super(aMessage, anException);
    }

    // ------------------------------------------------------------------------
    public StoreException(String aString)
    {
        super(aString);
    }
}
