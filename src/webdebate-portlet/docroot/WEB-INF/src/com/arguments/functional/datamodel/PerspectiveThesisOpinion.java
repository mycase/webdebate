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
implements Comparable<PerspectiveThesisOpinion>
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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime
                * result
                + ((thePerspectiveId == null) ? 0 : thePerspectiveId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PerspectiveThesisOpinion other = (PerspectiveThesisOpinion) obj;
        if (thePerspectiveId == null)
        {
            if (other.thePerspectiveId != null)
                return false;
        } else if (!thePerspectiveId.equals(other.thePerspectiveId))
            return false;
        return true;
    }

    // ------------------------------------------------------------------------
    @Override
    public int compareTo(PerspectiveThesisOpinion anOther)
    {
        int myOrder1 =  Double.compare(getPercentage(), anOther.getPercentage());
        if (myOrder1 != 0) return myOrder1;
        return thePerspectiveId.theID.compareTo(anOther.thePerspectiveId.theID);
    }

}
