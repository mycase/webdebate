/**
 * 
 */
package com.arguments.functional.report.html;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

import com.arguments.functional.datamodel.Thesis;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.requeststate.asstrings.ArgsRequestKeyAsString;
import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class HtmlThesisPrinter_Tester
{
    private ByteArrayOutputStream theBaos;
    private PrintStream theOut;

    // ------------------------------------------------------------------------
    @Before
    public void setUp()
    {
        TheArgsStore.i().deleteTestObjects();
    }
    
    // ------------------------------------------------------------------------
    @Before
    public void setPrintStream()
    {
        theBaos = new ByteArrayOutputStream();
        theOut = new PrintStream(theBaos);
    }

    // ------------------------------------------------------------------------
    @Test
    public void testThesisTreeString()
    {
        Thesis myThesis = TheArgsStore.i().selectThesisById(ThesisId.ONE);
        HtmlThesisPrinter myPrinter = new HtmlThesisPrinter(
                new UrlContainer(), ArgsRequestKeyAsString.getProtocolMap());
        String myHtml = myPrinter.getThesisTreeString(myThesis);
        theOut.println(myHtml);
    }
}
