/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public class ForeignUserId
{
    public static final ForeignUserId BOGUS = new ForeignUserId("-27");

    private final String theForeignId;

    // ------------------------------------------------------------------------
    /**
     * @param aForeignId
     */
    public ForeignUserId(String aForeignId)
    {
        theForeignId = aForeignId;
    }

    // ------------------------------------------------------------------------
    /**
     * @return the id
     */
    public String toString()
    {
        return theForeignId;
    }

    // ------------------------------------------------------------------------
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((theForeignId == null) ? 0 : theForeignId.hashCode());
        return result;
    }

    // ------------------------------------------------------------------------
    @Override
    public boolean equals(Object anObject)
    {
        if (this == anObject)
            return true;
        if (anObject == null)
            return false;
        if (getClass() != anObject.getClass())
            return false;
        ForeignUserId anOther = (ForeignUserId) anObject;
        if (theForeignId == null)
        {
            if (anOther.theForeignId != null)
                return false;
        } else if (!theForeignId.equals(anOther.theForeignId))
            return false;
        return true;
    }

}
