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
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateState;
import com.arguments.support.CgiParameterMap;
import com.arguments.support.Logger;
import com.arguments.support.PortletParameterMap;
import com.arguments.support.ServletParameterMap;

/**
 * @author mirleau
 * 
 */
public class ArgsActionRequest extends ArgsRequest
{
    private final CgiSource theStateInputMode;
    private final UpdateState theUpdateState;
    private ArgsStatefulCommand myStateCommand;

    // ------------------------------------------------------------------------
    public ArgsActionRequest(PortletParameterMap aParameterMap,
            ServletParameterMap aServletParameterMap, ArgumentsUser aUser)
    {
        super(aParameterMap, aServletParameterMap, aUser);
        theStateInputMode = CgiSource.PORTLET;
        theUpdateState = UpdateState.YES;
    }

    // ------------------------------------------------------------------------
    public void execute()
    {
        Logger.log("\n======= NEW REQUEST ===========\n");

        // This method performs a state update twice. First here:
        PortalArgsBridge.assureConnect();
        final ArgumentsUser myAppUser = getAppUser();

        final CgiParameterMap myParameterMap = 
                getCgiParameterMap(getStateInputMode());

        assertNotNull(myParameterMap);
        ProtocolMap myProtocolMap = TheContainerBridge.i().getProtocolMap(
                myParameterMap);

        Command myArgRequest = RequestParser.getCommand(myAppUser,
                myProtocolMap);

        if (getUpdateState() == UpdateState.YES)
        {
            StateChange myStateChange = new StateChange(myProtocolMap);
            if (myStateChange.hasChange())
            {
                myStateChange.mergeAndStore(myAppUser);
            }
        }
        ArgsState myArgsState = PortalArgsBridge.getState(myAppUser);
        myStateCommand = new ArgsStatefulCommand(myArgRequest, myArgsState);

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
