/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public class ArgumentsUserId
{
    public static final ArgumentsUserId ONE = new ArgumentsUserId(1L);
    public static final ArgumentsUserId TEST2 = new ArgumentsUserId(2L);
    public static final ArgumentsUserId TEST7 = new ArgumentsUserId(7L);
    public static final ArgumentsUserId BOGUS = new ArgumentsUserId(-27L);
    private long theId;

    // ------------------------------------------------------------------------
    public ArgumentsUserId(long anId)
    {
        theId = anId;
    }
    
    // ------------------------------------------------------------------------
    public ArgumentsUserId(ArgumentsUserId anOwnerID)
    {
        this(anOwnerID.theId);
    }

    // ------------------------------------------------------------------------
    public long getLongId()
    {
        return theId;
    }

    // ------------------------------------------------------------------------
    public String toString()
    {
        return ""+theId;
    }

    // ------------------------------------------------------------------------
    public boolean equals(Object anOther)
    {
        if (anOther == null) return false;
        if (!(anOther instanceof ArgumentsUserId)) return false;
        return theId == ((ArgumentsUserId)anOther).theId;
    }

    // ------------------------------------------------------------------------
    @Override
    public int hashCode()
    {
        return (int)(theId ^ (theId >>> 32));
    }
}
