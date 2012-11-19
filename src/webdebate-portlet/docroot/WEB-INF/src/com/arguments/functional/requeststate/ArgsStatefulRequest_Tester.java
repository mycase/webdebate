package com.arguments.functional.requeststate;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.arguments.application.liferay.LiferayArgsRequestKey;
import com.arguments.functional.command.Command;
import com.arguments.functional.datamodel.ArgsActionRequest;
import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.datamodel.ArgsReadOnlyState;
import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.ArgumentsUserId;
import com.arguments.functional.datamodel.ArgumentsUser_Tester;
import com.arguments.functional.datamodel.Perspective;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.PerspectiveThesisOpinion;
import com.arguments.functional.datamodel.ThesisOwnerPerspective;
import com.arguments.functional.datamodel.Relation;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.Relevance;
import com.arguments.functional.datamodel.Thesis;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.datamodel.ThesisText;
import com.arguments.functional.report.PerspectiveNotOwnedException;
import com.arguments.functional.report.ThesisNotOwnedException;
import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.report.pagemodels.PageModelFactory;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateStateFlag;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.support.PortletParameterMap;
import com.arguments.support.ServletParameterMap;

public class ArgsStatefulRequest_Tester
{
    // ------------------------------------------------------------------------
    @Before
    public void setUp()
    {
        TheArgsStore.i().deleteTestObjects();
    }

    // ------------------------------------------------------------------------
    @Test
    public void insertedThesisLeavesStateThesis()
    {
        ThesisId myThesisId = insertThesis();
        ArgsReadOnlyState myState = TheArgsStore.i().selectState(
                ArgumentsUser_Tester.getTestUser2());
        assertEquals(myThesisId, myState.getThesisId());
    }

    // ------------------------------------------------------------------------
    /**
     * Tests the creation of a thesis
     */
    @Test
    public void testInsertOpinion()
    {
        insertOpinion();
    }

    // ------------------------------------------------------------------------
    /**
     * Tests the modification of a thesis
     */
    @Test
    public void testUpdateThesis()
    {
        ThesisId myThesisId = insertThesis();
        ArgumentsUser myAppUser = ArgumentsUser_Tester.getTestUser2();

        OpinionatedThesis myThesis = TheArgsStore.i().getThesis(myThesisId,
                new ThesisOwnerPerspective());
        assertEquals(ThesisOpinion.BELIEVE_FALSE, myThesis.getOpinion());

        ArgsStatefulCommand myRequest = getThesisModificationCommand(
                myThesisId, myAppUser);

        myRequest.execute();

        myThesis = TheArgsStore.i().getThesis(myThesisId,
                new ThesisOwnerPerspective());
        assertEquals(ThesisOpinion.BELIEVE_TRUE, myThesis.getOpinion());
    }

    // ------------------------------------------------------------------------
    /**
     * Tests the blocking of illegal modification of a thesis
     * 
     * @throws Exception
     */
    @Test
    public void testIllegalUpdateThesis()
    {
        ThesisId myThesisId = insertThesis();

        ArgumentsUser myAppUser = ArgumentsUser_Tester.getTestUser2();
        ArgsStatefulCommand myRequest = getIllegalModificationCommand(
                myThesisId, myAppUser);

        try
        {
            myRequest.execute();
        } catch (ThesisNotOwnedException anException)
        {
            return;
        }
        throw new AssertionError("Expected exception not thrown");
    }

    // ------------------------------------------------------------------------
    /**
     * Tests the insertion and modification of a premise
     */
    @Test
    public void testInsertPremise()
    {
        ThesisId myThesis2Id = insertThesis();
        insertPremise(myThesis2Id);
    }

    // ------------------------------------------------------------------------
    /**
     * Tests the insertion of a premise
     */
    @Test
    public void testInsertPremise2()
    {
        ArgumentsUser myAppUser = TheArgsStore.i().selectUserById(
                ArgumentsUserId.TEST2);
        ArgsReadOnlyState myState = TheArgsStore.i().selectState(myAppUser);
        assertNotSame(myState.getPerspectiveId(),
                PerspectiveId.getThesisOwner());
        assertEquals(ArgumentsUser_Tester.getMailAddress(2),
                myAppUser.getEmailAddress());

        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap.put(ArgsRequestKey.PREMISE_TEXT, "This premise is true.");
        myRequestMap.put(ArgsRequestKey.PREMISE_IF_TRUE_WEIGHT, "0");
        myRequestMap.put(ArgsRequestKey.PREMISE_IF_FALSE_WEIGHT, "10");
        myRequestMap.put(ArgsRequestKey.PREMISE_OPINION, ""
                + ThesisOpinion.NEUTRAL_I);

        Command myCommand = RequestParser.getCommand(myAppUser, myRequestMap);

        ArgsStatefulCommand myStateCommand = new ArgsStatefulCommand(myCommand,
                new ArgsState(ThesisId.ONE, RelationId.BONE,
                        myAppUser.getDefaultPerspective()));
        myStateCommand.execute();
    }

    // ------------------------------------------------------------------------
    /**
     * Tests the insertion and modification of a premise
     */
    @Test
    public void testUpdateLink()
    {
        ThesisId myThesis2Id = insertThesis();
        RelationId myRelationId = insertPremise(myThesis2Id);
        int myIfTrueRelevance = 99;
        int myIfFalseRelevance = 88;

        ArgsStatefulCommand myRequest = getLinkModificationCommand(myThesis2Id,
                myRelationId, myIfTrueRelevance, myIfFalseRelevance);

        myRequest.execute();

        Relation myTestRelation = TheArgsStore.i().selectRelationById(
                myRelationId);
        assert myTestRelation.getIfTruePercentageRelevance().equals(
                myIfTrueRelevance) : "Wrong relevance: "
                + myTestRelation.getIfTruePercentageRelevance();
        assert myTestRelation.getIfFalsePercentageRelevance().equals(
                myIfFalseRelevance) : "Wrong relevance: "
                + myTestRelation.getIfFalsePercentageRelevance();
        assert myTestRelation.getThesis2().equals(ThesisId.ONE) : "Wrong target id: "
                + myTestRelation.getThesis2();
    }

    // ------------------------------------------------------------------------
    @Test
    public void testUpdatePerspective() throws NumberFormatException
    {
        ThesisId myThesisId = insertOpinion();

        ArgumentsUser myUser1 = ArgumentsUser_Tester.getTestUser2();
        ArgumentsUser myUser2 = ArgumentsUser_Tester.getTestUser7();

        PerspectiveId myPerspectiveId0 = PerspectiveId.getThesisOwner();
        PerspectiveId myPerspectiveId1 = myUser1.getDefaultPerspective();
        PerspectiveId myPerspectiveId2 = myUser2.getDefaultPerspective();

        ArgsReadOnlyState myState1 = TheArgsStore.i().selectState(myUser1);
        assertEquals(myPerspectiveId1, myState1.getPerspectiveId());

        ArgsStatefulCommand myRequest = getUpdatePerspectiveRequest(myPerspectiveId0);

        ArgsReadOnlyState myState = myRequest.execute();
        StateChange aStateString = myState.getStateChange();
        assertNotNull(aStateString);
        ArgsState myState1_1 = TheArgsStore.i().selectState(myUser1);

        myState1_1.mergeStateChange(aStateString);
        TheArgsStore.i(myUser1).updateState(myState1_1);

        ArgsReadOnlyState myState2 = TheArgsStore.i().selectState(
                ArgumentsUser_Tester.getTestUser7());
        PerspectiveId myOutPerspectiveId2 = myState2.getPerspectiveId();
        assert myOutPerspectiveId2.equals(myPerspectiveId2);

        Perspective myPerspective1 = myState1.getPerspectives().get(0);
        Perspective myPerspective2 = myState2.getPerspectives().get(0);

        assert !myPerspective1.equals(myPerspective2);

        OpinionatedThesis myThesis1 = TheArgsStore.i().getThesis(myThesisId,
                myPerspective1);

        OpinionatedThesis myThesis2 = TheArgsStore.i().getThesis(myThesisId,
                myPerspective2);

        assert !myThesis1.getOpinion().equals(myThesis2.getOpinion()) : "Equal: "
                + myThesis1.getOpinion() + ", " + myThesis2.getOpinion();
        assertNotSame(myThesis1.getOpinion(), myThesis2.getOpinion());
    }

    // ------------------------------------------------------------------------
    @Test
    public void testUrlContainer()
    {
        UrlContainer myContainer = new UrlContainer();
        myContainer.setEditLinkUrl("bla");
        myContainer.setEditThesisUrl("bla");
        myContainer.setAddPremiseUrl("bla");
        myContainer.setAddOpinionUrl("bla");
    }

    // ------------------------------------------------------------------------
    @Test
    public void cantWriteUnderOthersPerspective()
    {
        ArgumentsUser myUser1 = ArgumentsUser_Tester.getTestUser2();
        ArgumentsUser myUser2 = ArgumentsUser_Tester.getTestUser7();

        // User1 owns a thesis
        ThesisId myThesisId = insertThesis();

        // User2 focuses on User1's thesis
        ArgsJspRenderRequest myRequest = getThesisRenderRequest(myUser2,
                myThesisId);

        PageModelFactory.getThesisFocusPage(myRequest);

        // User2 takes User1's perspective
        PerspectiveId myPerspectiveId1 = myUser1.getDefaultPerspective();

        PortletParameterMap myPParameterMap1 = new PortletParameterMap();
        myPParameterMap1.put(
                LiferayArgsRequestKey.s(ArgsRequestKey.SET_PERSPECTIVE_ID),
                new String[] { myPerspectiveId1.getIdString() });
        ServletParameterMap mySParameterMap1 = new ServletParameterMap();

        ArgsActionRequest myRequest1 = new ArgsActionRequest(myPParameterMap1,
                mySParameterMap1, myUser2);
        myRequest1.execute();

        // User2 tries to write his own opinion

        PortletParameterMap myPParameterMap2 = new PortletParameterMap();
        myPParameterMap2.put(
                LiferayArgsRequestKey.s(ArgsRequestKey.NEW_THESIS_OPINION2),
                new String[] { "0" });
        ServletParameterMap mySParameterMap2 = new ServletParameterMap();

        try
        {
            ArgsActionRequest myRequest2 = new ArgsActionRequest(
                    myPParameterMap2, mySParameterMap2, myUser2);

            myRequest2.execute();
        } catch (PerspectiveNotOwnedException anException)
        {
            return;
        }

        throw new AssertionError("Expected exception not thrown");
    }

    // ------------------------------------------------------------------------
    @Test
    public void testSecondPerspective()
    {
        ArgumentsUser myUser1 = ArgumentsUser_Tester.getTestUser2();
        ArgumentsUser myUser2 = ArgumentsUser_Tester.getTestUser7();

        // User1 owns a thesis
        ThesisId myThesisId = insertThesis();
        PerspectiveId myPerspectiveId = PerspectiveId.P4;

        // User2 focuses on User1's thesis
        ArgsJspRenderRequest myRequest = getPerspective2RenderRequest(myUser2,
                myPerspectiveId);

        PageModelFactory.getThesisFocusPage(myRequest);
    }
    
    // ------------------------------------------------------------------------
    private static ArgsJspRenderRequest getThesisRenderRequest(
            ArgumentsUser aUser,
            ThesisId aThesisId)
    {
        ServletParameterMap mySParameterMap0 = new ServletParameterMap();
        mySParameterMap0.put(LiferayArgsRequestKey.s(ArgsRequestKey.THESIS_ID),
                new String[] { "" + aThesisId });

        return getServletRenderRequest(aUser, mySParameterMap0);
    }

    
    // ------------------------------------------------------------------------
    private static ArgsJspRenderRequest getPerspective2RenderRequest(
            ArgumentsUser aUser,
            PerspectiveId aPerspectiveId)
    {
        ServletParameterMap mySParameterMap0 = new ServletParameterMap();
        mySParameterMap0.put(LiferayArgsRequestKey.s(ArgsRequestKey.PERSPECTIVE2_ID),
                new String[] { "" + aPerspectiveId });

        return getServletRenderRequest(aUser, mySParameterMap0);
    }

    
    // ------------------------------------------------------------------------
    private static ArgsJspRenderRequest getServletRenderRequest(ArgumentsUser aUser,
            ServletParameterMap aSParameterMap0)
    {
        PortletParameterMap myPParameterMap0 = new PortletParameterMap();

        ArgsRequest myRequest0 = new ArgsRequest(myPParameterMap0,
                aSParameterMap0, aUser);

        UrlContainer myContainer = new UrlContainer();
        CgiSource mySource = CgiSource.SERVLET;
        UpdateStateFlag myUpdateState = UpdateStateFlag.YES;
        
        ArgsErrorHandler myErrorHandler = new ArgsErrorHandler(){

            @Override
            public String handle(Throwable aException)
            {
                throw new AssertionError(aException);
            }};
        ArgsJspRenderRequest myRequest = new ArgsJspRenderRequest(myRequest0,
                myContainer, mySource, myUpdateState, myErrorHandler);
        
        return myRequest;
    }

    // ------------------------------------------------------------------------
    /**
     * insert a thesis
     */
    public static ThesisId insertThesis(Integer anOpinion)
    {
        ArgsStatefulCommand myRequest = getInsertThesisCommand(anOpinion);

        long myNrOfThesesBefore = TheArgsStore.i().getNrOfTheses();
        long myNrOfOpinionsBefore = TheArgsStore.i().getNrOfOpinions();
        ArgsReadOnlyState myState = myRequest.execute();
        assert myState.getPerspectiveId().isWritable();
        TheArgsStore.i(ArgumentsUser_Tester.getTestUser2())
                .updateState(myState);
        long myNrOfThesesAfter = TheArgsStore.i().getNrOfTheses();
        assert myNrOfThesesAfter == myNrOfThesesBefore + 1;
        long myNrOfOpinionsAfter = TheArgsStore.i().getNrOfOpinions();
        assert myNrOfOpinionsAfter == myNrOfOpinionsBefore + 1;
        ThesisId myThesisId = TheArgsStore.i().selectLastTestThesisId();
        return myThesisId;
    }

    // ------------------------------------------------------------------------
    public static ThesisId insertOpinion()
    {
        return insertOpinion(ArgumentsUser_Tester.getTestUser2(),
                ArgumentsUser_Tester.getTestUser7());
    }

    // ------------------------------------------------------------------------
    private static ThesisId insertOpinion(ArgumentsUser aUser1,
            ArgumentsUser aUser2)
    {

        ThesisId myThesisId = insertThesis();

        ArgsStatefulCommand myRequest = getInsertOpinionCommand(myThesisId,
                ThesisOpinion.BELIEVE_TRUE, aUser1);

        long myCountBefore = TheArgsStore.i().getNrOfOpinions();

        myRequest.execute();
        long myCountAfter = TheArgsStore.i().getNrOfOpinions();
        assertTrue(myCountAfter == myCountBefore);

        myRequest = getInsertOpinionCommand(myThesisId,
                ThesisOpinion.NEUTRAL_OPINION, aUser2);

        myCountBefore = TheArgsStore.i().getNrOfOpinions();
        myRequest.execute();
        myCountAfter = TheArgsStore.i().getNrOfOpinions();
        assert myCountAfter == myCountBefore + 1;

        List<PerspectiveThesisOpinion> myOpinions = TheArgsStore.i()
                .getOpinionsForThesisId(myThesisId);
        for (ThesisOpinion myOpinion : myOpinions)
        {
            String myMessage = "Found opinion: ";
            myMessage = myMessage + myOpinion;
        }

        myRequest = getInsertOpinionCommand(myThesisId,
                ThesisOpinion.BELIEVE_FALSE, aUser2);

        myCountBefore = TheArgsStore.i().getNrOfOpinions();
        myRequest.execute();
        myCountAfter = TheArgsStore.i().getNrOfOpinions();
        assert myCountAfter == myCountBefore;

        return myThesisId;
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulCommand getUpdatePerspectiveRequest(
            PerspectiveId aPerspectiveId)
    {
        return getPerspectiveRequest(
                ArgsRequestKey.SET_PERSPECTIVE_ID, aPerspectiveId);
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulCommand getAddPerspectiveRequest(
            PerspectiveId aPerspectiveId)
    {
        return getPerspectiveRequest(
                ArgsRequestKey.ADD_PERSPECTIVE_ID, aPerspectiveId);
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulCommand getRemovePerspectiveRequest(
            PerspectiveId aPerspectiveId)
    {
        return getPerspectiveRequest(
                ArgsRequestKey.REMOVE_PERSPECTIVE_ID, aPerspectiveId);
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulCommand getPerspectiveRequest(
            ArgsRequestKey aKey, PerspectiveId aPerspectiveId)
    {
        ArgumentsUser myAppUser = ArgumentsUser_Tester.getTestUser2();

        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap.put(aKey,
                "" + aPerspectiveId.getIdString());

        Command myRequest1 = RequestParser.getCommand(myAppUser, myRequestMap);

        return new ArgsStatefulCommand(myRequest1, new ArgsState(ThesisId.ONE,
                RelationId.BONE, PerspectiveId.getThesisOwner()));
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulCommand getInsertThesisCommand(Integer anOpinion)
    {
        ArgumentsUser myAppUser = ArgumentsUser_Tester.getTestUser2();

        assertEquals(myAppUser.getEmailAddress(),
                ArgumentsUser_Tester.getMailAddress(2));
        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap
                .put(ArgsRequestKey.NEW_THESIS_TEXT, "This thesis is true.");
        myRequestMap.put(ArgsRequestKey.NEW_THESIS_OPINION, "" + anOpinion);

        Command myRequest = RequestParser.getCommand(myAppUser, myRequestMap);

        return new ArgsStatefulCommand(myRequest, new ArgsState(ThesisId.ONE,
                RelationId.BONE, myAppUser.getDefaultPerspective()));
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulCommand getInsertOpinionCommand(
            ThesisId aThesisId, ThesisOpinion anOpinion, ArgumentsUser anArgUser)
    {
        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap.put(ArgsRequestKey.NEW_THESIS_OPINION2,
                "" + anOpinion.getPercentage());

        Command myRequest = RequestParser.getCommand(anArgUser, myRequestMap);

        return new ArgsStatefulCommand(myRequest, new ArgsState(aThesisId,
                RelationId.BONE, anArgUser.getDefaultPerspective()));
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulCommand getIllegalModificationCommand(
            ThesisId myThesisId, ArgumentsUser myOwner)
    {
        OpinionatedThesis myThesis = TheArgsStore.i().getThesis(myThesisId,
                new ThesisOwnerPerspective());

        ArgumentsUser myModifier = TheArgsStore.i().selectUserById(
                ArgumentsUserId.TEST7);
        assert myOwner.equals(myThesis.getOwner());
        assert !myOwner.equals(myModifier);

        return getThesisModificationCommand(myThesisId, myModifier);
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulCommand getThesisModificationCommand(
            ThesisId myThesisId, ArgumentsUser myModifier)
    {
        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap
                .put(ArgsRequestKey.THESIS_TEXT, "This thesis is modified.");
        myRequestMap.put(ArgsRequestKey.THESIS_OPINION, ""
                + ThesisOpinion.TRUE_I);

        Command myRequest = RequestParser.getCommand(myModifier, myRequestMap);

        return new ArgsStatefulCommand(myRequest, new ArgsState(myThesisId,
                RelationId.BONE, myModifier.getDefaultPerspective()));
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulCommand getLinkModificationCommand(
            ThesisId myThesis2Id, RelationId myRelationId,
            int myIfTrueRelevance, int myIfFalseRelevance)
    {
        Relation myRelation = TheArgsStore.i().selectRelationById(myRelationId);
        ArgumentsUser myAppUser = ArgumentsUser_Tester.getTestUser2();

        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap.put(ArgsRequestKey.RELATION_ID, "" + myRelationId);
        myRequestMap.put(ArgsRequestKey.NEW_IF_TRUE_PERCENTAGE_RELEVANCE, ""
                + myIfTrueRelevance);
        myRequestMap.put(ArgsRequestKey.NEW_IF_FALSE_PERCENTAGE_RELEVANCE, ""
                + myIfFalseRelevance);
        myRequestMap.put(ArgsRequestKey.NEW_LINK_SOURCE_ID,
                "" + myRelation.getThesis1ID());
        myRequestMap.put(ArgsRequestKey.NEW_LINK_TARGET_ID,
                "" + ThesisId.ONE.getLongID());

        for (String myString : myRequestMap.values())
            assert !myString.startsWith("com");

        Command myRequest1 = RequestParser.getCommand(myAppUser, myRequestMap);

        return new ArgsStatefulCommand(myRequest1, new ArgsState(myThesis2Id,
                RelationId.BONE, PerspectiveId.getThesisOwner()));
    }

    // ------------------------------------------------------------------------
    public static ThesisId insertThesis()
    {
        return insertThesis(0);
    }

    // ------------------------------------------------------------------------
    private static RelationId insertPremise(ThesisId aThesis2Id)
    {
        ArgumentsUser myAppUser = ArgumentsUser_Tester.getTestUser2();

        assertEquals(myAppUser.getEmailAddress(),
                ArgumentsUser_Tester.getMailAddress(2));
        ProtocolMap myRequestMap = new ProtocolMap();
        ThesisText myPremiseText = new ThesisText("This premise is relevant.");
        Relevance myIfTruePremiseRelevance = Relevance.STRENGTHENS_;
        Relevance myIfFalsePremiseRelevance = Relevance.WEAKENS_;
        Integer myPremiseProbability = 75;
        myRequestMap.put(ArgsRequestKey.PREMISE_TEXT, myPremiseText + "");

        myRequestMap.put(ArgsRequestKey.PREMISE_IF_TRUE_WEIGHT, ""
                + myIfTruePremiseRelevance.getPercentage());
        myRequestMap.put(ArgsRequestKey.PREMISE_IF_FALSE_WEIGHT, ""
                + myIfFalsePremiseRelevance.getPercentage());
        myRequestMap.put(ArgsRequestKey.PREMISE_OPINION, ""
                + myPremiseProbability);

        Command myRequest1 = RequestParser.getCommand(myAppUser, myRequestMap);

        ArgsStatefulCommand myRequest = new ArgsStatefulCommand(myRequest1,
                new ArgsState(aThesis2Id, RelationId.BONE,
                        myAppUser.getDefaultPerspective()));

        long myNrOfThesesBefore = TheArgsStore.i().getNrOfTheses();
        long myNrOfOpinionsBefore = TheArgsStore.i().getNrOfOpinions();
        myRequest.execute();
        long myNrOfThesesAfter = TheArgsStore.i().getNrOfTheses();
        assert myNrOfThesesAfter == myNrOfThesesBefore + 1;
        long myNrOfOpinionsAfter = TheArgsStore.i().getNrOfOpinions();
        assert myNrOfOpinionsAfter == myNrOfOpinionsBefore + 1;
        RelationId myRelationId = TheArgsStore.i().selectLastTestRelationId();

        Relation myRelation = TheArgsStore.i().selectRelationById(myRelationId);

        assertEquals(myIfTruePremiseRelevance, myRelation.getIfTrueRelevance());
        assertEquals(myIfFalsePremiseRelevance,
                myRelation.getIfFalseRelevance());

        Thesis myPremise = myRelation.getThesis1();
        ThesisOpinion myPremiseOpinion = new ThesisOwnerPerspective()
                .getOpinion(myPremise);
        assertEquals(myPremiseProbability, myPremiseOpinion.getPercentage());

        assertEquals(myPremiseText, myPremise.getSummary());

        return myRelationId;
    }
}
