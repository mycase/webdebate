package com.arguments.functional.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

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
    private final List<RelatedThesis> thePremises = new ArrayList<>();
    private List<RelatedThesis> theDeductions = new ArrayList<>();
    private final Perspective thePerspective;
    private boolean theMainThesisOwned;
    private boolean thePerspectiveOwned;

    // ------------------------------------------------------------------------
    public ThesisFocusData(ThesisId aMainThesisId, Perspective aPerspective)
    {
        theThesisId = aMainThesisId;
        thePerspective = aPerspective;
    }

    // ------------------------------------------------------------------------
    public ThesisId getThesisId()
    {
        return theThesisId;
    }

    // ------------------------------------------------------------------------
    public void setMainThesis(OpinionatedThesis aThesis)
    {
        assert aThesis != null;
        theMainThesis = aThesis;
    }

    // ------------------------------------------------------------------------
    public void addPremise(RelatedThesis aPremise)
    {
        thePremises.add(aPremise);
    }

    // ------------------------------------------------------------------------
    public void addDeduction(RelatedThesis aPremise)
    {
        theDeductions.add(aPremise);
    }

    // ------------------------------------------------------------------------
    public List<RelatedThesis> getPremises()
    {
        return thePremises;
    }

    // ------------------------------------------------------------------------
    public void check()
    {
        assert theMainThesis != null;
    }

    // ------------------------------------------------------------------------
    public OpinionatedThesis getMainThesis()
    {
        assert theMainThesis != null;
        return theMainThesis;
    }

    // ------------------------------------------------------------------------
    public List<RelatedThesis> getDeductions()
    {
        return theDeductions;
    }
    
    // ------------------------------------------------------------------------
    public List<RelatedThesis>
    getPremisesSortedByStrength()
    {
        ArgumentStrengthComparator mySorter =
                new ArgumentStrengthComparator(getMainThesis().getOpinion());
        List<RelatedThesis> myPremises = getPremises();
        Collections.sort(myPremises, mySorter);
        return myPremises;
    }
    
    // ------------------------------------------------------------------------
    public Perspective getPerspective()
    {
        return thePerspective;
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
