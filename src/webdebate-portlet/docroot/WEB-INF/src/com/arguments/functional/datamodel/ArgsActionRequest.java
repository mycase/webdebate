/**
 * 
 */
package com.arguments.functional.datamodel;

import static org.junit.Assert.assertTrue;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsRequest2;
import com.arguments.functional.requeststate.ArgsStatefulCommand;
import com.arguments.functional.requeststate.StateChange;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateState;
import com.arguments.support.Logger;
import com.arguments.support.PortletParameterMap;
import com.arguments.support.ServletParameterMap;

/**
 * @author mirleau
 *
 */
public class ArgsActionRequest extends ArgsRequest
{
    private final UrlContainer theUrlContainer;
    private final CgiSource theStateInputMode;
    private final UpdateState theUpdateState;
    
    // ------------------------------------------------------------------------
    public ArgsActionRequest(
            PortletParameterMap aParameterMap,
            ServletParameterMap aServletParameterMap,
            ArgumentsUser aUser)
    {
        super(aParameterMap, aServletParameterMap, aUser);
        theUrlContainer = new UrlContainer();
        theStateInputMode = CgiSource.PORTLET;
        theUpdateState = UpdateState.YES;
        
    }

    // ------------------------------------------------------------------------
    public void execute()
    {
        Logger.log("\n======= NEW REQUEST ===========\n");
        
        // This method performs a state update twice. First here:
        final ArgsStatefulCommand myStateCommand =
                TheContainerBridge.i().storeStateGetArgumentsRequest(this);
        // Not here, which is where you would want it
        ArgsState myState = myStateCommand.execute();
        StateChange myStateString = myState.getStateString();
        assertTrue(myStateString.hasChange());
        // Then a second time here:
        myStateString.mergeAndStore(getAppUser());
    }

    
    // ------------------------------------------------------------------------
    public CgiSource getStateInputMode()
    {
        return theStateInputMode;
    }

    
    // ------------------------------------------------------------------------
    public UpdateState getUpdateState()
    {
        return theUpdateState;
    }    
}
