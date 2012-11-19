/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsStatefulRenderRequest;
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
        assertNotNull(anErrorHandler);
        theErrorHandler = anErrorHandler;
    }
    
    // ------------------------------------------------------------------------
    public String catchHtml(
            ArgsStatefulRenderRequest aRequest,
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
            ArgsStatefulRenderRequest aRequest,
            ProtocolMap aProtocolmap,
            UrlContainer aAnUrlContainer);
}
