/**
 * 
 */
package com.arguments.functional.requeststate;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Test;

import com.arguments.Deployment;
import com.arguments.functional.datamodel.ArgsActionRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

        FileReader myFileReader;
        try
        {
            myFileReader = new FileReader(Deployment.i().webinfPath +
                    "testdata/" + aShortFileName);
        }
        catch (FileNotFoundException anException)
        {
            throw new AssertionError(anException);
        }
        
        BufferedReader myReader = new BufferedReader(myFileReader);

        ArgsActionRequest myRequest = myGson.fromJson(myReader, ArgsActionRequest.class);
        return myRequest;
    }
}
