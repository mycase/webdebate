/**
 * 
 */
package com.arguments.functional.report.html;

import static org.junit.Assert.*;

import com.arguments.functional.datamodel.MPerspective;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.report.ListThesesData;
import com.arguments.functional.requeststate.ArgsStatefulRenderRequest;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class ListThesesPage
{
    private final ThesisId theThesisId;
    private final ArgsStatefulRenderRequest theRequest;
    private final MPerspective thePerspectives;
    
    // ------------------------------------------------------------------------
    public static String getInternalHtml(ArgsStatefulRenderRequest myRequest, ProtocolMap aProtocolMap)
    {
        final ListThesesPage thePage =
                new ListThesesPage(myRequest);
        
        return thePage.getInternalHtml(aProtocolMap);
    }

    // ------------------------------------------------------------------------
    /**
     * @param aRequest
     */
    private ListThesesPage(ArgsStatefulRenderRequest aRequest)
    {
        super();
        theRequest = aRequest;
        theThesisId = aRequest.getState().getThesisId();
        thePerspectives = aRequest.getState().getPerspectives();
    }

    // ------------------------------------------------------------------------
    private String getInternalHtml(ProtocolMap aProtocolMap)
    {
        assertNotNull(theThesisId);
        ListThesesData myData =
                new ListThesesData(
                TheArgsStore.i().getAllTheses(thePerspectives), thePerspectives);
        HtmlThesisPrinter myPrinter =
                new HtmlThesisPrinter(theRequest.getUrlContainer(), aProtocolMap);
        return myPrinter.thesisListToInternalHtml(myData);
    }
}
