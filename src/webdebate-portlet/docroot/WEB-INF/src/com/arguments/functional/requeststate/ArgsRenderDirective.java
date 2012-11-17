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
public class ArgsRenderDirective
{
    private final ArgumentsUser theUser;
    private final UrlContainer theUrlContainer;
    private final RelationId theRelationId;
    private final RelationId thePerspective2Id;

    // ------------------------------------------------------------------------
    public ArgsRenderDirective(
            ArgumentsUser anAppUser,
            UrlContainer aUrlContainer,
            ProtocolMap aProtocolMap)
    {
        assertNotNull(aUrlContainer);
        
        theUser = anAppUser;
        theUrlContainer = aUrlContainer;
        
        Logger.log("New arguments request: " + aProtocolMap + "\nUser: " + theUser);
        
        theRelationId = RelationId.parseRelationId(aProtocolMap.get(ArgsRequestKey.RELATION_ID));
        thePerspective2Id = RelationId.parseRelationId(aProtocolMap.get(ArgsRequestKey.PERSPECTIVE2_ID));
    }
    
    // ------------------------------------------------------------------------
    public ArgsRenderDirective(
            ArgumentsUser anAppUser,
            ProtocolMap aMap)
    {
        this(anAppUser, new UrlContainer(), aMap);
    }

    // ------------------------------------------------------------------------
    RelationId getRelationId()
    {
        return theRelationId;
    }
    
    // ------------------------------------------------------------------------
    RelationId getPerspective2Id()
    {
        return thePerspective2Id;
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
