package com.arguments.functional.requeststate;

import com.arguments.functional.command.Command;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.report.html.UrlContainer;

public class ArgumentsRequest extends ArgsRequest3
{
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
    }

    // ------------------------------------------------------------------------
    void execute(ArgsState aState)
    {
        Command myCommand;
        RequestParser myParser =
                new RequestParser(getUser(), theRequestMap);
        
        myCommand = myParser.parseCommand();
        myCommand.execute(aState);
    }
}
