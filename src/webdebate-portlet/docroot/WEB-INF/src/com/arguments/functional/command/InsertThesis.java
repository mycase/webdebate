/**
 * 
 */
package com.arguments.functional.command;

import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgsReadOnlyState;
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
        ArgsReadOnlyState myState = theAppUser.getState();
        assertTrue( aState.getFirstPerspectiveId().isWritable());
        
        PerspectiveId myPerspective = myState.getFirstPerspectiveId();
        assertTrue( myPerspective.isWritable());
        assertTrue( aState.getFirstPerspectiveId().isWritable());
        ThesisId myNewThesisId = TheArgsStore.i(theAppUser).addThesis(
                theNewThesisText,
                theThesisOpinion);
        assertTrue( aState.getFirstPerspectiveId().isWritable());
        aState.setThesisId(myNewThesisId);
        assertTrue( aState.getFirstPerspectiveId().isWritable());
    }
}
