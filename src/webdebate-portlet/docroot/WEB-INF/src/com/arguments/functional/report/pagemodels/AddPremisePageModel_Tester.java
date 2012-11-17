package com.arguments.functional.report.pagemodels;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.arguments.functional.datamodel.ArgumentsUserId;
import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.OwnedPerspective;
import com.arguments.functional.datamodel.PerspectiveName;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.datamodel.ThesisText;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.requeststate.asstrings.ArgsRequestKeyAsString;

/**
 * @author mirleau
 *
 */
public class AddPremisePageModel_Tester
{
    @Test
    public void relevanceIfTrueIsStrengthens()
    {
        OpinionatedThesis myThesis =
                new OpinionatedThesis(ThesisId.ONE,
                new ThesisText("da summary"),
                ThesisOpinion.BELIEVE_FALSE,
                new OwnedPerspective(new PerspectiveName("worldbank"),
                        ArgumentsUserId.TEST2),
                ArgumentsUserId.TEST2);
        ProtocolMap myProtocolMap = ArgsRequestKeyAsString.getProtocolMap();
        AddPremisePageModel myPage = new AddPremisePageModel(myThesis, myProtocolMap);
        assertEquals("100", myPage.theIfTrueRelevanceFormValue);
    }
}
