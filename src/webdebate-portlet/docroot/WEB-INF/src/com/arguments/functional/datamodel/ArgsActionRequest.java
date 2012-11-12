/**
 * 
 */
package com.arguments.functional.datamodel;

import static org.junit.Assert.*;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.requeststate.ArgsStatefulCommand2;
import com.arguments.functional.requeststate.PortalArgsBridge;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.requeststate.RequestParser;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.support.CgiParameterMap;
import com.arguments.support.Logger;
import com.arguments.support.PortletParameterMap;
import com.arguments.support.ServletParameterMap;

/**
 * @author mirleau
 * 
 */
//   This class' responsibility is to take over from foreign requests as soon
//   as possible, and provide an entry point for
//   testcases that simulate such foreign requests, like Macro_Tester.

public class ArgsActionRequest extends ArgsRequest
{
    // ------------------------------------------------------------------------
    public ArgsActionRequest(
            PortletParameterMap aParameterMap,
            ServletParameterMap aServletParameterMap,
            ArgumentsUser aUser)
    {
        super(aParameterMap, aServletParameterMap, aUser);
    }

    // ------------------------------------------------------------------------
    public void execute()
    {
        Logger.log("\n======= ArgsActionRequest.execute() ===========\n");

        PortalArgsBridge.assureConnect();
        getCommand().execute();
    }

    // ------------------------------------------------------------------------
    private ArgsStatefulCommand2 getCommand()
    {
        return RequestParser.getStatefulCommand(
                getAppUser(), getProtocolMap());
    }

    // ------------------------------------------------------------------------
    private ProtocolMap getProtocolMap()
    {
        final CgiParameterMap myParameterMap =
                getCgiParameterMap(CgiSource.PORTLET);

        assertNotNull(myParameterMap);
        return TheContainerBridge.i().getProtocolMap(myParameterMap);
    }
}
