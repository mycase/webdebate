/**
 * 
 */
package com.arguments.support;

/**
 * @author mirleau
 *
 */
public abstract class TreeHandler<A>
{
    // ------------------------------------------------------------------------
    public void traverse(Container<A> aContainer)
    {
        handle(aContainer.getItem());
        if (aContainer.isLeaf()) return;
        goIn();
        for (Container<A> aChild: aContainer.getChildren())
            traverse(aChild);
        goOut();
    }
    
    // ------------------------------------------------------------------------
    public abstract void handle(A anItem);
    
    // ------------------------------------------------------------------------
    public abstract void goIn();
    
    // ------------------------------------------------------------------------
    public abstract void goOut();
}
