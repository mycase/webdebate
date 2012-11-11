/**
 * 
 */
package com.arguments.functional.requeststate;

import com.arguments.functional.datamodel.ArgsState;

/**
 * @author mirleau
 *
 */
public class ArgsStatefulRequest extends ArgsStatefulRequest3
{
    private final ArgumentsRequest theRequest;
    
    // ------------------------------------------------------------------------
    public ArgsStatefulRequest(ArgumentsRequest aRequest, ArgsState aState)
    {
        super(aRequest, aState);
        theRequest = aRequest;
    }

    // ------------------------------------------------------------------------
    public ArgsState execute()
    {
        theRequest.execute(getState());
        return getState();
    }
}
