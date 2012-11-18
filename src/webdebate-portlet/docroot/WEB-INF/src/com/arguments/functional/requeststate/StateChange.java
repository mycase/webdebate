/**
 * 
 */
package com.arguments.functional.requeststate;

import org.apache.commons.lang3.StringUtils;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.MPerspectiveId;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class StateChange
{
    private final String theStateString;

    private final boolean theHasChange;
    private final ThesisId theThesisId;
    private final RelationId theRelationId;
    private final MPerspectiveId thePerspectiveId;
    
    // ------------------------------------------------------------------------
    public StateChange(
            ThesisId aThesisId,
            RelationId aRelationId,
            PerspectiveId aPerspectiveId)
    {
        this(aThesisId, aRelationId, getMPerspectiveId(aPerspectiveId));
    }
    
    
    // ------------------------------------------------------------------------
    public StateChange(
            ThesisId aThesisId,
            RelationId aRelationId,
            MPerspectiveId aPerspectiveId)
    {
        theThesisId = aThesisId;
        theRelationId = aRelationId;
        thePerspectiveId = aPerspectiveId;

        theHasChange = (theThesisId != null) || (theRelationId != null) || (thePerspectiveId != null);

        theStateString = theThesisId +","+theRelationId+
                ","+(thePerspectiveId == null?null:
                    StringUtils.join(thePerspectiveId, ","));
    }
    
    // ------------------------------------------------------------------------
    public StateChange(ProtocolMap aRequestMap)
    {
        this(
            ThesisId.parse(aRequestMap.get(ArgsRequestKey.THESIS_ID)),
            null,
            PerspectiveId.parsePerspectiveId(aRequestMap.get(ArgsRequestKey.PERSPECTIVE_ID)));
    }

    // ------------------------------------------------------------------------
    private static MPerspectiveId getMPerspectiveId(PerspectiveId aPerspectiveId)
    {
        return aPerspectiveId == null ? null : new MPerspectiveId(aPerspectiveId);
    }

    // ------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return theStateString;
    }
    
    // ------------------------------------------------------------------------
    public boolean hasChange()
    {
        return theHasChange;
    }

    // ------------------------------------------------------------------------
    public void mergeAndStore(ArgumentsUser aUser)
    {
        ArgsState myState = TheArgsStore.i().selectState(aUser);
        myState.mergeStateChange(this);
        TheArgsStore.i(aUser).updateState(myState);
    }

    // ------------------------------------------------------------------------
    public ThesisId getThesisId()
    {
        return theThesisId;
    }

    // ------------------------------------------------------------------------
    public RelationId getRelationId()
    {
        return theRelationId;
    }

    // ------------------------------------------------------------------------
    public PerspectiveId getPerspectiveId()
    {
        return thePerspectiveId == null? null:thePerspectiveId.get(0);
    }
}
