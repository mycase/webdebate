/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.requeststate.ArgsRequestKey;
import com.arguments.functional.requeststate.ProtocolMap;

/**
 * @author mirleau
 *
 */
public class AddThesisPageModel
{
    public final String theThesisTextFormName;

    public final String theThesisOpinionFormLabel;
    public final String theThesisOpinionFormName;
    public final String theThesisOpinionFormValue;
    
    // ------------------------------------------------------------------------
    public AddThesisPageModel(
            ProtocolMap aTheprotocolmap)
    {
        theThesisTextFormName = aTheprotocolmap.get(ArgsRequestKey.NEW_THESIS_TEXT);
        
        theThesisOpinionFormLabel = ThesisOpinion.OPINION_EXPLANATION;
        theThesisOpinionFormName = aTheprotocolmap.get(ArgsRequestKey.NEW_THESIS_OPINION);
        theThesisOpinionFormValue = ""+ThesisOpinion.NEUTRAL_I;
    }
}
