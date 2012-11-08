/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.functional.requeststate.ArgsRequestKey;
import com.arguments.functional.requeststate.ProtocolMap;

/**
 * @author mirleau
 *
 */
public class GotoThesisPageModel
{
    public final String theThesisIdFormName;
    
    // ------------------------------------------------------------------------
    public GotoThesisPageModel(
            ProtocolMap aProtocolMap)
    {
        theThesisIdFormName = aProtocolMap.get(ArgsRequestKey.THESIS_ID);
    }
}
