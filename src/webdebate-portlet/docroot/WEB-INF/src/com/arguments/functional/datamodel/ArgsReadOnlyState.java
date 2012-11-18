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
    public abstract MPerspective getPerspectives();

    // ------------------------------------------------------------------------
    public abstract PerspectiveId getPerspectiveId();

}