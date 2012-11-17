/**
 * 
 */
package com.arguments.functional.report;

import java.util.Comparator;

import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.RelatedThesis;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.support.Logger;

/**
 * @author mirleau
 *
 */
public class ArgumentStrengthComparator implements Comparator<RelatedThesis<OpinionatedThesis>>
{
    private final ThesisOpinion theTargetOpinion;
    
    public ArgumentStrengthComparator(ThesisOpinion aTargetOpinion)
    {
        Logger.log("target opinion: " + aTargetOpinion.getMinusOneToOne());
        theTargetOpinion = aTargetOpinion;
    }

    // ------------------------------------------------------------------------
    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(RelatedThesis<OpinionatedThesis> aO1, RelatedThesis<OpinionatedThesis> aO2)
    {
        ThesisOpinion myImplied1 =
                RelatedThesis.getImpliedBelief(aO1.getThesis().getOpinion(), aO1.getRelation());
        
        ThesisOpinion myImplied2 =
                RelatedThesis.getImpliedBelief(aO2.getThesis().getOpinion(), aO2.getRelation());

        int myTargetSign = (int) Math.signum(theTargetOpinion.getMinusOneToOne()) ;
        if (myTargetSign == 0) myTargetSign = 1;
        //Logger.log("(-1 ~ 1) = " + Double.compare(-1d, 1d));
        int myReturnValue =  - myTargetSign * Double.compare(
                    myImplied1.getMinusOneToOne(),
                    myImplied2.getMinusOneToOne());
        //Logger.log("Return value:"+myReturnValue);
        return myReturnValue;
    }
}
