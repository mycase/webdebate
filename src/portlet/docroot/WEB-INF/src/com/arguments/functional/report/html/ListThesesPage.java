/**
 * 
 */
package com.arguments.functional.report.html;

import static org.junit.Assert.*;

import java.util.List;
import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.requeststate.ArgsStatefulRequest;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class ListThesesPage
{
    private final ThesisId theThesisId;
    private final ArgsStatefulRequest theRequest;
    
    // ------------------------------------------------------------------------
    public static String getInternalHtml(ArgsStatefulRequest myRequest, ProtocolMap aProtocolMap)
    {
        final ListThesesPage thePage =
                new ListThesesPage(myRequest);
        
        return thePage.getInternalHtml(aProtocolMap);
    }

    // ------------------------------------------------------------------------
    /**
     * @param aRequest
     */
    private ListThesesPage(ArgsStatefulRequest aRequest)
    {
        super();
        theRequest = aRequest;
        theThesisId = aRequest.getState().getThesisId();
    }

    // ------------------------------------------------------------------------
    private String getInternalHtml(ProtocolMap aProtocolMap)
    {
        assertNotNull(theThesisId);
        List<OpinionatedThesis> myData = TheArgsStore.i().getAllTheses();
        HtmlThesisPrinter myPrinter =
                new HtmlThesisPrinter(theRequest.getUrlContainer(), aProtocolMap);
        return myPrinter.thesisListToInternalHtml(myData);
    }
}
