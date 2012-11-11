/**
 * 
 */
package com.arguments.functional.requeststate;

import static org.junit.Assert.assertTrue;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateState;

/**
 * @author mirleau
 *
 */
public class ArgsRequest4
{
    private final ArgsRequest2 theAR2;

    // ------------------------------------------------------------------------
    public ArgsRequest4(ArgsRequest aRequest, UrlContainer aUrlContainer,
            CgiSource aStateInputMode, UpdateState aUpdateState)
    {
        theAR2 = new ArgsRequest2(aRequest, aUrlContainer, aStateInputMode, aUpdateState, null);
    }
    
    // ------------------------------------------------------------------------
    public void execute()
    {
        // This method performs a state update twice. First here:
        ArgsStatefulCommand myArgsRequest =
                TheContainerBridge.i().storeStateGetArgumentsRequest(theAR2);
        // Not here, which is where you would want it
        ArgsState myState = myArgsRequest.execute();
        StateChange myStateString = myState.getStateString();
        assertTrue(myStateString.hasChange());
        // Then a second time here:
        myStateString.mergeAndStore(theAR2.getAppUser());
    }
}
