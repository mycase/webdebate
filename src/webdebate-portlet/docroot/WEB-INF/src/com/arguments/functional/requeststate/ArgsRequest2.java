/**
 * 
 */
package com.arguments.functional.requeststate;

import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateState;

/**
 * @author  mirleau
 */
public class ArgsRequest2
{
    private final ArgsRequest theRequest;
    private final UrlContainer theUrlContainer;
    private final CgiSource theStateInputMode;
    private final UpdateState theUpdateState;
    private final ArgsErrorHandler theErrorHandler;

    
    // ------------------------------------------------------------------------
    public ArgsRequest2(ArgsRequest aRequest, UrlContainer aUrlContainer,
            CgiSource aStateInputMode, UpdateState aUpdateState)
    {
        this(aRequest, aUrlContainer, aStateInputMode, aUpdateState, null);
    }
    
    // ------------------------------------------------------------------------
    public ArgsRequest2(
            ArgsRequest aRequest,
            UrlContainer aUrlContainer,
            CgiSource aStateInputMode,
            UpdateState aUpdateState,
            ArgsErrorHandler anErrorHandler)
    {
        theRequest = aRequest;
        theUrlContainer = aUrlContainer;
        theStateInputMode = aStateInputMode;
        theUpdateState = aUpdateState;
        theErrorHandler = anErrorHandler;
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
        return theStateInputMode;
    }

    // ------------------------------------------------------------------------
    public UpdateState getUpdateState()
    {
        return theUpdateState;
    }

    // ------------------------------------------------------------------------
    public ArgsErrorHandler getErrorHandler()
    {
        return theErrorHandler;
    }
}