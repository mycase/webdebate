/**
 * 
 */
package com.arguments.functional.datamodel;

import java.util.ArrayList;

/**
 * @author mirleau
 *
 */
public class MPerspectiveId extends ArrayList<PerspectiveId>
{
    // ------------------------------------------------------------------------
    public MPerspectiveId()
    {
        
    }

    // ------------------------------------------------------------------------
    public MPerspectiveId(PerspectiveId aPerspective)
    {
        //if (aPerspective != null)
        add(aPerspective);
    }

    // ------------------------------------------------------------------------
    @Override
    public boolean add(PerspectiveId anId)
    {
        assert anId != null;
        return super.add(anId);
    }
}
