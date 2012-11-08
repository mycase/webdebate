/**
 * 
 */
package com.arguments.functional.datamodel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author mirleau
 *
 */
public class Perspective_Tester
{
    // ------------------------------------------------------------------------
    @Test
    public void defaultOpinionForAnyThesisIsNeutral()
    {
        Perspective myPerspective = new Perspective(){

            @Override
            public ThesisOpinion getOpinion(ThesisId aThesisID)
            {
                return new ThesisOpinion();
            }

            @Override
            public ArgumentsUserId getOwner()
            {
                return ArgumentsUserId.BOGUS;
            }

            @Override
            public boolean isWritable()
            {
                return false;
            }
        };
    
        assertEquals(ThesisOpinion.NEUTRAL_OPINION,
                myPerspective.getOpinion(ThesisId.ONE));
    }

    // ------------------------------------------------------------------------
    @Test
    public void constructTrianglePerspective()
    {

    }
}
