package com.arguments.functional.requeststate;

import com.arguments.functional.command.Command;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.report.html.UrlContainer;

public class ArgumentsRequest extends ArgsRequest3
{
    private Command theCommand;
    
    // ------------------------------------------------------------------------
    public ArgumentsRequest(
            ArgumentsUser anAppUser,
            ProtocolMap aMap)
    {
        this(anAppUser, new UrlContainer(), aMap);
    }

    // ------------------------------------------------------------------------
    public ArgumentsRequest(
            ArgumentsUser anAppUser,
            UrlContainer aUrlContainer,
            ProtocolMap aProtocolMap)
    {
        super(anAppUser, aUrlContainer, aProtocolMap);
        RequestParser theParser =
                new RequestParser(getUser(), aProtocolMap);
        theCommand = theParser.parseCommand();
    }

    // ------------------------------------------------------------------------
    void execute(ArgsState aState)
    {
        theCommand.execute(aState);
    }
}
