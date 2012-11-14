package com.arguments.functional.requeststate;

import static org.junit.Assert.*;

import com.arguments.functional.datamodel.ArgsReadOnlyState;
import com.arguments.functional.datamodel.ArgsRequest;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.ArgumentsUserId;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.report.html.UrlContainer;
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
            ArgsRenderRequest aRequest)
    {
        assureConnect();
        final ArgsRequest myRequest = aRequest.getRequest();
        // PortletParameterMap, ServletParameterMap, ArgumentsUser
        
        final ArgumentsUser myAppUser = myRequest.getAppUser();
        
        final ProtocolMap myProtocolMap = getProtocolMap(myRequest, aRequest.getStateInputMode());

        if (aRequest.getUpdateState() == UpdateState.YES)
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
                aRequest.getUrlContainer(), myProtocolMap);
        // User, Urls, ChangeRelationId

        return new ArgsStatefulRequest3(myArgRequest, myArgsState);
        // ArgsReq3 + State
    }
    
    // ------------------------------------------------------------------------
    private ProtocolMap getProtocolMap(ArgsRequest aRequest,
            final CgiSource aCgiSource)
    {
        final CgiParameterMap myParameterMap = aRequest
                .getCgiParameterMap(aCgiSource);

        assertNotNull(myParameterMap);
        final ProtocolMap myProtocolMap =  getProtocolMap(myParameterMap);
        return myProtocolMap;
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
