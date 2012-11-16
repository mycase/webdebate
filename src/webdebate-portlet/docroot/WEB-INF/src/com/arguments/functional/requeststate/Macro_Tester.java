/**
 * 
 */
package com.arguments.functional.requeststate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

import org.junit.Test;

import com.arguments.Deployment;
import com.arguments.functional.datamodel.ArgsActionRequest;
import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;

/**
 * @author mirleau
 *
 */
public class Macro_Tester
{
    // ------------------------------------------------------------------------
    @Test
    public void bla(){}

    // ------------------------------------------------------------------------
    // Use case leading to modification of non-owned perspective.
    // Was translated into basic test
    // ArgsStatefulRequest_Tester.cantWriteUnderOthersPerspective()
    /*
    public void testMacro() throws FileNotFoundException
    {
        ArgsRequest2 myRequest1 = PageModel_Tester.getRequest("macro001_01.json");
        PageModelFactory.getThesisFocusPage(myRequest1);
        ArgsRequest myRequest = getRequest("macro001_02.json");
        myRequest.execute();
        myRequest = getRequest("macro001_03.json");
        myRequest.execute();
        myRequest = getRequest("macro001_04.json");
        myRequest.execute();
    }
    */
    
    // ------------------------------------------------------------------------
    static ArgsActionRequest getRequest(String aShortFileName)
    {
        Gson myGson = new GsonBuilder().create();

        BufferedReader myReader = getReader(aShortFileName);

        ArgsActionRequest myRequest = myGson.fromJson(myReader, ArgsActionRequest.class);
        return myRequest;
    }

    // ------------------------------------------------------------------------
    static ArgsJspRenderRequest getRenderRequest(String aShortFileName)
    {
        AJRRCreator myInstantiator1 = new Macro_Tester().new AJRRCreator();
        ErrorHandlerCreator myInstantiator2 = new Macro_Tester().new ErrorHandlerCreator();
        Gson myGson = new GsonBuilder()
            .registerTypeAdapter(ArgsJspRenderRequest.class, myInstantiator1)
            .registerTypeAdapter(ArgsErrorHandler.class, myInstantiator2)
            .create();

        BufferedReader myReader = getReader(aShortFileName);

        ArgsJspRenderRequest myRequest = myGson.fromJson(myReader, ArgsJspRenderRequest.class);
        return myRequest;
    }

    // ------------------------------------------------------------------------
    private class ErrorHandlerCreator implements InstanceCreator<ArgsErrorHandler> {
        public ArgsErrorHandler createInstance(Type type) {
          return new ArgsErrorHandler(){

            @Override
            public String handle(Throwable aAnException)
            {
                throw new AssertionError(aAnException);
            }};
        }
      }
    
    // ------------------------------------------------------------------------
    private class AJRRCreator implements InstanceCreator<ArgsJspRenderRequest> {
        public ArgsJspRenderRequest createInstance(Type type) {
          return new ArgsJspRenderRequest(null, null, null, null);
        }
      }
    
    // ------------------------------------------------------------------------
    static BufferedReader getReader(String aShortFileName)
    {
        FileReader myFileReader;
        try
        {
            myFileReader = new FileReader(Deployment.i().theWebinfPath +
                    "testdata/" + aShortFileName);
        }
        catch (FileNotFoundException anException)
        {
            throw new AssertionError(anException);
        }
        
        BufferedReader myReader = new BufferedReader(myFileReader);
        return myReader;
    }
}
