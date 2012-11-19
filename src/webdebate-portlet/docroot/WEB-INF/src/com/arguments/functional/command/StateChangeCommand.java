/**
 * 
 */
package com.arguments.functional.command;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.requeststate.PortalArgsBridge.UpdateStateFlag;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.requeststate.StateChange;

/**
 * @author mirleau
 *
 */
public class StateChangeCommand extends StateChange implements Command
{
    private final UpdateStateFlag theUpdateStateFlag;
    private final ArgumentsUser theAppUser;
    
    // ------------------------------------------------------------------------
    public StateChangeCommand(ProtocolMap aRequestMap,
            UpdateStateFlag anUpdateStateFlag,
            ArgumentsUser anAppUser)
    {
        super(aRequestMap);
        theUpdateStateFlag = anUpdateStateFlag;
        theAppUser = anAppUser;
    }

    // ------------------------------------------------------------------------
    @Override
    public void execute(ArgsState aState)
    {
        if (theUpdateStateFlag == UpdateStateFlag.YES && hasChange())
        {
            mergeAndStore(theAppUser);
        }
    }
}
