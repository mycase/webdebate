package com.arguments.functional.requeststate;

import com.arguments.functional.command.Command;
import com.arguments.functional.datamodel.ArgumentsUser;

public class ArgumentsRequest
{
    private final Command theCommand;
    
    // ------------------------------------------------------------------------
    private ArgumentsRequest(
            ArgumentsUser anAppUser,
            ProtocolMap aProtocolMap)
    {
        RequestParser theParser =
                new RequestParser(anAppUser, aProtocolMap);
        
        theCommand = theParser.parseCommand();
    }

    // ------------------------------------------------------------------------
    public static Command getRequest(
            ArgumentsUser anAppUser,
            ProtocolMap aProtocolMap)
    {
        return new ArgumentsRequest(anAppUser, aProtocolMap).getCommand();
    }
    
    // ------------------------------------------------------------------------
    Command getCommand()
    {
        return theCommand;
    }
}
