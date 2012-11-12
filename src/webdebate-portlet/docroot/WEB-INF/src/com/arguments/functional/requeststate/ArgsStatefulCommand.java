/**
 * 
 */
package com.arguments.functional.requeststate;

import com.arguments.functional.command.Command;
import com.arguments.functional.datamodel.ArgsState;

/**
 * @author mirleau
 *
 */
public class ArgsStatefulCommand
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
    public ArgsState execute()
    {
        theCommand.execute(theState);
        return theState;
    }
}