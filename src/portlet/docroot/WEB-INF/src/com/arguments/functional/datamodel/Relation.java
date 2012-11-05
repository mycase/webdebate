package com.arguments.functional.datamodel;

import com.arguments.functional.store.TheArgsStore;

public class Relation extends RelationId
{
    private final ThesisId theThesis1ID;
    private final ThesisId theThesis2ID;
    private final Relevance theIfTrueRelevance;
    private final Relevance theIfFalseRelevance;
    private final ArgumentsUserId theOwnerID;
    
    private Thesis theThesis1;
    private Thesis theThesis2;
    
    public static final String IF_TRUE_RELEVANCE_EXPLANATION = Relevance.getExplanation("premise", "conclusion", "true");
    public static final String IF_FALSE_RELEVANCE_EXPLANATION = Relevance.getExplanation("premise", "conclusion", "false");

    // ------------------------------------------------------------------------
    public Relation(
            RelationId aRelationID,
            ThesisId aThesis1id,
            ThesisId aThesis2id,
            Relevance anIfTrueRelevance,
            Relevance anIfFalseRelevance,
            ArgumentsUserId anOwnerID)
    {
        super(aRelationID);
        theThesis1ID = aThesis1id;
        theThesis2ID = aThesis2id;
        theIfTrueRelevance = anIfTrueRelevance;
        theIfFalseRelevance = anIfFalseRelevance;
        theOwnerID = anOwnerID;
    }

    // ------------------------------------------------------------------------
    public ThesisId getThesis1ID()
    {
        return theThesis1ID;
    }

    // ------------------------------------------------------------------------
    public ThesisId getThesis2ID()
    {
        return theThesis2ID;
    }

    // ------------------------------------------------------------------------
    /**
     * @return the relevance
     */
    public Relevance getIfTrueRelevance()
    {
        return theIfTrueRelevance;
    }

    // ------------------------------------------------------------------------
    /**
     * @return the relevance
     */
    public Relevance getIfFalseRelevance()
    {
        return theIfFalseRelevance;
    }

    // ------------------------------------------------------------------------
    /**
     * @return the percentage relevance
     */
    public Integer getIfTruePercentageRelevance()
    {
        return theIfTrueRelevance.getPercentage();
    }

    // ------------------------------------------------------------------------
    /**
     * @return the percentage relevance
     */
    public Integer getIfFalsePercentageRelevance()
    {
        return theIfFalseRelevance.getPercentage();
    }

    // ------------------------------------------------------------------------
    /**
     * @return the ownerID
     */
    public ArgumentsUserId getOwnerID()
    {
        return theOwnerID;
    }

    
    // ------------------------------------------------------------------------
    public Thesis getThesis1()
    {
        if (theThesis1 == null)
            theThesis1 = TheArgsStore.i().selectThesisById(theThesis1ID);
        return theThesis1;
    }
    
    // ------------------------------------------------------------------------
    public String getThesis1Summary()
    {
        return getThesis1().getSummary().toString();
    }
    
    // ------------------------------------------------------------------------
    public String getThesis2Summary()
    {
        return getThesis2().getSummary().toString();
    }
    
    // ------------------------------------------------------------------------
    public Thesis getThesis2()
    {
        if (theThesis2 == null)
            theThesis2 = TheArgsStore.i().selectThesisById(theThesis2ID);
        return theThesis2;
    }
}
