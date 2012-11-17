package com.arguments.functional.datamodel;



public class OpinionatedThesis extends Thesis
{
    private final ThesisOpinion theOpinion;
    private final Perspective thePerspective;

    // ------------------------------------------------------------------------
    public OpinionatedThesis(ThesisId aThesisID, ThesisText aSummary,
            ThesisOpinion aOpinion, Perspective aPerspective, ArgumentsUserId anOwnerID)
    {
        super(aThesisID, aSummary, anOwnerID);
        theOpinion = aOpinion;
        thePerspective = aPerspective;
  }

    // ------------------------------------------------------------------------
    public ThesisOpinion getOpinion()
    {
        return theOpinion;
    }

    // ------------------------------------------------------------------------
    public Perspective getPerspective()
    {
        return thePerspective;
    }
}
