package com.arguments.application;

import com.arguments.functional.datamodel.ArgumentsException;

@SuppressWarnings("serial")
public class ContainerException extends ArgumentsException
{
    // ------------------------------------------------------------------------
    public ContainerException(Exception anException)
    {
        super(anException);
    }

    // ------------------------------------------------------------------------
    public ContainerException(String aMessage, Exception anException)
    {
        super(aMessage, anException);
    }

    // ------------------------------------------------------------------------
    public ContainerException(String aString)
    {
        super(aString);
    }
}
