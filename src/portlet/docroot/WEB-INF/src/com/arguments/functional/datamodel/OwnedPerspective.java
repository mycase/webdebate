/**
 * 
 */
package com.arguments.functional.datamodel;

import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class OwnedPerspective extends Perspective
{
    private final ArgumentsUserId theOwnerId;
    private final PerspectiveName theName;
    
    private ArgumentsUser theOwner;

    // ------------------------------------------------------------------------
    public OwnedPerspective(
            PerspectiveId anId,
            PerspectiveName aName,
            ArgumentsUserId aUserId)
    {
        super(anId);
        theName = aName;
        theOwnerId = aUserId;
    }

    // ------------------------------------------------------------------------
    public OwnedPerspective(
            PerspectiveName aName,
            ArgumentsUserId aUserId)
    {
        this(PerspectiveId.VOLATILE,
                aName, aUserId);
    }

    // ------------------------------------------------------------------------
    @Override
    public ThesisOpinion getOpinion(ThesisId aThesisID)
    {
        return TheArgsStore.i().selectOpinionByThesisPerspective(
                aThesisID, this);
    }

    // ------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return theName + "(" + getOwner().getScreenName()+ ")";
    }

    // ------------------------------------------------------------------------
    @Override
    public ArgumentsUser getOwner()
    {
        if (theOwner == null)
            theOwner = TheArgsStore.i().selectUserById(theOwnerId);
        return theOwner;
    }
    
    // ------------------------------------------------------------------------
    @Override
    public boolean isWritable()
    {
        return true;
    }

    // ------------------------------------------------------------------------
    public ArgumentsUserId getOwnerId()
    {
        return theOwnerId;
    }

    // ------------------------------------------------------------------------
    public PerspectiveName getName()
    {
        return theName;
    }
}
