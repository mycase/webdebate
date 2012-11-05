/**
 * 
 */
package com.arguments.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author mirleau
 *
 */
public class CollectionTransforms
{
    // ------------------------------------------------------------------------
    public static <A, B> Map<A, B> toSimpleMap(Map<A, B[]> aMap)
    {
        Map<A, B> myReturnValue = new HashMap<>();
        for (Entry<A, B[]> myEntry : aMap.entrySet())
        {
            if (myEntry.getValue().length == 0)
                throw new IllegalArgumentException("Value for key = " +
                        myEntry.getKey() + " is empty array.");
            myReturnValue.put(myEntry.getKey(), myEntry.getValue()[0]);
        }
        return myReturnValue;
    }

    // ------------------------------------------------------------------------
    public static <A, B> Map<A, B> invert(Map<B, A> aMap)
    {
        Map<A, B> myReturnValue = new HashMap<>();
        for (Entry<B,A> myEntry : aMap.entrySet())
        {
            myReturnValue.put(myEntry.getValue(), myEntry.getKey());
        }
        return myReturnValue;
    }

    // ------------------------------------------------------------------------
    public static <K, I, V> Map<K, V> mapComposeInv(
            Map<I, V> aValueMap,
            Map<I, K> aKeyMap)
    {
        Map<K,V> myReturnValue = new HashMap<>();
        for (Entry<I, V> myEntry : aValueMap.entrySet())
        {
            I myI = myEntry.getKey();
            myReturnValue.put(
                    aKeyMap.get(myI), myEntry.getValue());
        }
        return myReturnValue;
    }
}
