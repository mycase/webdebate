/**
 * 
 */
package com.arguments.functional.datamodel;

import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class PerspectiveThesisOpinion extends ThesisOpinion
{
    private final PerspectiveId thePerspectiveId;
    
    // ------------------------------------------------------------------------
    private PerspectiveThesisOpinion(
            double anOpinionDouble, PerspectiveId aPerspectiveId)
    {
        super(anOpinionDouble);
        thePerspectiveId = aPerspectiveId;
    }

    // ------------------------------------------------------------------------
    public static PerspectiveThesisOpinion newPersOpinionByProbability(
            double anOpinionDouble, PerspectiveId aPerspectiveId)
    {
        return new PerspectiveThesisOpinion(anOpinionDouble, aPerspectiveId);
    }

    // ------------------------------------------------------------------------
    public PerspectiveId getPerspectiveId()
    {
        return thePerspectiveId;
    }

    // ------------------------------------------------------------------------
    public OwnedPerspective getPerspective()
    {
        return TheArgsStore.i().getPerspective(thePerspectiveId);
    }
}
