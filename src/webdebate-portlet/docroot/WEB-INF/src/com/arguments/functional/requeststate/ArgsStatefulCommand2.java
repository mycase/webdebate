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
    private final Command myCommand;
    private final ArgumentsUser theAppUser;
    private final StateChange myStateChange;

    // ------------------------------------------------------------------------
    public ArgsStatefulCommand2(Command aCommand, ArgumentsUser aAppUser,
            StateChange aStateChange)
    {
        myCommand = aCommand;
        theAppUser = aAppUser;
        myStateChange = aStateChange;
    }
    
    // ------------------------------------------------------------------------
    public void execute()
    {
        // This method performs a state update twice. First here:
        if (myStateChange.hasChange())
        {
            myStateChange.mergeAndStore(theAppUser);
        }
        final ArgsState myArgsState = PortalArgsBridge.getState(theAppUser);
        ArgsStatefulCommand myStateCommand = new ArgsStatefulCommand(myCommand, myArgsState);

        // Not here, which is where you would want it
        ArgsState myState = myStateCommand.execute();
        StateChange myStateString = myState.getStateString();
        assertTrue(myStateString.hasChange());
        // Then a second time here:
        myStateString.mergeAndStore(theAppUser);
    }
}
