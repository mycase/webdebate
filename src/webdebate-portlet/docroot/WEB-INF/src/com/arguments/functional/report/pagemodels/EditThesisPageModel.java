/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.requeststate.ArgsRequestKey;
import com.arguments.functional.requeststate.ArgsStatefulRenderRequest;
import com.arguments.functional.requeststate.ProtocolMap;

/**
 * @author mirleau
 *
 */
public class EditThesisPageModel
{
    public final String theThesisTextFormName;
    public final String theThesisTextFormValue;
    
    public final String theThesisOpinionFormLabel;
    public final String theThesisOpinionFormName;
    public final String theThesisOpinionFormValue;
    
    // ------------------------------------------------------------------------
    public EditThesisPageModel(
            ArgsStatefulRenderRequest aRequest,
            ProtocolMap aTheprotocolmap)
    {
        OpinionatedThesis myThesis = aRequest.getActiveThesis();
        
        theThesisTextFormName = aTheprotocolmap.get(ArgsRequestKey.THESIS_TEXT);
        theThesisTextFormValue = myThesis.getSummary().toString();
        
        theThesisOpinionFormLabel = ThesisOpinion.OPINION_EXPLANATION;
        theThesisOpinionFormName = aTheprotocolmap.get(ArgsRequestKey.THESIS_OPINION);
        theThesisOpinionFormValue = "" + myThesis.getOpinion().getPercentage();
     }
}
