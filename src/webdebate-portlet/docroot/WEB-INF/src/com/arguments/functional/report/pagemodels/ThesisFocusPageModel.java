/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.report.html.ThesisFocusPage;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsStatefulRequest3;
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
            ArgsStatefulRequest3 aRequest,
            ProtocolMap aProtocol,
            ArgsErrorHandler anErrorHandler)
    {
        super(anErrorHandler);
        theHtml = catchHtml(aRequest, aProtocol, null);
    }

    // ------------------------------------------------------------------------
    protected String noCatchHtml(
            ArgsStatefulRequest3 aRequest,
            ProtocolMap aProtocol,
            UrlContainer aUrlContainer)
    {
        return ThesisFocusPage.getInternalHtml(aRequest, aProtocol);
    }
}
