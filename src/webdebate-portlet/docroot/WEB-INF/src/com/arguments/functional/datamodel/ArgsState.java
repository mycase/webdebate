/**
 * 
 */
package com.arguments.functional.datamodel;

import com.arguments.functional.requeststate.StateChange;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.support.Logger;

/**
 * @author mirleau
 *
 */
public class ArgsState
{
    private ThesisId theThesisId;
    private RelationId theRelationId;
    private PerspectiveId thePerspectiveId;
    
    // ------------------------------------------------------------------------
    /**
     * @param aThesistId
     */
    public ArgsState(
            ThesisId aThesistId,
            RelationId aRelationId,
            PerspectiveId aPerspectiveId)
    {
        theThesisId = aThesistId;
        theRelationId = aRelationId;
        thePerspectiveId = aPerspectiveId;
    }

    // ------------------------------------------------------------------------
    /**
     * @return theThesisId
     */
    public ThesisId getThesisId()
    {
        return theThesisId;
    }

    // ------------------------------------------------------------------------
    /**
     * @param aNewThesisId the thesisId to set
     */
    public void setThesisId(ThesisId aNewThesisId)
    {
        theThesisId = aNewThesisId;
    }

    // ------------------------------------------------------------------------
    public void setPerspectiveId(PerspectiveId aPerspectiveId)
    {
        assert aPerspectiveId != null;
        
        thePerspectiveId = aPerspectiveId;
    }

    // ------------------------------------------------------------------------
    /**
     * @return
     */
    public StateChange getStateString()
    {
        return new StateChange(theThesisId, theRelationId, thePerspectiveId);
    }
    
    // ------------------------------------------------------------------------
    public RelationId getLinkId()
    {
        return theRelationId;
    }

    
    // ------------------------------------------------------------------------
    public Perspective getPerspective()
    {
        assert thePerspectiveId != null;

        if(thePerspectiveId.equals(PerspectiveId.getThesisOwner()))
            //throw new AssertionError("Deprecated PerspectiveId: " + thePerspectiveId);
            return new ThesisOwnerPerspective();
        if(thePerspectiveId.equals(PerspectiveId.READER1))
            throw new AssertionError("Deprecated PerspectiveId: " + thePerspectiveId);
            //return new ReaderPerspective(aUser);
        if(thePerspectiveId.equals(PerspectiveId.VOLATILE))
            throw new AssertionError("Deprecated PerspectiveId: " + thePerspectiveId);
        return TheArgsStore.i().getPerspective(thePerspectiveId);
    }
    
    // ------------------------------------------------------------------------
    public PerspectiveId getPerspectiveId()
    {
        return thePerspectiveId;
    }
    
    // ------------------------------------------------------------------------
    /**
     * @param aStateChange
     */
    public void mergeStateChange(StateChange aStateChange)
    {
        Logger.log("Merge State: " + aStateChange);

        if(aStateChange.getThesisId() != null)
            theThesisId = aStateChange.getThesisId();
            
        if (aStateChange.getRelationId() != null)
            theRelationId = aStateChange.getRelationId();
            
        if (aStateChange.getPerspectiveId() != null)
            thePerspectiveId = aStateChange.getPerspectiveId();
    }
}
