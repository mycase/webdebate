/**
 * 
 */
package com.arguments.functional.datamodel;

import java.util.List;

import com.arguments.functional.report.ThesisFocusData;
import com.arguments.support.Container;
import com.arguments.support.EmailAddress;
import com.arguments.support.ScreenName;

/**
 * @author mirleau
 *
 */
public interface ArgsStore
{

    // ------------------------------------------------------------------------
    public abstract ArgumentsUser selectUserById(ArgumentsUserId aUserId);

    // ------------------------------------------------------------------------
    public abstract OpinionatedThesis getThesis(ThesisId aThesisId,
            Perspective aPerspective);

    // ------------------------------------------------------------------------
    public abstract Thesis selectThesisById(ThesisId aThesisId);

    // ------------------------------------------------------------------------
    public abstract ThesisOpinion selectOwnerOpinion(ThesisId aThesisID);

    // ------------------------------------------------------------------------
    public abstract ThesisOpinion selectOpinionByThesisPerspective(ThesisId aThesisID,
            PerspectiveId aUserId);

    // ------------------------------------------------------------------------
    public abstract List<OpinionatedThesis> getAllTheses(Perspective aPerspective);

    // ------------------------------------------------------------------------
    public abstract long getNrOfTheses();

    // ------------------------------------------------------------------------
    public abstract long getNrOfOpinions();

    // ------------------------------------------------------------------------
    public abstract ArgsState selectState(ArgumentsUserId aUser);

    // ------------------------------------------------------------------------
    public abstract void assureConnect();

    // ------------------------------------------------------------------------
    public abstract Relation selectRelationById(RelationId aRelationId);

    // ------------------------------------------------------------------------
    public abstract Container<Thesis> selectPremiseTree(Thesis aThesis,
            int aDepth);

    // ------------------------------------------------------------------------
    public abstract void deleteTestObjects();

    // ------------------------------------------------------------------------
    public abstract ThesisId selectLastTestThesisId();

    // ------------------------------------------------------------------------
    public abstract RelationId selectLastTestRelationId();

    // ------------------------------------------------------------------------
    public abstract List<PerspectiveThesisOpinion> getOpinionsForThesisId(
            ThesisId aThesisId);

    // ------------------------------------------------------------------------
    public abstract ThesisFocusData getThesisFocusData(
            ThesisId aMainThesisId, ArgumentsUserId aUserId,
            MPerspective aPerspectives);

    // ------------------------------------------------------------------------
    public abstract ArgumentsUser selectUserByForeignId(ForeignUserId aUserId);

    // ------------------------------------------------------------------------
    public abstract ThesisOpinion selectOpinionById(ThesisOpinionId anOpinionID);
    
    // ------------------------------------------------------------------------
    public abstract ArgumentsUser getAppUser(
            EmailAddress aAnEmailAddress, ForeignUserId aAnLiferayId,
            ScreenName aScreenName);
    
    // ------------------------------------------------------------------------
    public abstract OwnedPerspective getPerspective(
            PerspectiveId aPerspectiveId);

    // ------------------------------------------------------------------------
    public abstract OwnedPerspective getDefaultPerspective(
            ArgumentsUserId aArgumentsUser);
    
    // ------------------------------------------------------------------------
    public abstract PerspectiveId getThesisOwnerPerspective();
    
    // ------------------------------------------------------------------------
    public List<PerspectiveThesisOpinion> selectAllOpinionsByThesis(ThesisId aThesisID);
}