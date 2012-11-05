package com.arguments.functional.requeststate;

import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
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
    public ArgsStatefulRequest storeStateGetArgumentsRequest(
            ArgsRequest2 aRequest2)
    {
        assureConnect();
        final ArgumentsUser myAppUser = aRequest2.getRequest().getAppUser();
        final ArgsRequest myRequest = aRequest2.getRequest();
        
        final CgiParameterMap myParameterMap = myRequest
                .getCgiParameterMap(aRequest2.getStateInputMode());

        assertNotSame(myParameterMap, null);
        ProtocolMap myProtocolMap =  getProtocolMap(myParameterMap);

        ArgumentsRequest myArgRequest = new ArgumentsRequest(myAppUser,
                aRequest2.getUrlContainer(), myProtocolMap);

        if (aRequest2.getUpdateState() == UpdateState.YES)
        {
            StateChange myStateChange = myArgRequest
                    .getStateChange();
            if (myStateChange.hasChange())
            {
                myStateChange.mergeAndStore(myAppUser);
            }
        }
        ArgsState myArgsState = getState(myAppUser);
        return new ArgsStatefulRequest(myArgRequest, myArgsState);
    }

    // ------------------------------------------------------------------------
    protected static ArgsState getState(ArgumentsUser aUser)
    {
        return TheArgsStore.i().selectState(aUser);
    }

    // ------------------------------------------------------------------------
    protected abstract ProtocolMap getProtocolMap(CgiParameterMap aParameterMap);

    // ------------------------------------------------------------------------
    protected static void assureConnect()
    {
        TheArgsStore.i().assureConnect();
    }
}
