/**
 * 
 */
package com.arguments.functional.store.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.arguments.functional.datamodel.ArgumentsUserId;
import com.arguments.functional.datamodel.ForeignUserId;
import com.arguments.functional.datamodel.PerspectiveId;
import com.arguments.functional.datamodel.PerspectiveName;
import com.arguments.functional.datamodel.RelationId;
import com.arguments.functional.datamodel.Relevance;
import com.arguments.functional.datamodel.ThesisId;
import com.arguments.functional.datamodel.ThesisOpinion;
import com.arguments.functional.datamodel.ThesisOpinionId;
import com.arguments.functional.datamodel.ThesisText;
import com.arguments.support.EmailAddress;
import com.arguments.support.Logger;
import com.arguments.support.ScreenName;
import com.arguments.support.sql.DBColumn;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;


/**
 * @author mirleau
 * 
 */

class ArgsDB
{
    private final ArgsQuery theFixedQuery;
    private final PreparedStatement theQuery;
    private final Map<Integer, Object> theSqlArgumentMap = new HashMap<>();

    public enum Patch
    {
        ID("ID");
        
        public static final String t = "Patch";
        public final DBColumn c;
        public final String f;
        
        private Patch(String aName)
        {
            c = new DBColumn(aName, t);
            f = c.getLongName();
        }
        
        @Override
        public String toString()
        {
            return c.getName();
        }
    }

    public enum Opinion
    {
        ID("ID"),
        THESIS_ID("StatementID"),
        PERSPECTIVE_ID1("UserID"), // OWNER_ID
        LEVEL("Level");
        
        public static final String t = "Opinions";
        public final DBColumn c;
        public final String f;
        
        private Opinion(String aName)
        {
            c = new DBColumn(aName, t);
            f = c.getLongName();
        }
        
        @Override
        public String toString()
        {
            return c.getName();
        }
    }

    public enum Perspective
    {
        ID("ID"),
        NAME("Name"),
        TYPE("Type"),
        OWNER_ID("OwnerID");
        
        public static final String t = "Perspective";
        public final DBColumn c;
        public final String f;

        private Perspective(String aName)
        {
            c = new DBColumn(aName, t);
            f = c.getLongName();
        }
        
        @Override
        public String toString()
        {
            return c.getName();
        }
    }

    public enum Relation
    {
        ID("ID"),
        THESIS_1_ID("Statement1ID"),
        THESIS_2_ID("Statement2ID"),
        RELATION_TYPE_ID("RelationTypeID"),
        WEIGHT("Weight"),
        OWNER_ID("OwnerID"),
        IMPLICATION21("implication21_");

        public static final String t = "Relation";
        public final DBColumn c;
        public final String f;

        private Relation(String aName)
        {
            c = new DBColumn(aName, t);
            f = c.getLongName();
        }
        
        @Override
        public String toString()
        {
            return c.getName();
        }
    }

    public enum State
    {
        USER_ID("UserID"), // OWNER_ID
        THESIS_ID("StatementID"),
        OPINION_STRATEGY_ID("OpinionStrategyID");
        
        public static final String t = "State";
        public final DBColumn c;
        public final String f;

        private State(String aName)
        {
            c = new DBColumn(aName, t);
            f = c.getLongName();
        }
        
        @Override
        public String toString()
        {
            return c.getName();
        }
    }

    public enum ActivePerspectives
    {
        ID("ID"),
        USER_ID("UserID"),
        PERSPECTIVE_ID("PerspectiveID");
        
        public static final String t = "ActivePerspectives";
        public final DBColumn c;
        public final String f;

        private ActivePerspectives(String aName)
        {
            c = new DBColumn(aName, t);
            f = c.getLongName();
        }
        
        @Override
        public String toString()
        {
            return c.getName();
        }
    }

    public enum Thesis
    {
        ID("ID"),
        SUMMARY("Summary"),
        COMMENT("Comment"),
        OWNER_ID("Owner");
        
        public static final String t = "Statements";
        public final DBColumn c;
        public final String f;

        private Thesis(String aName)
        {
            c = new DBColumn(aName, t);
            f = c.getLongName();
        }
        
        @Override
        public String toString()
        {
            return c.getName();
        }
    }

    public enum User
    {
        ID("ID"),
        EMAIL("email"),
        CONTAINER_ID("containerId"), // FOREIGN_ID
        SCREEN_NAME("screen_name_");
        
        public static final String t = "User";
        public final DBColumn c;
        public final String f;

        private User(String aName)
        {
            c = new DBColumn(aName, t);
            f = c.getLongName();
        }
        
        @Override
        public String toString()
        {
            return c.getName();
        }
    }

    final static String DELETE_FROM = "DELETE FROM";
    final static String INSERT_INTO = "INSERT INTO";
    final static String SELECT = "SELECT";
    final static String UPDATE = "UPDATE";
    final static String VALUES = "VALUES";
    final static String WHERE = "WHERE";
    final static String WRITE = "WRITE";
    final static String FROM = "FROM";
    final static String AND = "AND";
    final static String OR = "OR";
    final static String ON = "ON";
    final static String IF = "IF";
    final static String LEFT_JOIN = "LEFT JOIN";
    final static String SET = "SET";
    final static String AS = "AS";
    final static String LOCK_TABLE = "LOCK TABLE";
    
    final static DBColumn NR_OF_OPINIONS = new DBColumn("NrOfOpinions");
    final static DBColumn NR_OF_THESES = new DBColumn("NrOfTheses");
    final static DBColumn COUNT = new DBColumn("count");
    final static DBColumn LEVEL_ = new DBColumn("Level");
    final static DBColumn RELATION_ID = new DBColumn("RelationID");
    final static DBColumn PERSPECTIVE_ID_ = new DBColumn("PerspectiveID");
    final static DBColumn OWNER_ID_ = new DBColumn("OwnerID");

    public enum ArgsQuery
    {
        /* @formatter:off */
        DELETE_OPINIONS_BY_PERSPECTIVEID(DELETE_FROM, Opinion.t, WHERE, Opinion.PERSPECTIVE_ID1, "= ?"),
        DELETE_RELATIONS_BY_USERID(DELETE_FROM, Relation.t, WHERE, Relation.OWNER_ID , "= ?"),
        DELETE_STATE_BY_USERID(DELETE_FROM, State.t, WHERE, State.USER_ID, "= ?"),
        DELETE_THESES_BY_USERID(DELETE_FROM, Thesis.t, WHERE, Thesis.OWNER_ID, "= ?"),
        DELETE_PERSPECTIVES_BY_USERID(DELETE_FROM, Perspective.t, WHERE, Perspective.OWNER_ID, "= ?"),

        DELETE_USER_BY_ID(DELETE_FROM, User.t, WHERE, User.ID, "= ?"),

        COUNT_ALL_THESES1(SELECT, "count(*)", AS, NR_OF_THESES, FROM, Thesis.t),
        COUNT_ALL_OPINIONS(SELECT, "count(*)", AS, NR_OF_OPINIONS, FROM, Opinion.t),
        COUNT_OPINION_BY_THESIS_PESPECTIVE(SELECT, "count(*)", AS, NR_OF_OPINIONS, FROM, Opinion.t,
                WHERE, Opinion.THESIS_ID, "= ?",AND, Opinion.PERSPECTIVE_ID1, "= ?"),
        INSERT_OPINION1(INSERT_INTO, Opinion.t, VALUES, "(0, ?, ?, ?)"),
        INSERT_PERSPECTIVE(INSERT_INTO, Perspective.t, VALUES, "( 0, ?, 1, ?)"),
        INSERT_RELATION(INSERT_INTO, Relation.t, VALUES, "( 0, ?, ?, NULL, ?, ?, ?)"),
        INSERT_STATE1(INSERT_INTO, State.t, VALUES, "( ?, ?, ?)"),
        INSERT_THESIS(INSERT_INTO, Thesis.t, VALUES, "( 0, ?, NULL, ?)"),
        INSERT_USER(INSERT_INTO, User.t, VALUES, "( 0, ?, ?, ?)"),
        LOCK_OPINION_WRITE(LOCK_TABLE, Opinion.t, WRITE),
        LOCK_THESIS_WRITE(LOCK_TABLE, Thesis.t, WRITE),
        LOCK_USER_WRITE(LOCK_TABLE, User.t, WRITE),
        MAX_OPINION_ID(SELECT, "max(", Opinion.ID, ")", AS, COUNT, FROM, Opinion.t),
        MAX_THESIS_ID(SELECT, "max(", Thesis.ID,")", AS, COUNT, FROM, Thesis.t),
        MAX_USER_ID(SELECT, "max(", User.ID, ")", AS, COUNT, FROM, User.t),
        SELECT_CONCLUSION(
                SELECT,
                    Relation.ID.f, AS, commas(RELATION_ID,
                    Relation.THESIS_1_ID, Relation.THESIS_2_ID, Thesis.SUMMARY, Relation.WEIGHT,
                    Relation.IMPLICATION21, Thesis.OWNER_ID), AS, OWNER_ID_,
        		FROM, commas(Thesis.t, Relation.t),
        		WHERE,
        		    Thesis.ID.f, "=", Relation.THESIS_2_ID, AND, Relation.THESIS_1_ID, "= ?"),
        SELECT_PREMISE(
                SELECT,
                    Relation.ID.f, AS, commas(RELATION_ID,
                    Relation.THESIS_1_ID, Relation.THESIS_2_ID, Thesis.SUMMARY, Relation.WEIGHT,
                    Relation.IMPLICATION21, Thesis.OWNER_ID), AS, OWNER_ID_,
        		FROM, commas(Thesis.t, Relation.t),
        		WHERE, Thesis.ID.f, "=", Relation.THESIS_1_ID, AND, Relation.THESIS_2_ID, "= ?"),
        SELECT_RELATION_BY_ID(SELECT, "*", FROM, Relation.t, WHERE, Relation.ID, "= ?"),
        SELECT_GIVEN_OPINIONS_BY_THESIS_ID(SELECT, "*", FROM, Opinion.t, WHERE, Opinion.THESIS_ID, "= ?"), 
        SELECT_USER_BY_FOREIGN_ID(SELECT, "*", FROM, User.t, WHERE, User.CONTAINER_ID, "= ?"),
        SELECT_USER_BY_EMAIL_OR_FOREIGN_ID_OR_SCREENNAME(
                SELECT, "*", FROM, User.t, WHERE, User.EMAIL, "= ?", OR, User.CONTAINER_ID, "= ?", OR, User.SCREEN_NAME, "= ?"),
        SELECT_USER_BY_ID(SELECT, "*", FROM, User.t, WHERE, User.ID, "= ?"),
        SELECT_OPINION_BY_ID(SELECT, "*", FROM, Opinion.t, WHERE, Opinion.ID, "= ?"),
        SELECT_OPINION_BY_THESIS_PERSPECTIVE_ID1(SELECT, "*", FROM, Opinion.t, WHERE, Opinion.THESIS_ID, "= ?",
                AND, Opinion.PERSPECTIVE_ID1, "= ?"),
        SELECT_ALL_OPINIONS_BY_THESIS_ID_(SELECT,
                "0", AS, Opinion.ID, ",",
                "?", AS, Opinion.THESIS_ID, ",",
                Perspective.ID.c.getLongName(), AS, Opinion.PERSPECTIVE_ID1, ",",
                "IF(",Opinion.LEVEL, "is null, ?, ", Opinion.LEVEL, ")", AS, Opinion.LEVEL,
                FROM, Perspective.t, LEFT_JOIN, Opinion.t,
                ON, Perspective.ID.c.getLongName(), "=", Opinion.PERSPECTIVE_ID1,
                AND, Opinion.THESIS_ID ," = ?",
                AND, Perspective.TYPE, " = 1"),
        SELECT_PATCH(SELECT, "MAX(", Patch.ID, ")", AS, Patch.ID,FROM, Patch.t),
        SELECT_PERSPECTIVE_BY_NAME(SELECT, "*", FROM, Perspective.t, WHERE, Perspective.NAME , " = ? "),
        SELECT_PERSPECTIVE_BY_NAME_USERID1(SELECT, "*", FROM, Perspective.t, WHERE, Perspective.NAME , " = ? ", AND,
                Perspective.OWNER_ID, " = ?"),
        SELECT_PERSPECTIVE_BY_USERID1(SELECT, "*", FROM, Perspective.t, WHERE, Perspective.OWNER_ID, " = ?"),
        SELECT_PERSPECTIVE_BY_ID(SELECT, "*", FROM, Perspective.t, WHERE, Perspective.ID , " = ? "),
        SELECT_STATE_BY_USERID(SELECT, "*", FROM, State.t, WHERE, State.USER_ID, "= ?"),
        SELECT_LAST_TEST_THESIS(SELECT, "*", FROM, Thesis.t, WHERE, Thesis.OWNER_ID, "= 2", "ORDER BY", Thesis.ID, "DESC LIMIT 1"),
        SELECT_LAST_TEST_RELATION(SELECT, "*", FROM, Relation.t, WHERE, Relation.OWNER_ID, "= 2", "ORDER BY", Relation.ID, "DESC LIMIT 1"),
        SELECT_THESIS_BY_ID(SELECT, "*", FROM, Thesis.t, WHERE, Thesis.ID, "= ?"),
        SELECT_THESIS_ALL(SELECT, "*", FROM, Thesis.t),
        UNLOCK_TABLES("UNLOCK TABLES"),
        UPDATE_RELATION(
                UPDATE, Relation.t,
                    SET,
                        Relation.WEIGHT, "= ?,",
                        Relation.IMPLICATION21, "= ?,",
                        Relation.THESIS_2_ID, "= ?",
                    WHERE,
                        Relation.ID, "=?", AND, Relation.OWNER_ID, "= ?"),
        UPDATE_STATE1(
                UPDATE, State.t,
                    SET,
                        State.THESIS_ID, "= ?,",
                        State.OPINION_STRATEGY_ID, " = ?",
                    WHERE, State.USER_ID, "= ?"),
        UPDATE_THESIS(UPDATE, Thesis.t, SET, Thesis.SUMMARY, "= ?", WHERE, Thesis.ID, "=?", AND, Thesis.OWNER_ID, "= ?"),
        UPDATE_OPINION_BY_THESIS_PERSPECTIVE1(UPDATE, Opinion.t, SET, Opinion.LEVEL, "= ?", WHERE, Opinion.THESIS_ID, "= ?",
                AND, Opinion.PERSPECTIVE_ID1, "= ?"),
                UPDATE_USER_SET_FOREIGN_ID(UPDATE, User.t, SET, User.CONTAINER_ID, "= ?", WHERE, User.ID, "= ?"),
                UPDATE_USER_SET_SCREEN_NAME(UPDATE, User.t, SET, User.SCREEN_NAME, "= ?", WHERE, User.ID, "= ?"),
                UPDATE_USER_SET_EMAIL(UPDATE, User.t, SET, User.EMAIL, "= ?", WHERE, User.ID, "= ?"),
        PATCH_0A("DROP TABLE IF EXISTS Patch"),
        PATCH_0B("CREATE TABLE Patch ("+
                "ID int(10) NOT NULL,"+
                "PRIMARY KEY  (ID)" +
                 ") ENGINE=MyISAM DEFAULT CHARSET=latin1;"),
        INSERT_PATCH(INSERT_INTO, Patch.t, VALUES, "(?)"),
        PATCH_1A("CREATE  TABLE IF NOT EXISTS ActivePerspectives (\n" + 
                "  ID INT(10) NOT NULL AUTO_INCREMENT,\n" + 
                "  UserID INT(10) NOT NULL ,\n" + 
                "  PerspectiveID INT(10) NOT NULL ,\n" + 
                "  PRIMARY KEY (ID) ,\n" + 
                "  INDEX fk_ActivePerspectives_Perspective1 (PerspectiveID ASC) ,\n" + 
                "  CONSTRAINT fk_ActivePerspectives_Perspective1\n" + 
                "    FOREIGN KEY (PerspectiveID )\n" + 
                "    REFERENCES Perspective (ID )\n" + 
                "    ON DELETE NO ACTION\n" + 
                "    ON UPDATE NO ACTION,\n" + 
                "  CONSTRAINT fk_ActivePerspectives_User1\n" + 
                "    FOREIGN KEY (UserID )\n" + 
                "    REFERENCES User (ID )\n" + 
                "    ON DELETE NO ACTION\n" + 
                "    ON UPDATE NO ACTION)\n" + 
                "ENGINE = InnoDB\n" + 
                "DEFAULT CHARACTER SET = latin1\n" + 
                "COLLATE = latin1_swedish_ci\n"),
                PATCH_1B("insert into ActivePerspectives select 0, UserID, OpinionStrategyID from State;")
                ; /* @formatter:on */
        private final String theText;

        // ------------------------------------------------------------------------
        /**
         * @return the text
         */
        public ArgsDB ps()
        {
            return new ArgsDB(this);
        }

        // ------------------------------------------------------------------------
        private String
        getText()
        {
            return theText;
        }
        
        // ------------------------------------------------------------------------
        /**
         * @param aText
         */
        private ArgsQuery(Object... aSegments)
        {
            theText = StringUtils.join(aSegments, " ");
        }

        // ------------------------------------------------------------------------
        /**
         * @param aText
         */
        private static String commas(Object... aSegments)
        {
            return StringUtils.join(aSegments, ", ");
        }
    }

    // ------------------------------------------------------------------------
    /**
     * @param anArgsQuery
     */
    private ArgsDB(ArgsQuery anArgsQuery)
    {
        theFixedQuery = anArgsQuery;

        try
        {
            theQuery = ArgsSQLStoreConnection
                    .getPreparedQuery(theFixedQuery.theText);
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }
    }

    // ------------------------------------------------------------------------
    ResultSet executeQuery()
    {
        return executeQuery(10);
    }
    
    // ------------------------------------------------------------------------
    ResultSet executeQuery(int aCount)
    {
        try
        {
            return executeQueryNoCatch();
        }
        catch (MySQLSyntaxErrorException anException)
        {
            throw new ArgsSQLStoreException(anException);
        }
        catch (SQLException anException)
        {
            System.err.println(anException.getClass());
            if (aCount != 0)
            {
                Logger.logAlways("Failed query, aCount = " + aCount +
                        ", SQLexception message  = " + anException.getMessage());
                try
                {
                    Thread.sleep(1000/aCount);
                }
                catch (InterruptedException anException1)
                {
                    anException1.printStackTrace();
                }
                return executeQuery(aCount -1);
            }
            throw new ArgsSQLStoreException("Can't execute this query:\n"
                    + errorInfo(),
                    anException);
        }
    }

    // ------------------------------------------------------------------------
    void setInt(int aKey, Integer aValue)
    {
        try
        {
            theQuery.setInt(aKey, aValue);
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException("Can't set arguments (" + aKey
                    + ", " + aValue + ") on query " + theFixedQuery, anException);
        }
        theSqlArgumentMap.put(aKey, aValue);
    }

    // ------------------------------------------------------------------------
    ArgsDB setThesisId(int aKey, ThesisId aValue)
    {
        setLong(aKey, aValue.getLongID());
        return this;
    }
    
    // ------------------------------------------------------------------------
    ArgsDB setPatchId(int aKey, PatchId aValue)
    {
        setInt(aKey, aValue.getLongID());
        return this;
    }
    
    // ------------------------------------------------------------------------
    ArgsDB setThesisText(int aKey, ThesisText aValue)
    {
        setString(aKey, aValue.toString());
        return this;
    }
    
    // ------------------------------------------------------------------------
    ArgsDB setThesisOpinionId(int aKey, ThesisOpinionId aValue)
    {
        setLong(aKey, aValue.getLongID());
        return this;
    }
    
    // ------------------------------------------------------------------------
    ArgsDB setRelationId(int aKey, RelationId aValue)
    {
        setLong(aKey, aValue.getID());
        return this;
    }
    
    // ------------------------------------------------------------------------
    ArgsDB setPerspectiveName(int aKey, String aName)
    {
        setString(aKey, aName);
        return this;
    }
    
    // ------------------------------------------------------------------------
    ArgsDB setUserId(int aKey, ArgumentsUserId aValue)
    {
        assert aValue != null;
        setLong(aKey, aValue.getLongId());
        return this;
    }
    
    // ------------------------------------------------------------------------
    ArgsDB setEmailAddress(int aKey, EmailAddress aValue)
    {
        setString(aKey, aValue.toString());
        return this;
    }
    
    // ------------------------------------------------------------------------
    ArgsDB setForeignUserId(int aKey, ForeignUserId aValue)
    {
        setString(aKey, aValue.toString());
        return this;
    }
    
    // ------------------------------------------------------------------------
    ArgsDB setScreenName(int aKey, ScreenName aValue)
    {
        setString(aKey, aValue.toString());
        return this;
    }
    
    // ------------------------------------------------------------------------
    ArgsDB setPerspectiveId(int aKey, PerspectiveId aValue)
    {
        assert aValue != null;
        setLong(aKey, aValue.getLongID());
        return this;
    }

    // ------------------------------------------------------------------------
    ArgsDB setOpinion(int aKey, ThesisOpinion aValue)
    {
        setDouble(aKey, aValue.getDBDouble());
        return this;
    }
    
    
    // ------------------------------------------------------------------------
    ArgsDB setPerspectiveName(int aKey, PerspectiveName aName)
    {
        setString(aKey, aName.toString());
        return this;
    }

    // ------------------------------------------------------------------------
    ArgsDB setRelevance(int aKey, Relevance aValue)
    {
        setDouble(aKey, aValue.getDBDouble());
        return this;
    }
    
    // ------------------------------------------------------------------------
    int executeUpdate()
    {
        try
        {
            Logger.log("SQL: " + errorInfo());
            int myResult = theQuery.executeUpdate();
            return myResult;

        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException("Can't execute this query:\n" +
                   errorInfo(), anException);
        }
    }

    // ------------------------------------------------------------------------
    private void setLong(int aKey, Long aValue)
    {
        try
        {
            theQuery.setLong(aKey, aValue);
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException("Can't set arguments (" + aKey
                    + ", " + aValue + ") on query " + theFixedQuery, anException);
        }
        theSqlArgumentMap.put(aKey, aValue);
    }

    // ------------------------------------------------------------------------
    private void setString(int aKey, String aValue)
    {
        try
        {
            theQuery.setString(aKey, aValue);
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException("Can't set arguments (" + aKey
                    + ", " + aValue + ") on query " + theFixedQuery, anException);
        }
        theSqlArgumentMap.put(aKey, aValue);
    }

    // ------------------------------------------------------------------------
    private void setDouble(int aKey, Double aValue)
    {
        try
        {
            theQuery.setDouble(aKey, aValue);
        } catch (SQLException anException)
        {
            throw new ArgsSQLStoreException("Can't set arguments (" + aKey
                    + ", " + aValue + ") on query " + theFixedQuery, anException);
        }
        theSqlArgumentMap.put(aKey, aValue);
    }

    // ------------------------------------------------------------------------
    private ResultSet executeQueryNoCatch()
            throws SQLException
    {
        Logger.log("SQL: " + errorInfo());
        // assert theQuery.isClosed();
        ResultSet myResult = theQuery.executeQuery();
        return myResult;
    }

    // ------------------------------------------------------------------------
    private String errorInfo()
    {
        return theFixedQuery.getText() + "\n, with these arguments:\n" + theSqlArgumentMap;
    }

    
    // ------------------------------------------------------------------------
    public void setLevel(int aI, double aD)
    {
        setDouble(aI, aD);
    }
}
