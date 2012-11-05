/**
 * 
 */
package com.arguments.functional.datamodel;

/**
 * @author mirleau
 *
 */
public class ReaderPerspective extends OwnedPerspective
{
    // ------------------------------------------------------------------------
    private ReaderPerspective(ArgumentsUser aUser)
    {
        super(PerspectiveId.READER1, new PerspectiveName("gogo"), aUser);
    }

    // ------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return "Reader(" + getOwner().getScreenName()+ ")";
    }
}
