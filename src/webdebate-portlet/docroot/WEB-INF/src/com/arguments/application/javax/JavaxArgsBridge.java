package com.arguments.application.javax;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.datamodel.ArgsActionRequest;
import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsRenderRequest;
import com.arguments.functional.requeststate.PortalArgsBridge;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.support.Logger;
import com.arguments.support.PortletParameterMap;
import com.arguments.support.ServletParameterMap;

public abstract class JavaxArgsBridge extends PortalArgsBridge
{
    // ------------------------------------------------------------------------
    public ArgsRequest newArgsRequest(PortletRequest aRequest)
    {
        PortletParameterMap myParameterMap =
                new PortletParameterMap(aRequest.getParameterMap());
        ServletParameterMap myServletParameterMap = new ServletParameterMap(
            TheContainerBridge.i().
                getOriginalServletRequest(aRequest).getParameterMap());
        TheArgsStore.i().assureConnect();
        ArgumentsUser myUser = TheContainerBridge.i().getAppUser(aRequest);

        return new ArgsRequest(
                myParameterMap, myServletParameterMap, myUser);
    }
    
    // ------------------------------------------------------------------------
    public ArgsActionRequest newArgsActionRequest(PortletRequest aRequest)
    {
        return new ArgsActionRequest(newArgsRequest(aRequest));
    }
    
    // ------------------------------------------------------------------------
    public static ArgsRenderRequest getRenderRequest(
            RenderRequest aRequest,
            UrlContainer aUrlContainer,
            UpdateState anUpdateState)
    {
        Logger.log("\nJavaxArgsBridge.getRenderRequest()");
        final ArgsRequest myArgsRequest =
                TheContainerBridge.i().newArgsRequest(aRequest);
        final ArgsErrorHandler myErrorHandler =
                TheContainerBridge.i().newErrorHandler(
                        aRequest, myArgsRequest.getAppUser());
        
        final ArgsRenderRequest myRequest =
                new ArgsRenderRequest(
                        myArgsRequest, aUrlContainer,
                        CgiSource.SERVLET, anUpdateState, myErrorHandler);
        
        Logger.logRender(myRequest);
        return myRequest;
    }
    
    // ------------------------------------------------------------------------
    public abstract void processAction(ActionRequest aRequest);

    // ------------------------------------------------------------------------
    public abstract ArgumentsUser getAppUser(PortletRequest aPortletRequest);
    
    // ------------------------------------------------------------------------
    public abstract HttpServletRequest getOriginalServletRequest(
            PortletRequest aRequest);
    
    // ------------------------------------------------------------------------
    public abstract ArgsErrorHandler newErrorHandler(RenderRequest aRequest,
            ArgumentsUser aArgumentsUser);
    
    // ------------------------------------------------------------------------
    public abstract ProtocolMap getProtocolMap();
}
