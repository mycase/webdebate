package com.arguments.functional.datamodel;


public class RelatedThesis extends OpinionatedThesis
{
    private Relation theRelation;

    // ------------------------------------------------------------------------
    public RelatedThesis(
            ThesisId aThesisID,
            ThesisText aSummary,
            ThesisOpinion aOpinion,
            Relation aRelation,
            ArgumentsUserId anOwnerID)
    {
        super(aThesisID, aSummary, aOpinion, anOwnerID);
        theRelation = aRelation;
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
    /**
     * @return theRelation
     */
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
}
