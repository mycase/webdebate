package com.arguments.functional.datamodel;

@SuppressWarnings("serial")
public class ArgumentsException extends RuntimeException
{
    // ------------------------------------------------------------------------
    public ArgumentsException(Throwable anException)
    {
        super(anException);
    }

    // ------------------------------------------------------------------------
    public ArgumentsException(String aMessage, Throwable anException)
    {
        super(aMessage, anException);
    }

    // ------------------------------------------------------------------------
    public ArgumentsException(String aString)
    {
        super(aString);
    }
}
