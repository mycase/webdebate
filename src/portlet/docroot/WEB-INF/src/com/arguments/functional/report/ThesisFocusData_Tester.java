/**
 * 
 */
package com.arguments.functional.report;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.Perspective;
import com.arguments.functional.datamodel.PerspectiveThesisOpinion;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.requeststate.ArgsStatefulRequest_Tester;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.functional.store.TheArgsStore_Tester;

/**
 * @author mirleau
 *
 */
public class ThesisFocusData_Tester
{
    // ------------------------------------------------------------------------
    @Before
    public void setUp()
    {
        TheArgsStore.i().deleteTestObjects();
    }

    // ------------------------------------------------------------------------
    @Test
    public void differentPerspectives()
    {
        ArgumentsUser myUser1 = getTestUser1();
        // User 1 creates a thesis, User 1 and User 2 insert opinions:
        // User 1 focusses on this thesis.
        ThesisId myThesisId = ArgsStatefulRequest_Tester.insertOpinion();

        ArgsState myState = myUser1.getState(); 
        assert myState.getThesisId().equals(myThesisId);
        
        Perspective myPers1 = myUser1.getDefaultPerspective();
        ThesisFocusData myData = new ThesisFocusData(myThesisId, myPers1);
        List<PerspectiveThesisOpinion> myOpTheses = myData.getDifferentPerspectives();
        assertNotNull(myOpTheses);
        assertEquals(2, myOpTheses.size());
        PerspectiveThesisOpinion myOpinion = myOpTheses.get(0);
        assertNotNull(myOpinion.getPerspectiveId());
    }
    
    // ------------------------------------------------------------------------
    private static ArgumentsUser getTestUser1()
    {
        return TheArgsStore_Tester.getTestUser2();
    }
}
