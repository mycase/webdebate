/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsStatefulRequest;
import com.arguments.functional.requeststate.ProtocolMap;

/**
 * @author mirleau
 *
 */
public abstract class PageRenderer
{
    private final ArgsErrorHandler theErrorHandler;
    
    // ------------------------------------------------------------------------
    public PageRenderer(ArgsErrorHandler anErrorHandler)
    {
        theErrorHandler = anErrorHandler;
    }
    
    // ------------------------------------------------------------------------
    public String catchHtml(
            ArgsStatefulRequest aRequest,
            ProtocolMap aProtocolmap,
            UrlContainer aUrlContainer
            )
    {
        try
        {
            return noCatchHtml(aRequest, aProtocolmap, aUrlContainer);
        }
        catch (Exception | Error anException)
        {
            return theErrorHandler.handle(anException);
        }
    }
    
    // ------------------------------------------------------------------------
    protected abstract String noCatchHtml(
            ArgsStatefulRequest aRequest,
            ProtocolMap aProtocolmap,
            UrlContainer aAnUrlContainer);
}
