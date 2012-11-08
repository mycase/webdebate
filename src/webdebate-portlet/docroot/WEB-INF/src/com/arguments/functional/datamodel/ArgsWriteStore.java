/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public interface ArgsWriteStore extends ArgsStore
{
    // ------------------------------------------------------------------------
    public abstract void updateState(ArgsState aState);

    // ------------------------------------------------------------------------
    public abstract ThesisId addThesis(
            ThesisText aThesisText,
            ThesisOpinion aThesisOpinion);

    // ------------------------------------------------------------------------
    public abstract void setThesisOpinion(
            ThesisId aThesisId,
            ThesisOpinion aPremiseOpinion);

    // ------------------------------------------------------------------------
    public abstract void setThesisInfo(
            ThesisId aThesisId,
            ThesisText aNewThesisText,
            ThesisOpinion aThesisOpinion);

    // ------------------------------------------------------------------------
    public abstract ThesisOpinionId addOpinion(
            ThesisId aThesisId,
            ThesisOpinion aThesisOpinion);

    // ------------------------------------------------------------------------
    public abstract ThesisId newPremise(
            ThesisId aSId,
            ThesisText aPremiseText,
            Relevance anIfTruePremiseRelevance,
            Relevance anIfFalsePremiseRelevance);

    // ------------------------------------------------------------------------
    public abstract void setLinkInfo(
            RelationId aLinkId,
            Relevance aNewIfTrueRelevance,
            Relevance aNewIfFalseRelevance,
            ThesisId aNewTargetId);
}
