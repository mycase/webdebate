package com.arguments.functional.datamodel;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsRequest2;
import com.arguments.functional.requeststate.ArgsStatefulCommand;
import com.arguments.functional.requeststate.StateChange;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateState;
import com.arguments.support.CgiParameterMap;
import com.arguments.support.Logger;
import com.arguments.support.PortletParameterMap;
import com.arguments.support.ServletParameterMap;

/**
 * @author mirleau
 *
 */

// Not so clear what the difference is between this class and
// ArgsStatefulCommand

public class ArgsRequest
{
    private final PortletParameterMap theParameterMap;
    private final ServletParameterMap theServletParameterMap;
    private final ArgumentsUser theUser;
    
    // ------------------------------------------------------------------------
    public ArgsRequest(
            PortletParameterMap aParameterMap,
            ServletParameterMap aServletParameterMap,
            ArgumentsUser aUser)
    {
        // TODO: Not test covered.
        theParameterMap = aParameterMap;
        theServletParameterMap = aServletParameterMap;
        theUser = aUser;
        assertNotSame(theServletParameterMap, null);
        assertNotSame(theParameterMap, null);
    }

    // ------------------------------------------------------------------------
    public CgiParameterMap getCgiParameterMap(CgiSource aSource)
    {
        if (aSource == CgiSource.SERVLET)
        {
            assertNotSame(theServletParameterMap, null);
            return theServletParameterMap;
        }
        assert theParameterMap != null;
        assertNotSame(theParameterMap, null);        
        return theParameterMap;
    }

    // ------------------------------------------------------------------------
    public ArgumentsUser getAppUser()
    {
        return theUser;
    }

    // ------------------------------------------------------------------------
    public void execute()
    {
        Logger.log("\n======= NEW REQUEST ===========\n");
        
        UrlContainer myUrlContainer = new UrlContainer();
        CgiSource myStateInputMode = CgiSource.PORTLET;
        UpdateState myUpdateState = UpdateState.YES;
        
        ArgsRequest2 myAR2 = new ArgsRequest2(
                this, myUrlContainer,
                myStateInputMode, myUpdateState, null);
        // This method performs a state update twice. First here:
        ArgsStatefulCommand myStateCommand =
                TheContainerBridge.i().storeStateGetArgumentsRequest(myAR2);
        // Not here, which is where you would want it
        ArgsState myState = myStateCommand.execute();
        StateChange myStateString = myState.getStateString();
        assertTrue(myStateString.hasChange());
        // Then a second time here:
        myStateString.mergeAndStore(getAppUser());
    }    
}
