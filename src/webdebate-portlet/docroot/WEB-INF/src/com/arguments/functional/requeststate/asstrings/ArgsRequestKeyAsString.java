/**
 * 
 */
package com.arguments.functional.requeststate.asstrings;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;

import com.arguments.functional.requeststate.ArgsRequestKey;
import com.arguments.functional.requeststate.ProtocolMap;
import com.arguments.support.CgiParameterMap;
import com.arguments.support.CollectionTransforms;

/**
 * @author mirleau
 *
 */
public class ArgsRequestKeyAsString{
    
    private static Map<String, ArgsRequestKey> theString2Enum = new HashMap<>();
    private static Map<ArgsRequestKey, String> theEnum2String = new HashMap<>();

    static
    {
        theEnum2String.put(ArgsRequestKey.THESIS_ID, "thesisId");
        theEnum2String.put(ArgsRequestKey.RELATION_ID, "relationId");
        theEnum2String.put(ArgsRequestKey.NEW_IF_TRUE_PERCENTAGE_RELEVANCE, "newIfTrueRelevance");
        theEnum2String.put(ArgsRequestKey.NEW_IF_FALSE_PERCENTAGE_RELEVANCE, "newIfFalseRelevance");
        theEnum2String.put(ArgsRequestKey.NEW_LINK_SOURCE_ID, "newLinkSourceId");
        theEnum2String.put(ArgsRequestKey.NEW_LINK_TARGET_ID, "newLinkTargetId");
        theEnum2String.put(ArgsRequestKey.NEW_THESIS_TEXT, "newThesisText");
        theEnum2String.put(ArgsRequestKey.NEW_THESIS_OPINION, "newThesisOpinion");
        theEnum2String.put(ArgsRequestKey.NEW_THESIS_OPINION2, "newThesisOpinion2");
        theEnum2String.put(ArgsRequestKey.THESIS_TEXT, "thesisText");
        theEnum2String.put(ArgsRequestKey.THESIS_OPINION, "thesisOpinion");
        theEnum2String.put(ArgsRequestKey.PREMISE_TEXT, "premiseText");
        theEnum2String.put(ArgsRequestKey.PREMISE_IF_TRUE_WEIGHT, "premiseIfTrueWeight");
        theEnum2String.put(ArgsRequestKey.PREMISE_IF_FALSE_WEIGHT, "premiseIfFalseWeight");
        theEnum2String.put(ArgsRequestKey.PREMISE_OPINION, "premiseOpinion");
        theEnum2String.put(ArgsRequestKey.SET_PERSPECTIVE_ID, "perspectiveId");
        theEnum2String.put(ArgsRequestKey.ADD_PERSPECTIVE_ID, "addPerspectiveId");
        theEnum2String.put(ArgsRequestKey.REMOVE_PERSPECTIVE_ID, "removePerspectiveId");
        theEnum2String.put(ArgsRequestKey.PERSPECTIVE2_ID, "perspective2Id");

        theString2Enum = CollectionTransforms.invert(theEnum2String);
    }
    
    // ------------------------------------------------------------------------
    public static void check()
    {
        assertEquals(ArgsRequestKey.values().length, theEnum2String.keySet().size());
        assertEquals(ArgsRequestKey.values().length, theEnum2String.values().size());
        assertEquals(ArgsRequestKey.values().length, theString2Enum.keySet().size());
        assertEquals(ArgsRequestKey.values().length, theString2Enum.values().size());
    }
    
    // ------------------------------------------------------------------------
    public static String s(ArgsRequestKey aKey)
    {
        return theEnum2String.get(aKey);
    }
    
    // ------------------------------------------------------------------------
    public static ProtocolMap getProtocolMap()
    {
        return new ProtocolMap(theEnum2String);
    }
    
    // ------------------------------------------------------------------------
    public static ProtocolMap getParameterMap(CgiParameterMap aParameterMap)
    {
        assertNotSame(aParameterMap, null);
        Map<ArgsRequestKey, String> myMap =
                CollectionTransforms.mapComposeInv(aParameterMap.getSimpleMap(), theString2Enum);
        return new ProtocolMap(myMap);
    
    }
}
