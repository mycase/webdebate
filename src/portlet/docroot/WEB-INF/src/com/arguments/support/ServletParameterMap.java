/**
 * 
 */
package com.arguments.support;

import java.util.Map;

/**
 * @author mirleau
 *
 */
public class ServletParameterMap extends CgiParameterMap
{
    // ------------------------------------------------------------------------
    public ServletParameterMap()
    {
        super();
    }

    // ------------------------------------------------------------------------
    public ServletParameterMap(Map<String, String[]> aMap)
    {
        super(aMap);
    }
}
