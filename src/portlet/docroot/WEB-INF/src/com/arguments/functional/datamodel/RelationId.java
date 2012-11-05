/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public class RelationId
{
    private final Long theID;

    public static final RelationId ONE = new RelationId(1L);
    public static final RelationId BONE = new RelationId(-1L);

    // ------------------------------------------------------------------------
    public RelationId(Long aRelationID)
    {
        theID = aRelationID;
    }

    
    // ------------------------------------------------------------------------
    public RelationId(RelationId aRelationID)
    {
        this(aRelationID.theID);
    }

    // ------------------------------------------------------------------------
    public RelationId(String aRelationIDText)
    {
        this(Long.parseLong(aRelationIDText));
    }
    
    // ------------------------------------------------------------------------
    public static RelationId getRelationId(String aLinkIdText)
    {
        if (aLinkIdText == null) return null;
        try
        {
            return new RelationId(aLinkIdText);
        }
        catch(NumberFormatException anException)
        {
            throw new AssertionError(
                    "Invalid relation id string: " + aLinkIdText, anException);
        }
    }

    // ------------------------------------------------------------------------
    public Long getID()
    {
        return theID;
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
        if (!(anOther instanceof RelationId)) return false;
        return theID.equals(((RelationId)anOther).theID);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public int hashCode()
    {
        return theID.hashCode();
    }
}
