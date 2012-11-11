package com.arguments.functional.requeststate;

import com.arguments.functional.command.Command;
import com.arguments.functional.datamodel.ArgumentsUser;

public class ArgumentsRequest
{
    // ------------------------------------------------------------------------
    public static Command getRequest(
            ArgumentsUser anAppUser,
            ProtocolMap aProtocolMap)
    {
        RequestParser myParser =
                new RequestParser(anAppUser, aProtocolMap);
        
        Command theCommand = myParser.parseCommand();
        return theCommand;
    }
}
