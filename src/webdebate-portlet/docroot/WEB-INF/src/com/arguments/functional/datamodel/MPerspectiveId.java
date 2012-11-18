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
        add(aPerspective);
    }

}
