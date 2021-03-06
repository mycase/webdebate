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
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.report.html.ListThesesPage;
import com.arguments.functional.report.html.ThesisFocusPage;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsRenderRequest;
import com.arguments.functional.requeststate.ArgsStatefulRenderRequest;
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

        ArgsRenderRequest myRequest1 = new ArgsRenderRequest(
                myAppUser, new UrlContainer(), new ProtocolMap());

        ArgsStatefulRenderRequest myRequest = new ArgsStatefulRenderRequest(myRequest1,
                new ArgsState(ThesisId.Da13, RelationId.BONE, PerspectiveId.getThesisOwner()));
        String myHtml = ThesisFocusPage.getHtmlBody(myRequest,
                ArgsRequestKeyAsString.getProtocolMap());
        // System.out.println(myWebPage.getHtml());

        tidyParse(myHtml);
    }

    // ------------------------------------------------------------------------
    /**
     * Tests the rendering of a thesis focus comparison pagel
     */
    @Test
    public void test2()
    {
        ArgumentsUser myAppUser = TheArgsStore.i()
                .selectUserById(ArgumentsUserId.TEST2);

        ArgsRenderRequest myRequest1 = new ArgsRenderRequest(
                myAppUser, new UrlContainer(), new ProtocolMap());

        ArgsStatefulRenderRequest myRequest = new ArgsStatefulRenderRequest(myRequest1,
                new ArgsState(ThesisId.Da13, RelationId.BONE, PerspectiveId.getThesisOwner()));
        String myHtml = ThesisFocusPage.getHtmlBody(myRequest,
                ArgsRequestKeyAsString.getProtocolMap());
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

        ArgsRenderRequest myRequest1 = new ArgsRenderRequest(
                myAppUser,
                new ProtocolMap());

        ArgsStatefulRenderRequest myRequest = new ArgsStatefulRenderRequest(myRequest1,
                new ArgsState(ThesisId.Da13, RelationId.BONE, PerspectiveId.getThesisOwner()));
        String myHtml = ListThesesPage.getInternalHtml(
                myRequest, ArgsRequestKeyAsString.getProtocolMap());
        // System.out.println(myHtml);
        tidyParse(myHtml);
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
