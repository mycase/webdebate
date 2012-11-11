/**
 * 
 */
package com.arguments.functional.report.html;

import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.Perspective;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.report.ThesisFocusData;
import com.arguments.functional.requeststate.ArgsStatefulRequest3;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class ThesisFocusPage
{
    private final ArgumentsUser theAppUser;
    private final Perspective thePerspective;
    private final ThesisId theThesisId;
    private final ArgsStatefulRequest3 theRequest;
    
    // ------------------------------------------------------------------------
    public static String getHtmlBody(
            ArgsStatefulRequest3 aRequest,
            ProtocolMap aProtocolMap)
    {
        final ThesisFocusPage theThesisFocusPage =
                new ThesisFocusPage(aRequest);
        
        return theThesisFocusPage.getHtmlBody(aProtocolMap);
    }

    // ------------------------------------------------------------------------
    static ThesisFocusData getData(ArgsStatefulRequest3 aRequest)
    {
        final ThesisFocusPage theThesisFocusPage =
                new ThesisFocusPage(aRequest);
        
        return theThesisFocusPage.getData();
    }

    // ------------------------------------------------------------------------
    public static String getInternalHtml(
            ArgsStatefulRequest3 aRequest,
            ProtocolMap aProtocolMap)
    {
        final ThesisFocusPage theThesisFocusPage =
                new ThesisFocusPage(aRequest);
        
        return theThesisFocusPage.getInternalHtml(aProtocolMap);
    }

    // ------------------------------------------------------------------------
    /**
     * @param aRequest
     */
    private ThesisFocusPage(ArgsStatefulRequest3 aRequest)
    {
        theAppUser = aRequest.getUser();
        theThesisId = aRequest.getState().getThesisId();
        theRequest = aRequest;
        thePerspective = aRequest.getState().getPerspective();
    }

    // ------------------------------------------------------------------------
    private String getHtmlBody(ProtocolMap aProtocolMap)
    {
        ThesisFocusData myThesisFocusData = getData();
        HtmlThesisPrinter myPrinter = new HtmlThesisPrinter(
                theRequest.getUrlContainer(), aProtocolMap);
        return myPrinter.focusPageToHtmlPage(myThesisFocusData);
    }

    // ------------------------------------------------------------------------
    private String getInternalHtml(ProtocolMap aProtocolMap)
    {
        ThesisFocusData myData = getData();
        HtmlThesisPrinter myPrinter = new HtmlThesisPrinter(
                theRequest.getUrlContainer(), aProtocolMap);
        return myPrinter.focusPageToInternalHtml(myData);
    }

    // ------------------------------------------------------------------------
    private ThesisFocusData getData()
    {
        assertNotNull(theThesisId);
        return TheArgsStore.i().getThesisFocusData(
                theThesisId, theAppUser, thePerspective);
    }
}
