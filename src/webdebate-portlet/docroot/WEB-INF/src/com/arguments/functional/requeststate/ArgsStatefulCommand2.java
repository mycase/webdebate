/**
 * 
 */
package com.arguments.functional.requeststate;

import static org.junit.Assert.assertTrue;

import com.arguments.functional.command.Command;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;

/**
 * @author mirleau
 *
 */
public class ArgsStatefulCommand2
{
    private final Command theCommand;
    private final ArgumentsUser theAppUser;
    private final StateChange theStateChange;

    // ------------------------------------------------------------------------
    public ArgsStatefulCommand2(Command aCommand, ArgumentsUser aAppUser,
            StateChange aStateChange)
    {
        theCommand = aCommand;
        theAppUser = aAppUser;
        theStateChange = aStateChange;
    }
    
    // ------------------------------------------------------------------------
    public void execute()
    {
        // This method performs a state update twice. First here:
        if (theStateChange.hasChange())
        {
            theStateChange.mergeAndStore(theAppUser);
        }
        final ArgsState myArgsState = PortalArgsBridge.getState(theAppUser);

        // Not here, which is where you would want it
        theCommand.execute(myArgsState);
        StateChange myStateChange = myArgsState.getStateChange(); // copy
        assertTrue(myStateChange.hasChange());
        // Then a second time here:
        myStateChange.mergeAndStore(theAppUser);
    }
}
