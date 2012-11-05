/**
 * 
 */
package com.arguments.functional.command;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.datamodel.ThesisText;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class UpdateThesis implements Command
{
    private final ThesisText theModifiedThesisText;
    private final ArgumentsUser theUser;
    private final ThesisOpinion theModifiedOpinion;

    // ------------------------------------------------------------------------
    public UpdateThesis(
            ArgumentsUser aUser, 
            ThesisText aModifiedThesisText,
            ThesisOpinion aModifiedOpinion)
    {
        assert aModifiedThesisText != null;
        assert aModifiedOpinion != null;
        
        theUser = aUser;
        theModifiedThesisText = aModifiedThesisText;
        theModifiedOpinion = aModifiedOpinion;
    }

    // ------------------------------------------------------------------------
    @Override
    public void execute(ArgsState aState)
    {
        ThesisId mySId = aState.getThesisId();
        PerspectiveId myPerspective = aState.getPerspectiveId();
        assert myPerspective.isWritable() : "Non writable: " + myPerspective;

        TheArgsStore.i(theUser).setThesisInfo(
                mySId, theModifiedThesisText,
                theModifiedOpinion);
    }
}
