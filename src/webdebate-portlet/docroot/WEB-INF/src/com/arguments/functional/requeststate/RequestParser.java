/**
 * 
 */
package com.arguments.functional.requeststate;

import static org.junit.Assert.*;

import com.arguments.functional.command.ChangeThesis;
import com.arguments.functional.command.InsertOpinion;
import com.arguments.functional.command.InsertPremise;
import com.arguments.functional.command.Command;
import com.arguments.functional.command.InsertThesis;
import com.arguments.functional.command.UpdateLink;
import com.arguments.functional.command.ChangePerspective;
import com.arguments.functional.command.UpdateThesis;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.Relevance;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.datamodel.ThesisText;

/**
 * @author mirleau
 *
 */
public class RequestParser
{
    private final ArgumentsUser theAppUser;
    private final ProtocolMap theRequestMap;

    // ------------------------------------------------------------------------
    public static Command getCommand(
            ArgumentsUser anAppUser,
            ProtocolMap aProtocolMap)
    {
        RequestParser myParser =
                new RequestParser(anAppUser, aProtocolMap);
        
        Command theCommand = myParser.parseCommand();
        return theCommand;
    }

    // ------------------------------------------------------------------------
    public static ArgsStatefulCommand2 getStatefulCommand(
            ArgumentsUser anAppUser,
            ProtocolMap aProtocolMap)
    {
        final Command myCommand1 =
                RequestParser.getCommand(anAppUser, aProtocolMap);

        final StateChange myStateChange = new StateChange(aProtocolMap);

        ArgsStatefulCommand2 myCommand2 = new ArgsStatefulCommand2(
                myCommand1, anAppUser, myStateChange);

        return myCommand2;
    }

    // ------------------------------------------------------------------------
    private RequestParser(
            ArgumentsUser anAppUser,
            ProtocolMap aRequestMap)
    {
        theAppUser = anAppUser;
        theRequestMap = aRequestMap;
    }
    
    // ------------------------------------------------------------------------
    private Command parseCommand()
    {
        if (isPresent(ArgsRequestKey.THESIS_ID))
            return getChangeThesis();

        if (isPresent(ArgsRequestKey.THESIS_TEXT))
            return getUpdateThesis();

        if (isPresent(ArgsRequestKey.NEW_THESIS_TEXT))
            return getInsertThesis();

        if (isPresent(ArgsRequestKey.NEW_IF_TRUE_PERCENTAGE_RELEVANCE))
            return getUpdateLink();
        
        if (isPresent(ArgsRequestKey.PREMISE_TEXT))
            return getInsertPremise();

        if (isPresent(ArgsRequestKey.NEW_THESIS_OPINION2))
            return getInsertOpinion();

        if (isPresent(ArgsRequestKey.PERSPECTIVE_ID))
            return getUpdatePerspective();

        throw new AssertionError("Unknown mapconfiguration: " + theRequestMap.keySet());
    }
    
    // ------------------------------------------------------------------------
    private ChangeThesis getChangeThesis()
    {
        String myThesisId = getParameter(ArgsRequestKey.THESIS_ID);

        assert myThesisId != null;

        return new ChangeThesis(ThesisId.parse(myThesisId));
    }
    
    // ------------------------------------------------------------------------
    private UpdateThesis getUpdateThesis()
    {
        ThesisText myNewThesisText = getThesisText(ArgsRequestKey.THESIS_TEXT);
        String myThesisOpinion = getParameter(ArgsRequestKey.THESIS_OPINION);

        assertNotNull(myNewThesisText);
        assertNotNull( myThesisOpinion);

        return new UpdateThesis(
                theAppUser, myNewThesisText,
                ThesisOpinion.parseOpinion(myThesisOpinion));
    }
    
    // ------------------------------------------------------------------------
    private InsertOpinion getInsertOpinion()
    {
        String myThesisOpinion = getParameter(ArgsRequestKey.NEW_THESIS_OPINION2);

        assertNotNull( myThesisOpinion );

        return new InsertOpinion(
                theAppUser,
                ThesisOpinion.parseOpinion(myThesisOpinion));
    }
    
    // ------------------------------------------------------------------------
    private ChangePerspective getUpdatePerspective()
    {
        String myPerspectiveId = getParameter(ArgsRequestKey.PERSPECTIVE_ID);

        assertNotNull( myPerspectiveId );

        return new ChangePerspective(
                PerspectiveId.parsePerspectiveId(myPerspectiveId));
    }
    
    // ------------------------------------------------------------------------
    private InsertThesis getInsertThesis()
    {
        String myNewThesisText = getParameter(ArgsRequestKey.NEW_THESIS_TEXT);
        String myThesisOpinion = getParameter(ArgsRequestKey.NEW_THESIS_OPINION);

        return new InsertThesis(
                new ThesisText(myNewThesisText),
                theAppUser, ThesisOpinion.parseOpinion(myThesisOpinion));
    }
    
    // ------------------------------------------------------------------------
    private UpdateLink getUpdateLink()
    {
        String myLinkIdText = getParameter(ArgsRequestKey.RELATION_ID);
        String myNewIfTruePercentageRelevanceText =
                getParameter(ArgsRequestKey.NEW_IF_TRUE_PERCENTAGE_RELEVANCE);
        String myNewIfFalsePercentageRelevanceText =
                getParameter(ArgsRequestKey.NEW_IF_FALSE_PERCENTAGE_RELEVANCE);
        String myNewLinkTargetId = getParameter(ArgsRequestKey.NEW_LINK_TARGET_ID);

        assertNotNull( myNewIfFalsePercentageRelevanceText );
        RelationId myLinkId = RelationId.getRelationId(myLinkIdText);
        Relevance myNewIfTrueRelevance =
                Relevance.parseRelevance(myNewIfTruePercentageRelevanceText);
        Relevance myNewIfFalseRelevance =
                Relevance.parseRelevance(myNewIfFalsePercentageRelevanceText);
        ThesisId myNewTargetId = ThesisId.parse(myNewLinkTargetId);

        return new UpdateLink(
                theAppUser, myLinkId, myNewIfTrueRelevance,
                myNewIfFalseRelevance, myNewTargetId);
    }

    // ------------------------------------------------------------------------
    private InsertPremise getInsertPremise()
    {
        ThesisText myPremiseText = new ThesisText(getParameter(ArgsRequestKey.PREMISE_TEXT));

        String myIfTruePremiseRelevanceString =
                getParameter(ArgsRequestKey.PREMISE_IF_TRUE_WEIGHT);
        assertNotNull( myIfTruePremiseRelevanceString );
        String myIfFalsePremiseRelevanceString =
                getParameter(ArgsRequestKey.PREMISE_IF_FALSE_WEIGHT);
        assertNotNull( myIfFalsePremiseRelevanceString );
        String myPremiseOpinionString = getParameter(ArgsRequestKey.PREMISE_OPINION);

        assertNotNull( myIfTruePremiseRelevanceString );

        Relevance myIfTruePremiseRelevance = Relevance.parseRelevance(myIfTruePremiseRelevanceString);
        Relevance myIfFalsePremiseRelevance = Relevance.parseRelevance(myIfFalsePremiseRelevanceString);
        ThesisOpinion myPremiseOpinion = ThesisOpinion.parseOpinion(myPremiseOpinionString);

        return new InsertPremise(myPremiseText, myIfTruePremiseRelevance, myIfFalsePremiseRelevance, theAppUser,
                myPremiseOpinion);
    }

    // ------------------------------------------------------------------------
    private boolean isPresent(ArgsRequestKey aKey)
    {
        return theRequestMap.keySet().contains(aKey);
    }
    
    // ------------------------------------------------------------------------
    private ThesisText getThesisText(ArgsRequestKey aKey)
    {
        return new ThesisText(getParameter(aKey));
    }
    
    // ------------------------------------------------------------------------
    private String getParameter(ArgsRequestKey aKey)
    {
        String myUnsafeText = theRequestMap.get(aKey);
        if(myUnsafeText == null) return null;
        String myReturnValue = getSafeText(myUnsafeText);
        return myReturnValue;
    }

    // ------------------------------------------------------------------------
    private static String getSafeText(String aText)
    {
        String myText = aText.replaceAll(">", ")");
        return myText.replaceAll("<", "(");        
    }
}
