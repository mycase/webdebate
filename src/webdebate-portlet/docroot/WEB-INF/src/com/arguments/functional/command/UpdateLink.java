/**
 * 
 */
package com.arguments.functional.command;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.Relevance;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class UpdateLink implements Command
{
    private final ArgumentsUser theUser;
    private final RelationId theLinkId;
    private final Relevance theNewIfTrueRelevance;
    private final Relevance theNewIfFalseRelevance;
    private final ThesisId theNewTargetId;
    
    // ------------------------------------------------------------------------
    public UpdateLink(
            ArgumentsUser anAppUser,
            RelationId aLinkId,
            Relevance aNewIfTrueRelevance,
            Relevance aNewIfFalseRelevance,
            ThesisId aNewTargetId)
    {
        assert aLinkId != null;
        assert aNewIfTrueRelevance != null;
        assert aNewIfFalseRelevance != null;
        assert aNewTargetId != null;
        
        theLinkId = aLinkId;
        theUser = anAppUser;
        theNewIfTrueRelevance = aNewIfTrueRelevance;
        theNewIfFalseRelevance = aNewIfFalseRelevance;
        theNewTargetId = aNewTargetId;
    }

    // ------------------------------------------------------------------------
    @Override
    public void execute(ArgsState aState)
    {
        TheArgsStore.i(theUser).setLinkInfo(
                theLinkId, theNewIfTrueRelevance, theNewIfFalseRelevance, theNewTargetId);
    }

}
