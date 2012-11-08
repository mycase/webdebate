package com.arguments.application;

import com.arguments.functional.datamodel.ArgumentsException;

@SuppressWarnings("serial")
public class ContainerException extends ArgumentsException
{
    // ------------------------------------------------------------------------
    public ContainerException(Throwable anException)
    {
        super(anException);
    }

    // ------------------------------------------------------------------------
    public ContainerException(String aMessage, Throwable anException)
    {
        super(aMessage, anException);
    }

    // ------------------------------------------------------------------------
    public ContainerException(String aString)
    {
        super(aString);
    }
}
