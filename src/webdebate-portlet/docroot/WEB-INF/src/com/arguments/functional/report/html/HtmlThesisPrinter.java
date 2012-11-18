package com.arguments.functional.report.html;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import com.arguments.functional.datamodel.MPerspective;
import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.OwnedPerspective;
import com.arguments.functional.datamodel.PerspectiveThesisOpinion;
import com.arguments.functional.datamodel.RelatedThesis;
import com.arguments.functional.datamodel.Thesis;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.report.ListThesesData;
import com.arguments.functional.report.ThesisFocusData;
import com.arguments.functional.requeststate.ArgsRequestKey;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.support.Container;
import com.arguments.support.IndentHtmlPrinter;
import com.arguments.support.Renderer;

public class HtmlThesisPrinter
{
    
    private final UrlContainer theUrlContainer;
    private final ProtocolMap theProtocol;
    
    // ------------------------------------------------------------------------
    /**
     * @param aUser
     */
    public HtmlThesisPrinter(UrlContainer aUrlContainer, ProtocolMap aProtocol)
    {
        assertNotNull(aUrlContainer);
        
        theUrlContainer = aUrlContainer;
        theProtocol = aProtocol;
    }

    // ------------------------------------------------------------------------
    public String focusPageToHtmlPage(ThesisFocusData aThesisFocusData)
    {
        StringBuffer myText = new StringBuffer();
        myText.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n"
                + "<html><head><title>Argument Viewer</title></head>");
        myText.append("<body>");
        myText.append(focusPageToInternalHtml(aThesisFocusData));
        myText.append("</body>");

        return myText.toString();
    }

    private MPerspective thePerspectives;
    // ------------------------------------------------------------------------
    public String focusPageToInternalHtml(ThesisFocusData aThesisFocusData)
    {
        StringBuffer myText = new StringBuffer();
        OpinionatedThesis myMainThesis = aThesisFocusData.getMainThesis();
        thePerspectives = aThesisFocusData.getPerspective();
        assertEquals (myMainThesis.getPerspectives().size(), thePerspectives.size());
        myText.append("<ul>");

        for (int i = 0; i < thePerspectives.size(); i++)
            myText.append("<li>Perspective "+(i+1)+": " + thePerspectives.get(i)+"</li>");
        myText.append("</ul>");
        myText.append("<h1>Focus thesis: </h1>\n");
        myText.append("<table id=\"mainfocustable\"><tr>");
        myText.append(toHtml(myMainThesis));
        myText.append("</tr></table>");
        if (aThesisFocusData.getMainThesisOwned())
        {
            myText.append("<a href=\"" + theUrlContainer.getEditThesisUrl() + "\">edit</a>");
        }
        else
        {
            myText.append("Owner: " + aThesisFocusData.getMainThesis().getOwner().getScreenName());
        }
        if (aThesisFocusData.getPerspectiveOwned())
        {
            myText.append(", <a href=\"" + theUrlContainer.getAddPremiseUrl() + "\">add premise</a>");
            myText.append(", <a href=\"" + theUrlContainer.getAddOpinionUrl() + "\">set your opinion</a>");
        }

        myText.append("\n");

        myText.append("<h1>Reasons for this to be true or not: </h1>\n");

        myText.append(getRelatedThesesTable(aThesisFocusData.getPremisesSortedByStrength(),
                "Premise", "Relevance of premise for focus"));

        myText.append("<h1>Possible consequences: </h1>\n");
        myText.append(getRelatedThesesTable(aThesisFocusData.getDeductions(),
                "Consequence", "Relevance of focus for consequence"));

        myText.append("<h1>Tree view: </h1>\n");
        myText.append(getThesisTreeString(aThesisFocusData.getMainThesis()));
        
        myText.append("<h1>Different perspectives: </h1>\n");
        myText.append(getPerspectivesTable(aThesisFocusData.getDifferentPerspectives()));
        
        return myText.toString();
    }

    // ------------------------------------------------------------------------
    String getThesisTreeString(Thesis aThesis)
    {
        Container<Thesis> myThesisTree =
                TheArgsStore.i().selectPremiseTree(aThesis, 2);
        
        Renderer<Thesis> myRenderer = new Renderer<Thesis>()
                {

                    @Override
                    public String render(Thesis aThesis1)
                    {
                        return toFocusAnchor(aThesis1);
                    }};
                    
        IndentHtmlPrinter<Thesis> myPrinter =
                new IndentHtmlPrinter<>(myRenderer);
        myPrinter.traverse(myThesisTree);
        return myPrinter.getOutput();
    }
    
    // ------------------------------------------------------------------------
    private String getPerspectivesTable(Collection<PerspectiveThesisOpinion> anOpinions)
    {
        StringBuffer myText = new StringBuffer();
        myText.append("<table>\n");
        myText.append("<tr>\n");
        myText.append("  <th class=\"otherClass\">Perspective </th>\n");
        myText.append("  <th class=\"otherClass\">Opinion</th>\n");
        myText.append("</tr>\n");
        
        for (PerspectiveThesisOpinion myOpinion : anOpinions)
        {
            myText.append(toTableRow(myOpinion));
        }
        myText.append("</table>\n");
        return myText.toString();
    }
    
    // ------------------------------------------------------------------------
    private String getRelatedThesesTable(
            List<RelatedThesis<OpinionatedThesis>> aTheses,
            String aThesisHeader,
            String aRelevanceHeader)
    {
        StringBuffer myText = new StringBuffer();
        myText.append("<table>\n");
        myText.append("<tr>\n");
        myText.append("  <th class=\"otherClass\">Operations     </th>\n");
        myText.append("  <th class=\"otherClass\">"+aThesisHeader+"</th>\n");
        myText.append("  <th class=\"otherClass\">"+aRelevanceHeader+"</th>\n");
        myText.append("  <th class=\"otherClass\"> Owner</th>\n");
        myText.append("</tr>\n");
        
        for (RelatedThesis<OpinionatedThesis> myPremise : aTheses)
        {
            // TODO assertEquals(myPremise.getThesis().getOpinions().size(), thePerspectives.size());
            myText.append(toTableRow(myPremise, theUrlContainer));
        }
        myText.append("</table>\n");
        return myText.toString();
    }
    
    // ------------------------------------------------------------------------
    private String toPerspectiveAnchor(OwnedPerspective aPerspective)
    {
        return "<a href=\"focus?"+
                theProtocol.get(ArgsRequestKey.PERSPECTIVE_ID)+"="
                + aPerspective.getIdString() + "\"> "+aPerspective+"</a>";
    }

    // ------------------------------------------------------------------------
    private String toFocusAnchor(ThesisId aThesisID)
    {
        return "<a href=\"focus?"+
                theProtocol.get(ArgsRequestKey.THESIS_ID)+"="
                + aThesisID + "\"> focus</a>";
    }

    // ------------------------------------------------------------------------
    private String toFocusAnchor(Thesis aThesis)
    {
        return "<a href=\"focus?"+
                theProtocol.get(ArgsRequestKey.THESIS_ID)+"=" +
                aThesis.getID() + "\">"+aThesis.getSummary()+"</a>";
    }

    // ------------------------------------------------------------------------
    private String toRelinkAnchor(String aRelinkURL, RelatedThesis<OpinionatedThesis> aPremise)
    {
        return "<a href=\"" + aRelinkURL + "&" +
                theProtocol.get(ArgsRequestKey.RELATION_ID) +"=" + aPremise.getRelation().getID() + "\"> relink</a>";
    }

    // ------------------------------------------------------------------------
    private static String cssClassByOpinion(OpinionatedThesis aThesis)
    {
        assertNotNull(aThesis);
        return cssClassByOpinion(aThesis.getOpinion());
    }

    // ------------------------------------------------------------------------
    private static String cssClassByOpinion(ThesisOpinion aOpinion)
    {
        if (aOpinion.believe())
            return "believeStyle";
        if (aOpinion.neutral())
            return "dontKnowStyle";
        return "dontBelieveStyle";
    }

    // ------------------------------------------------------------------------
    private static StringBuffer toHtml(OpinionatedThesis aThesis)
    {
        assertNotNull(aThesis);
        StringBuffer myText = new StringBuffer();

        //myText.append("<div id=\"mainfocus\" class=\"" + cssClassByOpinion(aThesis)
        //        + "\">" + getIDWithSummary(aThesis) + "</div>\n");
        for (ThesisOpinion myOpinion : aThesis.getOpinions())
            myText.append("<td><div id=\"mainfocuspers\" class=\"" + cssClassByOpinion(myOpinion)
                 + "\">" + "X" + "</div></td>\n");
        myText.append("<td><div id=\"mainfocus\" class=\"" + cssClassByOpinion(aThesis.getOpinion())
                + "\">" + getIDWithSummary(aThesis) + "</div></td>\n");
        
        return myText;
    }

    // ------------------------------------------------------------------------
    private static String getIDWithSummary(OpinionatedThesis aThesis)
    {
        return aThesis.getID() + " -- "
                + aThesis.getSummary() + " ("+aThesis.getOpinion().getPercentage()+"%)";
    }
    
    // ------------------------------------------------------------------------
    private StringBuffer toTableRow(RelatedThesis<OpinionatedThesis> aPremise, UrlContainer aRelinkURL)
    {
        assertNotNull(aRelinkURL);
        
        StringBuffer myText = new StringBuffer("<tr>\n");
        myText.append("  <td class=\"otherClass\"> "
                + toFocusAnchor(aPremise.getID()) + ", "
                + toRelinkAnchor(aRelinkURL.getEditLinkUrl(), aPremise) + "</td>\n"                
                );
        myText.append("  <td class=\"" + cssClassByOpinion(aPremise.getThesis()) + "\">"
                + getIDWithSummary(aPremise.getThesis())+"</td>\n");
        myText.append("  <td class=\"otherClass\">" + relevanceText(aPremise)
                + "</td>\n");
        myText.append("  <td>" + aPremise.getOwner().getScreenName() + "</td>\n");
        myText.append("</tr>\n");
        return myText;
    }

    // ------------------------------------------------------------------------
    private StringBuffer toTableRow(OpinionatedThesis aThesis)
    {
        StringBuffer myText = new StringBuffer("<tr>\n");
        myText.append("  <td class=\"otherClass\"> "
                + toFocusAnchor(aThesis.getID()) + "</td>\n");
        myText.append("  <td class=\"" + cssClassByOpinion(aThesis) + "\">"
                + getIDWithSummary(aThesis) + "</td>\n");
        myText.append("  <td>" + aThesis.getOwner().getScreenName() + "</td>\n");
        myText.append("</tr>\n");
        return myText;
    }

    // ------------------------------------------------------------------------
    private StringBuffer toTableRow(
            PerspectiveThesisOpinion anOpinion)
    {
        StringBuffer myText = new StringBuffer("<tr>\n");
        myText.append("  <td class=\"otherClass\"> "
                + toPerspectiveAnchor(anOpinion.getPerspective()) + "</td>\n");
        myText.append("  <td class=\"" + cssClassByOpinion(anOpinion) + "\">"
                + anOpinion.toString() + "</td>\n");
        myText.append("</tr>\n");
        return myText;
    }

    // ------------------------------------------------------------------------
    private static StringBuffer relevanceText(RelatedThesis<OpinionatedThesis> aPremise)
    {
        StringBuffer myText = new StringBuffer();
        myText.append("IfTrue:");
        myText.append(aPremise.getIfTrueRelevance());
        myText.append(", IfFalse:");
        myText.append(aPremise.getIfFalseRelevance());
        myText.append(", Implied:" + 
                RelatedThesis.getImpliedBelief(
                        aPremise.getThesis().getOpinion(), aPremise.getRelation()));
        return myText;
    }

    // ------------------------------------------------------------------------
    /**
     * @param aData
     * @return
     */
    public String thesisListToInternalHtml(ListThesesData aData)
    {
        StringBuffer myText = new StringBuffer();
        myText.append("Perspective: " + aData.getPerspective());
        myText.append("<h1> All theses </h1>\n");
        myText.append("<table>\n");
        myText.append("<tr>\n");
        myText.append("  <th class=\"otherClass\"> Operations      </th>\n");
        myText.append("  <th class=\"otherClass\"> Thesis </th>\n");
        myText.append("  <th class=\"otherClass\"> Owner </th>\n");
        myText.append("</tr>\n");

        for (OpinionatedThesis myThesis : aData.getTheses())
        {
            myText.append(toTableRow(myThesis));
        }
        myText.append("</table>\n");

        return myText.toString();
    }
}
