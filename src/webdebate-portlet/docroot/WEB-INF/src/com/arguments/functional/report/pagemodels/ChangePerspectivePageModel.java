/**
 * 
 */
package com.arguments.functional.report.pagemodels;

import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.Perspective;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.requeststate.ArgsRequestKey;
import com.arguments.functional.requeststate.ArgsStatefulRequest3;
import com.arguments.functional.requeststate.ProtocolMap;

/**
 * @author mirleau
 *
 */
public class ChangePerspectivePageModel
{
    public final String thePerspectiveIdFormLabel;
    public final String thePerspectiveIdFormName;
    public final String thePerspectiveIdFormValue;
    
    // ------------------------------------------------------------------------
    public ChangePerspectivePageModel(
            ArgsStatefulRequest3 aRequest,
            ProtocolMap aTheprotocolmap)
    {
        String myPerspectiveIdText = aRequest.getPerspectiveIdText();
        ArgumentsUser myUser = aRequest.getUser();
        Perspective myDefaultPerspective = myUser.getDefaultPerspective();
        thePerspectiveIdFormLabel = PerspectiveId.perspectiveIdExplanation(myDefaultPerspective);
        thePerspectiveIdFormName = aTheprotocolmap.get(ArgsRequestKey.PERSPECTIVE_ID);
        thePerspectiveIdFormValue = "" + myPerspectiveIdText;
    }
}
