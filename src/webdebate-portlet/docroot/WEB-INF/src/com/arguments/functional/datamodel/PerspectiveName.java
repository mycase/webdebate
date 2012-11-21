/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public class PerspectiveName
{
    public static final PerspectiveName DEFAULT = new PerspectiveName("main");
    public static final PerspectiveName THESIS_OWNER = new PerspectiveName("thesis_owner");
    
    private final String theName;
    
    // ------------------------------------------------------------------------
    public PerspectiveName(String aName)
    {
        theName = aName;
    }

    // ------------------------------------------------------------------------
    public String getName()
    {
        return theName;
    }

    // ------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return theName;
    }

    // ------------------------------------------------------------------------
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((theName == null) ? 0 : theName.hashCode());
        return result;
    }

    // ------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PerspectiveName other = (PerspectiveName) obj;
        if (theName == null)
        {
            if (other.theName != null)
                return false;
        } else if (!theName.equals(other.theName))
            return false;
        return true;
    }
}
