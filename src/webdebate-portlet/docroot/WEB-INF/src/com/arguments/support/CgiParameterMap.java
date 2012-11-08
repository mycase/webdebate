/**
 * 
 */
package com.arguments.support;

import java.util.Map;

/**
 * @author mirleau
 *
 */
public class CgiParameterMap extends MapWrapper<String, String[]>
{
    // ------------------------------------------------------------------------
    protected CgiParameterMap(Map<String, String[]> aMap)
    {
        super(aMap);
    }
    
    // ------------------------------------------------------------------------
    protected CgiParameterMap()
    {
        super();
    }
    
    // ------------------------------------------------------------------------
    public Map<String, String> getSimpleMap()
    {
        return CollectionTransforms.toSimpleMap(getMap());
    }
}
