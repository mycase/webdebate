/**
 * 
 */
package com.arguments.functional.command;

import com.arguments.functional.datamodel.ArgsState;

/**
 * @author mirleau
 *
 */
public interface Command
{
    public void execute(ArgsState aState);
}
