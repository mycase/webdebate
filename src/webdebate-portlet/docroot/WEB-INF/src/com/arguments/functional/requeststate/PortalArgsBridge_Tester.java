/**
 * 
 */
package com.arguments.functional.requeststate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.arguments.functional.datamodel.ArgsActionRequest;
import com.arguments.functional.report.PerspectiveNotOwnedException;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.support.CgiParameterMap;

/**
 * @author mirleau
 *
 */
public class PortalArgsBridge_Tester
{
    // ------------------------------------------------------------------------
    @Before
    public void setUp()
    {
        TheArgsStore.i().deleteTestObjects();
    }

    // ------------------------------------------------------------------------
    @Test
    public void changePerspective()
    {
        ArgsActionRequest myRequest = Macro_Tester.getRequest("010_ChangePerspective.json");
        myRequest.execute();
    }

    // ------------------------------------------------------------------------
    @Test
    public void gotoThesis()
    {
        ArgsActionRequest myRequest1 =
                Macro_Tester.getRequest("MakeYourCase.2012.11.07.23.06.03.actionRequest.json");
        myRequest1.execute();
        //ArgsActionRequest myRequest2 =
        //        Macro_Tester.getRequest("MakeYourCase.2012.11.12.23.06.03.actionRequest.json");
        //myRequest2.execute();
    }

    // ------------------------------------------------------------------------
    @Test
    public void cantAddThesisUnderWrongPerspective()
    {
        long myNrThesesBefore = TheArgsStore.i().getNrOfTheses();
        // testuser1 changes to testuser2 main perspective:
        ArgsActionRequest myRequest = Macro_Tester.getRequest("MakeYourCase.2012.10.27.16.04.41.actionRequest.json");
        myRequest.execute();
        // testuser1 enters a new thesis with opinion (under non-owned perspective):
        myRequest = Macro_Tester.getRequest("MakeYourCase.2012.10.27.16.06.55.actionRequest.json");
        
        try
        {
            myRequest.execute();
        }
        catch (PerspectiveNotOwnedException anException)
        {
            long myNrThesesAfter = TheArgsStore.i().getNrOfTheses();
            assertEquals(myNrThesesBefore, myNrThesesAfter);
            return;
        }

        throw new AssertionError("Expected exception not thrown");
        // This corrupts the database, fix as follows:
        // delete from Opinions where StatementID in (select ID from Statements where Owner = 2);
    }

    // ------------------------------------------------------------------------
    @Test
    public void cantAddPremiseUnderWrongPerspective()
    {
        long myNrThesesBefore = TheArgsStore.i().getNrOfTheses();
        // testuser1 changes to testuser2 main perspective:
        ArgsActionRequest myRequest = Macro_Tester.getRequest("MakeYourCase.2012.10.27.20.26.51.actionRequest.json");
        myRequest.execute();
        // testuser1 adds a premise (under non-owned perspective):
        myRequest = Macro_Tester.getRequest("MakeYourCase.2012.10.27.20.27.22.actionRequest.json");
        
        try
        {
            myRequest.execute();
        }
        catch (PerspectiveNotOwnedException anException)
        {
            long myNrThesesAfter = TheArgsStore.i().getNrOfTheses();
            assertEquals(myNrThesesBefore, myNrThesesAfter);
            return;
        }

        throw new AssertionError("Expected exception not thrown");
        // This corrupts the database, fix as follows:
        // delete from Opinions where StatementID in (select ID from Statements where Owner = 2);
    }

    // ------------------------------------------------------------------------
    @Test
    public void testProcessAction()
    {
        PortalArgsBridge myBridge = 
                new PortalArgsBridge(){

                    @Override
                    public ProtocolMap getProtocolMap(
                            CgiParameterMap aParameterMap)
                    {
                        return null;
                    }
                };
        myBridge.getProtocolMap(null);
    }
}
