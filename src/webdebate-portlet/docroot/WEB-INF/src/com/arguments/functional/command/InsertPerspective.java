/**
 * 
 */
package com.arguments.functional.command;

import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgsReadOnlyState;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.PerspectiveName;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class InsertPerspective implements Command
{
    private final PerspectiveName theNewPerspectiveName;
    private final ArgumentsUser theAppUser;
    
    // ------------------------------------------------------------------------
    public InsertPerspective(
            PerspectiveName aNewPerspectiveName,
            ArgumentsUser anAppUser)
    {
        assertNotNull(aNewPerspectiveName);
        
        theNewPerspectiveName = aNewPerspectiveName;
        theAppUser = anAppUser;
    }

    // ------------------------------------------------------------------------
    @Override
    public void execute(ArgsState aState)
    {
        ArgsReadOnlyState myState = theAppUser.getState();
        
        PerspectiveId myNewPerspectiveId =
                TheArgsStore.i(theAppUser).addPerspective(
                theNewPerspectiveName);
        assertTrue( myNewPerspectiveId.isWritable());
        aState.setPerspectiveId(myNewPerspectiveId);
        assertTrue( aState.getFirstPerspectiveId().isWritable());
    }
}
