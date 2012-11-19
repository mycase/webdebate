/**
 * 
 */
package com.arguments.functional.requeststate;

import com.arguments.functional.datamodel.ArgsReadOnlyState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.Relation;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class ArgsStatefulRenderRequest
{
    private final ArgsRenderRequest theRequest;
    private final ArgsReadOnlyState theState;
    
    // ------------------------------------------------------------------------
    public ArgsStatefulRenderRequest(ArgsRenderRequest aRequest, ArgsReadOnlyState aState)
    {
        theRequest = aRequest;
        theState = aState;
        // assert theState.getPerspectiveId().isWritable();
    }

    // ------------------------------------------------------------------------
    public ArgsReadOnlyState getState()
    {
        return theState;
    }

    // ------------------------------------------------------------------------
    public UrlContainer getUrlContainer()
    {
        return theRequest.getUrlContainer();
    }

    // ------------------------------------------------------------------------
    public PerspectiveId getPerspective2Id()
    {
        return theRequest.getPerspective2Id();
    }

    // ------------------------------------------------------------------------
    private RelationId getRelationId()
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
}
