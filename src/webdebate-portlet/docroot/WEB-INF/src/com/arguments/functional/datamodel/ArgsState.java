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
public class ArgsState implements ArgsReadOnlyState
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
    @Override
    public ThesisId getThesisId()
    {
        return theThesisId;
    }

    // ------------------------------------------------------------------------
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
    @Override
    public StateChange getStateChange()
    {
        return new StateChange(theThesisId, theRelationId, thePerspectiveId);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public RelationId getLinkId()
    {
        return theRelationId;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public MPerspective getPerspectives()
    {
        assert thePerspectiveId != null;
        MPerspective myReturnValue = new MPerspective();
        
        if(thePerspectiveId.equals(PerspectiveId.getThesisOwner()))
        {
            myReturnValue.add(new ThesisOwnerPerspective());
        }
        if(thePerspectiveId.equals(PerspectiveId.VOLATILE))
            throw new AssertionError("Deprecated PerspectiveId: " + thePerspectiveId);
        myReturnValue.add(TheArgsStore.i().getPerspective(thePerspectiveId));
        
        //Perspective myOther = TheArgsStore.i().getPerspective(PerspectiveId.P4);
        //myReturnValue.add(myOther);
        return myReturnValue;
    }
    
    // ------------------------------------------------------------------------
    @Override
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
