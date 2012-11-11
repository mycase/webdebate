package com.arguments.application.liferay;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import com.arguments.application.TheContainerBridge;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class LiferayArgsPortlet extends MVCPortlet
{
    // ------------------------------------------------------------------------
    // Two concerns:
    //  1. Having the least possible in this class because it depends on liferay.MVCPortlet
    //  2. Removing dependency on javax.ActionRequest-> Done in LiferayBridge,
    //       which also gets called from jsps.
    @Override
    public void processAction(
            ActionRequest anActionRequest,
            ActionResponse anActionResponse)
                    throws IOException, PortletException
    {
        TheContainerBridge.i().processAction(anActionRequest);
        super.processAction(anActionRequest, anActionResponse);
    }
}
