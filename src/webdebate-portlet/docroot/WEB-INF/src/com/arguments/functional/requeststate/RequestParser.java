/**
 * 
 */
package com.arguments.functional.requeststate;

import static org.junit.Assert.*;

import com.arguments.application.TheContainerBridge;
import com.arguments.functional.command.ChangeThesis;
import com.arguments.functional.command.InsertOpinion;
import com.arguments.functional.command.InsertPerspective;
import com.arguments.functional.command.InsertPremise;
import com.arguments.functional.command.Command;
import com.arguments.functional.command.InsertThesis;
import com.arguments.functional.command.StateChangeCommand;
import com.arguments.functional.command.UpdateLink;
import com.arguments.functional.command.ChangePerspective;
import com.arguments.functional.command.UpdateThesis;
import com.arguments.functional.datamodel.ArgsReadOnlyState;
import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.PerspectiveName;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.Relevance;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.datamodel.ThesisText;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.support.CgiParameterMap;

/**
 * @author mirleau
 *
 */
public class RequestParser
{
    private final ArgumentsUser theUser;
    private final ProtocolMap theProtocolMap;

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
                getCommand(anAppUser, aProtocolMap);

        ArgsStatefulCommand2 myCommand2 = new ArgsStatefulCommand2(
                myCommand1, anAppUser);

        return myCommand2;
    }
    
    // ------------------------------------------------------------------------
    public static Command parseCommand(ArgsJspRequest aJspRequest)
    {
        // ArgsRequest contains data that is simplest (least error prone)
        //  to extract from a javax.RenderRequest.
        // ArgsJspRenderRequest extends this with information encoded in
        //   the target jsp page (which itself is addressed via the
        //   source jsp page).

        final ArgsRequest myRequest = aJspRequest.getRequest();
        // PortletParameterMap, ServletParameterMap, ArgumentsUser
        
        final ArgumentsUser myAppUser = myRequest.getAppUser();
        
        // Depending on the jsp target, we either get the servlet
        // or the portlet parameter map:
        final ProtocolMap myProtocolMap = getProtocolMap(
                myRequest, aJspRequest.getStateInputMode());

        // From that protocolmap, we extract the actual information
        // needed, being a possible state change, and
        // non-state changing directives like the linkid in case
        // a link needs to be edited:
        
        Command myCommand =
                new StateChangeCommand(
                        myProtocolMap,
                        aJspRequest.getUpdateState(),
                        myAppUser);

        return myCommand;
    }

    // ------------------------------------------------------------------------
    public static ArgsStatefulRenderRequest fetchStatefulRenderRequest(
            ArgsJspRequest aJspRequest)
    {
        // ArgsRequest contains data that is simplest (least error prone)
        //  to extract from a javax.RenderRequest.
        // ArgsJspRenderRequest extends this with information encoded in
        //   the target jsp page (which itself is addressed via the
        //   source jsp page).

        final ArgsRequest myRequest = aJspRequest.getRequest();
        // PortletParameterMap, ServletParameterMap, ArgumentsUser
        
        final ArgumentsUser myAppUser = myRequest.getAppUser();
        
        // Depending on the jsp target, we either get the servlet
        // or the portlet parameter map:
        final ProtocolMap myProtocolMap = getProtocolMap(
                myRequest, aJspRequest.getStateInputMode());

        ArgsRenderRequest myRenderDirective =
                new ArgsRenderRequest(myAppUser,
                aJspRequest.getUrlContainer(), myProtocolMap);
        // User, Urls, ChangeRelationId

        ArgsReadOnlyState myStateAfter = 
                TheArgsStore.i().selectState(myAppUser);
        return new ArgsStatefulRenderRequest(
                myRenderDirective, myStateAfter);
    }
    
    // ------------------------------------------------------------------------
    private static ProtocolMap getProtocolMap(ArgsRequest aRequest,
            final CgiSource aCgiSource)
    {
        final CgiParameterMap myParameterMap = aRequest
                .getCgiParameterMap(aCgiSource);

        assertNotNull(myParameterMap);
        final ProtocolMap myProtocolMap =
                TheContainerBridge.i().getProtocolMap(myParameterMap);
        return myProtocolMap;
    }
    
    // ------------------------------------------------------------------------
    private RequestParser(
            ArgumentsUser anAppUser,
            ProtocolMap aRequestMap)
    {
        theUser = anAppUser;
        theProtocolMap = aRequestMap;
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

        if (isPresent(ArgsRequestKey.NEW_PERSPECTIVE_NAME))
            return getInsertPerspective();

        if (isPresent(ArgsRequestKey.NEW_IF_TRUE_PERCENTAGE_RELEVANCE))
            return getUpdateLink();
        
        if (isPresent(ArgsRequestKey.PREMISE_TEXT))
            return getInsertPremise();

        if (isPresent(ArgsRequestKey.NEW_THESIS_OPINION2))
            return getInsertOpinion();

        assertTrue("Unknown mapconfiguration: " + theProtocolMap.keySet(),
                isPresent(ArgsRequestKey.SET_PERSPECTIVE_ID));
        return getUpdatePerspective();
    }
    
    // ------------------------------------------------------------------------
    private ChangeThesis getChangeThesis()
    {
        String myThesisId = getParameter(ArgsRequestKey.THESIS_ID);

        assertNotNull( myThesisId );

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
                theUser, myNewThesisText,
                ThesisOpinion.parseOpinion(myThesisOpinion));
    }
    
    // ------------------------------------------------------------------------
    private InsertOpinion getInsertOpinion()
    {
        String myThesisOpinion = getParameter(ArgsRequestKey.NEW_THESIS_OPINION2);

        assertNotNull( myThesisOpinion );

        return new InsertOpinion(
                theUser,
                ThesisOpinion.parseOpinion(myThesisOpinion));
    }
    
    // ------------------------------------------------------------------------
    private ChangePerspective getUpdatePerspective()
    {
        String myPerspectiveId = getParameter(ArgsRequestKey.SET_PERSPECTIVE_ID);

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
                theUser, ThesisOpinion.parseOpinion(myThesisOpinion));
    }
    
    // ------------------------------------------------------------------------
    private InsertPerspective getInsertPerspective()
    {
        String myNewPerspectiveName = getParameter(ArgsRequestKey.NEW_PERSPECTIVE_NAME);

        return new InsertPerspective(
                new PerspectiveName(myNewPerspectiveName), theUser);
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
        RelationId myLinkId = RelationId.parseRelationId(myLinkIdText);
        Relevance myNewIfTrueRelevance =
                Relevance.parseRelevance(myNewIfTruePercentageRelevanceText);
        Relevance myNewIfFalseRelevance =
                Relevance.parseRelevance(myNewIfFalsePercentageRelevanceText);
        ThesisId myNewTargetId = ThesisId.parse(myNewLinkTargetId);

        return new UpdateLink(
                theUser, myLinkId, myNewIfTrueRelevance,
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

        return new InsertPremise(myPremiseText, myIfTruePremiseRelevance, myIfFalsePremiseRelevance, theUser,
                myPremiseOpinion);
    }

    // ------------------------------------------------------------------------
    private boolean isPresent(ArgsRequestKey aKey)
    {
        return theProtocolMap.keySet().contains(aKey);
    }
    
    // ------------------------------------------------------------------------
    private ThesisText getThesisText(ArgsRequestKey aKey)
    {
        return new ThesisText(getParameter(aKey));
    }
    
    // ------------------------------------------------------------------------
    private String getParameter(ArgsRequestKey aKey)
    {
        String myUnsafeText = theProtocolMap.get(aKey);
        assertNotNull("Can't find value for key: " + aKey + " in protocol map with keys: " +
        theProtocolMap.keySet(), myUnsafeText);
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
