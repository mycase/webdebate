/**
 * 
 */
package com.arguments.application.liferay;

import static org.junit.Assert.assertNotSame;

import com.arguments.functional.requeststate.ArgsRequestKey;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.requeststate.asstrings.ArgsRequestKeyAsString;
import com.arguments.support.CgiParameterMap;

/**
 * @author mirleau
 *
 */
public class LiferayArgsRequestKey{
    
    // ------------------------------------------------------------------------
    public static String s(ArgsRequestKey aKey)
    {
        return ArgsRequestKeyAsString.s(aKey);
    }
    
    // ------------------------------------------------------------------------
    public static ProtocolMap getProtocolMap()
    {
        return ArgsRequestKeyAsString.getProtocolMap();
    }
    
    // ------------------------------------------------------------------------
    public static ProtocolMap getProtocolMap(CgiParameterMap aParameterMap)
    {
        assertNotSame(aParameterMap, null);
        return ArgsRequestKeyAsString.getParameterMap(aParameterMap);
    }
}
