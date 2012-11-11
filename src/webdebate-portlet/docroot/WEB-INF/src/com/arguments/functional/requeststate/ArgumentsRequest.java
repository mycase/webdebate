package com.arguments.functional.requeststate;

import com.arguments.functional.command.Command;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;

public class ArgumentsRequest
{
    private final Command theCommand;
    
    // ------------------------------------------------------------------------
    public ArgumentsRequest(
            ArgumentsUser anAppUser,
            ProtocolMap aProtocolMap)
    {
        RequestParser theParser =
                new RequestParser(anAppUser, aProtocolMap);
        
        theCommand = theParser.parseCommand();
    }

    // ------------------------------------------------------------------------
    void execute(ArgsState aState)
    {
        theCommand.execute(aState);
    }
}
