package com.arguments.functional.datamodel;

import static org.junit.Assert.*;
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
        theParameterMap = aParameterMap;
        theServletParameterMap = aServletParameterMap;
        theUser = aUser;
        assertNotNull(theServletParameterMap);
        assertNotNull(theParameterMap);
        assertNotNull(theUser);
    }
    
    // ------------------------------------------------------------------------
    public ArgsRequest(ArgsRequest aRequest)
    {
        this(aRequest.theParameterMap,
             aRequest.theServletParameterMap,
             aRequest.theUser);
    }

    // ------------------------------------------------------------------------
    public CgiParameterMap getCgiParameterMap(CgiSource aSource)
    {
        if (aSource == CgiSource.SERVLET)
        {
            assertNotNull(theServletParameterMap);
            return theServletParameterMap;
        }

        assertNotNull( theParameterMap );
        assertNotNull(theParameterMap);        
        return theParameterMap;
    }

    // ------------------------------------------------------------------------
    public ArgumentsUser getAppUser()
    {
        return theUser;
    }

}
