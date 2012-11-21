/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import static org.junit.Assert.*;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.requeststate.ArgsJspRequest;
import com.arguments.functional.requeststate.ArgsStatefulRenderRequest;
import com.arguments.functional.requeststate.ProtocolMap;

/**
 * @author mirleau
 *
 */
public class PageModelFactory
{
    public final static ProtocolMap theProtocol =
            TheContainerBridge.i().getProtocolMap();

    // ------------------------------------------------------------------------
    public static AddOpinionPageModel getAddOpinionPage(
            ArgsJspRequest aRenderRequest)
    {
        ArgsStatefulRenderRequest myRequest = aRenderRequest.executeAndGetRenderRequest();

        return new AddOpinionPageModel(myRequest, theProtocol);
    }

    // ------------------------------------------------------------------------
    public static AddPremisePageModel getAddPremisePage(
            ArgsJspRequest aRenderRequest)
    {
        ArgsStatefulRenderRequest myRequest = aRenderRequest.executeAndGetRenderRequest();
        
        return new AddPremisePageModel(myRequest, theProtocol);
    }

    // ------------------------------------------------------------------------
    public static AddPerspectivePageModel getAddPerspectivePage(
            ArgsJspRequest aRenderRequest)
    {
        aRenderRequest.executeAndGetRenderRequest();
        return new AddPerspectivePageModel(theProtocol);
    }

    // ------------------------------------------------------------------------
    public static AddThesisPageModel getAddThesisPage(
            ArgsJspRequest aRenderRequest)
    {
        aRenderRequest.executeAndGetRenderRequest();
        return new AddThesisPageModel(theProtocol);
    }

    // ------------------------------------------------------------------------
    public static EditThesisPageModel getEditThesisPage(
            ArgsJspRequest aRenderRequest)
    {
        ArgsStatefulRenderRequest myRequest = aRenderRequest.executeAndGetRenderRequest();
        return new EditThesisPageModel(myRequest, theProtocol);
    }

    // ------------------------------------------------------------------------
    public static GotoThesisPageModel getGotoThesisPage(
            ArgsJspRequest aRenderRequest)
    {
        aRenderRequest.executeAndGetRenderRequest();
        return new GotoThesisPageModel(theProtocol);
    }
    
    // ------------------------------------------------------------------------
    public static ListThesesPageModel getListThesesPage(
            ArgsJspRequest aRenderRequest)
    {
        ArgsStatefulRenderRequest myRequest = aRenderRequest.executeAndGetRenderRequest();

        return new ListThesesPageModel(myRequest, theProtocol, aRenderRequest.getErrorHandler());
    }

    // ------------------------------------------------------------------------
    public static ThesisFocusPageModel getThesisFocusPage(
            ArgsJspRequest aRenderRequest)
    {
        assertNotNull(aRenderRequest.getErrorHandler());
        ArgsStatefulRenderRequest myRequest = aRenderRequest.executeAndGetRenderRequest();

        return new ThesisFocusPageModel(myRequest, theProtocol, aRenderRequest.getErrorHandler());
    }

    // ------------------------------------------------------------------------
    public static EditLinkPageModel getEditLinkPage(
            ArgsJspRequest aRenderRequest)
    {
        ArgsStatefulRenderRequest myRequest = aRenderRequest.executeAndGetRenderRequest();

        return new EditLinkPageModel(myRequest,  theProtocol);
    }
}
