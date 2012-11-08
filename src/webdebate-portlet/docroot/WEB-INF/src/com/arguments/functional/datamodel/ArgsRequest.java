package com.arguments.functional.datamodel;

import static org.junit.Assert.assertNotSame;

import com.arguments.functional.report.html.UrlContainer;
import com.arguments.functional.requeststate.ArgsRequest2;
import com.arguments.functional.requeststate.PortalArgsBridge.CgiSource;
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateState;
import com.arguments.support.CgiParameterMap;
import com.arguments.support.Logger;
import com.arguments.support.PortletParameterMap;
import com.arguments.support.ServletParameterMap;

/**
 * @author mirleau
 *
 */

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
    public ServletParameterMap getServletParameterMap()
    {
        return theServletParameterMap;
    }

    // ------------------------------------------------------------------------
    public PortletParameterMap getParameterMap()
    {
        return theParameterMap;
    }

    // ------------------------------------------------------------------------
    public ArgumentsUser getAppUser()
    {
        return theUser;
    }

    // ------------------------------------------------------------------------
    public void execute()
    {
        Logger.log("\n======= NEW REQUEST ===========\n");
        ArgsRequest2 myRequest2 = new ArgsRequest2(
                this, new UrlContainer(), CgiSource.PORTLET,
                UpdateState.YES);
        myRequest2.execute();
    }    
}
