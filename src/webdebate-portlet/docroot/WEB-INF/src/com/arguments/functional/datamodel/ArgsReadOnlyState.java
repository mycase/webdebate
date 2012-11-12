/**
 * 
 */
package com.arguments.functional.datamodel;

import com.arguments.functional.requeststate.StateChange;

/**
 * @author mirleau
 *
 */
public interface ArgsReadOnlyState
{

    // ------------------------------------------------------------------------
    /**
     * @return theThesisId
     */
    public abstract ThesisId getThesisId();

    // ------------------------------------------------------------------------
    /**
     * @return
     */
    public abstract StateChange getStateChange();

    // ------------------------------------------------------------------------
    public abstract RelationId getLinkId();

    // ------------------------------------------------------------------------
    public abstract Perspective getPerspective();

    // ------------------------------------------------------------------------
    public abstract PerspectiveId getPerspectiveId();

}