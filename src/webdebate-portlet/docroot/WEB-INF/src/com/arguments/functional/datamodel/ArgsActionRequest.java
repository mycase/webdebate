/**
 * 
 */
package com.arguments.functional.datamodel;

import static org.junit.Assert.*;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.command.Command;
import com.arguments.functional.requeststate.ArgsStatefulCommand;
import com.arguments.functional.requeststate.PortalArgsBridge;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.requeststate.RequestParser;
import com.arguments.functional.requeststate.StateChange;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.support.CgiParameterMap;
import com.arguments.support.Logger;
import com.arguments.support.PortletParameterMap;
import com.arguments.support.ServletParameterMap;

/**
 * @author mirleau
 * 
 */
// Two concerns:
//   1. To take over from foreign requests as soon
//   as possible, and provide an entry point for
//   testcases that simulate foreign requests, like Macro_Tester.
//   2. To execute state changes.
// TODO: Separate these two concerns by moving state change functionality
//   to ArgsStatefulCommand

public class ArgsActionRequest extends ArgsRequest
{
    private ArgsStatefulCommand myStateCommand;
    private CgiParameterMap myParameterMap;
    
    // ------------------------------------------------------------------------
    public ArgsActionRequest(PortletParameterMap aParameterMap,
            ServletParameterMap aServletParameterMap, ArgumentsUser aUser)
    {
        super(aParameterMap, aServletParameterMap, aUser);
    }

    // ------------------------------------------------------------------------
    public void execute()
    {
        Logger.log("\n======= NEW REQUEST ===========\n");

        // This method performs a state update twice. First here:

        PortalArgsBridge.assureConnect();
        // These lines cannot be lifted to the Constructor because
        // macro testcases will not set their values as fields.
        myParameterMap = getCgiParameterMap(CgiSource.PORTLET);

        assertNotNull(myParameterMap);
        final ProtocolMap myProtocolMap = TheContainerBridge.i().getProtocolMap(
                myParameterMap);

        final Command myCommand = RequestParser.getCommand(getAppUser(),
                myProtocolMap);

        final StateChange myStateChange = new StateChange(myProtocolMap);
        if (myStateChange.hasChange())
        {
            myStateChange.mergeAndStore(getAppUser());
        }
        final ArgsState myArgsState = PortalArgsBridge.getState(getAppUser());
        myStateCommand = new ArgsStatefulCommand(myCommand, myArgsState);

        // Not here, which is where you would want it
        ArgsState myState = myStateCommand.execute();
        StateChange myStateString = myState.getStateString();
        assertTrue(myStateString.hasChange());
        // Then a second time here:
        myStateString.mergeAndStore(getAppUser());
    }
}
