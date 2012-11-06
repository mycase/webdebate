package com.arguments.functional.requeststate;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.arguments.application.liferay.LiferayArgsRequestKey;
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
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateState;
import com.arguments.functional.store.TheArgsStore_Tester;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.support.EmailAddress;
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
        ArgsState myState = TheArgsStore.i().selectState(
                ArgumentsUser_Tester.getTestUser2());
        assertEquals( myThesisId, myState.getThesisId());
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

        OpinionatedThesis myThesis = TheArgsStore.i().getThesis(
                myThesisId, new ThesisOwnerPerspective());
        assertEquals(ThesisOpinion.BELIEVE_FALSE, myThesis.getOpinion());
        
        ArgsStatefulRequest myRequest =
                getThesisModificationCommand(myThesisId, myAppUser);

        myRequest.execute();

        myThesis = TheArgsStore.i().getThesis(
                myThesisId, new ThesisOwnerPerspective());
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
        ArgsStatefulRequest myRequest = getIllegalModificationCommand(
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
     * Tests the insertion and modification of a premise
     */
    @Test
    public void testUpdateLink()
    {
        ThesisId myThesis2Id = insertThesis();
        RelationId myRelationId = insertPremise(myThesis2Id);
        int myIfTrueRelevance = 99;
        int myIfFalseRelevance = 88;

        ArgsStatefulRequest myRequest = getLinkModificationCommand(
                myThesis2Id, myRelationId,
                myIfTrueRelevance, myIfFalseRelevance);

        myRequest.execute();

        Relation myTestRelation = TheArgsStore.i()
                .selectRelationById(myRelationId);
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
    public void testUpdatePerspective()
            throws NumberFormatException
    {
        ThesisId myThesisId = insertOpinion();

        ArgumentsUser myUser1 = ArgumentsUser_Tester.getTestUser2();
        ArgumentsUser myUser2 = ArgumentsUser_Tester.getTestUser7();

        PerspectiveId myPerspectiveId0 = PerspectiveId.getThesisOwner();
        PerspectiveId myPerspectiveId1 = myUser1.getDefaultPerspective();
        PerspectiveId myPerspectiveId2 = myUser2.getDefaultPerspective();
        
        ArgsState myState1 = TheArgsStore.i().selectState(myUser1);
        assertEquals(myPerspectiveId1, myState1.getPerspectiveId());

        ArgsStatefulRequest myRequest =
                getUpdatePerspectiveRequest(myPerspectiveId0);
        
        ArgsState myState = myRequest.execute();
        StateChange aStateString = myState.getStateString();
        assertNotSame(null, aStateString);
        ArgsState myState1_1 = TheArgsStore.i().selectState(myUser1);
            
        myState1_1.mergeStateChange(aStateString);
        TheArgsStore.i(myUser1).updateState(myState1_1);
        
        ArgsState myState2 = TheArgsStore.i().selectState(ArgumentsUser_Tester.getTestUser7());
        PerspectiveId myOutPerspectiveId2 = myState2.getPerspectiveId();
        assert myOutPerspectiveId2.equals(myPerspectiveId2);
        
        Perspective myPerspective1 = myState1.getPerspective();
        Perspective myPerspective2 = myState2.getPerspective();
        
        assert ! myPerspective1.equals(myPerspective2);
        
        OpinionatedThesis myThesis1 =
                TheArgsStore.i().getThesis(
                        myThesisId, myPerspective1);

        OpinionatedThesis myThesis2 =
                TheArgsStore.i().getThesis(
                        myThesisId, myPerspective2);
        
        assert ! myThesis1.getOpinion().equals(myThesis2.getOpinion())
            : "Equal: " + myThesis1.getOpinion() + ", " + myThesis2.getOpinion();
        assertNotSame( myThesis1.getOpinion() , myThesis2.getOpinion());
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

        //User1 owns a thesis
        ThesisId myThesisId = insertThesis();
        
        //User2 focuses on User1's thesis

        PortletParameterMap myPParameterMap0 = new PortletParameterMap();
        ServletParameterMap mySParameterMap0 = new ServletParameterMap();
        myPParameterMap0.put(LiferayArgsRequestKey.s(ArgsRequestKey.THESIS_ID),
                new String[]{"" + myThesisId});
        
        ArgsRequest myRequest0 =
                new ArgsRequest(myPParameterMap0, mySParameterMap0, myUser2);

        UrlContainer myContainer = new UrlContainer();
        CgiSource mySource = CgiSource.SERVLET;
        UpdateState myUpdateState = UpdateState.YES;
        
        ArgsRequest2 myRequest = new ArgsRequest2(myRequest0, myContainer, mySource, myUpdateState);
        PageModelFactory.getThesisFocusPage(myRequest);
        
        //User2 takes User1's perspective
        PerspectiveId myPerspectiveId1 = myUser1.getDefaultPerspective();

        PortletParameterMap myPParameterMap1 = new PortletParameterMap();
        myPParameterMap1.put(LiferayArgsRequestKey.s(ArgsRequestKey.PERSPECTIVE_ID),
                new String[]{myPerspectiveId1.getIdString()});
        ServletParameterMap mySParameterMap1 = new ServletParameterMap();
        
        ArgsRequest myRequest1 =
                new ArgsRequest(myPParameterMap1, mySParameterMap1, myUser2);
        myRequest1.execute();
        
        //User2 tries to write his own opinion

        PortletParameterMap myPParameterMap2 = new PortletParameterMap();
        myPParameterMap2.put(LiferayArgsRequestKey.s(ArgsRequestKey.NEW_THESIS_OPINION2),
                new String[]{"0"});
        ServletParameterMap mySParameterMap2 = new ServletParameterMap();
        
        ArgsRequest myRequest2 =
                new ArgsRequest(myPParameterMap2, mySParameterMap2, myUser2);

        try
        {
            myRequest2.execute();
        }
        catch (PerspectiveNotOwnedException anException)
        {
            return;
        }

        throw new AssertionError("Expected exception not thrown");
    }
    
    // ------------------------------------------------------------------------
    /**
     * insert a thesis
     */
    public static ThesisId insertThesis(Integer anOpinion)
    {
        ArgsStatefulRequest myRequest = getInsertThesisCommand(
                anOpinion);

        long myNrOfThesesBefore = TheArgsStore.i().getNrOfTheses();
        long myNrOfOpinionsBefore = TheArgsStore.i().getNrOfOpinions();
        ArgsState myState = myRequest.execute();
        assert myState.getPerspectiveId().isWritable();
        TheArgsStore.i(ArgumentsUser_Tester.getTestUser2()).updateState(myState);
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
         return insertOpinion(ArgumentsUser_Tester.getTestUser2(), ArgumentsUser_Tester.getTestUser7());
    }
    
    // ------------------------------------------------------------------------
    private static ThesisId insertOpinion(ArgumentsUser aUser1, ArgumentsUser aUser2)
    {
            
        ThesisId myThesisId = insertThesis();

        ArgsStatefulRequest myRequest =
                getInsertOpinionCommand(
                        myThesisId,
                        ThesisOpinion.BELIEVE_TRUE,
                        aUser1);

        long myCountBefore = TheArgsStore.i().getNrOfOpinions();        
        
        myRequest.execute();
        long myCountAfter = TheArgsStore.i().getNrOfOpinions();
        assertTrue( myCountAfter == myCountBefore );
        
        myRequest =
                getInsertOpinionCommand(
                        myThesisId,
                        ThesisOpinion.NEUTRAL_OPINION,
                        aUser2);

        myCountBefore = TheArgsStore.i().getNrOfOpinions();        
        myRequest.execute();
        myCountAfter = TheArgsStore.i().getNrOfOpinions();
        assert myCountAfter == myCountBefore + 1;
        
        List<PerspectiveThesisOpinion> myOpinions =
                TheArgsStore.i().getOpinionsForThesisId(myThesisId);
        for(ThesisOpinion myOpinion: myOpinions)
        {
            String myMessage = "Found opinion: ";
            myMessage = myMessage + myOpinion;
        }
        
        myRequest =
                getInsertOpinionCommand(
                        myThesisId,
                        ThesisOpinion.BELIEVE_FALSE,
                        aUser2);

        myCountBefore = TheArgsStore.i().getNrOfOpinions();        
        myRequest.execute();
        myCountAfter = TheArgsStore.i().getNrOfOpinions();
        assert myCountAfter == myCountBefore;
        
        return myThesisId;
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulRequest getUpdatePerspectiveRequest(
            PerspectiveId aPerspectiveId)
    {
        ArgumentsUser myAppUser = ArgumentsUser_Tester.getTestUser2();
        
        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap.put(ArgsRequestKey.PERSPECTIVE_ID,
                "" + aPerspectiveId.getIdString() );

        ArgumentsRequest myRequest1 = new ArgumentsRequest(
                myAppUser, myRequestMap);

        return new ArgsStatefulRequest(myRequest1,
                new ArgsState(ThesisId.ONE, RelationId.BONE, PerspectiveId.getThesisOwner()));
    }
    
    // ------------------------------------------------------------------------
    private static ArgsStatefulRequest getInsertThesisCommand(Integer anOpinion)
    {
        ArgumentsUser myAppUser = ArgumentsUser_Tester.getTestUser2();

        assertEquals( myAppUser.getEmailAddress(),
                ArgumentsUser_Tester.getMailAddress(2));
        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap.put(ArgsRequestKey.NEW_THESIS_TEXT,
                "This thesis is true." );
        myRequestMap.put(ArgsRequestKey.NEW_THESIS_OPINION,
                 "" + anOpinion );

        ArgumentsRequest myRequest = new ArgumentsRequest(
                myAppUser, myRequestMap);

        return new ArgsStatefulRequest(myRequest,
                new ArgsState(ThesisId.ONE, RelationId.BONE,
                        myAppUser.getDefaultPerspective()));
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulRequest getInsertOpinionCommand(
            ThesisId aThesisId,
            ThesisOpinion anOpinion, 
            ArgumentsUser anArgUser)
    {
        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap.put(ArgsRequestKey.NEW_THESIS_OPINION2,
                 "" + anOpinion.getPercentage() );

        ArgumentsRequest myRequest = new ArgumentsRequest(
                anArgUser, myRequestMap);

        return new ArgsStatefulRequest(myRequest,
                new ArgsState(aThesisId, RelationId.BONE,
                        anArgUser.getDefaultPerspective()));
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulRequest getIllegalModificationCommand(
            ThesisId myThesisId, ArgumentsUser myOwner)
    {
        OpinionatedThesis myThesis = TheArgsStore.i().getThesis(
                myThesisId, new ThesisOwnerPerspective());

        ArgumentsUser myModifier = TheArgsStore.i().selectUserById(ArgumentsUserId.TEST7);
        assert myOwner.equals(myThesis.getOwner());
        assert !myOwner.equals(myModifier);
        
        return getThesisModificationCommand(
                myThesisId, myModifier);
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulRequest getThesisModificationCommand(
            ThesisId myThesisId, ArgumentsUser myModifier)
    {
        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap.put(ArgsRequestKey.THESIS_TEXT,
                "This thesis is modified." );
        myRequestMap.put(ArgsRequestKey.THESIS_OPINION,
                "" + ThesisOpinion.TRUE_I );

        ArgumentsRequest myRequest = new ArgumentsRequest(
                myModifier, myRequestMap);

        return new ArgsStatefulRequest(myRequest,
                new ArgsState(myThesisId, RelationId.BONE,
                        myModifier.getDefaultPerspective()));
    }

    // ------------------------------------------------------------------------
    private static ArgsStatefulRequest getLinkModificationCommand(
            ThesisId myThesis2Id, RelationId myRelationId,
            int myIfTrueRelevance, int myIfFalseRelevance)
    {
        Relation myRelation = TheArgsStore.i().selectRelationById(myRelationId);
        ArgumentsUser myAppUser = ArgumentsUser_Tester.getTestUser2();
        
        ProtocolMap myRequestMap = new ProtocolMap();
        myRequestMap.put(ArgsRequestKey.RELATION_ID,
                "" + myRelationId );
        myRequestMap.put(
                ArgsRequestKey.NEW_IF_TRUE_PERCENTAGE_RELEVANCE,
                "" + myIfTrueRelevance );
        myRequestMap.put(
                ArgsRequestKey.NEW_IF_FALSE_PERCENTAGE_RELEVANCE,
                "" + myIfFalseRelevance );
        myRequestMap.put(ArgsRequestKey.NEW_LINK_SOURCE_ID,
                 "" + myRelation.getThesis1ID() );
        myRequestMap.put(ArgsRequestKey.NEW_LINK_TARGET_ID,
                 "" + ThesisId.ONE.getLongID() );

        for (String myString:myRequestMap.values())
            assert !myString.startsWith("com");

        ArgumentsRequest myRequest1 = new ArgumentsRequest(
                myAppUser, myRequestMap);

        return new ArgsStatefulRequest(myRequest1,
                new ArgsState(myThesis2Id, RelationId.BONE, PerspectiveId.getThesisOwner()));
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

        assertEquals( myAppUser.getEmailAddress(),
                ArgumentsUser_Tester.getMailAddress(2));
        ProtocolMap myRequestMap = new ProtocolMap();
        ThesisText myPremiseText = new ThesisText("This premise is relevant.");
        Relevance myIfTruePremiseRelevance = Relevance.STRENGTHENS_;
        Relevance myIfFalsePremiseRelevance = Relevance.WEAKENS_;
        Integer myPremiseProbability = 75;
        myRequestMap.put(ArgsRequestKey.PREMISE_TEXT,
                myPremiseText + "");

        myRequestMap.put(ArgsRequestKey.PREMISE_IF_TRUE_WEIGHT,
                 "" + myIfTruePremiseRelevance.getPercentage() );
        myRequestMap
                .put(ArgsRequestKey.PREMISE_IF_FALSE_WEIGHT,
                        "" + myIfFalsePremiseRelevance.getPercentage());
        myRequestMap.put(ArgsRequestKey.PREMISE_OPINION,
                "" + myPremiseProbability );

        ArgumentsRequest myRequest1 = new ArgumentsRequest(
                myAppUser, myRequestMap);

        ArgsStatefulRequest myRequest = new ArgsStatefulRequest(myRequest1,
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
        ThesisOpinion myPremiseOpinion = new ThesisOwnerPerspective().getOpinion(
                myPremise);
        assertEquals(myPremiseProbability, myPremiseOpinion.getPercentage());

        assertEquals(myPremiseText, myPremise.getSummary());

        return myRelationId;
    }
}
