/**
 * 
 */
package com.arguments.support;

import java.util.Arrays;

/**
 * @author mirleau
 *
 */
public class IndentHtmlPrinter<A> extends TreeHandler<A>
{
    private final Renderer<A> theRenderer;
    private int theIndent = -1;
    private String theIndentString;
    private StringBuffer theOutput = new StringBuffer();
    
    // ------------------------------------------------------------------------
    public IndentHtmlPrinter(Renderer<A> aRenderer)
    {
        theRenderer = aRenderer;
        addIndent(1);
        goIn();
    }
    
    // ------------------------------------------------------------------------
    /* (non-Javadoc)
     * @see com.arguments.support.TreeHandler#handle(java.lang.Object)
     */
    @Override
    public void handle(A anItem)
    {
        outputln("<li>" + theRenderer.render(anItem) + "</li>");
    }

    
    // ------------------------------------------------------------------------
    @Override
    public void goIn()
    {
        outputln("<ul>");
        addIndent(1);
    }

    // ------------------------------------------------------------------------
    @Override
    public void goOut()
    {
        addIndent(-1);
        outputln("</ul>");
    }
    
    // ------------------------------------------------------------------------
    public String getOutput()
    {
        goOut();
        return theOutput.toString();
    }
    
    // ------------------------------------------------------------------------
    private void outputln(String aString)
    {
        theOutput.append(theIndentString);
        theOutput.append(aString);
        theOutput.append("\n");
    }

    // ------------------------------------------------------------------------
    private void addIndent(int aDiff)
    {
        theIndent += aDiff;
        char[] theSpaces = new char[theIndent*2];
        Arrays.fill(theSpaces, ' ');
        theIndentString = new String(theSpaces);
    }
}
