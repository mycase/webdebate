/**
 * 
 */
package com.arguments.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * @author mirleau
 *
 */
public class CollectionTransforms_Tester extends CollectionTransforms
{
    // ------------------------------------------------------------------------
    @Test
    public void toSimpleMap_empty()
    {
        Map<String, String[]> myIn = new HashMap<>();
        Map<String, String> myOut = toSimpleMap(myIn);
        Map<String, String> myOutRef = new HashMap<>();
        assertEquals(myOutRef, myOut);
    }

    // ------------------------------------------------------------------------
    @Test(expected=IllegalArgumentException.class)
    public void toSimpleMap_zeroLength()
    {
        Map<String, String[]> myIn = new HashMap<String, String[]>(){{
            put("bobo", new String[0]);
        }};
        toSimpleMap(myIn);
    }

    // ------------------------------------------------------------------------
    @Test
    public void toSimpleMap_lengthOne()
    {
        Map<String, String[]> myIn = new HashMap<String, String[]>(){{
            put("bobo", new String[]{"gogo"});
        }};
        Map<String, String> myOut = toSimpleMap(myIn);
        Map<String, String> myOutRef = new HashMap<String, String>(){{
            put("bobo", "gogo");
        }};
        assertEquals(myOutRef, myOut);
    }

    // ------------------------------------------------------------------------
    @Test
    public void toSimpleMap_lengthTwoReturnsFirst()
    {
        Map<String, String[]> myIn = new HashMap<String, String[]>(){{
            put("bobo", new String[]{"gogo", "mama"});
        }};
        Map<String, String> myOut = toSimpleMap(myIn);
        Map<String, String> myOutRef = new HashMap<String, String>(){{
            put("bobo", "gogo");
        }};
        assertEquals(myOutRef, myOut);
    }

    // ------------------------------------------------------------------------
    @Test
    public void invert_ofEmptyIsEmpty()
    {
        Map<String, Integer> myIn = new HashMap<>();
        Map<Integer, String> myOut = invert(myIn);
        Map<Integer, String> myOutRef = new HashMap<>();
        assertEquals(myOutRef, myOut);
    }

    // ------------------------------------------------------------------------
    @Test
    public void invert_ofOneEntry()
    {
        Map<String, Integer> myIn = new HashMap<String, Integer>(){{
            put("a",1);
        }};
        Map<Integer, String> myOut = invert(myIn);
        Map<Integer, String> myOutRef = new HashMap<Integer, String>(){{
            put(1, "a");}};
        assertEquals(myOutRef, myOut);
    }

    // ------------------------------------------------------------------------
    @Test
    public void mapComposeInv_empty()
    {
        Map<String, Integer> myIn1 = new HashMap<String, Integer>(){{
            put("a", 1);
            put("b", 2);
        }};
        Map<String, Double> myIn2 = new HashMap<String, Double>(){{
            put("a", 1.1d);
            put("b", 2.2d);
        }};
        
        Map<Double, Integer> myOut = mapComposeInv(myIn1, myIn2);
        Map<Double, Integer> myOutRef = new HashMap<Double, Integer>(){{
            put(1.1, 1);
            put(2.2, 2);
        }};

        assertEquals(myOutRef, myOut);
    }

    // ------------------------------------------------------------------------
    @Test
    public void parentIsNotLeaf()
    {
        Container<String> myParent = new Container<>("parent");
        Container<String> myChild = new Container<>("child");
        myParent.add(myChild);
        assertFalse(myParent.isLeaf());
    }

    // ------------------------------------------------------------------------
    @Test
    public void twoChildrenAre2()
    {
        Container<String> myParent = new Container<>("parent");
        Container<String> myChild1 = new Container<>("child1");
        Container<String> myChild2 = new Container<>("child2");
        myParent.add(myChild1);
        myParent.add(myChild2);
        assertEquals(2, myParent.getNumberOfChildren());
    }
}
