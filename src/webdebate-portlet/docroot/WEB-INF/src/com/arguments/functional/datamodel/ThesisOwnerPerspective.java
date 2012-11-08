/**
 * 
 */
package com.arguments.functional.datamodel;

import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class ThesisOwnerPerspective extends Perspective
{
    
    // ------------------------------------------------------------------------
    /**
     * @param aAnID
     */
    public ThesisOwnerPerspective()
    {
        super(PerspectiveId.getThesisOwner());
    }

    // ------------------------------------------------------------------------
    @Override
    public ThesisOpinion getOpinion(ThesisId aThesisID)
    {
        return TheArgsStore.i().selectOwnerOpinion(aThesisID);
    }

    // ------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return "Variable Thesis Owner";
    }
    
    // ------------------------------------------------------------------------
    public boolean isWritable()
    {
        return false;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public ArgumentsUserId getOwner()
    {
        return null;
    }
}
