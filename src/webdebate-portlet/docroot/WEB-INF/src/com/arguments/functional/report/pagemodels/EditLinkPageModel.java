/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.functional.datamodel.Relation;
import com.arguments.functional.requeststate.ArgsRequestKey;
import com.arguments.functional.requeststate.ArgsStatefulRequest3;
import com.arguments.functional.requeststate.ProtocolMap;

/**
 * @author mirleau
 *
 */
public class EditLinkPageModel
{
    public final String theThesis1Summary;
    public final String theThesis2Summary;

    public final String theLinkIdFormName;
    public final String theLinkIdFormValue;
    
    public final String theTargetIdFormName;
    public final String theTargetIdFormValue;
    
    public final String theIfTrueRelevanceFormLabel;
    public final String theIfTrueRelevanceFormName;
    public final String theIfTrueRelevanceFormValue;
    
    public final String theIfFalseRelevanceFormLabel;
    public final String theIfFalseRelevanceFormName;
    public final String theIfFalseRelevanceFormValue;
    
    // ------------------------------------------------------------------------
    public EditLinkPageModel(
            ArgsStatefulRequest3 aRequest,
            ProtocolMap aTheprotocolmap)
    {
        Relation myLink = aRequest.getActiveLink();

        theThesis1Summary = myLink.getThesis1Summary();
        theThesis2Summary = myLink.getThesis2Summary();

        theLinkIdFormName = aTheprotocolmap.get(ArgsRequestKey.RELATION_ID);
        theLinkIdFormValue = "" + myLink.getID();
        
        theTargetIdFormName = aTheprotocolmap.get(ArgsRequestKey.NEW_LINK_TARGET_ID);
        theTargetIdFormValue = "" + myLink.getThesis2ID().getLongID();
        
        theIfTrueRelevanceFormLabel = Relation.IF_TRUE_RELEVANCE_EXPLANATION;
        theIfTrueRelevanceFormName = aTheprotocolmap.get(ArgsRequestKey.NEW_IF_TRUE_PERCENTAGE_RELEVANCE);
        theIfTrueRelevanceFormValue = "" +myLink.getIfTruePercentageRelevance();
        
        theIfFalseRelevanceFormLabel = Relation.IF_FALSE_RELEVANCE_EXPLANATION;
        theIfFalseRelevanceFormName = aTheprotocolmap.get(ArgsRequestKey.NEW_IF_FALSE_PERCENTAGE_RELEVANCE);
        theIfFalseRelevanceFormValue = ""+myLink.getIfFalsePercentageRelevance();
    }
}
