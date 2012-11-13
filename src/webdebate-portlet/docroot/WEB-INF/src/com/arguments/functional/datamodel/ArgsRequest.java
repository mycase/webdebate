package com.arguments.functional.datamodel;

import static org.junit.Assert.assertNotSame;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.support.CgiParameterMap;
import com.arguments.support.PortletParameterMap;
import com.arguments.support.ServletParameterMap;

/**
 * @author mirleau
 *
 */

// Not so clear what the difference is between this class and
// ArgsStatefulCommand

public class ArgsRequest
{
    private final PortletParameterMap theParameterMap;
    private final ServletParameterMap theServletParameterMap;
    private final ArgumentsUser theUser;
    
    // ------------------------------------------------------------------------
    public ArgsRequest(
            PortletParameterMap aParameterMap,
            ServletParameterMap aServletParameterMap,
            ArgumentsUser aUser)
    {
        // TODO: Not test covered.
        theParameterMap = aParameterMap;
        theServletParameterMap = aServletParameterMap;
        theUser = aUser;
        assertNotSame(theServletParameterMap, null);
        assertNotSame(theParameterMap, null);
    }

    
    // ------------------------------------------------------------------------
    public ArgsRequest(ArgsRequest aRequest)
    {
        theParameterMap = aRequest.theParameterMap;
        theServletParameterMap = aRequest.theServletParameterMap;
        theUser = aRequest.theUser;
    }

    // ------------------------------------------------------------------------
    public CgiParameterMap getCgiParameterMap(CgiSource aSource)
    {
        if (aSource == CgiSource.SERVLET)
        {
            assertNotSame(theServletParameterMap, null);
            return theServletParameterMap;
        }
        assert theParameterMap != null;
        assertNotSame(theParameterMap, null);        
        return theParameterMap;
    }

    // ------------------------------------------------------------------------
    public ArgumentsUser getAppUser()
    {
        return theUser;
    }

}
