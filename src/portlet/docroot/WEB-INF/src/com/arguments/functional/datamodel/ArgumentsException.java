package com.arguments.functional.datamodel;

@SuppressWarnings("serial")
public class ArgumentsException extends RuntimeException
{
    // ------------------------------------------------------------------------
    public ArgumentsException(Exception anException)
    {
        super(anException);
    }

    // ------------------------------------------------------------------------
    public ArgumentsException(String aMessage, Exception anException)
    {
        super(aMessage, anException);
    }

    // ------------------------------------------------------------------------
    public ArgumentsException(String aString)
    {
        super(aString);
    }
}
