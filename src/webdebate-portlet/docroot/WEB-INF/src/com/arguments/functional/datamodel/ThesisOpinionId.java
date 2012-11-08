/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public class ThesisOpinionId
{

    protected final Long theID;

    public static final ThesisOpinionId ONE = new ThesisOpinionId(1L);
    
    // ------------------------------------------------------------------------
    public ThesisOpinionId(Long anID)
    {
        theID = anID;
    }
    
    // ------------------------------------------------------------------------
    public ThesisOpinionId(ThesisOpinionId aThesisID)
    {
        this(aThesisID.theID);
    }
    
    // ------------------------------------------------------------------------
    public ThesisOpinionId(String aThesisIdString)
    {
        this(Long.parseLong(aThesisIdString));
    }

    // ------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return ""+theID;
    }

    // ------------------------------------------------------------------------
    @Override
    public boolean equals(Object anOther)
    {
        if (anOther == null) return false;
        if (!(anOther instanceof ThesisOpinionId)) return false;
        return theID.equals(((ThesisOpinionId)anOther).theID);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public int hashCode()
    {
        return theID.hashCode();
    }

    // ------------------------------------------------------------------------
    public Long getLongID()
    {
        return theID;
    }
}
