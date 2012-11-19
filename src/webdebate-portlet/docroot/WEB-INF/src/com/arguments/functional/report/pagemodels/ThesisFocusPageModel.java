/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.report.html.ThesisFocusPage;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsStatefulRenderRequest;
import com.arguments.functional.requeststate.ProtocolMap;

/**
 * @author mirleau
 *
 */
public class ThesisFocusPageModel extends PageRenderer
{
    public final String theHtml;
    
    // ------------------------------------------------------------------------
    public ThesisFocusPageModel(
            ArgsStatefulRenderRequest aRequest,
            ProtocolMap aProtocol,
            ArgsErrorHandler anErrorHandler)
    {
        super(anErrorHandler);
        theHtml = catchHtml(aRequest, aProtocol, null);
    }

    // ------------------------------------------------------------------------
    protected String noCatchHtml(
            ArgsStatefulRenderRequest aRequest,
            ProtocolMap aProtocol,
            UrlContainer aUrlContainer)
    {
        return ThesisFocusPage.getInternalHtml(aRequest, aProtocol);
    }
}
