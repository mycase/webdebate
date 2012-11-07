package com.arguments.functional.store.sql;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgsStore;
import com.arguments.functional.datamodel.ArgumentsException;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.ArgumentsUserId;
import com.arguments.functional.datamodel.OwnedPerspective;
import com.arguments.functional.datamodel.ForeignUserId;
import com.arguments.functional.datamodel.Perspective;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.PerspectiveName;
import com.arguments.functional.datamodel.PerspectiveThesisOpinion;
import com.arguments.functional.datamodel.RelatedThesis;
import com.arguments.functional.datamodel.Relation;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.Relevance;
import com.arguments.functional.datamodel.Thesis;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.datamodel.ThesisOpinionId;
import com.arguments.functional.datamodel.ThesisText;
import com.arguments.functional.report.ThesisFocusData;
import com.arguments.functional.store.sql.ArgsDB.ArgsQuery;
import com.arguments.support.Container;
import com.arguments.support.EmailAddress;
import com.arguments.support.Logger;
import com.arguments.support.ScreenName;
import com.arguments.support.sql.DBColumn;

public class ArgsSQLStore implements ArgsStore
{
    // ------------------------------------------------------------------------
    public ArgsSQLStore()
    {
        // empty
    }

    // ------------------------------------------------------------------------
    @Override
    public ArgumentsUser selectUserById(ArgumentsUserId aUserId)
    {
        return staticSelectUserById(aUserId);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public OwnedPerspective getPerspective(
            PerspectiveId aPerspectiveId)
    {
        return staticSelectPerspectiveById(aPerspectiveId);
    }

    // ------------------------------------------------------------------------
    @Override
    public OwnedPerspective getDefaultPerspective(ArgumentsUserId aUserId)
    {
        OwnedPerspective myPerspective = selectDefaultPerspective(aUserId);
        if (myPerspective == null)
        {
            myPerspective = createDefaultPerspective(aUserId);
            insertPerspective(myPerspective);
            myPerspective = selectDefaultPerspective(aUserId);
        }
        return myPerspective;
    }

    // ------------------------------------------------------------------------
    @Override
    public PerspectiveId getThesisOwnerPerspective()
    {
        return selectPerspectiveByName(PerspectiveName.THESIS_OWNER);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public OpinionatedThesis getThesis(
            ThesisId aThesisId,
            Perspective aPerspective)
    {
        return getOpinionatedThesis(aThesisId, aPerspective);
    }

    // ------------------------------------------------------------------------
    @Override
    public Thesis selectThesisById(ThesisId aThesisId)
    {
        ArgsDB myQuery = ArgsQuery.SELECT_THESIS_BY_ID.ps();
        myQuery.setThesisId(1, aThesisId);
        ResultSet myResult = myQuery.executeQuery();

        Thesis myThesis = parseThesis(myResult);
        assertNotNull( "Can't find thesis for ID = " + aThesisId, myThesis);
        return myThesis;
    }

    // ------------------------------------------------------------------------
    @Override
    public ThesisOpinion selectOwnerOpinion(
            ThesisId aThesisID)
    {
        Thesis myThesis = selectThesisById(aThesisID);
        ArgumentsUserId myOwnerID = myThesis.getOwnerID();
        assertNotNull(myOwnerID);

        PerspectiveId myPerspectiveId = getDefaultPerspective(myOwnerID);
        
        return selectOpinionByThesisPerspective(aThesisID, myPerspectiveId);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public ThesisOpinion selectOpinionByThesisPerspective(
            ThesisId aThesisID, PerspectiveId aPerspectiveId)
    {
        ArgsDB myQuery = ArgsQuery.SELECT_OPINION_BY_THESIS_PERSPECTIVE_ID.ps();
        myQuery.setThesisId(1, aThesisID);
        myQuery.setPerspectiveId(2, aPerspectiveId);
        ResultSet myResult = myQuery.executeQuery();

        ThesisOpinion myOpinion;
        try
        {
            boolean myHasRow = myResult.next();
            if (!myHasRow)
                return ThesisOpinion.NEUTRAL_OPINION;
            myOpinion = ThesisOpinion.newOpinionByProbability(
                    getDouble(myResult, ArgsDB.Opinion.LEVEL.c));
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }

        return myOpinion;
    }

    // ------------------------------------------------------------------------
    @Override
    public List<OpinionatedThesis> getAllTheses()
    {
        ArgsDB myQuery = ArgsQuery.SELECT_THESIS_ALL.ps();
        ResultSet myResult = myQuery.executeQuery();

        List<OpinionatedThesis> myTheses = parseTheses(myResult);
        return myTheses;
    }

    // ------------------------------------------------------------------------
    /**
     * @return
     */
    @Override
    public long getNrOfTheses()
    {
        ResultSet myResult = ArgsQuery.COUNT_ALL_THESES1.ps()
                .executeQuery();

        Integer myNrOfTheses;
        try
        {
            boolean myHasRow = myResult.next();
            assertTrue( myHasRow);
            myNrOfTheses = getInt(myResult, ArgsDB.NR_OF_THESES);
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }

        return myNrOfTheses;
    }

    // ------------------------------------------------------------------------
    /**
     * @return
     */
    @Override
    public long getNrOfOpinions()
    {
        ResultSet myResult = ArgsQuery.COUNT_ALL_OPINIONS.ps()
                .executeQuery();

        Integer myNrOfOpinions;
        try
        {
            boolean myHasRow = myResult.next();
            assertTrue( myHasRow);
            myNrOfOpinions = getInt(myResult, ArgsDB.NR_OF_OPINIONS);
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }

        return myNrOfOpinions;
    }

    // ------------------------------------------------------------------------
    @Override
    public ArgsState selectState(ArgumentsUser aUser)
    {
        ArgsState myState = selectStateNull(aUser);
        if (myState == null)
        {
            myState = createDefaultState(aUser); // Volatile ID
            insertState(myState, aUser);
            myState = selectStateNull(aUser); // With valid ID
        }
        return myState;
    }
    
    // ------------------------------------------------------------------------
    @Override
    public ArgumentsUser getAppUser(EmailAddress anEmailAddress,
            ForeignUserId aForeignId, ScreenName aScreenName)
    {
        ArgumentsUser myAppUser = ArgsSQLStore
                .selectUserByEmailForeignIdScreenName(
                    anEmailAddress, new ForeignUserId("" + aForeignId), aScreenName);

        if (myAppUser == null)
            myAppUser = newAppUser(anEmailAddress, aForeignId, aScreenName);

        assertNotNull( myAppUser );

        assertEquals( myAppUser.getEmailAddress(), anEmailAddress);
        assertEquals( myAppUser.getScreenName(), aScreenName);
        
        if (! myAppUser.getContainerId().equals(aForeignId))
                    updateUserSetForeignId(myAppUser, aForeignId);

        ArgumentsUser myAppUser2 = ArgsSQLStore
                .selectUserByEmailForeignIdScreenName(
                    anEmailAddress, aForeignId, aScreenName);
        
        assert myAppUser2.getContainerId().equals(aForeignId);
        
        return myAppUser2;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public void assureConnect()
    {
        ArgsSQLStoreException myException = null;
        for (int i = 0; i < 10; i++)
            try
            {
                ArgsQuery.MAX_USER_ID.ps().executeQuery();
                Logger.logAlways("Assured Connect");
                return;
            } catch (ArgsSQLStoreException anException)
            {
                myException = anException;
                try
                {
                    Thread.sleep((i + 1) * 100);
                } catch (InterruptedException anException1)
                {
                    // Doesn't happen
                }
                ArgsSQLStoreConnection.renewConnection();
            }
        throw new ArgsSQLStoreException("Can't renew connection", myException);
    }

    // ------------------------------------------------------------------------
    @Override
    public Relation selectRelationById(RelationId aRelationId)
    {
        assert aRelationId != null;
        ResultSet myResult;
        ArgsDB myQuery = ArgsQuery.SELECT_RELATION_BY_ID
                .ps();
        myQuery.setRelationId(1, aRelationId);
        myResult = myQuery.executeQuery();

        Relation myRelation = parseRelation(myResult);

        if (myRelation == null)
        {
            throw new ArgsSQLStoreException("Can't find relation with id = "
                    + aRelationId);
        }

        return myRelation;
    }

    // ------------------------------------------------------------------------
    @Override
    public Container<Thesis> selectPremiseTree(Thesis aThesis, int aDepth)
    {
        Container<Thesis> myReturnValue = new Container<>(aThesis);
        if (aDepth == 0)
            return myReturnValue;
        List<RelatedThesis> myPremises = ArgsSQLStore.getPremises(aThesis.getID());
        for (Thesis myPremise : myPremises)
            myReturnValue.add(selectPremiseTree(myPremise, aDepth - 1));
        return myReturnValue;
    }

    // ------------------------------------------------------------------------
    @Override
    public void deleteTestObjects()
    {
        deleteUserData(ArgumentsUserId.TEST7);
        deleteUserData(ArgumentsUserId.TEST2);
        ArgumentsUserId myNewUserId =
                selectUserByForeignId(ForeignUserId.BOGUS);
        if (myNewUserId != null)
        {
            deleteUserData(myNewUserId);
            deleteUser(myNewUserId);
        }
    }

    // ------------------------------------------------------------------------
    @Override
    public ThesisId selectLastTestThesisId()
    {
        return getThesisId(ArgsQuery.SELECT_LAST_TEST_THESIS.ps(),
                ArgsDB.Thesis.ID.c);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public RelationId selectLastTestRelationId()
    {
        return getRelationId(ArgsQuery.SELECT_LAST_TEST_RELATION.ps(),
                ArgsDB.Relation.ID.c);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public List<PerspectiveThesisOpinion> getOpinionsForThesisId(ThesisId aThesisId)
    {
        ArgsDB myQuery =
                ArgsQuery.SELECT_OPINIONS_BY_THESIS_ID1.ps();

        myQuery.setThesisId(1, aThesisId);
        
        return staticSelectThesisOpinion(myQuery);
    }

    // ------------------------------------------------------------------------
    @Override
    public ThesisFocusData getThesisFocusData(
            ThesisId aMainThesisId,
            ArgumentsUserId aUserId,
            Perspective aPerspective)
    {
        assert aPerspective != null;
        
        ThesisFocusData myReturnValue =
                new ThesisFocusData(aMainThesisId, aPerspective);

        try
        {
            OpinionatedThesis myThesis =
                    getOpinionatedThesis(aMainThesisId, aPerspective);
            myReturnValue.setMainThesis(myThesis);
            myReturnValue.setMainThesisOwned(myThesis.getOwnerID().equals(aUserId));
            ArgumentsUserId myPerspectiveOwner = aPerspective.getOwner();
            assertNotNull(aUserId);
            myReturnValue.setPerspectiveOwned(aUserId.equals(myPerspectiveOwner));
            ArgsDB myQuery = ArgsQuery.SELECT_PREMISE.ps();
            myQuery.setThesisId(1, aMainThesisId);
            ResultSet myPremiseResult = myQuery.executeQuery();
            while (myPremiseResult.next())
            {
                RelatedThesis myPremise = getRelatedThesis(myPremiseResult, aPerspective);
                myReturnValue.addPremise(myPremise);
            }
            
            myQuery = ArgsQuery.SELECT_CONCLUSION.ps();
            myQuery.setThesisId(1, aMainThesisId);
            ResultSet myDeductionResult = myQuery.executeQuery();

            while (myDeductionResult.next())
            {
                RelatedThesis myPremise = getRelatedThesis2(myDeductionResult, aPerspective);
                myReturnValue.addDeduction(myPremise);
            }
        }
        catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(
                    "Problem with focus thesis:\n" + aMainThesisId + "\n",
                    anException);
        }
        myReturnValue.check();
        return myReturnValue;
    }

    // ------------------------------------------------------------------------
    @Override
    public ThesisOpinion selectOpinionById(
            ThesisOpinionId anOpinionID)
    {
        ArgsDB myQuery = ArgsQuery.SELECT_OPINION_BY_ID.ps();
        myQuery.setThesisOpinionId(1, anOpinionID);
        List<PerspectiveThesisOpinion> myList =  staticSelectThesisOpinion(myQuery);
        assert myList.size() == 1;
        return myList.get(0);
    }

    // ------------------------------------------------------------------------
    @Override
    public ArgumentsUser selectUserByForeignId(ForeignUserId aUserId)
    {
        return staticSelectUserByForeignId(aUserId);
    }

    // ------------------------------------------------------------------------
    protected static void insertState(ArgsState aState, ArgumentsUser aUser)
    {
        assert ! aState.getPerspectiveId().equals(PerspectiveId.VOLATILE);
        int myNrRows = ArgsQuery.INSERT_STATE1.ps().
                setUserId(1, aUser).
                setThesisId(2, aState.getThesisId()).
                setPerspectiveId(3, aState.getPerspectiveId()).
                executeUpdate();
        assert myNrRows == 1;
    }

    // ------------------------------------------------------------------------
    protected static ArgumentsUser staticSelectUserById(ArgumentsUserId aUserId)
    {
        assert aUserId != null;
        ResultSet myResult;
        ArgsDB myQuery = ArgsQuery.SELECT_USER_BY_ID.ps();
        myQuery.setUserId(1, aUserId);
        myResult = myQuery.executeQuery();

        ArgumentsUser myUser = getUser(myResult);

        if (myUser == null)
        {
            throw new ArgsSQLStoreException("Can't find user with id = "
                    + aUserId);
        }

        return myUser;
    }

    // ------------------------------------------------------------------------
    protected static ThesisId getThesisId(
            ArgsDB aQuery,
            DBColumn aColumn)
    {
        Long myLongId = getLong(aQuery, aColumn);
        if (myLongId == null) return null;
        return new ThesisId(myLongId);
    }

    // ------------------------------------------------------------------------
    protected static Long getLong(
            ArgsDB aQuery,
            DBColumn aColumn)
    {
        ResultSet myResult = aQuery.executeQuery();

        try
        {
            boolean myHasRow = myResult.next();
            if (!myHasRow)
                return null;
            return myResult.getLong(aColumn.getName());
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }
    }

    // ------------------------------------------------------------------------
    protected static ThesisOpinionId getThesisOpinionId(
            ArgsDB aQuery,
            DBColumn aColumn)
    {
        Long myLongId = getLong(aQuery, aColumn);
        if (myLongId == null)
            return null;
        return new ThesisOpinionId(myLongId);
    }

    // ------------------------------------------------------------------------
    private static void updateUserSetForeignId(
            ArgumentsUser aUser,
            ForeignUserId aLiferayId)
    {
        ArgsDB myQuery = ArgsQuery.UPDATE_USER_SET_FOREIGN_ID.ps();
        myQuery.setForeignUserId(1, aLiferayId);
        myQuery.setUserId(2, aUser);
        int myNrRows = myQuery.executeUpdate();
        assert myNrRows == 1;
    }

    // ------------------------------------------------------------------------
    private static ArgsState selectStateNull(ArgumentsUser aUser)
    {
        ArgsDB myQuery = ArgsQuery.SELECT_STATE_BY_USERID.ps();
        myQuery.setUserId(1, aUser);
        return getState(myQuery);
    }
    
    // ------------------------------------------------------------------------
    private static ArgsState getState(
            ArgsDB aQuery)
    {
        DBColumn myColumn = ArgsDB.State.THESIS_ID.c;
        Long myLongId = getLong(aQuery, myColumn);
        
        if (myLongId == null) return null;

        PerspectiveId myPerspectiveId = new PerspectiveId(
                getLong(aQuery, ArgsDB.State.OPINION_STRATEGY_ID.c));
        ThesisId myThesisId = new ThesisId(myLongId);
        
        ArgsState myState = new ArgsState(myThesisId, RelationId.BONE, myPerspectiveId);
        return myState;
    }

    // ------------------------------------------------------------------------
    private static void deleteUserData(ArgumentsUserId aUserId)
    {
        ArgsQuery.DELETE_RELATIONS_BY_USERID.ps().setUserId(1, aUserId).executeUpdate();
        for (PerspectiveId myPerspectiveId: selectPerspectives(aUserId))
            ArgsQuery.DELETE_OPINIONS_BY_PERSPECTIVEID.ps().setPerspectiveId(1, myPerspectiveId).executeUpdate();
        ArgsQuery.DELETE_STATE_BY_USERID.ps().setUserId(1, aUserId).executeUpdate();
        ArgsQuery.DELETE_THESES_BY_USERID.ps().setUserId(1, aUserId).executeUpdate();
        ArgsQuery.DELETE_PERSPECTIVES_BY_USERID.ps().setUserId(1, aUserId).executeUpdate();
    }
    
    // ------------------------------------------------------------------------
    private static void deleteUser(ArgumentsUserId aUserId)
    {
        ArgsQuery.DELETE_USER_BY_ID.ps().setUserId(1, aUserId).executeUpdate();
    }
    
    // ------------------------------------------------------------------------
    private static List<PerspectiveThesisOpinion> staticSelectThesisOpinion(ArgsDB aQuery)
    {
        ResultSet myResult = aQuery.executeQuery();

        List<PerspectiveThesisOpinion> myReturnValue = new ArrayList<>();
        
        try
        {
            while(myResult.next())
            {
                double myOpinionDouble = getDouble(myResult, ArgsDB.Opinion.LEVEL.c);
                PerspectiveId myPerspectiveId =
                        getPerspectiveId(myResult, ArgsDB.Opinion.PERSPECTIVE_ID1.c);
                PerspectiveThesisOpinion myThesisOpinion = PerspectiveThesisOpinion.
                        newPersOpinionByProbability(myOpinionDouble, myPerspectiveId);
                myReturnValue.add(myThesisOpinion);
            }
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }

        return myReturnValue;
    }
    
    // ------------------------------------------------------------------------
    private static OwnedPerspective staticSelectDefaultPerspective(
            ArgumentsUserId aUserId)
    {
        assert aUserId != null;
        ResultSet myResult =
                ArgsQuery.SELECT_PERSPECTIVE_BY_NAME_USERID1.ps().
                setPerspectiveName(1, PerspectiveName.DEFAULT).
                setUserId(2, aUserId).executeQuery();

        OwnedPerspective myPerspective = getFixedUserPerspective(myResult);

        return myPerspective;
    }

    // ------------------------------------------------------------------------
    private static PerspectiveId selectPerspectiveByName(PerspectiveName aName)
    {
        ResultSet myResult =
                ArgsQuery.SELECT_PERSPECTIVE_BY_NAME.ps().
                setPerspectiveName(1, aName).executeQuery();

        OwnedPerspective myPerspective = getFixedUserPerspective(myResult);

        return myPerspective;
    }

    // ------------------------------------------------------------------------
    private static List<OwnedPerspective> selectPerspectives(
            ArgumentsUserId aUserId)
    {
        assert aUserId != null;
        ResultSet myResult =
                ArgsQuery.SELECT_PERSPECTIVE_BY_USERID1.ps().
                setUserId(1, aUserId).executeQuery();

        List<OwnedPerspective> myPerspectives = getFixedUserPerspectiveList(myResult);

        return myPerspectives;
        
    }
    
    // ------------------------------------------------------------------------
    private static OwnedPerspective staticSelectPerspectiveById(
            PerspectiveId aPerspectiveId)
    {
        assert aPerspectiveId != null;
        ResultSet myResult =
                ArgsQuery.SELECT_PERSPECTIVE_BY_ID.ps().
                setPerspectiveId(1, aPerspectiveId).
                executeQuery();

        OwnedPerspective myPerspective = getFixedUserPerspective(myResult);

        return myPerspective;
    }

    // ------------------------------------------------------------------------
    private static ArgumentsUser staticSelectUserByForeignId(ForeignUserId aUserId)
    {
        ArgsDB myQuery = ArgsQuery.SELECT_USER_BY_FOREIGN_ID.ps();
        myQuery.setForeignUserId(1, aUserId);
        ResultSet myResult = myQuery.executeQuery();

        ArgumentsUser myUser = getUser(myResult);

        return myUser;
    }

    // ------------------------------------------------------------------------
    private static ArgumentsUser selectUserByEmailForeignIdScreenName(
            EmailAddress anEmailAddress,
            ForeignUserId aUserId,
            ScreenName aScreenName)
    {
        ArgsDB myQuery = ArgsQuery.SELECT_USER_BY_EMAIL_FOREIGN_ID_SCREENNAME.ps();
        myQuery.setEmailAddress(1, anEmailAddress);
        myQuery.setForeignUserId(2, aUserId);
        myQuery.setScreenName(3, aScreenName);
        ResultSet myResult = myQuery.executeQuery();

        ArgumentsUser myUser = getUser(myResult);

        return myUser;
    }

    // ------------------------------------------------------------------------
    private static RelationId getRelationId(
            ArgsDB aQuery,
            DBColumn aColumn)
    {
        Long myLongId = getLong(aQuery, aColumn);
        if (myLongId == null) return null;
        return new RelationId(myLongId);
    }

    // ------------------------------------------------------------------------
    private static ArgumentsUserId getUserId(
            ArgsDB aQuery,
            DBColumn aColumn)
    {
        Long myLongId = getLong(aQuery, aColumn);
        if (myLongId == null) return null;
        return new ArgumentsUserId(myLongId);
    }

    // ------------------------------------------------------------------------
    private static ThesisId getThesisId(ResultSet aResultSet, DBColumn aColumn)
            throws SQLException
    {
        return new ThesisId(aResultSet.getLong(aColumn.getName()));
    }
    
    // ------------------------------------------------------------------------
    private static ThesisText getThesisText(ResultSet aResultSet, DBColumn aColumn)
            throws SQLException
    {
        return new ThesisText(aResultSet.getString(aColumn.getName()));
    }
    
    // ------------------------------------------------------------------------
    private static EmailAddress getEmailAddress(ResultSet aResultSet, DBColumn aColumn)
            throws SQLException
    {
        return new EmailAddress(aResultSet.getString(aColumn.getName()));
    }
    
    // ------------------------------------------------------------------------
    private static ForeignUserId getForeignUserId(ResultSet aResultSet, DBColumn aColumn)
            throws SQLException
    {
        return new ForeignUserId(aResultSet.getString(aColumn.getName()));
    }
    
    // ------------------------------------------------------------------------
    private static ScreenName getScreenName(ResultSet aResultSet, DBColumn aColumn)
            throws SQLException
    {
        return new ScreenName(aResultSet.getString(aColumn.getName()));
    }
    
    // ------------------------------------------------------------------------
    private static ArgumentsUserId getUserId(ResultSet aResultSet, DBColumn aColumn)
            throws SQLException
    {
        return new ArgumentsUserId(aResultSet.getLong(aColumn.getName()));
    }
    
    // ------------------------------------------------------------------------
    private static PerspectiveId getPerspectiveId(ResultSet aResultSet, DBColumn aColumn)
            throws SQLException
    {
        return new PerspectiveId(aResultSet.getLong(aColumn.getName()));
    }
    
    // ------------------------------------------------------------------------
    private static PerspectiveName getPerspectiveName(ResultSet aResultSet, DBColumn aColumn)
            throws SQLException
    {
        return new PerspectiveName(aResultSet.getString(aColumn.getName()));
    }
    
    // ------------------------------------------------------------------------
    private static RelationId getRelationId(ResultSet aResultSet, DBColumn aColumn)
            throws SQLException
    {
        return new RelationId(aResultSet.getLong(aColumn.getName()));
    }
    
    // ------------------------------------------------------------------------
    private static RelatedThesis getRelatedThesis(
            ResultSet aResultSet,
            Perspective aPerspective)
            throws SQLException
    {
        ThesisId myThesis1ID = getThesisId(aResultSet, ArgsDB.Relation.THESIS_1_ID.c);
        ThesisId myThesis2ID = getThesisId(aResultSet, ArgsDB.Relation.THESIS_2_ID.c);
        ThesisText mySummary = getThesisText(aResultSet, ArgsDB.Thesis.SUMMARY.c);
        ThesisOpinion myOpinion = aPerspective.getOpinion(myThesis1ID);
        Relevance myIfTrueRelevance = selectIfTrueRelevance(aResultSet);
        Relevance myIfFalseRelevance = selectIfFalseRelevance(aResultSet);
        ArgumentsUserId myOwnerID = getUserId(aResultSet, ArgsDB.OWNER_ID_);
        RelationId myRelationID = getRelationId(aResultSet, ArgsDB.RELATION_ID);
        Relation myRelation = new Relation(myRelationID,
                myThesis1ID, myThesis2ID,
                myIfTrueRelevance, myIfFalseRelevance, ArgumentsUserId.BOGUS);
        return new RelatedThesis(
                myThesis1ID, mySummary, myOpinion, myRelation,
                myOwnerID);
    }
    
    // ------------------------------------------------------------------------
    private static RelatedThesis getRelatedThesis2(
            ResultSet aResultSet,
            Perspective aPerspective)
            throws SQLException
    {
        ThesisId myThesis1ID = getThesisId(aResultSet, ArgsDB.Relation.THESIS_1_ID.c);
        ThesisId myThesis2ID = getThesisId(aResultSet, ArgsDB.Relation.THESIS_2_ID.c);
        ThesisText mySummary = getThesisText(aResultSet, ArgsDB.Thesis.SUMMARY.c);
        ThesisOpinion myOpinion = aPerspective.getOpinion(myThesis2ID);
        Relevance myIfTrueRelevance = selectIfTrueRelevance(aResultSet);
        Relevance myIfFalseRelevance = selectIfFalseRelevance(aResultSet);
        ArgumentsUserId myOwnerID = getUserId(aResultSet, ArgsDB.OWNER_ID_);
        RelationId myRelationID = getRelationId(aResultSet, ArgsDB.RELATION_ID);
        Relation myRelation = new Relation(myRelationID,
                myThesis1ID, myThesis2ID, myIfTrueRelevance, myIfFalseRelevance,
                ArgumentsUserId.BOGUS);
        return new RelatedThesis(
                myThesis2ID, mySummary, myOpinion, myRelation,
                myOwnerID);
    }

    // ------------------------------------------------------------------------
    private static List<Relation> parseRelations(ResultSet aResultSet)
    {
        List<Relation> myReturnValue = new ArrayList<>();
        try
        {
            while (aResultSet.next())
            {
                RelationId myRelationID = getRelationId(aResultSet, ArgsDB.Relation.ID.c);
                ThesisId myThesis1ID = getThesisId(aResultSet, ArgsDB.Relation.THESIS_1_ID.c);
                ThesisId myThesis2ID = getThesisId(aResultSet, ArgsDB.Relation.THESIS_2_ID.c);
                Relevance myIfTrueRelevance = selectIfTrueRelevance(aResultSet);
                Relevance myIfFalseRelevance = selectIfFalseRelevance(aResultSet);
                ArgumentsUserId myOwnerID = getUserId(aResultSet, ArgsDB.Relation.OWNER_ID.c);
                myReturnValue.add(
                        new Relation(
                            myRelationID,
                            myThesis1ID,
                            myThesis2ID,
                            myIfTrueRelevance,
                            myIfFalseRelevance,
                            myOwnerID));
            }
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }
        
        return myReturnValue;
    }
    
    // ------------------------------------------------------------------------
    private static OwnedPerspective getFixedUserPerspective(ResultSet aResultSet)
    {
        List<OwnedPerspective> myPerspectives = getFixedUserPerspectiveList(aResultSet);
        
        assert myPerspectives.size() < 2;
        if (myPerspectives.size() == 0) return null;
        return myPerspectives.get(0);
    }

    // ------------------------------------------------------------------------
    private static List<OwnedPerspective> getFixedUserPerspectiveList(ResultSet aResultSet)
    {
        List<OwnedPerspective> myReturnValue = new ArrayList<>();
        try
        {
            while (aResultSet.next())
            {
                PerspectiveId myID = getPerspectiveId(aResultSet, ArgsDB.Perspective.ID.c);
                PerspectiveName myName = getPerspectiveName(aResultSet, ArgsDB.Perspective.NAME.c);
                ArgumentsUserId myUserID = getUserId(aResultSet, ArgsDB.Perspective.OWNER_ID.c);
                
                OwnedPerspective myObject =
                        new OwnedPerspective(
                                myID,
                                myName,
                                myUserID);
                myReturnValue.add(myObject);
            }
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }
        
        return myReturnValue;
    }

    // ------------------------------------------------------------------------
    private static ArgumentsUser getUser(ResultSet aResultSet)
    {
        int myRecordCount = 0;
        ArgumentsUser myUser = null;
        try
        {
            while (aResultSet.next())
            {
                myRecordCount += 1;
                ArgumentsUserId myUserID = getUserId(aResultSet, ArgsDB.User.ID.c);
                EmailAddress myEmailAddress = getEmailAddress(aResultSet, ArgsDB.User.EMAIL.c);
                ForeignUserId myContainerId = getForeignUserId(aResultSet, ArgsDB.User.CONTAINER_ID.c);
                ScreenName myScreenName = getScreenName(aResultSet, ArgsDB.User.SCREEN_NAME.c);
                myUser =
                        new ArgumentsUser(
                            myEmailAddress,
                            myUserID,
                            myContainerId,
                            myScreenName);
            }
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }
        
        if ( myRecordCount > 1)
            throw new ArgumentsException("Ambiguous user specification");
        return myUser;
    }

    // ------------------------------------------------------------------------
    private static List<RelatedThesis> getPremises(ThesisId aThesisId)
    {
        List<RelatedThesis> myReturnValue = new ArrayList<>();
        ArgsDB myQuery = ArgsQuery.SELECT_PREMISE.ps();
        myQuery.setThesisId(1, aThesisId);
        ResultSet myPremiseResult = myQuery.executeQuery();

        try
        {
            while (myPremiseResult.next())
            {
                ThesisId myThesis1ID = getThesisId(myPremiseResult, ArgsDB.Relation.THESIS_1_ID.c);
                ThesisId myThesis2ID = getThesisId(myPremiseResult, ArgsDB.Relation.THESIS_2_ID.c);
                ThesisText mySummary = getThesisText(myPremiseResult, ArgsDB.Thesis.SUMMARY.c);
                Relevance myIfTrueRelevance = selectIfTrueRelevance(myPremiseResult);
                Relevance myIfFalseRelevance = selectIfFalseRelevance(myPremiseResult);
                ArgumentsUserId myOwnerID = getUserId(myPremiseResult, ArgsDB.OWNER_ID_);
                RelationId myRelationID = getRelationId(myPremiseResult, ArgsDB.RELATION_ID);
                Relation myRelation = new Relation(myRelationID,
                        myThesis1ID, myThesis2ID,
                        myIfTrueRelevance, myIfFalseRelevance, ArgumentsUserId.BOGUS);
                RelatedThesis myPremise = new RelatedThesis(
                        myThesis1ID, mySummary, ThesisOpinion.BELIEVE_FALSE, myRelation, myOwnerID);
                myReturnValue.add(myPremise);
            }
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(
                    "Problem with retrieving thesis with id:\n" + aThesisId + "\n",
                    anException);
        }

        return myReturnValue;
    }

    // ------------------------------------------------------------------------
    private static OpinionatedThesis getOpinionatedThesis(
            ThesisId aThesisId,
            Perspective aPerspective)
    {
        assert aPerspective != null;
        
        ArgsDB myQuery =
                ArgsQuery.SELECT_THESIS_BY_ID.ps();

        myQuery.setThesisId(1, aThesisId);
        ResultSet myResult = myQuery.executeQuery();

        OpinionatedThesis myThesis = getOpinionThesis(myResult,
                aPerspective);
        assert myThesis != null : "Can't find thesis for ID = "
                + aThesisId;
        return myThesis;
    }

    // ------------------------------------------------------------------------
    private static OpinionatedThesis getOpinionThesis(
            ResultSet aResultSet,
            Perspective aPerspective)
    {
        assert aPerspective != null;

        int myMiddleCount = 0;
        OpinionatedThesis myThesis = null;
        try
        {
            while (aResultSet.next())
            {
                myMiddleCount += 1;
                ThesisId mySourceID = getThesisId(aResultSet, ArgsDB.Thesis.ID.c);
                ThesisText mySummary = getThesisText(aResultSet, ArgsDB.Thesis.SUMMARY.c);
                ThesisOpinion myOpinion = aPerspective.getOpinion(mySourceID);
                ArgumentsUserId myOwnerID = getUserId(aResultSet, ArgsDB.Thesis.OWNER_ID.c);
                assert mySourceID != null;
                myThesis = new OpinionatedThesis(mySourceID, mySummary,
                        myOpinion, myOwnerID);
            }
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }
        assert myMiddleCount < 2;
        return myThesis;
    }

    // ------------------------------------------------------------------------
    private static Thesis parseThesis(ResultSet aResultSet)
    {
        List<OpinionatedThesis> myTheses = parseTheses(aResultSet);
        assert myTheses.size() == 1;
        return myTheses.get(0);
    }

    // ------------------------------------------------------------------------
    private static List<OpinionatedThesis> parseTheses(ResultSet aResultSet)
    {
        List<OpinionatedThesis> myTheses = new ArrayList<>();
        try
        {
            while (aResultSet.next())
            {
                ThesisId mySourceID = getThesisId(aResultSet, ArgsDB.Thesis.ID.c);
                ThesisText mySummary = getThesisText(aResultSet, ArgsDB.Thesis.SUMMARY.c);
                ArgumentsUserId myOwnerID = getUserId(aResultSet, ArgsDB.Thesis.OWNER_ID.c);
                ThesisOpinion myOpinion = ThesisOpinion.NEUTRAL_OPINION;
                assert mySourceID != null;
                OpinionatedThesis myThesis = new OpinionatedThesis(
                        mySourceID, mySummary, myOpinion, myOwnerID);
                myTheses.add(myThesis);
            }
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }
        return myTheses;
    }

    // ------------------------------------------------------------------------
    private static ArgumentsUser newAppUser(
            EmailAddress anEmailAddress,
            ForeignUserId aContainerId,
            ScreenName aScreenName)
    {
        ArgsQuery.LOCK_USER_WRITE.ps().executeQuery();
        ArgsDB myQuery = ArgsQuery.INSERT_USER.ps();
        myQuery.setEmailAddress(1, anEmailAddress);
        myQuery.setForeignUserId(2, aContainerId);
        myQuery.setScreenName(3, aScreenName);
        int myNrOfRowsAffected = myQuery.executeUpdate();
        assert myNrOfRowsAffected == 1;
        ArgumentsUserId myUserId = getUserId(ArgsQuery.MAX_USER_ID.ps(),
                ArgsDB.COUNT);
        ArgsQuery.UNLOCK_TABLES.ps().executeQuery();

        return staticSelectUserById(new ArgumentsUserId(myUserId));
    }

    // ------------------------------------------------------------------------
    private static Relevance selectIfTrueRelevance(ResultSet aPremiseResult)
            throws SQLException
    {
        return new Relevance(getDouble(aPremiseResult, ArgsDB.Relation.WEIGHT.c));
    }
    
    // ------------------------------------------------------------------------
    private static Relevance selectIfFalseRelevance(ResultSet aPremiseResult)
            throws SQLException
    {
        return new Relevance(getDouble(aPremiseResult, ArgsDB.Relation.IMPLICATION21.c));
    }
    
    // ------------------------------------------------------------------------
    private static Relation parseRelation(ResultSet aResult)
    {
        List<Relation> myRelations = parseRelations(aResult);
        if( myRelations.size() == 1)
            return myRelations.get(0);
        return null;
    }

    // ------------------------------------------------------------------------
    private static double getDouble(ResultSet aResult, DBColumn aC)
            throws SQLException
    {
        return aResult.getDouble(aC.getName());
    }

    // ------------------------------------------------------------------------
    private static Integer getInt(ResultSet aResult, DBColumn aC)
            throws SQLException
    {
        return aResult.getInt(aC.getName());
    }

    // ------------------------------------------------------------------------
    private static OwnedPerspective createDefaultPerspective(
            ArgumentsUserId aUser)
    {
        return new OwnedPerspective(
                PerspectiveName.DEFAULT, aUser);
    }

    // ------------------------------------------------------------------------
    private static void insertPerspective(OwnedPerspective aPerspective)
    {
        int myNrRows = ArgsQuery.INSERT_PERSPECTIVE.ps().
                setPerspectiveName(1, aPerspective.getName()).
                setUserId(2, aPerspective.getOwnerId()).
                executeUpdate();
        assert myNrRows == 1;
    }

    // ------------------------------------------------------------------------
    private ArgsState createDefaultState(ArgumentsUser aUser)
    {
        PerspectiveId myPerspective = getDefaultPerspective(aUser);
        assert myPerspective.isWritable();
        ArgsState myState = new ArgsState(ThesisId.ONE, RelationId.BONE, myPerspective);
        return myState;
    }

    // ------------------------------------------------------------------------
    private static OwnedPerspective selectDefaultPerspective(
            ArgumentsUserId aUserId)
    {
        return staticSelectDefaultPerspective(aUserId);
    }
}
