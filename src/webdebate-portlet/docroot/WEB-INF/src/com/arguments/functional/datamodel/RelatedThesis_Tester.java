package com.arguments.functional.datamodel;

import org.junit.Test;

import com.arguments.functional.datamodel.ArgumentsUserId;
import com.arguments.functional.datamodel.RelatedThesis;
import com.arguments.functional.datamodel.Relation;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.Relevance;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;

public class RelatedThesis_Tester
{
    // ------------------------------------------------------------------------
    @Test
    public void testImpliedBeliefsForSpecialCases()
    {
        /* @formatter:off */
        assert getImplied(ThesisOpinion.BELIEVE_FALSE, Relevance.STRENGTHENS_, Relevance.WEAKENS_).dontbelieve();
        assert getImplied(ThesisOpinion.BELIEVE_FALSE, Relevance.STRENGTHENS_, Relevance.IRRELEVANT_).neutral();
        assert getImplied(ThesisOpinion.BELIEVE_FALSE, Relevance.STRENGTHENS_, Relevance.STRENGTHENS_).believe();

        assert getImplied(ThesisOpinion.BELIEVE_FALSE, Relevance.IRRELEVANT_, Relevance.WEAKENS_).dontbelieve();
        assert getImplied(ThesisOpinion.BELIEVE_FALSE, Relevance.IRRELEVANT_, Relevance.IRRELEVANT_).neutral();
        assert getImplied(ThesisOpinion.BELIEVE_FALSE, Relevance.IRRELEVANT_, Relevance.STRENGTHENS_).believe();

        assert getImplied(ThesisOpinion.BELIEVE_FALSE, Relevance.WEAKENS_, Relevance.WEAKENS_).dontbelieve();
        assert getImplied(ThesisOpinion.BELIEVE_FALSE, Relevance.WEAKENS_, Relevance.IRRELEVANT_).neutral();
        assert getImplied(ThesisOpinion.BELIEVE_FALSE, Relevance.WEAKENS_, Relevance.STRENGTHENS_).believe();

        assert getImplied(ThesisOpinion.NEUTRAL_OPINION, Relevance.STRENGTHENS_, Relevance.WEAKENS_).neutral();
        assert getImplied(ThesisOpinion.NEUTRAL_OPINION, Relevance.STRENGTHENS_, Relevance.IRRELEVANT_).neutral();
        assert getImplied(ThesisOpinion.NEUTRAL_OPINION, Relevance.STRENGTHENS_, Relevance.STRENGTHENS_).neutral();

        assert getImplied(ThesisOpinion.NEUTRAL_OPINION, Relevance.IRRELEVANT_, Relevance.WEAKENS_).neutral();
        assert getImplied(ThesisOpinion.NEUTRAL_OPINION, Relevance.IRRELEVANT_, Relevance.IRRELEVANT_).neutral();
        assert getImplied(ThesisOpinion.NEUTRAL_OPINION, Relevance.IRRELEVANT_, Relevance.STRENGTHENS_).neutral();

        assert getImplied(ThesisOpinion.NEUTRAL_OPINION, Relevance.WEAKENS_, Relevance.WEAKENS_).neutral();
        assert getImplied(ThesisOpinion.NEUTRAL_OPINION, Relevance.WEAKENS_, Relevance.IRRELEVANT_).neutral();
        assert getImplied(ThesisOpinion.NEUTRAL_OPINION, Relevance.WEAKENS_, Relevance.STRENGTHENS_).neutral();

        assert getImplied(ThesisOpinion.BELIEVE_TRUE, Relevance.STRENGTHENS_, Relevance.WEAKENS_).believe();
        assert getImplied(ThesisOpinion.BELIEVE_TRUE, Relevance.STRENGTHENS_, Relevance.IRRELEVANT_).believe();
        assert getImplied(ThesisOpinion.BELIEVE_TRUE, Relevance.STRENGTHENS_, Relevance.STRENGTHENS_).believe();

        assert getImplied(ThesisOpinion.BELIEVE_TRUE, Relevance.IRRELEVANT_, Relevance.WEAKENS_).neutral();
        assert getImplied(ThesisOpinion.BELIEVE_TRUE, Relevance.IRRELEVANT_, Relevance.IRRELEVANT_).neutral();
        assert getImplied(ThesisOpinion.BELIEVE_TRUE, Relevance.IRRELEVANT_, Relevance.STRENGTHENS_).neutral();

        assert getImplied(ThesisOpinion.BELIEVE_TRUE, Relevance.WEAKENS_, Relevance.WEAKENS_).dontbelieve();
        assert getImplied(ThesisOpinion.BELIEVE_TRUE, Relevance.WEAKENS_, Relevance.IRRELEVANT_).dontbelieve();
        assert getImplied(ThesisOpinion.BELIEVE_TRUE, Relevance.WEAKENS_, Relevance.STRENGTHENS_).dontbelieve();
        /* @formatter:on */
    }

    // ------------------------------------------------------------------------
    @Test
    public void OpinionPerspectiveUser()
    {
        
    }
    
    // ------------------------------------------------------------------------
    private static ThesisOpinion getImplied(ThesisOpinion aPremiseOpinion,
            Relevance anIfTrueRelevance, Relevance anIfFalseRelevance)
    {
        ThesisOpinion myPremiseOpinion = aPremiseOpinion;
        Relation myRelation = new Relation(
                RelationId.ONE,
                ThesisId.ONE,
                ThesisId.TWO, anIfTrueRelevance,
                anIfFalseRelevance, ArgumentsUserId.TEST2);

        return RelatedThesis.getImpliedBelief(myPremiseOpinion, myRelation);
    }
}
