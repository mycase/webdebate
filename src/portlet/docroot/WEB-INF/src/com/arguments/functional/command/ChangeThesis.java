/**
 * 
 */
package com.arguments.functional.command;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ThesisId;

/**
 * @author mirleau
 *
 */
public class ChangeThesis implements Command
{
        private ThesisId theForcusThesisId;
    
        // ------------------------------------------------------------------------
        public ChangeThesis(ThesisId anId)
        {
            theForcusThesisId = anId;
        }
    
        // ------------------------------------------------------------------------
        @Override
        public void execute(ArgsState aState)
        {
            // nothing here at the moment, because of seperate cgi2state channel.
        }
}