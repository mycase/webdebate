/**
 * 
 */
package com.arguments.functional.store.sql;

import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgsReadOnlyState;
import com.arguments.functional.datamodel.ArgsWriteStore;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.Perspective;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.Relevance;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.datamodel.ThesisOpinionId;
import com.arguments.functional.datamodel.ThesisText;
import com.arguments.functional.report.PerspectiveNotOwnedException;
import com.arguments.functional.report.ThesisNotOwnedException;
import com.arguments.functional.store.sql.ArgsDB.ArgsQuery;
import com.arguments.support.Logger;

/**
 * @author mirleau
 *
 */
public class SecureArgsSQLStore extends ArgsSQLStore implements ArgsWriteStore
{
    private final ArgumentsUser theLoginUser;
    private final Perspective theWritePerspective;

    // ------------------------------------------------------------------------
    public SecureArgsSQLStore(ArgumentsUser aLoginUser)
    {
        assert aLoginUser != null;
        theLoginUser = aLoginUser;
        
        ArgsReadOnlyState myState = selectState(theLoginUser);
        PerspectiveId myPID = myState.getPerspectiveId();
        theWritePerspective = getPerspective(myPID);
        // Can't call checkCanWrite here, because some writes are OK, like
        // changing the active thesis.
    }

    // ------------------------------------------------------------------------
    @Override
    public void updateState(ArgsReadOnlyState aState)
    {
        Logger.log("Update state");
        ArgsDB myQuery = ArgsQuery.UPDATE_STATE1.ps();
        myQuery.setThesisId(1, aState.getThesisId());
        myQuery.setPerspectiveId(2, aState.getPerspectiveId());
        myQuery.setUserId(3, theLoginUser);
        int myNrRows = myQuery.executeUpdate();
        assert myNrRows < 2;
        if (myNrRows == 1)
            return;
        insertState(aState, theLoginUser);
    }

    // ------------------------------------------------------------------------
    @Override
    public void setThesisOpinion(
            ThesisId aThesisId,
            ThesisOpinion aPremiseOpinion)
    {
        setThesisOpinion2(aThesisId, aPremiseOpinion);
    }

    // ------------------------------------------------------------------------
    @Override
    public ThesisOpinionId addOpinion(
            ThesisId aThesisId,
            ThesisOpinion aThesisOpinion)
    {
        assert theWritePerspective.isWritable();
        return setThesisOpinion2(aThesisId, aThesisOpinion);
    }

    // ------------------------------------------------------------------------
    @Override
    public void setThesisInfo(
            ThesisId aThesisId,
            ThesisText aNewThesisText,
            ThesisOpinion aThesisOpinion)
    {
        assert theWritePerspective.isWritable() : "Non writable: " + theWritePerspective;
        setThesisInfo2(aThesisId, aNewThesisText, aThesisOpinion);
    }

    // ------------------------------------------------------------------------
    @Override
    public void setLinkInfo(
            RelationId aLinkId,
            Relevance aNewIfTrueRelevance,
            Relevance aNewIfFalseRelevance,
            ThesisId aNewTargetId)
    {
        setLinkInfo2(aLinkId, aNewIfTrueRelevance, aNewIfFalseRelevance, aNewTargetId);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public ThesisId addThesis(
            ThesisText aThesisText,
            ThesisOpinion aThesisOpinion)
    {
        return addThesis2(aThesisText, aThesisOpinion);
    }

    // ------------------------------------------------------------------------
    @Override
    public ThesisId newPremise(
            ThesisId aSId,
            ThesisText aPremiseText,
            Relevance anIfTruePremiseRelevance,
            Relevance anIfFalsePremiseRelevance)
    {
        checkCanWrite();
        return newPremise2(
                aSId, aPremiseText, anIfTruePremiseRelevance, anIfFalsePremiseRelevance);
    }

    // ------------------------------------------------------------------------
    private ThesisOpinionId setThesisOpinion2(
            ThesisId aThesisId,
            ThesisOpinion aThesisOpinion)
    {
        checkCanWrite();
        ArgsDB myQuery = ArgsQuery.COUNT_OPINION_BY_THESIS_PESPECTIVE.ps();
        myQuery.setThesisId(1, aThesisId);
        myQuery.setPerspectiveId(2, theWritePerspective);

        long myNrOfOpinions = getLong(myQuery, ArgsDB.NR_OF_OPINIONS);

        if (myNrOfOpinions == 0)
            return insertThesisOpinion(aThesisId, aThesisOpinion);
        return updateThesisOpinion(aThesisId, aThesisOpinion);
    }

    // ------------------------------------------------------------------------
    private ThesisOpinionId updateThesisOpinion(ThesisId aThesisId,
            ThesisOpinion aThesisOpinion)
    {
        ArgsDB myQuery = ArgsQuery.UPDATE_OPINION_BY_THESIS_PERSPECTIVE1.ps();
        myQuery.setOpinion(1, aThesisOpinion);
        myQuery.setThesisId(2, aThesisId);
        myQuery.setPerspectiveId(3, theWritePerspective);
        myQuery.executeUpdate();
        return null;
    }

    // ------------------------------------------------------------------------
    private ThesisOpinionId insertThesisOpinion(
            ThesisId aThesisId,
            ThesisOpinion aThesisOpinion)
    {
        checkCanWrite();
        ArgsQuery.LOCK_OPINION_WRITE.ps().executeQuery();
        ArgsDB myQuery = ArgsQuery.INSERT_OPINION1.ps();
        myQuery.setThesisId(1, aThesisId);
        myQuery.setPerspectiveId(2, theWritePerspective);
        myQuery.setOpinion(3, aThesisOpinion);
        int myNrOfRowsAffected = myQuery.executeUpdate();
        assert myNrOfRowsAffected == 1;

        ThesisOpinionId myOpinionId =
                getThesisOpinionId(ArgsQuery.MAX_OPINION_ID.ps(),
                        ArgsDB.COUNT);
        ArgsQuery.UNLOCK_TABLES.ps().executeQuery();
        return myOpinionId;

    }

    // ------------------------------------------------------------------------
    private ThesisId addThesis2(
            ThesisText aNewThesisText,
            ThesisOpinion aThesisOpinion)
    {
        checkCanWrite();
        ThesisId myThesisId = newThesis(aNewThesisText);
        insertThesisOpinion(myThesisId, aThesisOpinion);
        return myThesisId;
    }

    // ------------------------------------------------------------------------
    private void setLinkInfo2(
            RelationId aRelationId,
            Relevance anIfTrueNewRelevance,
            Relevance anIfFalseNewRelevance,
            ThesisId aNewTargetId)
    {
        ArgsDB myQuery = ArgsQuery.UPDATE_RELATION.ps();
        myQuery.setRelevance(1, anIfTrueNewRelevance);
        myQuery.setRelevance(2, anIfFalseNewRelevance);
        myQuery.setThesisId(3, aNewTargetId);
        myQuery.setRelationId(4, aRelationId);
        myQuery.setUserId(5, theLoginUser);
        int myNrOfRowsAffected = myQuery.executeUpdate();
        if (myNrOfRowsAffected == 0)
        {
            throw new ThesisNotOwnedException("You don't own relation "
                    + aRelationId);
        }
    }

    // ------------------------------------------------------------------------
    private ThesisId newThesis(ThesisText aThesisText)
    {
        ArgsQuery.LOCK_THESIS_WRITE.ps().executeQuery();
        ArgsDB myQuery = ArgsQuery.INSERT_THESIS.ps();
        myQuery.setThesisText(1, aThesisText);
        myQuery.setUserId(2, theLoginUser);
        int myNrOfRowsAffected = myQuery.executeUpdate();
        assert myNrOfRowsAffected == 1;

        ThesisId myThesisId = getThesisId(ArgsQuery.MAX_THESIS_ID.ps(),
                ArgsDB.COUNT);
        ArgsQuery.UNLOCK_TABLES.ps().executeQuery();
        return myThesisId;
    }

    // ------------------------------------------------------------------------
    private ThesisId newPremise2(ThesisId aSId, ThesisText aPremiseText,
            Relevance anIfTruePremiseRelevance, Relevance anIfFalsePremiseRelevance)
    {
        ThesisId myPremiseId = newThesis(aPremiseText);
        ArgsDB myQuery = ArgsQuery.INSERT_RELATION.ps();
        myQuery.setThesisId(1, myPremiseId);
        myQuery.setThesisId(2, aSId);
        myQuery.setRelevance(3, anIfTruePremiseRelevance);
        myQuery.setUserId(4, theLoginUser);
        myQuery.setRelevance(5, anIfFalsePremiseRelevance);

        myQuery.executeUpdate();
        return myPremiseId;
    }

    // ------------------------------------------------------------------------
    private void setThesisInfo2(
            ThesisId aThesisId, ThesisText aNewThesisText,
            ThesisOpinion aThesisOpinion)
    {
        assert theWritePerspective.isWritable() : "Non writable: " + theWritePerspective;
        ArgsDB myQuery = ArgsQuery.UPDATE_THESIS.ps();
        myQuery.setThesisText(1, aNewThesisText);
        myQuery.setThesisId(2, aThesisId);
        myQuery.setUserId(3, theLoginUser);
        int myNrOfRowsAffected = myQuery.executeUpdate();
        if (myNrOfRowsAffected == 0)
        {
            throw new ThesisNotOwnedException("You don't own thesis "
                    + aThesisId);
        }

        setThesisOpinion2(aThesisId, aThesisOpinion);
    }

    // ------------------------------------------------------------------------
    private void checkCanWrite()
    {
        assertTrue(theWritePerspective.isWritable());

        if (! theLoginUser.equals(theWritePerspective.getOwner()))
        {
            throw new PerspectiveNotOwnedException("You (" + theLoginUser.getScreenName() + ") don't own perspective " +
                theWritePerspective);
        }
    }
    
}
