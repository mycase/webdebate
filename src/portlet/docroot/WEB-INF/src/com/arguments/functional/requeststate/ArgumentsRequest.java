package com.arguments.functional.requeststate;

import static org.junit.Assert.*;

import com.arguments.functional.command.Command;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.support.Logger;

public class ArgumentsRequest
{
    private final ProtocolMap theRequestMap;
    private final ArgumentsUser theUser;
    private final UrlContainer theUrlContainer;
    
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
        assertNotNull(aUrlContainer);
        
        theRequestMap = aProtocolMap;
        theUser = anAppUser;
        theUrlContainer = aUrlContainer;
        
        Logger.log("New arguments request: " + theRequestMap + "\nUser: " + theUser);
    }

    // ------------------------------------------------------------------------
    public StateChange getStateChange()
    {
        return new StateChange(theRequestMap);
    }

    // ------------------------------------------------------------------------
    void execute(ArgsState aState)
    {
        RequestParser myParser =
                new RequestParser(theUser, theRequestMap);
        
        Command myCommand = myParser.parseCommand();
        myCommand.execute(aState);
    }

    // ------------------------------------------------------------------------
    ArgumentsUser getUser()
    {
        return theUser;
    }
    
    // ------------------------------------------------------------------------
    RelationId getRelationId()
    {
        return RelationId.getRelationId(theRequestMap.get(ArgsRequestKey.RELATION_ID));
    }
    
    // ------------------------------------------------------------------------
    UrlContainer getUrlContainer()
    {
        assertNotNull(theUrlContainer);
        
        return theUrlContainer;
    }    
}
