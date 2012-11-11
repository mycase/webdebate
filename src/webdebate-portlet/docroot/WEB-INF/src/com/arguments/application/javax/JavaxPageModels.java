/**
 * 
 */
package com.arguments.application.javax;

import javax.portlet.RenderRequest;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.report.pagemodels.AddOpinionPageModel;
import com.arguments.functional.report.pagemodels.AddPremisePageModel;
import com.arguments.functional.report.pagemodels.AddThesisPageModel;
import com.arguments.functional.report.pagemodels.ChangePerspectivePageModel;
import com.arguments.functional.report.pagemodels.EditLinkPageModel;
import com.arguments.functional.report.pagemodels.EditThesisPageModel;
import com.arguments.functional.report.pagemodels.GotoThesisPageModel;
import com.arguments.functional.report.pagemodels.ListThesesPageModel;
import com.arguments.functional.report.pagemodels.PageModelFactory;
import com.arguments.functional.report.pagemodels.ThesisFocusPageModel;
import com.arguments.functional.requeststate.ArgsRequest2;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateState;
import com.arguments.support.Logger;

/**
 * @author mirleau
 * 
 */
public class JavaxPageModels
{
    public final static ProtocolMap theProtocolMap =
            TheContainerBridge.i().getProtocolMap();
    
    // ------------------------------------------------------------------------
    public static AddOpinionPageModel getAddOpinionPage(
            RenderRequest aRenderRequest)
    {
        ArgsRequest2 myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getAddOpinionPage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static AddPremisePageModel getAddPremisePage(
            RenderRequest aRenderRequest)
    {
        ArgsRequest2 myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getAddPremisePage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static AddThesisPageModel getAddThesisPage(
            RenderRequest aRenderRequest)
    {
        ArgsRequest2 myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getAddThesisPage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static ChangePerspectivePageModel getChangePerspectivePage(
            RenderRequest aRenderRequest)
    {
        ArgsRequest2 myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getChangePerspectivePage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static EditThesisPageModel getEditThesisPage(
            RenderRequest aRenderRequest)
    {
        ArgsRequest2 myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getEditThesisPage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static GotoThesisPageModel getGotoThesisPage(
            RenderRequest aRenderRequest)
    {
        ArgsRequest2 myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getGotoThesisPage(myRequest);
    }
    
    // ------------------------------------------------------------------------
    public static ListThesesPageModel getListThesesPage(
            RenderRequest aRenderRequest)
    {
        ArgsRequest2 myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getListThesesPage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static ThesisFocusPageModel getThesisFocusPage(
            RenderRequest aRenderRequest, UrlContainer aUrlContainer)
    {
        ArgsRequest2 myRequest =
                getRequest(aRenderRequest, aUrlContainer);
        return PageModelFactory.getThesisFocusPage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static EditLinkPageModel getEditLinkPage(
            RenderRequest aRenderRequest)
    {
        ArgsRequest2 myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getEditLinkPage(myRequest);
    }

    // ------------------------------------------------------------------------
    // private
    // ------------------------------------------------------------------------
    private static ArgsRequest2 getRequest(RenderRequest aRequest)
    {
        return getRequest3(aRequest, new UrlContainer(), UpdateState.NO);
    }

    // ------------------------------------------------------------------------
    private static ArgsRequest2 getRequest(
            RenderRequest aRequest, UrlContainer aUrlContainer)
    {
        // Viewing the focus page should set the state.
        return getRequest3(aRequest, aUrlContainer, UpdateState.YES);
    }
    
    // TODO: Do the branching by page outside of the javax package. 
    // ------------------------------------------------------------------------
    private static ArgsRequest2 getRequest3(
            RenderRequest aRequest,
            UrlContainer aUrlContainer,
            UpdateState anUpdateState)
    {
        Logger.log("\nRender call");
        final ArgsRequest myArgsRequest =
                TheContainerBridge.i().newArgsRequest(aRequest);
        final ArgsErrorHandler myErrorHandler =
                TheContainerBridge.i().newErrorHandler(
                        aRequest, myArgsRequest.getAppUser());
        
        final ArgsRequest2 myRequest =
                new ArgsRequest2(
                        myArgsRequest, aUrlContainer,
                        CgiSource.SERVLET, anUpdateState, myErrorHandler);
        
        Logger.logRender(myRequest);
        return myRequest;
    }
}
