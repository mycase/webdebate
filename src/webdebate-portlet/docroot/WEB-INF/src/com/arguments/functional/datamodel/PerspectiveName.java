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
}
