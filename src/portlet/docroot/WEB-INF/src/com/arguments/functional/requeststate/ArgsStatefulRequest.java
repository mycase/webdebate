/**
 * 
 */
package com.arguments.functional.requeststate;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.Perspective;
import com.arguments.functional.datamodel.Relation;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class ArgsStatefulRequest
{
    private final ArgumentsRequest theRequest;
    private final ArgsState theState;
    
    // ------------------------------------------------------------------------
    public ArgsStatefulRequest(ArgumentsRequest aRequest, ArgsState aState)
    {
        super();
        theRequest = aRequest;
        theState = aState;
        // assert theState.getPerspectiveId().isWritable();
    }

    // ------------------------------------------------------------------------
    public ArgsState getState()
    {
        return theState;
    }

    
    // ------------------------------------------------------------------------
    public ArgsState execute()
    {
        theRequest.execute(theState);
        return theState;
    }
    
    // ------------------------------------------------------------------------
    public UrlContainer getUrlContainer()
    {
        return theRequest.getUrlContainer();
    }

    // ------------------------------------------------------------------------
    public RelationId getRelationId()
    {
        return theRequest.getRelationId();
    }

    // ------------------------------------------------------------------------
    public ArgumentsUser getUser()
    {
        return theRequest.getUser();
    }
    
    // ------------------------------------------------------------------------
    public Relation getActiveLink()
    {
        RelationId myRelationId = getRelationId();
        return TheArgsStore.i().selectRelationById(myRelationId);
    }
    
    // ------------------------------------------------------------------------
    public OpinionatedThesis getActiveThesis()
    {
        ThesisId myThesisId = theState.getThesisId();
        OpinionatedThesis myThesis =
                    TheArgsStore.i().getThesis(myThesisId,
                            getUser().getDefaultPerspective());
        return myThesis;
    }

    // ------------------------------------------------------------------------
    public String getPerspectiveIdText()
    {
        ArgumentsUser myUser = getUser();
        ArgsState myState = getState();
        Perspective myPerspective = myState.getPerspective();
        return myPerspective.getIdString();
    }
}
