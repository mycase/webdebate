/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.report.html.ListThesesPage;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsStatefulRequest3;
import com.arguments.functional.requeststate.ProtocolMap;

/**
 * @author mirleau
 *
 */
public class ListThesesPageModel extends PageRenderer
{
    public final String theHtml;
    
    // ------------------------------------------------------------------------
    public ListThesesPageModel(
            ArgsStatefulRequest3 aRequest,
            ProtocolMap aTheprotocolmap,
            ArgsErrorHandler anErrorHandler)
    {
        super(anErrorHandler);
        theHtml = catchHtml(aRequest, aTheprotocolmap, null);
    }
    
    // ------------------------------------------------------------------------
    protected String noCatchHtml(
            ArgsStatefulRequest3 aRequest, ProtocolMap aProtocolMap,
            UrlContainer aUrlContainer)
    {
        return ListThesesPage.getInternalHtml(aRequest, aProtocolMap);
    }
}
