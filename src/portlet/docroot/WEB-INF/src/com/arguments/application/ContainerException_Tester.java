/**
 * 
 */
package com.arguments.application;

import org.junit.Test;

/**
 * @author mirleau
 *
 */
public class ContainerException_Tester
{
    // ------------------------------------------------------------------------
    @Test(expected = ContainerException.class)
    public void testConstruct1()
    {
        throw new ContainerException(new AssertionError());
    }

    // ------------------------------------------------------------------------
    @Test(expected = ContainerException.class)
    public void testConstruct2()
    {
        throw new ContainerException("bla", new AssertionError());
    }

    // ------------------------------------------------------------------------
    @Test(expected = ContainerException.class)
    public void testConstruct3()
    {
        throw new ContainerException("bla");
    }
}
