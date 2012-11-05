/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public class ThesisOpinion
{
    public static final int FALSE_I = 0;
    public static final int NEUTRAL_I = 50;
    public static final int TRUE_I = 100;

    public static final String OPINION_EXPLANATION =
            "<p>Thesis opinion:   " +
            "<ul><li>"+FALSE_I+" = I believe this is not true,</li>    " +
            "<li>"+NEUTRAL_I+" = I don't know,</li>    " +
            "<li>"+TRUE_I+" = I believe this thesis is true.</li></ul></p>";

    public final static ThesisOpinion
    NEUTRAL_OPINION = new ThesisOpinion(NEUTRAL_I/100.0);
    public final static ThesisOpinion
    BELIEVE_FALSE = new ThesisOpinion(FALSE_I/100.0);
    public final static ThesisOpinion
    BELIEVE_TRUE = new ThesisOpinion(TRUE_I/100.0);

    private final double theProbability;
    
    // ------------------------------------------------------------------------
    protected ThesisOpinion(double aProbability)
    {
        theProbability = aProbability;
    }

    // ------------------------------------------------------------------------
    public static ThesisOpinion newOpinionByProbability(double aProbability)
    {
        return new ThesisOpinion(aProbability);
    }
    
    // ------------------------------------------------------------------------
    public ThesisOpinion()
    {
        this(NEUTRAL_I/100.0);
    }
    
    // ------------------------------------------------------------------------
    public Integer getPercentage()
    {
        return (int) Math.floor(theProbability*100+.5);
    }

    // ------------------------------------------------------------------------
    public Integer getMinus100To100()
    {
        return 2*getPercentage() - 100;
    }

    // ------------------------------------------------------------------------
    public boolean believe()
    {
        return (theProbability > NEUTRAL_I/100.0);
    }

    // ------------------------------------------------------------------------
    public boolean dontbelieve()
    {
        return (theProbability < NEUTRAL_I/100.0);
    }

    // ------------------------------------------------------------------------
    public double getMinusOneToOne()
    {
        return 2d*(theProbability - NEUTRAL_I/100.0);
    }

    // ------------------------------------------------------------------------
    public boolean neutral()
    {
        return (theProbability == NEUTRAL_I/100.0);
    }

    // ------------------------------------------------------------------------
    public boolean equals(Object anObject)
    {
        assert anObject.getClass().equals(ThesisOpinion.class);
        return theProbability == ((ThesisOpinion)anObject).theProbability;
    }

    // ------------------------------------------------------------------------
    public int hashCode()
    {
        long bits = Double.doubleToLongBits(theProbability);
        return (int)(bits ^ (bits >>> 32));
    }

    // ------------------------------------------------------------------------
    public Double getDBDouble()
    {
        return theProbability;
    }

    // ------------------------------------------------------------------------
    public String toString()
    {
        if (believe())
        {
            return "Believe(" + getMinus100To100() + "%)";
        }
        if (neutral())
        {
            return "Neutral";
        }
        return "DontBelieve(" + getMinus100To100() + "%)";
    }
    
    // ------------------------------------------------------------------------
    /**
     * @param aThesisOpinion
     * @return
     */
    public static ThesisOpinion parseOpinion(String aThesisOpinion)
    {
        Double myProbability = Integer.parseInt(aThesisOpinion)/100.0;
        return new ThesisOpinion(myProbability);
    }

    
    // ------------------------------------------------------------------------
    public static ThesisOpinion minusOneToOne(double aNewMinusOneToOne)
    {
        double myProbability = (aNewMinusOneToOne + 1.0)/2.0;
        return new ThesisOpinion(myProbability);
    }
}
