/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public class Relevance
{
    public static int WEAKENS=-100;
    public static int IRRELEVANT = 0;
    public static int STRENGTHENS = 100;

    public static Relevance WEAKENS_= new Relevance(WEAKENS);
    public static Relevance IRRELEVANT_ = new Relevance(IRRELEVANT);
    public static Relevance STRENGTHENS_ = new Relevance(STRENGTHENS);

    private double theValue; // Between WEAKENS/100.0 and STRENGTHENS/100.0
    
    // ------------------------------------------------------------------------
    public static final String getExplanation(String aFromString, String aToString, String aBoolean)
    {
        return "<p>"+aFromString+" if "+aBoolean+" relevance:   " +
            "<ul><li>"+WEAKENS+" = The "+aFromString+", if "+aBoolean+", would WEAKEN my belief in the "+aToString+",  </li>  " +
            "<li>"+IRRELEVANT+" = I consider the "+aFromString+", if "+aBoolean+", to be IRRELEVANT for the "+aToString+"  </li>  " +
            "<li>+"+STRENGTHENS+" = The "+aFromString+", if "+aBoolean+", would STRENGTHEN my belief in the "+aToString+".</li></ul></p>";
    }
    
    // ------------------------------------------------------------------------
    public Relevance(double aValue)
    {
        theValue = aValue;
    }

    // ------------------------------------------------------------------------
    public double getMinusOneToOne()
    {
        return theValue;
    }
    // ------------------------------------------------------------------------
    public Relevance()
    {
        this(IRRELEVANT/100.0);
    }
    
    // ------------------------------------------------------------------------
    public void setValue(double aDouble)
    {
        theValue = aDouble;
    }

    // ------------------------------------------------------------------------
    public Integer getPercentage()
    {
        return (int) Math.floor(theValue*100+.5);
    }

    // ------------------------------------------------------------------------
    public boolean equals(Object anObject)
    {
        assert anObject.getClass().equals(Relevance.class);
        return theValue == ((Relevance)anObject).theValue;
    }

    // ------------------------------------------------------------------------
    public int hashCode()
    {
        long bits = Double.doubleToLongBits(theValue);
        return (int)(bits ^ (bits >>> 32));
    }

    
    // ------------------------------------------------------------------------
    public boolean weakens()
    {
        return theValue < IRRELEVANT;
    }
    
    // ------------------------------------------------------------------------
    public boolean strengthens()
    {
        return theValue > IRRELEVANT;
    }
    
    // ------------------------------------------------------------------------
    public String toString()
    {
        StringBuffer myText = new StringBuffer();
        myText.append(relevanceWord());
        myText.append("(");
        myText.append(Math.abs(getPercentage()));
        myText.append("%)");
        return myText.toString();
    }

    // ------------------------------------------------------------------------
    public Double getDBDouble()
    {
        return theValue;
    }

    // ------------------------------------------------------------------------
    public String relevanceWord()
    {
        if (weakens())
            return "Weakens";
        if (strengthens())
            return "Supports";
        return "Irrelevant";
    }
    
    // ------------------------------------------------------------------------
    public static Relevance parseRelevance(String aRelevance)
    {
        assert aRelevance != null;
        Double myValue = Double.parseDouble(aRelevance)/100;
        return new Relevance(myValue);
    }
    
}
