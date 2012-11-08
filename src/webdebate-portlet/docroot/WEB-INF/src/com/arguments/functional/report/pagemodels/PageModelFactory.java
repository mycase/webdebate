/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.requeststate.ArgsRequest2;
import com.arguments.functional.requeststate.ArgsStatefulRequest;
import com.arguments.functional.requeststate.ProtocolMap;

/**
 * @author mirleau
 *
 */
public class PageModelFactory
{
    public final static ProtocolMap theProtocolMap =
            TheContainerBridge.i().getProtocolMap();

    // ------------------------------------------------------------------------
    public static AddOpinionPageModel getAddOpinionPage(
            ArgsRequest2 aRenderRequest)
    {
        ArgsStatefulRequest myRequest = storeGet(aRenderRequest);

        return new AddOpinionPageModel(myRequest, theProtocolMap);
    }

    // ------------------------------------------------------------------------
    public static AddPremisePageModel getAddPremisePage(
            ArgsRequest2 aRenderRequest)
    {
        ArgsStatefulRequest myRequest = storeGet(aRenderRequest);
        
        return new AddPremisePageModel(myRequest, theProtocolMap);
    }

    // ------------------------------------------------------------------------
    public static AddThesisPageModel getAddThesisPage(
            ArgsRequest2 aRenderRequest)
    {
        storeGet(aRenderRequest);
        return new AddThesisPageModel(theProtocolMap);
    }

    // ------------------------------------------------------------------------
    public static ChangePerspectivePageModel getChangePerspectivePage(
            ArgsRequest2 aRenderRequest)
    {
        ArgsStatefulRequest myRequest = storeGet(aRenderRequest);

        return new ChangePerspectivePageModel(myRequest, theProtocolMap);
    }

    // ------------------------------------------------------------------------
    public static EditThesisPageModel getEditThesisPage(
            ArgsRequest2 aRenderRequest)
    {
        ArgsStatefulRequest myRequest = storeGet(aRenderRequest);
        return new EditThesisPageModel(myRequest, theProtocolMap);
    }

    // ------------------------------------------------------------------------
    public static GotoThesisPageModel getGotoThesisPage(
            ArgsRequest2 aRenderRequest)
    {
        storeGet(aRenderRequest);
        return new GotoThesisPageModel(theProtocolMap);
    }
    
    // ------------------------------------------------------------------------
    public static ListThesesPageModel getListThesesPage(
            ArgsRequest2 aRenderRequest)
    {
        ArgsStatefulRequest myRequest = storeGet(aRenderRequest);

        return new ListThesesPageModel(myRequest, theProtocolMap, aRenderRequest.getErrorHandler());
    }

    // ------------------------------------------------------------------------
    public static ThesisFocusPageModel getThesisFocusPage(
            ArgsRequest2 aRenderRequest)
    {
        ArgsStatefulRequest myRequest = storeGet(aRenderRequest);

        return new ThesisFocusPageModel(myRequest, theProtocolMap, aRenderRequest.getErrorHandler());
    }

    // ------------------------------------------------------------------------
    public static EditLinkPageModel getEditLinkPage(
            ArgsRequest2 aRenderRequest)
    {
        ArgsStatefulRequest myRequest = storeGet(aRenderRequest);

        return new EditLinkPageModel(myRequest,  theProtocolMap);
    }

    // ------------------------------------------------------------------------
    // private
    // ------------------------------------------------------------------------
    protected static ArgsStatefulRequest storeGet(ArgsRequest2 aRequest2)
    {
        return TheContainerBridge.i().storeStateGetArgumentsRequest(aRequest2);
    }
}
