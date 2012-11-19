/**
 * 
 */
package com.arguments.functional.requeststate;

import com.arguments.functional.command.Command;
import com.arguments.functional.datamodel.ArgsReadOnlyState;
import com.arguments.functional.datamodel.ArgsState;

/**
 * @author mirleau
 *
 */
public final class ArgsStatefulCommand
{
    private final Command theCommand;
    private final ArgsState theState;
    
    // ------------------------------------------------------------------------
    public ArgsStatefulCommand(Command aRequest, ArgsState aState)
    {
        theState = aState;
        theCommand = aRequest;
    }

    // ------------------------------------------------------------------------
    public ArgsReadOnlyState execute()
    {
        theCommand.execute(theState);
        return theState;
    }
}
