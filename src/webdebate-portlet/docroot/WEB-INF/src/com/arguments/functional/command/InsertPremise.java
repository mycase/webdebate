/**
 * 
 */
package com.arguments.functional.command;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsException;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.Perspective;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.Relevance;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.datamodel.ThesisText;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class InsertPremise implements Command
{
    private final ThesisText thePremiseText;
    private final Relevance theIfTruePremiseRelevance;
    private final Relevance theIfFalsePremiseRelevance;
    private final ArgumentsUser theAppUser;
    private final ThesisOpinion thePremiseOpinion;
    
    // ------------------------------------------------------------------------
    public InsertPremise(ThesisText aPremiseText, Relevance aIfTruePremiseRelevance,
            Relevance aIfFalsePremiseRelevance, ArgumentsUser anAppUser,
            ThesisOpinion aPremiseOpinion)
    {
        thePremiseText = aPremiseText;
        theIfTruePremiseRelevance = aIfTruePremiseRelevance;
        theIfFalsePremiseRelevance = aIfFalsePremiseRelevance;
        theAppUser = anAppUser;
        thePremiseOpinion = aPremiseOpinion;
    }

    // ------------------------------------------------------------------------
    @Override
    public void execute(ArgsState aState)
    {
        assert ! aState.getPerspectiveId().equals(PerspectiveId.getThesisOwner());
        ThesisId mySId = aState.getThesisId();
        ThesisId myPremiseId = TheArgsStore.i(theAppUser).newPremise(
                mySId, thePremiseText,
                theIfTruePremiseRelevance, theIfFalsePremiseRelevance);
        Perspective myPerspective = aState.getPerspectives().get(0);
        if (! myPerspective.isWritable())
            throw new ArgumentsException("User " + theAppUser + "can't write with this perspective: " + myPerspective);
        if (!myPerspective.getOwner().equals(theAppUser))
            myPerspective = theAppUser.getDefaultPerspective();
        TheArgsStore.i(theAppUser).setThesisOpinion(
                myPremiseId, thePremiseOpinion);
    }
}
