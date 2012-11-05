package com.arguments.application.liferay;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import com.arguments.application.TheContainerBridge;
import com.arguments.support.Logger;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class LiferayArgsPortlet extends MVCPortlet
{
    // ------------------------------------------------------------------------
    @Override
    public void processAction(
            ActionRequest anActionRequest,
            ActionResponse anActionResponse)
                    throws IOException, PortletException
    {
        Logger.log("\nprocessAction");
        TheContainerBridge.i().processAction(anActionRequest);
        super.processAction(anActionRequest, anActionResponse);
    }
}
