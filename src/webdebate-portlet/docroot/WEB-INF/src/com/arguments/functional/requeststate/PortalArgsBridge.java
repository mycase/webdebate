package com.arguments.functional.requeststate;

import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgsReadOnlyState;
import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
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
    public enum UpdateState
    {
        YES, NO;
    }

    // ------------------------------------------------------------------------
    public ArgsStatefulRequest3 storeStateGetArgumentsRequest3(
            ArgsRenderRequest aRequest2)
    {
        assureConnect();
        final ArgumentsUser myAppUser = aRequest2.getRequest().getAppUser();
        final ArgsRequest myRequest = aRequest2.getRequest();
        
        final CgiParameterMap myParameterMap = myRequest
                .getCgiParameterMap(aRequest2.getStateInputMode());

        assertNotSame(myParameterMap, null);
        final ProtocolMap myProtocolMap =  getProtocolMap(myParameterMap);

        if (aRequest2.getUpdateState() == UpdateState.YES)
        {
            StateChange myStateChange =
                    new StateChange(myProtocolMap);
            if (myStateChange.hasChange())
            {
                myStateChange.mergeAndStore(myAppUser);
            }
        }
        ArgsReadOnlyState myArgsState = getState(myAppUser);

        ArgsRequest3 myArgRequest = new ArgsRequest3(myAppUser,
                aRequest2.getUrlContainer(), myProtocolMap);

        return new ArgsStatefulRequest3(myArgRequest, myArgsState);
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
