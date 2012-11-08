/**
 * 
 */
package com.arguments.functional.command;

import static org.junit.Assert.*;

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
public class InsertThesis implements Command
{
    private final ThesisText theNewThesisText;
    private final ThesisOpinion theThesisOpinion;
    private final ArgumentsUser theAppUser;
    
    // ------------------------------------------------------------------------
    public InsertThesis(
            ThesisText aNewThesisText,
            ArgumentsUser anAppUser,
            ThesisOpinion aThesisOpinion)
    {
        assertNotNull(aNewThesisText);
        assertNotNull(aThesisOpinion);
        
        theNewThesisText = aNewThesisText;
        theAppUser = anAppUser;
        theThesisOpinion = aThesisOpinion;
    }

    // ------------------------------------------------------------------------
    @Override
    public void execute(ArgsState aState)
    {
        ArgsState myState = theAppUser.getState();
        assert aState.getPerspectiveId().isWritable();
        
        PerspectiveId myPerspective = myState.getPerspectiveId();
        assert myPerspective.isWritable();
        assert aState.getPerspectiveId().isWritable();
        ThesisId myNewThesisId = TheArgsStore.i(theAppUser).addThesis(
                theNewThesisText,
                theThesisOpinion);
        assert aState.getPerspectiveId().isWritable();
        aState.setThesisId(myNewThesisId);
        assert aState.getPerspectiveId().isWritable();
    }
}
