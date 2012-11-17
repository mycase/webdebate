/**
 * 
 */
package com.arguments.functional.datamodel;

import com.arguments.functional.store.TheArgsStore;

/**
 * @author mirleau
 *
 */
public class PerspectiveId
{
    protected final Long theID;

    public static final PerspectiveId VOLATILE = new PerspectiveId(-3L);
    private static PerspectiveId THESIS_OWNER2 = null;
    public static final PerspectiveId P1 = new PerspectiveId(1L);
    public static final PerspectiveId P3 = new PerspectiveId(3L);
    public static final PerspectiveId P4 = new PerspectiveId(4L);
    
    // ------------------------------------------------------------------------
    public PerspectiveId(Long anID)
    {
        theID = anID;
    }
    
    // ------------------------------------------------------------------------
    public static String perspectiveIdExplanation(Perspective aDefaultPerspective)
    {
        return             "<ul>"+
                "<li> " + getThesisOwner().getLongID() + " = Every seperate thesis owner's perspective </li>" +
                "<li> " + aDefaultPerspective.getIdString() + " = Yours = "+aDefaultPerspective+"</li></ul>";
    }
    
    // ------------------------------------------------------------------------
    public boolean isWritable()
    {
        return !(equals(VOLATILE) || equals(getThesisOwner()) );
    }
    
    // ------------------------------------------------------------------------
    public static PerspectiveId getThesisOwner()
    {
        if (THESIS_OWNER2 == null)
            THESIS_OWNER2 = TheArgsStore.i().getThesisOwnerPerspective();
        return THESIS_OWNER2;
    }
    
    // ------------------------------------------------------------------------
    public PerspectiveId(PerspectiveId aPerspectiveID)
    {
        this(aPerspectiveID.theID);
    }
    
    // ------------------------------------------------------------------------
    public PerspectiveId(String anIdString)
    {
        this(Long.parseLong(anIdString));
    }

    // ------------------------------------------------------------------------
    @Override
    public String toString()
    {
        return ""+theID;
    }

    // ------------------------------------------------------------------------
    @Override
    public boolean equals(Object anOther)
    {
        if (anOther == null) return false;
        if (!(anOther instanceof PerspectiveId)) return false;
        return theID.equals(((PerspectiveId)anOther).theID);
    }
    
    // ------------------------------------------------------------------------
    @Override
    public int hashCode()
    {
        return theID.hashCode();
    }

    // ------------------------------------------------------------------------
    public String getIdString()
    {
        return "" + theID;
    }
    
    // ------------------------------------------------------------------------
    public Long getLongID()
    {
        return theID;
    }

    
    // ------------------------------------------------------------------------
    public static PerspectiveId parsePerspectiveId(
            String anIdString)
    {
        if (anIdString == null) return null;
        return new PerspectiveId(Long.parseLong(anIdString));
    }
}
