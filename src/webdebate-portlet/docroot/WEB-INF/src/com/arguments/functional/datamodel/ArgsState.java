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
    private MPerspectiveId thePerspectiveIds;
    
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
        thePerspectiveIds = aPerspectiveId;
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
        
        thePerspectiveIds = new MPerspectiveId(aPerspectiveId);
    }

    // ------------------------------------------------------------------------
    @Override
    public StateChange getStateChange()
    {
        return new StateChange(theThesisId, theRelationId, thePerspectiveIds, null, null);
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
        MPerspective myReturnValue = new MPerspective();
        for (PerspectiveId myPerspectiveId : thePerspectiveIds)
            myReturnValue.add(getPerspective(myPerspectiveId));
        
        return myReturnValue;
    }
    
    // ------------------------------------------------------------------------
    public Perspective getPerspective(PerspectiveId myPerspectiveId)
    {
        assert myPerspectiveId != null;
        
        if(myPerspectiveId.equals(PerspectiveId.getThesisOwner()))
        {
            return new ThesisOwnerPerspective();
        }
        if(myPerspectiveId.equals(PerspectiveId.VOLATILE))
            throw new AssertionError("Deprecated PerspectiveId: " + myPerspectiveId);
        return TheArgsStore.i().getPerspective(myPerspectiveId);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public PerspectiveId getFirstPerspectiveId()
    {
        return thePerspectiveIds.get(0);
    }
    
    // ------------------------------------------------------------------------
    public void mergeStateChange(StateChange aStateChange)
    {
        Logger.log("Merge State: " + aStateChange);

        if(aStateChange.getThesisId() != null)
            theThesisId = aStateChange.getThesisId();
            
        if (aStateChange.getRelationId() != null)
            theRelationId = aStateChange.getRelationId();
            
        if (aStateChange.getPerspectiveId() != null)
            thePerspectiveIds = new MPerspectiveId(aStateChange.getPerspectiveId());

        if (aStateChange.getAddPerspectiveId() != null)
            if (!thePerspectiveIds.contains(aStateChange.getAddPerspectiveId()))
                thePerspectiveIds.add(aStateChange.getAddPerspectiveId());
        
        if (aStateChange.getRemovePerspectiveId() != null)
            if (thePerspectiveIds.contains(aStateChange.getRemovePerspectiveId()))
                thePerspectiveIds.remove(aStateChange.getRemovePerspectiveId());
    }

    // ------------------------------------------------------------------------
    @Override
    public Perspective getFirstPerspective()
    {
        return getPerspective(thePerspectiveIds.get(0));
    }

    // ------------------------------------------------------------------------
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((thePerspectiveIds == null) ? 0 : thePerspectiveIds
                        .hashCode());
        result = prime * result
                + ((theRelationId == null) ? 0 : theRelationId.hashCode());
        result = prime * result
                + ((theThesisId == null) ? 0 : theThesisId.hashCode());
        return result;
    }

    // ------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ArgsState other = (ArgsState) obj;
        if (thePerspectiveIds == null)
        {
            if (other.thePerspectiveIds != null)
                return false;
        } else if (!thePerspectiveIds.equals(other.thePerspectiveIds))
            return false;
        if (theRelationId == null)
        {
            if (other.theRelationId != null)
                return false;
        } else if (!theRelationId.equals(other.theRelationId))
            return false;
        if (theThesisId == null)
        {
            if (other.theThesisId != null)
                return false;
        } else if (!theThesisId.equals(other.theThesisId))
            return false;
        return true;
    }
}
