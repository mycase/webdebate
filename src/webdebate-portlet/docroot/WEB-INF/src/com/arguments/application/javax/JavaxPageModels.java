/**
 * 
 */
package com.arguments.application.javax;

import javax.portlet.RenderRequest;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.report.pagemodels.AddOpinionPageModel;
import com.arguments.functional.report.pagemodels.AddPerspectivePageModel;
import com.arguments.functional.report.pagemodels.AddPremisePageModel;
import com.arguments.functional.report.pagemodels.AddThesisPageModel;
import com.arguments.functional.report.pagemodels.EditLinkPageModel;
import com.arguments.functional.report.pagemodels.EditThesisPageModel;
import com.arguments.functional.report.pagemodels.GotoThesisPageModel;
import com.arguments.functional.report.pagemodels.ListThesesPageModel;
import com.arguments.functional.report.pagemodels.PageModelFactory;
import com.arguments.functional.report.pagemodels.ThesisFocusPageModel;
import com.arguments.functional.requeststate.ArgsJspRequest;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateStateFlag;

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
        ArgsJspRequest myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getAddOpinionPage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static AddPremisePageModel getAddPremisePage(
            RenderRequest aRenderRequest)
    {
        ArgsJspRequest myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getAddPremisePage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static AddThesisPageModel getAddThesisPage(
            RenderRequest aRenderRequest)
    {
        ArgsJspRequest myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getAddThesisPage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static AddPerspectivePageModel getAddPerspectivePage(
            RenderRequest aRenderRequest)
    {
        ArgsJspRequest myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getAddPerspectivePage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static EditThesisPageModel getEditThesisPage(
            RenderRequest aRenderRequest)
    {
        ArgsJspRequest myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getEditThesisPage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static GotoThesisPageModel getGotoThesisPage(
            RenderRequest aRenderRequest)
    {
        ArgsJspRequest myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getGotoThesisPage(myRequest);
    }
    
    // ------------------------------------------------------------------------
    public static ListThesesPageModel getListThesesPage(
            RenderRequest aRenderRequest)
    {
        ArgsJspRequest myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getListThesesPage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static ThesisFocusPageModel getThesisFocusPage(
            RenderRequest aRenderRequest, UrlContainer aUrlContainer)
    {
        ArgsJspRequest myRequest =
                getRequest(aRenderRequest, aUrlContainer);
        return PageModelFactory.getThesisFocusPage(myRequest);
    }

    // ------------------------------------------------------------------------
    public static EditLinkPageModel getEditLinkPage(
            RenderRequest aRenderRequest)
    {
        ArgsJspRequest myRequest = getRequest(aRenderRequest);
        return PageModelFactory.getEditLinkPage(myRequest);
    }

    // ------------------------------------------------------------------------
    // private
    // ------------------------------------------------------------------------
    private static ArgsJspRequest getRequest(RenderRequest aRequest)
    {
        return getRequest3(aRequest, new UrlContainer(), UpdateStateFlag.NO);
    }

    // ------------------------------------------------------------------------
    private static ArgsJspRequest getRequest(
            RenderRequest aRequest, UrlContainer aUrlContainer)
    {
        // Viewing the focus page should set the state.
        return getRequest3(aRequest, aUrlContainer, UpdateStateFlag.YES);
    }
    
    // TODO: Do the above branching by page outside of the javax package. 
    // ------------------------------------------------------------------------
    private static ArgsJspRequest getRequest3(
            RenderRequest aRequest,
            UrlContainer aUrlContainer,
            UpdateStateFlag anUpdateState)
    {
        return JavaxArgsBridge.getArgsRenderRequest(aRequest, aUrlContainer, anUpdateState);
    }
}
