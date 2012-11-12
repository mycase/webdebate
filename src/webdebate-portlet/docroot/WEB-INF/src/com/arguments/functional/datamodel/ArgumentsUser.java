package com.arguments.functional.datamodel;

import com.arguments.functional.store.TheArgsStore;
import com.arguments.support.EmailAddress;
import com.arguments.support.ScreenName;

public class ArgumentsUser extends ArgumentsUserId
{
    private final EmailAddress theEmailAddress;
    private final ForeignUserId theContainerId;
    private final ScreenName theScreenName;
    
    private OwnedPerspective theDefaultPerspective;
    
    // ------------------------------------------------------------------------
    /**
     * @param aEmailAddress
     * @param aUserID
     * @param aContainerId
     * @param aScreenName
     */
    public ArgumentsUser(
            EmailAddress aEmailAddress,
            ArgumentsUserId aUserID,
            ForeignUserId aContainerId,
            ScreenName aScreenName)
    {
        super(aUserID);
        theEmailAddress = aEmailAddress;
        theContainerId = aContainerId;
        theScreenName = aScreenName;
    }
    
    // ------------------------------------------------------------------------
    /**
     * @return the emailAddress
     */
    public EmailAddress getEmailAddress()
    {
        return theEmailAddress;
    }
    // ------------------------------------------------------------------------
    /**
     * @return the containerId
     */
    public ForeignUserId getContainerId()
    {
        return theContainerId;
    }
    
    // ------------------------------------------------------------------------
    /**
     * @return the sceenName
     */
    public ScreenName getScreenName()
    {
        return theScreenName;
    }

    // ------------------------------------------------------------------------
    public String toString()
    {
        return "User(" + getLongId() + ", " + theContainerId + ", " + 
                theEmailAddress + ", "  + theScreenName + ")";
    }

    
    // ------------------------------------------------------------------------
    public Perspective getDefaultPerspective()
    {
        if (theDefaultPerspective == null)
            theDefaultPerspective = TheArgsStore.i().getDefaultPerspective(this);
        assert theDefaultPerspective != null;
        return theDefaultPerspective;
    }

    
    // ------------------------------------------------------------------------
    public ArgsReadOnlyState getState()
    {
        return TheArgsStore.i().selectState(this);
    }
}
