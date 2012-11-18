package com.arguments.functional.report;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import com.arguments.functional.datamodel.MPerspective;
import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.Perspective;
import com.arguments.functional.datamodel.PerspectiveThesisOpinion;
import com.arguments.functional.datamodel.RelatedThesis;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.store.TheArgsStore;


public class ThesisFocusData
{
    private final ThesisId theThesisId;
    private OpinionatedThesis theMainThesis;
    private final List<RelatedThesis<OpinionatedThesis>> thePremises = new ArrayList<>();
    private List<RelatedThesis<OpinionatedThesis>> theConclusions = new ArrayList<>();
    private final MPerspective thePerspectives;
    private boolean theMainThesisOwned;
    private boolean thePerspectiveOwned;

    // ------------------------------------------------------------------------
    public ThesisFocusData(ThesisId aMainThesisId, MPerspective aPerspective)
    {
        theThesisId = aMainThesisId;
        thePerspectives = aPerspective;
    }

    // ------------------------------------------------------------------------
    public void setMainThesis(OpinionatedThesis aThesis)
    {
        assertNotNull( aThesis );
        theMainThesis = aThesis;
    }

    // ------------------------------------------------------------------------
    public void addPremise(RelatedThesis<OpinionatedThesis> aPremise)
    {
        thePremises.add(aPremise);
    }

    // ------------------------------------------------------------------------
    public void addConclusion(RelatedThesis<OpinionatedThesis> aPremise)
    {
        theConclusions.add(aPremise);
    }

    // ------------------------------------------------------------------------
    public List<RelatedThesis<OpinionatedThesis>> getPremises()
    {
        return thePremises;
    }

    // ------------------------------------------------------------------------
    public void check()
    {
        assertNotNull( theMainThesis );
    }

    // ------------------------------------------------------------------------
    public OpinionatedThesis getMainThesis()
    {
        assertNotNull( theMainThesis );
        return theMainThesis;
    }

    // ------------------------------------------------------------------------
    public List<RelatedThesis<OpinionatedThesis>> getDeductions()
    {
        return theConclusions;
    }
    
    // ------------------------------------------------------------------------
    public List<RelatedThesis<OpinionatedThesis>>
    getPremisesSortedByStrength()
    {
        ArgumentStrengthComparator mySorter =
                new ArgumentStrengthComparator(getMainThesis().getOpinion());
        List<RelatedThesis<OpinionatedThesis>> myPremises = getPremises();
        Collections.sort(myPremises, mySorter);
        return myPremises;
    }
    
    // ------------------------------------------------------------------------
    public MPerspective getPerspectives()
    {
        return thePerspectives;
    }
    
    // ------------------------------------------------------------------------
    public boolean getMainThesisOwned()
    {
        return theMainThesisOwned;
    }

    // ------------------------------------------------------------------------
    public boolean getPerspectiveOwned()
    {
        return thePerspectiveOwned;
    }

    // ------------------------------------------------------------------------
    public void setMainThesisOwned(boolean aMainThesisOwned)
    {
        theMainThesisOwned = aMainThesisOwned;
    }
    
    // ------------------------------------------------------------------------
    public void setPerspectiveOwned(boolean aValue)
    {
        thePerspectiveOwned = aValue;
    }
    
    // ------------------------------------------------------------------------
    public Collection<PerspectiveThesisOpinion> getDifferentPerspectives()
    {
        List<PerspectiveThesisOpinion> myOpinions =
                TheArgsStore.i().selectAllOpinionsByThesis(theThesisId);
        
        return new TreeSet<>(myOpinions);
    }
}
