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
        private ThesisId theFocusThesisId;
    
        // ------------------------------------------------------------------------
        public ChangeThesis(ThesisId anId)
        {
            theFocusThesisId = anId;
        }
    
        // ------------------------------------------------------------------------
        @Override
        public void execute(ArgsState aState)
        {
            assert (theFocusThesisId.equals(aState.getThesisId()));
            // nothing here at the moment, because of seperate cgi2state channel.
        }
}