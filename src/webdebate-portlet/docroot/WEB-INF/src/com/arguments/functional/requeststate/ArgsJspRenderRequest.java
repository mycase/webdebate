/**
 * 
 */
package com.arguments.functional.requeststate;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.command.Command;
import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateStateFlag;

/**
 * @author  mirleau
 */
public class ArgsJspRenderRequest
{
    private final ArgsRequest theRequest;
    private final UrlContainer theUrlContainer;
    private final CgiSource theCgiSource;
    private final UpdateStateFlag theUpdateStateFlag;
    private final ArgsErrorHandler theErrorHandler;

    // ------------------------------------------------------------------------
    public ArgsJspRenderRequest(
            ArgsRequest aRequest,
            UrlContainer aUrlContainer,
            CgiSource aStateInputMode,
            UpdateStateFlag aUpdateState,
            ArgsErrorHandler anErrorHandler)
    {
        theRequest = aRequest;
        theUrlContainer = aUrlContainer;
        theCgiSource = aStateInputMode;
        theUpdateStateFlag = aUpdateState;
        theErrorHandler = anErrorHandler;
    }

    // ------------------------------------------------------------------------
    public ArgsStatefulRequest3 executeAndGetRenderRequest()
    {
        Command myCommand = TheContainerBridge.i().parseCommand(this);
        myCommand.execute(null);
        return TheContainerBridge.i().fetchStatefulRenderRequest(this);
    }
    
    // ------------------------------------------------------------------------
    public ArgsRequest getRequest()
    {
        return theRequest;
    }
    
    // ------------------------------------------------------------------------
    public UrlContainer getUrlContainer()
    {
        return theUrlContainer;
    }
    
    // ------------------------------------------------------------------------
    public CgiSource getStateInputMode()
    {
        return theCgiSource;
    }

    // ------------------------------------------------------------------------
    public UpdateStateFlag getUpdateState()
    {
        return theUpdateStateFlag;
    }

    // ------------------------------------------------------------------------
    public ArgsErrorHandler getErrorHandler()
    {
        return theErrorHandler;
    }
}