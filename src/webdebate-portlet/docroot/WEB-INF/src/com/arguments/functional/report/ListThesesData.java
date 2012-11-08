/**
 * 
 */
package com.arguments.functional.report;

import java.util.List;

import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.Perspective;

/**
 * @author mirleau
 *
 */
public class ListThesesData
{
    private final List<OpinionatedThesis> theTheses;
    private final Perspective thePerspective;

    // ------------------------------------------------------------------------
    public ListThesesData(List<OpinionatedThesis> aTheses,
            Perspective aPerspective)
    {
        theTheses = aTheses;
        thePerspective = aPerspective;
    }
    
    // ------------------------------------------------------------------------
    public Perspective getPerspective()
    {
        return thePerspective;
    }
    
    // ------------------------------------------------------------------------
    public List<OpinionatedThesis> getTheses()
    {
        return theTheses;
    }
}
