/**
 * 
 */
package com.arguments.functional.command;

import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class InsertOpinion implements Command
{
    private final ThesisOpinion theThesisOpinion;
    private final ArgumentsUser theAppUser;

    
    // ------------------------------------------------------------------------
    public InsertOpinion(ArgumentsUser anAppUser, ThesisOpinion aThesisOpinion)
    {
        assertTrue( aThesisOpinion != null);

        theAppUser = anAppUser;
        theThesisOpinion = aThesisOpinion;
    }

    // ------------------------------------------------------------------------
    @Override
    public void execute(ArgsState aState)
    {
        ThesisId myThesisId = aState.getThesisId();
        TheArgsStore.i(theAppUser).addOpinion(
            myThesisId, theThesisOpinion);
    }
}
