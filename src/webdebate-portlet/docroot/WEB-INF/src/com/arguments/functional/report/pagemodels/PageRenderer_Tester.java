/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import org.junit.Test;

import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.report.ThesisNotOwnedException;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsStatefulRenderRequest;
import com.arguments.functional.requeststate.ProtocolMap;

/**
 * @author mirleau
 *
 */
public class PageRenderer_Tester
{
    // ------------------------------------------------------------------------
    @Test
    public void testCatch()
    {
        ArgsErrorHandler myHandler =
                new ArgsErrorHandler(){

                    @Override
                    public String handle(Throwable aAnException)
                    {
                        return null;
                    }};
        
        PageRenderer myRenderer = new PageRenderer(myHandler){

            @Override
            protected String noCatchHtml(ArgsStatefulRenderRequest aRequest,
                    ProtocolMap aProtocolmap, UrlContainer aAnUrlContainer)
            {
                throw new ThesisNotOwnedException("bla");
            }
        };
        
        myRenderer.catchHtml(null, null, null);
    }
}
