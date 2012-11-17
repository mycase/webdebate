package com.arguments.functional.datamodel;


public class RelatedThesis
{
    private final Relation theRelation;
    private final OpinionatedThesis theThesis;

    // ------------------------------------------------------------------------
    public RelatedThesis(
            ThesisId aThesisID,
            ThesisText aSummary,
            ThesisOpinion aOpinion,
            Relation aRelation,
            ArgumentsUserId anOwnerID)
    {
        this(new OpinionatedThesis(aThesisID, aSummary, aOpinion, anOwnerID), aRelation);
    }

    // ------------------------------------------------------------------------
    public RelatedThesis(
            OpinionatedThesis aThesis,
            Relation aRelation)
    {
        theThesis = aThesis;
        theRelation = aRelation;
    }

    // ------------------------------------------------------------------------
    public OpinionatedThesis getThesis()
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

    // ------------------------------------------------------------------------
    public ThesisOpinion getOpinion()
    {
        return theThesis.getOpinion();
    }
}
