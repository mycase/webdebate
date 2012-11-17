package com.arguments.application.liferay;

import static org.junit.Assert.*;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;

import com.arguments.application.ContainerException;
import com.arguments.application.javax.JavaxArgsBridge;
import com.arguments.functional.datamodel.ArgsActionRequest;
import com.arguments.functional.datamodel.ArgsErrorHandler;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.ForeignUserId;
import com.arguments.functional.report.PerspectiveNotOwnedException;
import com.arguments.functional.report.ThesisNotOwnedException;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.support.CgiParameterMap;
import com.arguments.support.EmailAddress;
import com.arguments.support.Logger;
import com.arguments.support.ScreenName;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

public class LiferayArgsBridge extends JavaxArgsBridge
{
    public static final String THESIS_NOT_OWNED_KEY = "thesisNotOwnedException5";
    public static final String THESIS_NOT_OWNED_MESS = "You don't own this thesis 5.";
    
    public static final String PERSPECTIVE_NOT_OWNED_KEY = "perspectiveNotOwnedException";
    public static final String PERSPECTIVE_NOT_OWNED_MESS = "You don't own this perspective.";
    
    public static final String MAKE_YOUR_CASE_EXC_KEY = "makeYourCaseException";
    public static final String MAKE_YOUR_CASE_EXC_MESS = "Make-Your-Case had an error";
    
    // ------------------------------------------------------------------------
    @Override
    public void processAction(ActionRequest anActionRequest)
    // Concerns:
    //   1. Get rid of javax.ActionRequest
    //   2. Extract user & error handlers from portal
    //   3. Execute request.
    {
        Logger.log("\nprocessAction");

        try
        {
            ArgsActionRequest myRequest = getArgsActionRequest(anActionRequest);
            Logger.logAction(myRequest);
            myRequest.execute();
        }
        catch (ThesisNotOwnedException anException)
        {
            anException.printStackTrace();
            SessionErrors.add(anActionRequest, THESIS_NOT_OWNED_KEY);            
        }
        catch (PerspectiveNotOwnedException anException)
        {
            anException.printStackTrace();
            SessionErrors.add(anActionRequest, PERSPECTIVE_NOT_OWNED_KEY);            
        }
        catch (Throwable anException)
        {
            anException.printStackTrace();
            SessionErrors.add(anActionRequest, MAKE_YOUR_CASE_EXC_KEY);
        }
    }

    // ------------------------------------------------------------------------
    @Override
    public ProtocolMap getProtocolMap(CgiParameterMap aParameterMap)
    {
        assertNotNull(aParameterMap);
        return LiferayArgsRequestKey.getProtocolMap(aParameterMap);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public ProtocolMap getProtocolMap()
    {
        return LiferayArgsRequestKey.getProtocolMap();
    }    
    
    // ------------------------------------------------------------------------
    @Override
    public ArgumentsUser getAppUser(PortletRequest aPortletRequest)
    {
        Long myLiferayUserId = Long.parseLong(aPortletRequest.getRemoteUser());
        User myLiferayUser;
        try
        {
            myLiferayUser = UserLocalServiceUtil.getUserById(myLiferayUserId);
        }
        catch (PortalException | SystemException anException)
        {
            throw new ContainerException("Can't find liferay user: " + myLiferayUserId,
                    anException);
        }
        
        return TheArgsStore.i().getAppUser(
                new EmailAddress(myLiferayUser.getEmailAddress()),
                new ForeignUserId("" + myLiferayUser.getUserId()),
                new ScreenName(myLiferayUser.getScreenName()));
    }
    
    // ------------------------------------------------------------------------
    @Override
    public ArgsErrorHandler newErrorHandler(
            RenderRequest aRequest, ArgumentsUser aArgumentsUser)
    {
        return new LiferayErrorHandler(aRequest, aArgumentsUser);
    }

    // ------------------------------------------------------------------------
    @Override
    public HttpServletRequest getOriginalServletRequest(
            PortletRequest aRequest1)
    {
        HttpServletRequest myRequest2 = PortalUtil
                .getHttpServletRequest(aRequest1);
        HttpServletRequest myRequest3 = PortalUtil
                .getOriginalServletRequest(myRequest2);
        return myRequest3;
    }
}
