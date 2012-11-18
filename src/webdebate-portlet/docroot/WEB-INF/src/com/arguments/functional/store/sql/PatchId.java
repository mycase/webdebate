/**
 * 
 */
package com.arguments.functional.store.sql;


/**
 * @author mirleau
 *
 */
public class PatchId
{
    private final Integer theId;

    // ------------------------------------------------------------------------
    public PatchId(Integer aForeignId)
    {
        theId = aForeignId;
    }

    // ------------------------------------------------------------------------
    public String toString()
    {
        return theId.toString();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((theId == null) ? 0 : theId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PatchId other = (PatchId) obj;
        if (theId == null)
        {
            if (other.theId != null)
                return false;
        } else if (!theId.equals(other.theId))
            return false;
        return true;
    }
    
    // ------------------------------------------------------------------------
    public Integer getLongID()
    {
        return theId;
    }
}
