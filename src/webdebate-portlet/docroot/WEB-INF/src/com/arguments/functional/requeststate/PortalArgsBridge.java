package com.arguments.functional.requeststate;

import static org.junit.Assert.*;

import com.arguments.functional.command.StateChangeCommand;
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
    public enum UpdateStateFlag
    {
        YES, NO;
    }

    // ------------------------------------------------------------------------
    public void execute(ArgsJspRenderRequest aJspRequest)
    {
        assureConnect();
        // ArgsRequest contains data that is simplest (least error prone)
        //  to extract from a javax.RenderRequest.
        // ArgsJspRenderRequest extends this with information encoded in
        //   the target jsp page (which itself is addressed via the
        //   source jsp page).

        final ArgsRequest myRequest = aJspRequest.getRequest();
        // PortletParameterMap, ServletParameterMap, ArgumentsUser
        
        final ArgumentsUser myAppUser = myRequest.getAppUser();
        
        // Depending on the jsp target, we either get the servlet
        // or the portlet parameter map:
        final ProtocolMap myProtocolMap = getProtocolMap(
                myRequest, aJspRequest.getStateInputMode());

        // From that protocolmap, we extract the actual information
        // needed, being a possible state change, and
        // non-state changing directives like the linkid in case
        // a link needs to be edited:
        
        StateChangeCommand myStateChange =
                new StateChangeCommand(
                        myProtocolMap,
                        aJspRequest.getUpdateState(),
                        myAppUser);

        myStateChange.execute(null);
    }

    // ------------------------------------------------------------------------
    public ArgsStatefulRequest3 storeStateGetArgumentsRequest3(
            ArgsJspRenderRequest aJspRequest)
    {
        // ArgsRequest contains data that is simplest (least error prone)
        //  to extract from a javax.RenderRequest.
        // ArgsJspRenderRequest extends this with information encoded in
        //   the target jsp page (which itself is addressed via the
        //   source jsp page).

        final ArgsRequest myRequest = aJspRequest.getRequest();
        // PortletParameterMap, ServletParameterMap, ArgumentsUser
        
        final ArgumentsUser myAppUser = myRequest.getAppUser();
        
        // Depending on the jsp target, we either get the servlet
        // or the portlet parameter map:
        final ProtocolMap myProtocolMap = getProtocolMap(
                myRequest, aJspRequest.getStateInputMode());

        ArgsRenderDirective myRenderDirective =
                new ArgsRenderDirective(myAppUser,
                aJspRequest.getUrlContainer(), myProtocolMap);
        // User, Urls, ChangeRelationId

        ArgsReadOnlyState myStateAfter = 
                TheArgsStore.i().selectState(myAppUser);
        return new ArgsStatefulRequest3(
                myRenderDirective, myStateAfter);
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
