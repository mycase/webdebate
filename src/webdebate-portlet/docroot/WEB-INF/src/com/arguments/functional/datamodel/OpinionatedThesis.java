package com.arguments.functional.datamodel;

import java.util.ArrayList;
import java.util.List;

public class OpinionatedThesis extends Thesis
{
    private final List<ThesisOpinion> theOpinions = new ArrayList<>();
    private final List<Perspective> thePerspectives = new ArrayList<>();

    // ------------------------------------------------------------------------
    public OpinionatedThesis(ThesisId aThesisID, ThesisText aSummary,
            ArgumentsUserId anOwnerID)
    {
        super(aThesisID, aSummary, anOwnerID);
    }

    // ------------------------------------------------------------------------
    public OpinionatedThesis(ThesisId aThesisID, ThesisText aSummary,
            ThesisOpinion aOpinion, Perspective aPerspective,
            ArgumentsUserId anOwnerID)
    {
        super(aThesisID, aSummary, anOwnerID);
        add(aPerspective, aOpinion);
    }

    // ------------------------------------------------------------------------
    public void add(Perspective aPerspective, ThesisOpinion aOpinion)
    {
        theOpinions.add(aOpinion);
        thePerspectives.add(aPerspective);
    }

    // ------------------------------------------------------------------------
    public ThesisOpinion getOpinion()
    {
        return theOpinions.get(0);
    }

    // ------------------------------------------------------------------------
    public Perspective getPerspective()
    {
        return thePerspectives.get(0);
    }
}
