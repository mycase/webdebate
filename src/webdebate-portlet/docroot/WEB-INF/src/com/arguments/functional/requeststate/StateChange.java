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
    private final MPerspectiveId theSetPerspectiveIds;
    private final PerspectiveId theAddPerspectiveId;
    private final PerspectiveId theRemovePerspectiveId;
    
    // ------------------------------------------------------------------------
    public StateChange(
            ThesisId aThesisId,
            RelationId aRelationId,
            PerspectiveId aSetPerspectiveId,
            PerspectiveId anAddPerspectiveId,
            PerspectiveId aRemovePerspectiveId)
    {
        this(aThesisId, aRelationId, getMPerspectiveId(aSetPerspectiveId), anAddPerspectiveId, aRemovePerspectiveId);
    }
    
    
    // ------------------------------------------------------------------------
    public StateChange(
            ThesisId aThesisId,
            RelationId aRelationId,
            MPerspectiveId aPerspectiveIds,
            PerspectiveId anAddPerspectiveId,
            PerspectiveId aRemovePerspectiveId)
    {
        theThesisId = aThesisId;
        theRelationId = aRelationId;
        theSetPerspectiveIds = aPerspectiveIds;
        theAddPerspectiveId = anAddPerspectiveId;
        theRemovePerspectiveId = aRemovePerspectiveId;
        
        theHasChange =
                (theThesisId != null) ||
                (theRelationId != null) || (theSetPerspectiveIds != null) ||
                (theAddPerspectiveId != null) || (theRemovePerspectiveId != null);

        theStateString = theThesisId +","+theRelationId+
                ","+(theSetPerspectiveIds == null?null:
                    StringUtils.join(theSetPerspectiveIds, ",") + "," + theAddPerspectiveId + "," + theRemovePerspectiveId);
    }
    
    // ------------------------------------------------------------------------
    public StateChange(ProtocolMap aRequestMap)
    {
        this(
            ThesisId.parse(aRequestMap.get(ArgsRequestKey.THESIS_ID)),
            null,
            PerspectiveId.parsePerspectiveId(aRequestMap.get(ArgsRequestKey.SET_PERSPECTIVE_ID)),
            PerspectiveId.parsePerspectiveId(aRequestMap.get(ArgsRequestKey.ADD_PERSPECTIVE_ID)),
            PerspectiveId.parsePerspectiveId(aRequestMap.get(ArgsRequestKey.REMOVE_PERSPECTIVE_ID))
            );
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
        return theSetPerspectiveIds == null? null:theSetPerspectiveIds.get(0);
    }

    // ------------------------------------------------------------------------
    public PerspectiveId getAddPerspectiveId()
    {
        return theAddPerspectiveId;
    }

    // ------------------------------------------------------------------------
    public PerspectiveId getRemovePerspectiveId()
    {
        return theRemovePerspectiveId;
    }
}
