/**
 * 
 */
package com.arguments.functional.report;

import java.util.List;

import com.arguments.functional.datamodel.MPerspective;
import com.arguments.functional.datamodel.OpinionatedThesis;

/**
 * @author mirleau
 *
 */
public class ListThesesData
{
    private final List<OpinionatedThesis> theTheses;
    private final MPerspective thePerspectives;

    // ------------------------------------------------------------------------
    public ListThesesData(List<OpinionatedThesis> aTheses,
            MPerspective aPerspective)
    {
        theTheses = aTheses;
        thePerspectives = aPerspective;
    }
    
    // ------------------------------------------------------------------------
    public MPerspective getPerspective()
    {
        return thePerspectives;
    }
    
    // ------------------------------------------------------------------------
    public List<OpinionatedThesis> getTheses()
    {
        return theTheses;
    }
}
