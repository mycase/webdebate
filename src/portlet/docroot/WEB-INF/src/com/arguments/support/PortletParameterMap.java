/**
 * 
 */
package com.arguments.support;

import java.util.Map;

/**
 * @author mirleau
 *
 */
public class PortletParameterMap extends CgiParameterMap
{
    // ------------------------------------------------------------------------
    public PortletParameterMap()
    {
        super();
    }

    // ------------------------------------------------------------------------
    public PortletParameterMap(Map<String, String[]> aMap)
    {
        super(aMap);
    }
}
