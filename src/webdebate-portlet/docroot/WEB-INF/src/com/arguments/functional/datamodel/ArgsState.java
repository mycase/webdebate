/**
 * 
 */
package com.arguments.functional.datamodel;

import static org.junit.Assert.*;

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
    private MPerspectiveId thePerspectiveId;
    
    // ------------------------------------------------------------------------
    public ArgsState(
            ThesisId aThesistId,
            RelationId aRelationId,
            PerspectiveId aPerspectiveId)
    {
        this(aThesistId, aRelationId, new MPerspectiveId(aPerspectiveId));
    }

    // ------------------------------------------------------------------------
    public ArgsState(
            ThesisId aThesistId,
            RelationId aRelationId,
            MPerspectiveId aPerspectiveId)
    {
        theThesisId = aThesistId;
        theRelationId = aRelationId;
        assertNotNull(aPerspectiveId);
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
        
        thePerspectiveId = new MPerspectiveId(aPerspectiveId);
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
        PerspectiveId myPerspectiveId = thePerspectiveId.get(0);
        assert myPerspectiveId != null;
        MPerspective myReturnValue = new MPerspective();
        
        if(myPerspectiveId.equals(PerspectiveId.getThesisOwner()))
        {
            myReturnValue.add(new ThesisOwnerPerspective());
        }
        if(myPerspectiveId.equals(PerspectiveId.VOLATILE))
            throw new AssertionError("Deprecated PerspectiveId: " + myPerspectiveId);
        myReturnValue.add(TheArgsStore.i().getPerspective(myPerspectiveId));
        
        return myReturnValue;
    }
    
    // ------------------------------------------------------------------------
    @Override
    public PerspectiveId getPerspectiveId()
    {
        return thePerspectiveId.get(0);
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
            thePerspectiveId = new MPerspectiveId(aStateChange.getPerspectiveId());
    }
}
