package com.arguments.support;

/**
 * @author mirleau
 *
 */
public class EmailAddress
{
    protected final String theEmailString;

    // ------------------------------------------------------------------------
    public EmailAddress(String anAddress)
    {
        theEmailString = anAddress;
    }
    
    // ------------------------------------------------------------------------
    public EmailAddress(EmailAddress anEmailAddress)
    {
        this(anEmailAddress.theEmailString);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return ""+theEmailString;
    }

    // ------------------------------------------------------------------------
    @Override
    public boolean equals(Object anOther)
    {
        if (anOther == null) return false;
        if (!(anOther instanceof EmailAddress)) return false;
        return theEmailString.equals(((EmailAddress)anOther).theEmailString);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public int hashCode()
    {
        return theEmailString.hashCode();
    }
}
