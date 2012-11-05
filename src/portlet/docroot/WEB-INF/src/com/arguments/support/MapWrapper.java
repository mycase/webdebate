/**
 * 
 */
package com.arguments.support;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author mirleau
 *
 */
public class MapWrapper<A, B>
{
    private final Map<A, B> theMap;
    
    // ------------------------------------------------------------------------
    public MapWrapper(Map<A, B> aMap)
    {
        theMap = aMap;
    }
    
    // ------------------------------------------------------------------------
    public MapWrapper()
    {
        theMap = new HashMap<>();
    }

    // ------------------------------------------------------------------------
    protected Map<A,B> getMap()
    {
        return theMap;
    }
    
    // ------------------------------------------------------------------------
    public B get(A aKey)
    {
        return theMap.get(aKey);
    }

    // ------------------------------------------------------------------------
    public void put(A aKey, B aValue)
    {
        theMap.put(aKey, aValue);
    }
    
    // ------------------------------------------------------------------------
    public Set<A> keySet()
    {
        return theMap.keySet();
    }
    
    // ------------------------------------------------------------------------
    public Collection<B> values()
    {
        return theMap.values();
    }
}
