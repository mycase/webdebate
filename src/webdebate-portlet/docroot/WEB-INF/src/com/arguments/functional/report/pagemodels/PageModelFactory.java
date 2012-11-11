/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.requeststate.ArgsRenderRequest;
import com.arguments.functional.requeststate.ArgsStatefulRequest3;
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
            ArgsRenderRequest aRenderRequest)
    {
        ArgsStatefulRequest3 myRequest = storeGet(aRenderRequest);

        return new AddOpinionPageModel(myRequest, theProtocolMap);
    }

    // ------------------------------------------------------------------------
    public static AddPremisePageModel getAddPremisePage(
            ArgsRenderRequest aRenderRequest)
    {
        ArgsStatefulRequest3 myRequest = storeGet(aRenderRequest);
        
        return new AddPremisePageModel(myRequest, theProtocolMap);
    }

    // ------------------------------------------------------------------------
    public static AddThesisPageModel getAddThesisPage(
            ArgsRenderRequest aRenderRequest)
    {
        storeGet(aRenderRequest);
        return new AddThesisPageModel(theProtocolMap);
    }

    // ------------------------------------------------------------------------
    public static ChangePerspectivePageModel getChangePerspectivePage(
            ArgsRenderRequest aRenderRequest)
    {
        ArgsStatefulRequest3 myRequest = storeGet(aRenderRequest);

        return new ChangePerspectivePageModel(myRequest, theProtocolMap);
    }

    // ------------------------------------------------------------------------
    public static EditThesisPageModel getEditThesisPage(
            ArgsRenderRequest aRenderRequest)
    {
        ArgsStatefulRequest3 myRequest = storeGet(aRenderRequest);
        return new EditThesisPageModel(myRequest, theProtocolMap);
    }

    // ------------------------------------------------------------------------
    public static GotoThesisPageModel getGotoThesisPage(
            ArgsRenderRequest aRenderRequest)
    {
        storeGet(aRenderRequest);
        return new GotoThesisPageModel(theProtocolMap);
    }
    
    // ------------------------------------------------------------------------
    public static ListThesesPageModel getListThesesPage(
            ArgsRenderRequest aRenderRequest)
    {
        ArgsStatefulRequest3 myRequest = storeGet(aRenderRequest);

        return new ListThesesPageModel(myRequest, theProtocolMap, aRenderRequest.getErrorHandler());
    }

    // ------------------------------------------------------------------------
    public static ThesisFocusPageModel getThesisFocusPage(
            ArgsRenderRequest aRenderRequest)
    {
        ArgsStatefulRequest3 myRequest = storeGet(aRenderRequest);

        return new ThesisFocusPageModel(myRequest, theProtocolMap, aRenderRequest.getErrorHandler());
    }

    // ------------------------------------------------------------------------
    public static EditLinkPageModel getEditLinkPage(
            ArgsRenderRequest aRenderRequest)
    {
        ArgsStatefulRequest3 myRequest = storeGet(aRenderRequest);

        return new EditLinkPageModel(myRequest,  theProtocolMap);
    }

    // ------------------------------------------------------------------------
    // private
    // ------------------------------------------------------------------------
    protected static ArgsStatefulRequest3 storeGet(ArgsRenderRequest aRequest2)
    {
        return TheContainerBridge.i().storeStateGetArgumentsRequest3(aRequest2);
    }
}
