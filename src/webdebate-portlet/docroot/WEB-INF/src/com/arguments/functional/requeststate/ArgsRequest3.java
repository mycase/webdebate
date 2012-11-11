/**
 * 
 */
package com.arguments.functional.requeststate;

import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.support.Logger;

/**
 * @author mirleau
 *
 */
public class ArgsRequest3
{
    protected final ProtocolMap theRequestMap;
    private final ArgumentsUser theUser;
    private final UrlContainer theUrlContainer;
    private final StateChange theStateChange;
    private final RelationId theRelationId;
    

    // ------------------------------------------------------------------------
    public ArgsRequest3(
            ArgumentsUser anAppUser,
            UrlContainer aUrlContainer,
            ProtocolMap aProtocolMap)
    {
        assertNotNull(aUrlContainer);
        
        theRequestMap = aProtocolMap;
        theUser = anAppUser;
        theUrlContainer = aUrlContainer;
        
        Logger.log("New arguments request: " + theRequestMap + "\nUser: " + theUser);
        
        theStateChange = new StateChange(theRequestMap);
        theRelationId = RelationId.getRelationId(theRequestMap.get(ArgsRequestKey.RELATION_ID));
    }
    
    // ------------------------------------------------------------------------
    RelationId getRelationId()
    {
        return theRelationId;
    }
    
    // ------------------------------------------------------------------------
    public StateChange getStateChange()
    {
        return theStateChange;
    }

    // ------------------------------------------------------------------------
    ArgumentsUser getUser()
    {
        return theUser;
    }
    
    // ------------------------------------------------------------------------
    UrlContainer getUrlContainer()
    {
        return theUrlContainer;
    }    
}
