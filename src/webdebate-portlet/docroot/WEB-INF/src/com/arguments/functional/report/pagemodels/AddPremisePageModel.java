/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.Relation;
import com.arguments.functional.datamodel.Relevance;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.requeststate.ArgsRequestKey;
import com.arguments.functional.requeststate.ArgsStatefulRequest3;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.requeststate.asstrings.ArgsRequestKeyAsString;

/**
 * @author mirleau
 *
 */
public class AddPremisePageModel
{
    public final String theThesisText;
    
    public final String thePremiseTextFormName;

    public final String thePremiseOpinionFormLabel;
    public final String thePremiseOpinionFormName;
    public final String thePremiseOpinionFormValue;
    
    public final String theIfTrueRelevanceFormLabel;
    public final String theIfTrueRelevanceFormName;
    public final String theIfTrueRelevanceFormValue;
    
    public final String theIfFalseRelevanceFormLabel;
    public final String theIfFalseRelevanceFormName;
    public final String theIfFalseRelevanceFormValue;
    
    // ------------------------------------------------------------------------
    public AddPremisePageModel(
            ArgsStatefulRequest3 myStatefulRequest,
            ProtocolMap aProtocolmap)
    {
        this(myStatefulRequest.getActiveThesis(), aProtocolmap);
    }

    // ------------------------------------------------------------------------
    public AddPremisePageModel(
            OpinionatedThesis aThesis,
            ProtocolMap aProtocolMap)
    {
        theThesisText = aThesis.getSummary().toString();
        thePremiseTextFormName = aProtocolMap.get(ArgsRequestKey.PREMISE_TEXT);
        
        thePremiseOpinionFormLabel = ThesisOpinion.OPINION_EXPLANATION;
        thePremiseOpinionFormName = aProtocolMap.get(ArgsRequestKey.PREMISE_OPINION);
        thePremiseOpinionFormValue = "" + ThesisOpinion.NEUTRAL_I;
        
        theIfTrueRelevanceFormLabel = Relation.IF_TRUE_RELEVANCE_EXPLANATION;
        theIfTrueRelevanceFormName = ArgsRequestKeyAsString.s(ArgsRequestKey.PREMISE_IF_TRUE_WEIGHT);
        theIfTrueRelevanceFormValue = "" + Relevance.STRENGTHENS;
        
        theIfFalseRelevanceFormLabel = Relation.IF_FALSE_RELEVANCE_EXPLANATION;
        theIfFalseRelevanceFormName = ArgsRequestKeyAsString.s(ArgsRequestKey.PREMISE_IF_FALSE_WEIGHT);
        theIfFalseRelevanceFormValue = "" + Relevance.IRRELEVANT;
    }
}
