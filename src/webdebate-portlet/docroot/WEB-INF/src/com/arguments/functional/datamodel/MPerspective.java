/**
 * 
 */
package com.arguments.functional.datamodel;

import java.util.ArrayList;

/**
 * @author mirleau
 *
 */
public class MPerspective extends ArrayList<Perspective>
{
    // ------------------------------------------------------------------------
    public MPerspective()
    {
        
    }

    // ------------------------------------------------------------------------
    public MPerspective(Perspective aPerspective)
    {
        add(aPerspective);
    }
}
