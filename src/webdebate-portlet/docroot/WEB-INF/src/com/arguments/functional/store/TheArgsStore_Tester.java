package com.arguments.functional.store;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.ArgumentsUserId;
import com.arguments.functional.datamodel.ArgumentsUser_Tester;
import com.arguments.functional.datamodel.ForeignUserId;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.datamodel.ThesisText;
import com.arguments.support.EmailAddress;
import com.arguments.support.ScreenName;

public class TheArgsStore_Tester
{
    // ------------------------------------------------------------------------
    @Before
    public void setUp()
    {
        TheArgsStore.i().deleteTestObjects();
    }

    // ------------------------------------------------------------------------
    @Test
    public void defaultLiferayIdIsTestUser1()
    {
        ForeignUserId myForeignId = new ForeignUserId("10004");
        ArgumentsUser myUser =
                TheArgsStore.i().selectUserByForeignId(myForeignId);
        assertNotNull( myUser );
        assertEquals("For id: " + myForeignId, ArgumentsUser_Tester.getMailAddress(4),
                myUser.getEmailAddress());
    }

    // ------------------------------------------------------------------------
    @Test
    public void insertThesisIncreasesThesesAndOpinions()
    {
        long myNrOfThesesBefore = TheArgsStore.i().getNrOfTheses();
        long myNrOfOpinionsBefore = TheArgsStore.i().getNrOfOpinions();

        insertThesis();

        long myNrOfThesesAfter = TheArgsStore.i().getNrOfTheses();
        long myNrOfOpinionsAfter = TheArgsStore.i().getNrOfOpinions();

        assert myNrOfThesesAfter == myNrOfThesesBefore + 1;
        assert myNrOfOpinionsAfter == myNrOfOpinionsBefore + 1;

    }

    // ------------------------------------------------------------------------
    @Test
    public void canHaveNonForeignUser()
    {
        Assert.assertNotNull(
                TheArgsStore.i().getAppUser(
                        ArgumentsUser_Tester.getMailAddress(3),
                        new ForeignUserId("-1"),
                        ArgumentsUser_Tester.getScreenName(3)));
    }

    // ------------------------------------------------------------------------
    @Test
    public void modifyForeignUserId()
    {
        ForeignUserId myOrgUserId = ArgumentsUser_Tester.getForeignUserId(2);
        ForeignUserId myNewUserId = ArgumentsUser_Tester.getForeignUserId(100);
        
        EmailAddress myEmail = ArgumentsUser_Tester.getMailAddress(2);
        ScreenName myScreenName = ArgumentsUser_Tester.getScreenName(2);
        
        ArgumentsUser myUser1 = TheArgsStore.i().selectUserByForeignId(myOrgUserId);
        assertNotNull(myUser1);
        assertEquals(myEmail, myUser1.getEmailAddress());
        assertEquals(myScreenName, myUser1.getScreenName());
        
        ArgumentsUser myUser2 =
                TheArgsStore.i().getAppUser(myEmail, myOrgUserId, myScreenName);
        assertEquals(myUser1, myUser2);
        
        ArgumentsUser myUser3 =
                TheArgsStore.i().getAppUser(myEmail, myNewUserId, myScreenName);
        assertEquals(myUser3.getContainerId(), myNewUserId);

        TheArgsStore.i().getAppUser(myEmail, myOrgUserId, myScreenName);    
    }
    
    // ------------------------------------------------------------------------
    @Test
    public void triangularPerspectiveQuery()
    {
        // User 1 owns a thesis
        // User 2 has an opinion about that thesis
        // User 3 queries User 2's opinion:
        //     User 2 is neither owner nor querier.
    }

    // ------------------------------------------------------------------------
    @Test
    public void getNewUser()
    {
        TheArgsStore.i().getAppUser(
                new EmailAddress("new@new.com"),
                ForeignUserId.BOGUS,
                new ScreenName("newbee"));
    }

    // ------------------------------------------------------------------------
    public static ArgumentsUser getTestUser2()
    {
        ArgumentsUser myReturnValue =
                TheArgsStore.i().selectUserById(ArgumentsUserId.TEST2);
        assertEquals( myReturnValue.getEmailAddress(), ArgumentsUser_Tester.getMailAddress(2));
        return myReturnValue;
    }

    // ------------------------------------------------------------------------
    public static ArgumentsUser getTestUser7()
    {
        ArgumentsUser myReturnValue =
                TheArgsStore.i().selectUserById(ArgumentsUserId.TEST7);
        assertEquals( myReturnValue.getEmailAddress(),
                ArgumentsUser_Tester.getMailAddress(7));
        return myReturnValue;
    }

    // ------------------------------------------------------------------------
    private static ThesisId insertThesis_new(Integer anOpinion)
    {
        ArgumentsUser myAppUser = getTestUser2();

        ThesisText myThesisText = new ThesisText("This thesis is true.");
        ThesisOpinion myThesisOpinion = ThesisOpinion.parseOpinion(""+anOpinion);
        return TheArgsStore.i(myAppUser).addThesis(
                myThesisText, myThesisOpinion);
    }

    // ------------------------------------------------------------------------
    private static ThesisId insertThesis()
    {
        return insertThesis_new(0);
    }
}
