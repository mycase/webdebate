package com.arguments.functional.datamodel;



public class OpinionatedThesis extends Thesis
{
    private final ThesisOpinion theOpinion;

    // ------------------------------------------------------------------------
    public OpinionatedThesis(ThesisId aThesisID, ThesisText aSummary,
            ThesisOpinion aOpinion, ArgumentsUserId anOwnerID)
    {
        super(aThesisID, aSummary, anOwnerID);
        theOpinion = aOpinion;
    }

    // ------------------------------------------------------------------------
    public ThesisOpinion getOpinion()
    {
        return theOpinion;
    }
}
