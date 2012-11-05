/**
 * 
 */
package com.arguments.support;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author mirleau
 *
 */
public class Container_Tester
{
    // ------------------------------------------------------------------------
    @Test
    public void singleContainerIsLeaf()
    {
        Container<String> myContainer = new Container<>("single");
        assertTrue(myContainer.isLeaf());
    }

    // ------------------------------------------------------------------------
    @Test
    public void singleContainerHasNoChldren()
    {
        Container<String> myContainer = new Container<>("single");
        assertEquals(0, myContainer.getNumberOfChildren());
    }

    // ------------------------------------------------------------------------
    @Test
    public void containerContainsItem()
    {
        String myItem = "single";
        Container<String> myContainer = new Container<>(myItem);
        assertEquals(myItem, myContainer.getItem());
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
