/**
 * 
 */
package com.arguments.functional.report;

import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgsReadOnlyState;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.MPerspective;
import com.arguments.functional.datamodel.Perspective;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.PerspectiveThesisOpinion;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.report.pagemodels.PageModelFactory;
import com.arguments.functional.report.pagemodels.PageModel_Tester;
import com.arguments.functional.report.pagemodels.ThesisFocusPageModel;
import com.arguments.functional.requeststate.ArgsJspRequest;
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
    public void twoActivePerspectives1()
    {
        ArgumentsUser myUser1 = getTestUser2();
        // TheArgsStore.i(myUser1).insertActivePerspectiveId(PerspectiveId.P3, myUser1);
        ThesisId myThesisId = ArgsStatefulRequest_Tester.insertDifferentOpinions();
        TheArgsStore.i(myUser1).insertActivePerspectiveId(PerspectiveId.P3, myUser1);

        ArgsReadOnlyState myState = myUser1.getState(); 
        assertEquals( myState.getThesisId(), myThesisId);
        
        Perspective myPers1 = myUser1.getDefaultPerspective();
        ThesisFocusData myData = new ThesisFocusData(myThesisId, new MPerspective(myPers1));

    }

    // ------------------------------------------------------------------------
    @Test
    public void twoActivePerspectives2()
    {
        ArgumentsUser myUser1 = getTestUser1();
        TheArgsStore.i(myUser1).insertActivePerspectiveId(PerspectiveId.P3, myUser1);
        ArgsState myState = TheArgsStore.i().selectState(myUser1);
        MPerspective myPerspectives = myState.getPerspectives();
        assert myPerspectives.size() == 2;
        ThesisFocusData myData =
                new ThesisFocusData(ThesisId.Da13, myPerspectives);
        ArgsJspRequest myRequest =
                PageModel_Tester.getRequest("001_Direct_Cgi_Focus.json");
        ThesisFocusPageModel myPageModel = PageModelFactory.getThesisFocusPage(myRequest);

    }
    
    // ------------------------------------------------------------------------
    @Test
    public void twoActivePerspectives3()
    {
        ArgumentsUser myUser1 = getTestUser1();
        TheArgsStore.i(myUser1).insertActivePerspectiveId(PerspectiveId.P3, myUser1);
        ArgsState myState = TheArgsStore.i().selectState(myUser1);
        MPerspective myPerspectives = myState.getPerspectives();
        assert myPerspectives.size() == 2;
        ThesisFocusData myData =
                new ThesisFocusData(ThesisId.Da13, myPerspectives);
        
        assert myData.getPerspectives().size() == 2;
        
        
        ArgsJspRequest myRequest =
                PageModel_Tester.getRequest("MakeYourCase.2012.11.19.00.33.07.renderRequest.json");
        ThesisFocusPageModel myPageModel = PageModelFactory.getThesisFocusPage(myRequest);
        assert myPageModel.theHtml.contains("P1");
        assert myPageModel.theHtml.contains("P2");
    }
    
    // ------------------------------------------------------------------------
    @Test
    public void differentPerspectives()
    {
        ArgumentsUser myUser1 = getTestUser2();
        // User 1 creates a thesis, User 1 and User 2 insert opinions:
        // User 1 focusses on this thesis.
        ThesisId myThesisId = ArgsStatefulRequest_Tester.insertDifferentOpinions();

        ArgsReadOnlyState myState = myUser1.getState(); 
        assertEquals( myState.getThesisId(), myThesisId);
        
        Perspective myPers1 = myUser1.getDefaultPerspective();
        ThesisFocusData myData = new ThesisFocusData(myThesisId, new MPerspective(myPers1));
        Collection<PerspectiveThesisOpinion> myOpTheses = myData.getDifferentPerspectives();
        assertNotNull(myOpTheses);
        //for (PerspectiveThesisOpinion myPTO : myOpTheses)
        //    System.out.println(myPTO.getPerspective().toString());
        assertEquals(6, myOpTheses.size());
    }
    
    // ------------------------------------------------------------------------
    private static ArgumentsUser getTestUser1()
    {
        return TheArgsStore_Tester.getTestUser1();
    }

    // ------------------------------------------------------------------------
    private static ArgumentsUser getTestUser2()
    {
        return TheArgsStore_Tester.getTestUser2();
    }
}
