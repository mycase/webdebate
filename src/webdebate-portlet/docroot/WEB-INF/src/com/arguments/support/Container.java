/**
 * 
 */
package com.arguments.support;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mirleau
 *
 */
public class Container<A>
{
    private List<Container<A>> theChildren = new ArrayList<>();
    private final A theItem; 
    
    // ------------------------------------------------------------------------
    public Container(A anItem)
    {
        theItem = anItem;
    }
    
    // ------------------------------------------------------------------------
    public void add(Container<A> aContainer)
    {
        theChildren.add(aContainer);
    }
    
    // ------------------------------------------------------------------------
    public A getItem()
    {
        return theItem;
    }
    
    // ------------------------------------------------------------------------
    public boolean isLeaf()
    {
        return theChildren.size() == 0;
    }
    
    // ------------------------------------------------------------------------
    public int getNumberOfChildren()
    {
        return theChildren.size();
    }
    
    // ------------------------------------------------------------------------
    public List<Container<A>> getChildren()
    {
        return theChildren;
    }
}
