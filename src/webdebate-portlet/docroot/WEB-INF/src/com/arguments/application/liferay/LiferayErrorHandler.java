/**
 * 
 */
package com.arguments.application.liferay;

import static org.junit.Assert.*;

import javax.portlet.RenderRequest;

import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.datamodel.ArgumentsUserId;
import com.liferay.portal.kernel.servlet.SessionErrors;

/**
 * @author mirleau
 *
 */
public class LiferayErrorHandler implements ArgsErrorHandler
{
    private final RenderRequest theRequest;
    private final ArgumentsUserId theUserId;

    // ------------------------------------------------------------------------
    public LiferayErrorHandler(RenderRequest aRequest, ArgumentsUserId aUserId)
    {
        assertNotNull(aRequest);
        theRequest = aRequest;
        theUserId = aUserId;
    }

    // ------------------------------------------------------------------------
    @Override
    public String handle(Throwable anException)
    {
        String myErrorId = LiferayArgsBridge.MAKE_YOUR_CASE_EXC_KEY;
        anException.printStackTrace(System.err);
        assertNotNull(myErrorId);
        SessionErrors.add(theRequest, myErrorId);
        String myError = "";
        if (theUserId.equals(ArgumentsUserId.ONE))
            for (StackTraceElement myElement : anException.getStackTrace())
                myError += "<p>"+myElement + "</p>\n";
        return myError;
    }
}
