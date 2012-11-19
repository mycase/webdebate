package com.arguments.functional.requeststate;

import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUserId;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.support.CgiParameterMap;

/**
 * @author mirleau
 * 
 */
public abstract class PortalArgsBridge
{
    // ------------------------------------------------------------------------
    public enum CgiSource
    {
        SERVLET, PORTLET;
    }

    // ------------------------------------------------------------------------
    public enum UpdateStateFlag
    {
        YES, NO;
    }

    // ------------------------------------------------------------------------
    public static ArgsState getState(ArgumentsUserId aUser)
    {
        return TheArgsStore.i().selectState(aUser);
    }

    // ------------------------------------------------------------------------
    public abstract ProtocolMap getProtocolMap(CgiParameterMap aParameterMap);

    // ------------------------------------------------------------------------
    public static void assureConnect()
    {
        TheArgsStore.i().assureConnect();
    }
}
