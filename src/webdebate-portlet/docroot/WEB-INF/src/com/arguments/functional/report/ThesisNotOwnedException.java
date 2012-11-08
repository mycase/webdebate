package com.arguments.functional.report;

import com.arguments.functional.datamodel.ArgumentsException;

/**
 * @author mirleau
 *
 */
public class ThesisNotOwnedException extends ArgumentsException
{
    // ------------------------------------------------------------------------
    /**
     * @param aAnException
     */
    public ThesisNotOwnedException(Exception aAnException)
    {
        super(aAnException);
    }

    
    // ------------------------------------------------------------------------
    /**
     * @param aString
     */
    public ThesisNotOwnedException(String aString)
    {
       super(aString);
    }
}
