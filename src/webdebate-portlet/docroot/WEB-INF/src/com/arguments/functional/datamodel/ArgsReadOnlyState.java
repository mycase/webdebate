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
    public abstract ThesisId getThesisId();

    // ------------------------------------------------------------------------
    public abstract StateChange getStateChange();

    // ------------------------------------------------------------------------
    public abstract RelationId getLinkId();

    // ------------------------------------------------------------------------
    public abstract Perspective getPerspective();

    // ------------------------------------------------------------------------
    public abstract PerspectiveId getPerspectiveId();

}