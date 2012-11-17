package com.arguments.functional.datamodel;


public class RelatedThesis<A extends Thesis>
{
    private final Relation theRelation;
    private final A theThesis;

    // ------------------------------------------------------------------------
    public RelatedThesis(
            A aThesis,
            Relation aRelation)
    {
        theThesis = aThesis;
        theRelation = aRelation;
    }

    // ------------------------------------------------------------------------
    public A getThesis()
    {
        return theThesis;
    }
    
    // ------------------------------------------------------------------------
    public Relevance getIfTrueRelevance()
    {
        return theRelation.getIfTrueRelevance();
    }

    // ------------------------------------------------------------------------
    public Relevance getIfFalseRelevance()
    {
        return theRelation.getIfFalseRelevance();
    }
    
    // ------------------------------------------------------------------------
    public Relation getRelation()
    {
        return theRelation;
    }

    // ------------------------------------------------------------------------
    public static ThesisOpinion getImpliedBelief(ThesisOpinion anOpinion, Relation aRelation)
    {
        double myNewMinusOneToOne;
        Relevance myRelevance;
        if (anOpinion.believe())
            myRelevance = aRelation.getIfTrueRelevance();
        else
            myRelevance = aRelation.getIfFalseRelevance();
        myNewMinusOneToOne = Math.abs(anOpinion.getMinusOneToOne())*myRelevance.getMinusOneToOne();
        return ThesisOpinion.minusOneToOne(myNewMinusOneToOne);
    }

    // ------------------------------------------------------------------------
    public ThesisId getID()
    {
        return theThesis.getID();
    }

    // ------------------------------------------------------------------------
    public ArgumentsUser getOwner()
    {
        return theThesis.getOwner();
    }
}
