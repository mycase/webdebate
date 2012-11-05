/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public abstract class Perspective extends PerspectiveId
{
    
    // ------------------------------------------------------------------------
    public Perspective(Long aAnID)
    {
        super(aAnID);
    }
    
    // ------------------------------------------------------------------------
    public Perspective(PerspectiveId anId)
    {
        super(anId);
    }

    // ------------------------------------------------------------------------
    public Perspective()
    {
        super(PerspectiveId.VOLATILE);
    }

    // ------------------------------------------------------------------------
    public abstract ThesisOpinion getOpinion(ThesisId aThesisID);
    
    // ------------------------------------------------------------------------
    public static Perspective getPerspective(Long aPerspectiveId)
    {
        if (aPerspectiveId == 1L) return new ThesisOwnerPerspective();
        
        throw new AssertionError();
    }

    // ------------------------------------------------------------------------
    public abstract ArgumentsUserId getOwner();
}
