/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public class ThesisText
{
    private final String theText;

    
    // ------------------------------------------------------------------------
    public ThesisText(String aText)
    {
        theText = aText;
    }

    // ------------------------------------------------------------------------
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((theText == null) ? 0 : theText.hashCode());
        return result;
    }

    // ------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ThesisText other = (ThesisText) obj;
        if (theText == null)
        {
            if (other.theText != null)
                return false;
        } else if (!theText.equals(other.theText))
            return false;
        return true;
    }

    // ------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return theText;
    }
}
