/**
 * 
 */
package com.arguments.support;

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

/**
 * @author mirleau
 *
 */
public class IndentHtmlPrinter_Tester
{
    // ------------------------------------------------------------------------
    @Test
    public void test1()
    {
        Container<String> myL1= newC("Level 1");
        Container<String> myL1A= newC("Level 1_A");
        Container<String> myL1B= newC("Level 1_B");
        Container<String> myL1A1= newC("Level 1A1");
        Container<String> myL1A2= newC("Level 1A2");
        myL1.add(myL1A);
        myL1.add(myL1B);
        myL1A.add(myL1A1);
        myL1A.add(myL1A2);
        
        Renderer<String> myRenderer = new Renderer<String>()
                {

                    @Override
                    public String render(String anItem)
                    {
                        return anItem;
                    }};
                    
        IndentHtmlPrinter<String> myPrinter =
                new IndentHtmlPrinter<>(myRenderer);
        myPrinter.traverse(myL1);
        String myRefString =
                "<ul>\n" + 
        		"  <li>Level 1</li>\n" + 
        		"  <ul>\n" + 
        		"    <li>Level 1_A</li>\n" + 
        		"    <ul>\n" + 
        		"      <li>Level 1A1</li>\n" + 
        		"      <li>Level 1A2</li>\n" + 
        		"    </ul>\n" + 
        		"    <li>Level 1_B</li>\n" + 
        		"  </ul>\n" + 
        		"</ul>\n" ; 
        
        assertEquals(myRefString, myPrinter.getOutput());
    }
    
    // ------------------------------------------------------------------------
    private static Container<String> newC(String aString)
    {
        return new Container<>(aString);
    }
}
