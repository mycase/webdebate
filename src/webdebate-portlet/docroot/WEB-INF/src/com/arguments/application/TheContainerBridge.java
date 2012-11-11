/**
 * 
 */
package com.arguments.application;

import com.arguments.application.javax.JavaxArgsBridge;
import com.arguments.application.liferay.LiferayArgsBridge;

/**
 * @author mirleau
 *
 */
public class TheContainerBridge
{
    public final static JavaxArgsBridge theInstance = new LiferayArgsBridge();
    
    // ------------------------------------------------------------------------
    public static JavaxArgsBridge i()
    {
        return theInstance;
    }
}
