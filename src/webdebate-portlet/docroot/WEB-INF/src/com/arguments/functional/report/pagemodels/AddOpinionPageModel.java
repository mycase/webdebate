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
public class AddOpinionPageModel
{
    public final String theThesisText;
    public final String theThesisOpinion;
    public final String theFormName;
    public final String theFormLabel;
    
    // ------------------------------------------------------------------------
    public AddOpinionPageModel(
            ArgsStatefulRenderRequest aStatefulRequest,
            ProtocolMap aTheprotocolmap)
    {
        theFormName =aTheprotocolmap.get(ArgsRequestKey.NEW_THESIS_OPINION2);
        OpinionatedThesis myThesis = aStatefulRequest.getActiveThesis();
        theThesisText = myThesis.getSummary().toString();
        theThesisOpinion = "" + myThesis.getOpinion().getPercentage();
        theFormLabel = ThesisOpinion.OPINION_EXPLANATION;
    }    
}
