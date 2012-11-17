package com.arguments.functional.report.pagemodels;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.arguments.Deployment;
import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.datamodel.ArgumentsException;
import com.arguments.functional.requeststate.ArgsJspRenderRequest;
import com.arguments.functional.requeststate.ArgsRequestKey;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.functional.store.sql.ArgsSQLStore;
import com.arguments.functional.store.sql.ArgsTestDB;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

/**
 * @author mirleau
 * 
 */
public class PageModel_Tester
{

    // ------------------------------------------------------------------------
    @Before
    public void resetDB()
    {
        TheArgsStore.setDB(new ArgsSQLStore());
    }
    
    // ------------------------------------------------------------------------
    private class ErrorHandlerCreator implements
            InstanceCreator<ArgsErrorHandler>
    {
        public ArgsErrorHandler createInstance(Type type)
        {
            return new ArgsErrorHandler(){

                @Override
                public String handle(Throwable anException)
                {
                    throw new AssertionError(anException);
                }};
        }
    }

    // ------------------------------------------------------------------------
    @Test
    public void testCoverer()
    {
        assertNotSame(new PageModelFactory(), null);
    }
    
    // ------------------------------------------------------------------------
    @Test // (expected = AssertionError.class)
    public void testErrorHandler()
    {
        ArgsErrorHandler myA = new ErrorHandlerCreator().createInstance(null);
        Exception myE = new ArgumentsException("test");
        try
        {
            myA.handle(myE);
        }
        catch(AssertionError anException)
        {
            return; // expected
        }
    }
    
    // ------------------------------------------------------------------------
    @Test
    public void directWebEntry() throws FileNotFoundException
    {
        ArgsJspRenderRequest myRequest = getRequest("001_Direct_Cgi_Focus.json");
        ThesisFocusPageModel myPageModel = PageModelFactory.getThesisFocusPage(myRequest);
        assertNotNull( myPageModel.theHtml);
    }

    // ------------------------------------------------------------------------
    @Test
    public void directWebEntryNoDBConnection() throws FileNotFoundException
    {
        TheArgsStore.setDB(new ArgsTestDB(TheArgsStore.i()));
        ArgsJspRenderRequest myRequest = getRequest("001_Direct_Cgi_Focus.json");
        ThesisFocusPageModel myPageModel = PageModelFactory.getThesisFocusPage(myRequest);
        assertNotNull( myPageModel.theHtml);
    }

    // ------------------------------------------------------------------------
    @Test
    public void addPremise() throws FileNotFoundException
    {
        ArgsJspRenderRequest myRequest = getRequest("002_Add_Premise.json");
        AddPremisePageModel myPageModel = PageModelFactory.getAddPremisePage(myRequest);
        assertNotNull(myPageModel.theIfFalseRelevanceFormLabel);
        assertNotNull(myPageModel.theIfFalseRelevanceFormName);
        assertNotNull(myPageModel.theIfFalseRelevanceFormValue);
        
        assertNotNull(myPageModel.theIfTrueRelevanceFormLabel);
        assertNotNull(myPageModel.theIfTrueRelevanceFormName);
        assertNotNull(myPageModel.theIfTrueRelevanceFormValue);
        
        assertNotNull(myPageModel.thePremiseOpinionFormLabel);
        assertNotNull(myPageModel.thePremiseOpinionFormName);
        assertNotNull(myPageModel.thePremiseOpinionFormValue);
        assertNotNull(myPageModel.thePremiseTextFormName);
        assertNotNull(myPageModel.theThesisText);
    }

    // ------------------------------------------------------------------------
    @Test
    public void editLink() throws FileNotFoundException
    {
        ArgsJspRenderRequest myRequest = getRequest("003_Edit_Link.json");
        EditLinkPageModel myPageModel = PageModelFactory.getEditLinkPage(myRequest);
        assertNotNull( myPageModel.theIfFalseRelevanceFormLabel);
        assertNotNull( myPageModel.theIfFalseRelevanceFormName);
        assertNotNull( myPageModel.theIfFalseRelevanceFormValue);
        
        assertNotNull( myPageModel.theIfTrueRelevanceFormLabel);
        assertNotNull( myPageModel.theIfTrueRelevanceFormName);
        assertNotNull( myPageModel.theIfTrueRelevanceFormValue);
        
        assertNotNull( myPageModel.theLinkIdFormName);
        assertNotNull( myPageModel.theLinkIdFormValue);
        assertNotNull( myPageModel.theTargetIdFormName);
        assertNotNull( myPageModel.theTargetIdFormValue);
        assertNotNull( myPageModel.theThesis1Summary);
        assertNotNull( myPageModel.theThesis2Summary);
    }

    // ------------------------------------------------------------------------
    @Test
    public void listTheses() throws FileNotFoundException
    {
        ArgsJspRenderRequest myRequest = getRequest("004_List_Theses.json");
        ListThesesPageModel myPageModel = PageModelFactory.getListThesesPage(myRequest);
        assertNotNull( myPageModel.theHtml);
    }

    // ------------------------------------------------------------------------
    @Test
    public void gotoThesis() throws FileNotFoundException
    {
        ArgsJspRenderRequest myRequest = getRequest("005_Goto_Thesis.json");
        GotoThesisPageModel myPageModel = PageModelFactory.getGotoThesisPage(myRequest);
        assertNotNull( myPageModel.theThesisIdFormName);
    }

    // ------------------------------------------------------------------------
    @Test
    public void editThesis() throws FileNotFoundException
    {
        ArgsJspRenderRequest myRequest = getRequest("006_Edit_Thesis.json");
        EditThesisPageModel myPageModel = PageModelFactory.getEditThesisPage(myRequest);
        assertNotNull( myPageModel.theThesisOpinionFormLabel);
        assertNotNull( myPageModel.theThesisOpinionFormName);
        assertNotNull( myPageModel.theThesisOpinionFormValue);
        assertNotNull( myPageModel.theThesisTextFormName);
        assertNotNull( myPageModel.theThesisTextFormValue);
    }

    // ------------------------------------------------------------------------
    @Test
    public void changePerspective() throws FileNotFoundException
    {
        ArgsJspRenderRequest myRequest = getRequest("007_Change_Perspective.json");
        ChangePerspectivePageModel myPageModel = PageModelFactory.getChangePerspectivePage(myRequest);
        assertNotNull(myPageModel.thePerspectiveIdFormLabel);
        assertNotNull(myPageModel.thePerspectiveIdFormName);
        assertNotNull(myPageModel.thePerspectiveIdFormValue);
        assertFalse( myPageModel.thePerspectiveIdFormValue.startsWith("Fixed") );
    }

    // ------------------------------------------------------------------------
    @Test
    public void newThesis() throws FileNotFoundException
    {
        ArgsJspRenderRequest myRequest = getRequest("008_New_Thesis.json");
        AddThesisPageModel myPageModel = PageModelFactory.getAddThesisPage(myRequest);
        assertNotNull( myPageModel.theThesisOpinionFormLabel);
        assertNotNull( myPageModel.theThesisOpinionFormName);
        assertNotNull( myPageModel.theThesisOpinionFormValue);
        assertNotNull( myPageModel.theThesisTextFormName);
    }

    // ------------------------------------------------------------------------
    @Test
    public void newOpinion() throws FileNotFoundException
    {
        ArgsJspRenderRequest myRequest = getRequest("009_Add_Opinion.json");
        AddOpinionPageModel myPageModel = PageModelFactory.getAddOpinionPage(myRequest);
        assertNotNull( myPageModel.theFormLabel);
        assertNotNull( myPageModel.theFormName);
        assertNotNull( myPageModel.theThesisOpinion);
        assertNotNull( myPageModel.theThesisText);
    }

    // ------------------------------------------------------------------------
    // private
    // ------------------------------------------------------------------------
    public static ArgsJspRenderRequest getRequest(String aShortFileName)
            throws FileNotFoundException
    {
        Gson myGson = new GsonBuilder()
        .registerTypeAdapter(ArgsErrorHandler.class,
                new PageModel_Tester().new ErrorHandlerCreator())
        .create();

        BufferedReader myReader = new BufferedReader(new FileReader(
                Deployment.i().theWebinfPath + 
                        "testdata/" + aShortFileName));

        ArgsJspRenderRequest myRequest = myGson.fromJson(myReader, ArgsJspRenderRequest.class);
        return myRequest;
    }
}
