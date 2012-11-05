/**
 * 
 */
package com.arguments.support;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mirleau
 *
 */
public class Logger_Tester extends Logger
{
    private ByteArrayOutputStream theBaos;
    
    // ------------------------------------------------------------------------
    @Before
    public void setPrintStream()
    {
        assertEquals(theOut, System.out);
        assertEquals(LOG, false);
        assertEquals(DEBUG, false);
        theBaos = new ByteArrayOutputStream();
        theOut = new PrintStream(theBaos);
        LOG = true;
    }
    
    // ------------------------------------------------------------------------
    @After
    public void resetPrintStream()
    {
        theOut = System.out;
        LOG = false;
    }
    
    // ------------------------------------------------------------------------
    @Test
    public void logMessageGetsPrinted()
    {
        String myMessage = "blah";
        Logger.log(myMessage);
        assertEquals(myMessage + "\n", collectLogString());
    }

    // ------------------------------------------------------------------------
    private String collectLogString()
    {
        try
        {
            return theBaos.toString("utf-8");
        } catch (UnsupportedEncodingException anException)
        {
            throw new AssertionError(anException);
        }
    }
    
}
