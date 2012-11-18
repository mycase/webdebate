/**
 * 
 */
package com.arguments.functional.store.sql;

import java.net.SocketException;
import java.util.List;

import static org.junit.Assert.*;
import com.arguments.functional.datamodel.ArgsState;
import com.arguments.functional.datamodel.ArgsStore;
import com.arguments.functional.datamodel.ArgumentsUser;
import com.arguments.functional.datamodel.ArgumentsUserId;
import com.arguments.functional.datamodel.ForeignUserId;
import com.arguments.functional.datamodel.MPerspective;
import com.arguments.functional.datamodel.OpinionatedThesis;
import com.arguments.functional.datamodel.OwnedPerspective;
import com.arguments.functional.datamodel.Perspective;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.PerspectiveThesisOpinion;
import com.arguments.functional.datamodel.Relation;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.Thesis;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.datamodel.ThesisOpinionId;
import com.arguments.functional.report.ThesisFocusData;
import com.arguments.functional.store.TheArgsStore;
import com.arguments.support.Container;
import com.arguments.support.EmailAddress;
import com.arguments.support.ScreenName;

/**
 * @author mirleau
 *
 */
public class ArgsTestDB implements ArgsStore
{
    
    private final ArgsStore theStore;
    private boolean theIsConnected;

    // ------------------------------------------------------------------------
    public ArgsTestDB(ArgsStore aStore)
    {
        assertNotSame(aStore, this);
        assertNotNull(aStore);
        theStore = aStore;
        theIsConnected = false;
    }
    
    // ------------------------------------------------------------------------
    @Override
    public ArgumentsUser selectUserById(ArgumentsUserId aUserId)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public OpinionatedThesis getThesis(ThesisId aThesisId,
            Perspective aPerspective)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public Thesis selectThesisById(ThesisId aThesisId)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public ThesisOpinion selectOwnerOpinion(ThesisId aThesisID)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public ThesisOpinion selectOpinionByThesisPerspective(ThesisId aThesisID,
            PerspectiveId aUserId)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public List<OpinionatedThesis> getAllTheses(MPerspective aPerspective)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public long getNrOfTheses()
    {
        return 0;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public long getNrOfOpinions()
    {
        return 0;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public ArgsState selectState(ArgumentsUserId aUser)
    {
        ifNotConnectedThrowError();
        return theStore.selectState(aUser);
    }
    
    // ------------------------------------------------------------------------
    private void ifNotConnectedThrowError()
    {
        if (!theIsConnected)
            throw new ArgsSQLStoreException(
                    "TestError",
                    new SocketException("Broken Pipe"));
    }
    
    // ------------------------------------------------------------------------
    @Override
    public void assureConnect()
    {
        theIsConnected = true;
        TheArgsStore.setDB(theStore);
    }

    
    // ------------------------------------------------------------------------
    @Override
    public Relation selectRelationById(RelationId aRelationId)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public Container<Thesis> selectPremiseTree(Thesis aThesis, int aDepth)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public void deleteTestObjects()
    {
        
    }

    
    // ------------------------------------------------------------------------
    @Override
    public ThesisId selectLastTestThesisId()
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public RelationId selectLastTestRelationId()
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public List<PerspectiveThesisOpinion> getOpinionsForThesisId(
            ThesisId aThesisId)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public ThesisFocusData getThesisFocusData(ThesisId aMainThesisId,
            ArgumentsUserId aUserId, MPerspective aPerspectives)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public ArgumentsUser selectUserByForeignId(ForeignUserId aUserId)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public ThesisOpinion selectOpinionById(ThesisOpinionId aAnOpinionID)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public ArgumentsUser getAppUser(EmailAddress aAnEmailAddress,
            ForeignUserId aAnLiferayId, ScreenName aScreenName)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public OwnedPerspective getPerspective(PerspectiveId aPerspectiveId)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public OwnedPerspective getDefaultPerspective(ArgumentsUserId aArgumentsUser)
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    @Override
    public PerspectiveId getThesisOwnerPerspective()
    {
        return null;
    }

    
    // ------------------------------------------------------------------------
    /* (non-Javadoc)
     * @see com.arguments.functional.datamodel.ArgsStore#selectAllOpinionsByThesis(com.arguments.functional.datamodel.ThesisId)
     */
    @Override
    public List<PerspectiveThesisOpinion> selectAllOpinionsByThesis(
            ThesisId aThesisID)
    {
        return null;
    }
}
