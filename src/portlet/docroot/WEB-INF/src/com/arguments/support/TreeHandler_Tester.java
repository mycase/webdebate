/**
 * 
 */
package com.arguments.support;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author mirleau
 * 
 */
public class TreeHandler_Tester
{
    String theHandledString;

    TreeHandler<String> theTestHandler = new TreeHandler<String>()
    {

        @Override
        public void handle(String anItem)
        {
            theHandledString += anItem;
        }

        @Override
        public void goIn()
        {
            theHandledString += "in";
        }

        @Override
        public void goOut()
        {
            theHandledString += "out";
        }
    };

    // ------------------------------------------------------------------------
    @Before
    public void resetTestString()
    {
        theHandledString = "";
    }

    // ------------------------------------------------------------------------
    @Test
    public void test1()
    {
        Container<String> myL1 = newC("Level 1");
        Container<String> myL1A = newC("Level 1_A");
        Container<String> myL1B = newC("Level 1_B");
        Container<String> myL1A1 = newC("Level 1A1");
        Container<String> myL1A2 = newC("Level 1A2");
        myL1.add(myL1A);
        myL1.add(myL1B);
        myL1A.add(myL1A1);
        myL1A.add(myL1A2);

        theTestHandler.traverse(myL1);
        String myRefString = "Level 1inLevel 1_AinLevel 1A1Level 1A2outLevel 1_Bout";
        
        assertEquals(myRefString, theHandledString);
    }

    // ------------------------------------------------------------------------
    private static Container<String> newC(String aString)
    {
        return new Container<>(aString);
    }
}
