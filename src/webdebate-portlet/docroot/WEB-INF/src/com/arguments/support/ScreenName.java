package com.arguments.support;

/**
 * @author mirleau
 *
 */
public class ScreenName
{
    protected final String theScreenNameString;

    // ------------------------------------------------------------------------
    public ScreenName(String aScreenName)
    {
        theScreenNameString = aScreenName;
    }
    
    // ------------------------------------------------------------------------
    public ScreenName(ScreenName aScreenName)
    {
        this(aScreenName.theScreenNameString);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return ""+theScreenNameString;
    }

    // ------------------------------------------------------------------------
    @Override
    public boolean equals(Object anOther)
    {
        if (anOther == null) return false;
        if (!(anOther instanceof ScreenName)) return false;
        return theScreenNameString.equals(((ScreenName)anOther).theScreenNameString);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public int hashCode()
    {
        return theScreenNameString.hashCode();
    }
}
