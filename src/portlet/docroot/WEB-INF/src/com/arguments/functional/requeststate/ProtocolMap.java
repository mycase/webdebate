/**
 * 
 */
package com.arguments.functional.requeststate;

import java.util.Map;

import com.arguments.support.MapWrapper;

/**
 * @author mirleau
 *
 */
public class ProtocolMap extends MapWrapper<ArgsRequestKey, String>
{
    // ------------------------------------------------------------------------
    public ProtocolMap(Map<ArgsRequestKey, String> aMap)
    {
        super(aMap);
    }

    
    // ------------------------------------------------------------------------
    public ProtocolMap()
    {
        super();
    }
}
