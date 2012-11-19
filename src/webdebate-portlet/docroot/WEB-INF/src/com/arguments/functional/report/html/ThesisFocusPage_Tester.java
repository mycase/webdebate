package com.arguments.functional.report.html;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.ArgumentsUserId;
import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.RelatedThesis;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.report.ThesisFocusData;
import com.arguments.functional.requeststate.ArgsRenderRequest;
import com.arguments.functional.requeststate.ArgsStatefulRenderRequest;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.store.TheArgsStore;

public class ThesisFocusPage_Tester
{
    @Before
    public void setUp()
    {
        TheArgsStore.i().deleteTestObjects();
    }

    // ------------------------------------------------------------------------
    /**
     * Tests the sorting of premises
     */
    @Test
    public void premiseOrdering()
    {
        ArgumentsUser myAppUser = TheArgsStore.i()
                .selectUserById(ArgumentsUserId.TEST2);

        ArgsRenderRequest myRequest1 = new ArgsRenderRequest(
                myAppUser, new ProtocolMap());

        ArgsStatefulRenderRequest myRequest = new ArgsStatefulRenderRequest(myRequest1,
                new ArgsState(ThesisId.Da156, RelationId.BONE, PerspectiveId.getThesisOwner()));
        ThesisFocusData myData = ThesisFocusPage.getData(myRequest);

        List<RelatedThesis<OpinionatedThesis>> myPremises = myData
                .getPremisesSortedByStrength();
        for (RelatedThesis<OpinionatedThesis> myPremise : myPremises)
        {
            // TODO: Assert on implied belief ordering
            // System.out.println("Summary: " + myPremise.getSummary());
        }
    }
}
