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

    // ------------------------------------------------------------------------
    public ArgsStatefulCommand2(Command aCommand, ArgumentsUser aAppUser)
    {
        theCommand = aCommand;
        theAppUser = aAppUser;
    }
    
    // ------------------------------------------------------------------------
    // This method would be clearer if there would be no writing in the argument
    // variable myArgsState during theCommand.execute().
    public void execute()
    {
        final ArgsState myArgsState = PortalArgsBridge.getState(theAppUser);
        theCommand.execute(myArgsState);
        final StateChange myStateChange = myArgsState.getStateChange(); // copy
        assertTrue(myStateChange.hasChange());
        myStateChange.mergeAndStore(theAppUser);
    }
}
