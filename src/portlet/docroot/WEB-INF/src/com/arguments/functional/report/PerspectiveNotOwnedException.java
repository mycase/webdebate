/**
 * 
 */
package com.arguments.functional.report;

import com.arguments.functional.datamodel.ArgumentsException;

/**
 * @author mirleau
 *
 */
public class PerspectiveNotOwnedException extends ArgumentsException
{
    // ------------------------------------------------------------------------
    public PerspectiveNotOwnedException(Exception aAnException)
    {
        super(aAnException);
    }
    
    // ------------------------------------------------------------------------
    public PerspectiveNotOwnedException(String aString)
    {
       super(aString);
    }
}
