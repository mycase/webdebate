package com.arguments.functional.datamodel;

import com.arguments.functional.store.TheArgsStore;

public class Thesis extends ThesisId
{
    private final ThesisText theSummary;
    private final ArgumentsUserId theOwnerID;

    private ArgumentsUser theOwner;
    
    // ------------------------------------------------------------------------
    public Thesis(
            ThesisId aThesisID,
            ThesisText aSummary,
            ArgumentsUserId aAnOwnerID)
    {
        super(aThesisID);
        theSummary = aSummary;
        theOwnerID = aAnOwnerID;
    }

    // ------------------------------------------------------------------------
    public ArgumentsUserId getOwnerID()
    {
        return theOwnerID;
    }

    // ------------------------------------------------------------------------
    public ThesisText getSummary()
    {
        return theSummary;
    }

    // ------------------------------------------------------------------------
    public ThesisId getID()
    {
        return this;
    }
    
    // ------------------------------------------------------------------------
    public ArgumentsUser getOwner()
    {
        if (theOwner == null)
            theOwner = TheArgsStore.i().selectUserById(new ArgumentsUserId(theOwnerID));
        return theOwner;
    }
}
