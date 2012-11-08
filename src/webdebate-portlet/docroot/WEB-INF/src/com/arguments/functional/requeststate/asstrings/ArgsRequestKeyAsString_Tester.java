package com.arguments.functional.requeststate.asstrings;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import org.junit.Before;
import org.junit.Test;
import org.w3c.tidy.Tidy;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.ArgumentsUserId;
import com.arguments.functional.datamodel.ArgumentsUser_Tester;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.Thesis;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.report.html.HtmlThesisPrinter;
import com.arguments.functional.report.html.ListThesesPage;
import com.arguments.functional.report.html.ThesisFocusPage;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsRequestKey;
import com.arguments.functional.requeststate.ArgsStatefulRequest;
import com.arguments.functional.requeststate.ArgumentsRequest;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.store.TheArgsStore;

public class ArgsRequestKeyAsString_Tester
{
    private ByteArrayOutputStream theBaos;
    private ByteArrayOutputStream theErrBaos;
    private PrintStream theOut;
    private PrintStream theErrOut;

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
        theErrBaos = new ByteArrayOutputStream();
        theOut = new PrintStream(theBaos);
        theErrOut = new PrintStream(theErrBaos);
    }

    // ------------------------------------------------------------------------
    @Test
    public void test0()
    {
        assertNotNull(new ArgsRequestKeyAsString());
        ArgsRequestKeyAsString.check();
    }
    
    // ------------------------------------------------------------------------
    /**
     * Tests the rendering of a thesis focus page,
     *  including generation of liferay-protocol-dependent URLs.
     */
    @Test
    public void test1()
    {
        ArgumentsUser myAppUser = TheArgsStore.i()
                .selectUserById(ArgumentsUserId.TEST2);

        ArgumentsRequest myRequest1 = new ArgumentsRequest(
                myAppUser, new UrlContainer(), new ProtocolMap());

        ArgsStatefulRequest myRequest = new ArgsStatefulRequest(myRequest1,
                new ArgsState(ThesisId.Da13, RelationId.BONE, PerspectiveId.getThesisOwner()));
        String myHtml = ThesisFocusPage.getHtmlBody(myRequest,
                ArgsRequestKeyAsString.getProtocolMap());
        // System.out.println(myWebPage.getHtml());

        tidyParse(myHtml);
    }

    // ------------------------------------------------------------------------
    /**
     * Tests the insertion of a premise
     */
    @Test
    public void test2()
    {
        ArgumentsUser myAppUser = TheArgsStore.i()
                .selectUserById(ArgumentsUserId.TEST2);
        ArgsState myState = TheArgsStore.i().selectState(myAppUser);
        assertNotSame( myState.getPerspectiveId(), PerspectiveId.getThesisOwner());
        assertEquals(ArgumentsUser_Tester.getMailAddress(2), myAppUser.getEmailAddress());
        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap.put(ArgsRequestKey.PREMISE_TEXT,
                "This premise is true.");
        myRequestMap.put(ArgsRequestKey.PREMISE_IF_TRUE_WEIGHT,
                "0" );
        myRequestMap.put(ArgsRequestKey.PREMISE_IF_FALSE_WEIGHT,
                 "10" );
        myRequestMap.put(ArgsRequestKey.PREMISE_OPINION,
                "" + ThesisOpinion.NEUTRAL_I);

        ArgumentsRequest myRequest1 = new ArgumentsRequest(
                myAppUser,
                myRequestMap);

        ArgsStatefulRequest myRequest = new ArgsStatefulRequest(myRequest1,
                new ArgsState(ThesisId.ONE, RelationId.BONE, myAppUser.getDefaultPerspective()));
        myRequest.execute();
        String myHtml = ThesisFocusPage.getHtmlBody(
                myRequest, ArgsRequestKeyAsString.getProtocolMap());
        // System.out.println(myWebPage.getHtml());

        tidyParse(myHtml);
    }

    // ------------------------------------------------------------------------
    /**
     * Tests the rendering of a thesis focus page.
     */
    @Test
    public void test8()
    {
        ArgumentsUser myAppUser = TheArgsStore.i()
                .selectUserById(ArgumentsUserId.TEST2);

        ArgumentsRequest myRequest1 = new ArgumentsRequest(
                myAppUser,
                new ProtocolMap());

        ArgsStatefulRequest myRequest = new ArgsStatefulRequest(myRequest1,
                new ArgsState(ThesisId.Da13, RelationId.BONE, PerspectiveId.getThesisOwner()));
        String myHtml = ListThesesPage.getInternalHtml(
                myRequest, ArgsRequestKeyAsString.getProtocolMap());
        // System.out.println(myHtml);
        tidyParse(myHtml);
    }

    // ------------------------------------------------------------------------
    /**
     * Tests the insertion and modification of a premise
     */
    @Test
    public void test11()
    {
        Thesis myThesis = TheArgsStore.i().selectThesisById(ThesisId.ONE);
        HtmlThesisPrinter myPrinter = new HtmlThesisPrinter(
                new UrlContainer(), ArgsRequestKeyAsString.getProtocolMap());
        String myHtml = myPrinter.getThesisTreeString(myThesis);
        theOut.println(myHtml);
    }

    // ------------------------------------------------------------------------
    private void tidyParse(String aHtml)
    {
        Tidy myTidy = new Tidy();
        // myTidy.setOnlyErrors(true);
        InputStream myInputStream = new ByteArrayInputStream(aHtml.getBytes());
        PrintWriter myErrWriter = new PrintWriter(theErrOut);
        myTidy.setErrout(myErrWriter);
        myTidy.parse(myInputStream, theOut);
    }
}
