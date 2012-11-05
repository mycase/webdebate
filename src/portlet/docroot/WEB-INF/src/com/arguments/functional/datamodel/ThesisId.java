/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public class ThesisId
{

    protected final Long theID;

    public static final ThesisId ONE = new ThesisId(1L);
    public static final ThesisId TWO = new ThesisId(2L);
    public static final ThesisId Da13 = new ThesisId(13L);
    public static final ThesisId Da156 = new ThesisId(156L);
    
    // ------------------------------------------------------------------------
    public ThesisId(Long anID)
    {
        theID = anID;
    }
    
    // ------------------------------------------------------------------------
    public ThesisId(ThesisId aThesisID)
    {
        this(aThesisID.theID);
    }
    
    
    // ------------------------------------------------------------------------
    public ThesisId(String aString)
    {
        this(Long.parseLong(aString));
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
        if (!(anOther instanceof ThesisId)) return false;
        return theID.equals(((ThesisId)anOther).theID);
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

    // ------------------------------------------------------------------------
    public static ThesisId parse(String aThesisIdString)
    {
        if (aThesisIdString == null) return null;
        return new ThesisId(Long.parseLong(aThesisIdString));
    }
}
