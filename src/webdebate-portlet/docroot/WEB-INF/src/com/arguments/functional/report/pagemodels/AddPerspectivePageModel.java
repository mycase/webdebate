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
public class AddPerspectivePageModel
{
    public final String thePerspectiveTextFormName;
    
    // ------------------------------------------------------------------------
    public AddPerspectivePageModel(
            ProtocolMap aTheprotocolmap)
    {
        thePerspectiveTextFormName = aTheprotocolmap.get(ArgsRequestKey.NEW_PERSPECTIVE_NAME);
    }
}
