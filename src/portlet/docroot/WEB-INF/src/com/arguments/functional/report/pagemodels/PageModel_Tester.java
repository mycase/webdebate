package com.arguments.functional.report.pagemodels;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

import org.junit.Test;

import com.arguments.application.liferay.LiferayErrorHandler;
import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.requeststate.ArgsRequest2;
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
    private class ErrorHandlerCreator implements
            InstanceCreator<ArgsErrorHandler>
    {
        public ArgsErrorHandler createInstance(Type type)
        {
            return new LiferayErrorHandler(null, null);
        }
    }

    // ------------------------------------------------------------------------
    @Test
    public void testCoverer()
    {
        assertNotSame(new PageModelFactory(), null);
    }
    
    // ------------------------------------------------------------------------
    @Test
    public void directWebEntry() throws FileNotFoundException
    {
        ArgsRequest2 myRequest = getRequest("001_Direct_Cgi_Focus.json");
        ThesisFocusPageModel myPageModel = PageModelFactory.getThesisFocusPage(myRequest);
        assertNotNull( myPageModel.theHtml);
    }

    // ------------------------------------------------------------------------
    @Test
    public void directWebEntryNoDBConnection() throws FileNotFoundException
    {
        TheArgsStore.setDB(new ArgsTestDB(TheArgsStore.i()));
        try
        {
            ArgsRequest2 myRequest = getRequest("001_Direct_Cgi_Focus.json");
            ThesisFocusPageModel myPageModel = PageModelFactory.getThesisFocusPage(myRequest);
            assertNotNull( myPageModel.theHtml);
        }
        finally
        {
            TheArgsStore.setDB(new ArgsSQLStore());
        }
        
    }

    // ------------------------------------------------------------------------
    @Test
    public void addPremise() throws FileNotFoundException
    {
        ArgsRequest2 myRequest = getRequest("002_Add_Premise.json");
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
        ArgsRequest2 myRequest = getRequest("003_Edit_Link.json");
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
        ArgsRequest2 myRequest = getRequest("004_List_Theses.json");
        ListThesesPageModel myPageModel = PageModelFactory.getListThesesPage(myRequest);
        assertNotNull( myPageModel.theHtml);
    }

    // ------------------------------------------------------------------------
    @Test
    public void gotoThesis() throws FileNotFoundException
    {
        ArgsRequest2 myRequest = getRequest("005_Goto_Thesis.json");
        GotoThesisPageModel myPageModel = PageModelFactory.getGotoThesisPage(myRequest);
        assertNotNull( myPageModel.theThesisIdFormName);
    }

    // ------------------------------------------------------------------------
    @Test
    public void editThesis() throws FileNotFoundException
    {
        ArgsRequest2 myRequest = getRequest("006_Edit_Thesis.json");
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
        ArgsRequest2 myRequest = getRequest("007_Change_Perspective.json");
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
        ArgsRequest2 myRequest = getRequest("008_New_Thesis.json");
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
        ArgsRequest2 myRequest = getRequest("009_Add_Opinion.json");
        AddOpinionPageModel myPageModel = PageModelFactory.getAddOpinionPage(myRequest);
        assertNotNull( myPageModel.theFormLabel);
        assertNotNull( myPageModel.theFormName);
        assertNotNull( myPageModel.theThesisOpinion);
        assertNotNull( myPageModel.theThesisText);
    }

    // ------------------------------------------------------------------------
    // private
    // ------------------------------------------------------------------------
    public static ArgsRequest2 getRequest(String aShortFileName)
            throws FileNotFoundException
    {
        Gson myGson = new GsonBuilder()
        .registerTypeAdapter(ArgsErrorHandler.class,
                new PageModel_Tester().new ErrorHandlerCreator())
        .create();

        BufferedReader myReader = new BufferedReader(new FileReader(
                "/opt/linux/i386/liferay/liferay-sdk/portlets/argumentation-portlet/"
                        + "docroot/WEB-INF/testdata/" + aShortFileName));

        ArgsRequest2 myRequest = myGson.fromJson(myReader, ArgsRequest2.class);
        return myRequest;
    }
}
