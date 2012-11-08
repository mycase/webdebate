/**
 * 
 */
package com.arguments.functional.command;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.PerspectiveId;

/**
 * @author mirleau
 *
 */
public class ChangePerspective implements Command
{
    private final PerspectiveId thePerspectiveId;

    // ------------------------------------------------------------------------
    public ChangePerspective(
            PerspectiveId aPerspectiveId)
    {
        thePerspectiveId = aPerspectiveId;
    }

    // ------------------------------------------------------------------------
    @Override
    public void execute(ArgsState aState)
    {
        assert aState != null;
        
        aState.setPerspectiveId(thePerspectiveId);
    }
}
