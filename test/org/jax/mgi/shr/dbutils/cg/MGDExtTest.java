package org.jax.mgi.shr.dbutils.cg;

import junit.framework.TestCase;
import org.jax.mgi.dbs.mgd.MGDExt;
import org.jax.mgi.shr.dbutils.SQLDataManager;
import org.jax.mgi.shr.dbutils.ResultsNavigator;
import org.jax.mgi.shr.dbutils.RowReference;
import org.jax.mgi.shr.dbutils.BindableStatement;


public class MGDExtTest extends TestCase
{
    private SQLDataManager sqlMgr = null;

    public MGDExtTest(String arg0)
    {
        super(arg0);
    }

    protected void setUp() throws Exception
    {
        super.setUp();
        sqlMgr = new SQLDataManager();
        doDeletes();
        doInserts(); 
    }

    protected void tearDown() throws Exception
    {
        super.tearDown();
        doDeletes();
        sqlMgr = null;
    }
    
    public void testInterpreter() throws Exception
    {
        String sql = "select " + 
                        MGDExt.acc_accession._accession_key + ", " +
                        MGDExt.acc_accessionreference._refs_key + " " +
                     "from " +
                        MGDExt.acc_accession._name + ", " +
                        MGDExt.acc_accessionreference._name + " " +
                     "where " + 
                        MGDExt.acc_accession._accession_key + " = " +
                        MGDExt.acc_accessionreference._accession_key + " " +
                     "and " +
                        MGDExt.acc_accession._accession_key + " < 0 " + 
                     "and " +
                        MGDExt.acc_accession._accession_key + " > -4 " +
                     "order by " + MGDExt.acc_accession._accession_key;
        ResultsNavigator nav = sqlMgr.executeQuery(sql);
        RowReference row = null;
        String key1 = null;
        String key2 = null;
        nav.next();
        row = nav.getRowReference();
        key1 = row.getString(MGDExt.acc_accession._accession_key);
        key2 = row.getString(MGDExt.acc_accessionreference._refs_key);
        assertEquals("-3", key1);
        assertEquals("3", key2);
        nav.next();
        row = nav.getRowReference();
        key1 = row.getString(MGDExt.acc_accession._accession_key);
        key2 = row.getString(MGDExt.acc_accessionreference._refs_key);
        assertEquals("-2", key1);
        assertEquals("2", key2);
        nav.close();
        sql = "select * from " + MGDExt.acc_accession._name + 
              " order by " + MGDExt.acc_accession._accession_key;
        nav = sqlMgr.executeQuery(sql);
        nav.next();
        row = nav.getRowReference();
        assertEquals("-3", row.getString(MGDExt.acc_accession._accession_key));
        nav.next();
        row = nav.getRowReference();
        assertEquals("-2", row.getString(MGDExt.acc_accession._accession_key));
        nav.close();        
    }
    
    private void doInserts() throws Exception
    {
        String insert = 
           "insert into acc_accession values " +
           "(?, ?, ?, ?, 1, 1, 1, 1, 1, 1, 1, curtime(), curtime())";
        BindableStatement bs = sqlMgr.getBindableStatement(insert);
        bs.setInt(1, -1);
        bs.setString(2, "a1");
        bs.setString(3, "a");
        bs.setInt(4, 1);
        bs.executeUpdate();
        bs.setInt(1, -2);
        bs.setString(2, "a2");
        bs.setString(3, "a");
        bs.setInt(4, 2);
        bs.executeUpdate();
        bs.setInt(1, -3);
        bs.setString(2, "a3");
        bs.setString(3, "a");
        bs.setInt(4, 3);
        bs.executeUpdate();
        bs.close();
        insert = "insert into acc_accessionreference values " +
                 "(?, ?, 1, 1, curtime(), curtime())";
        bs = sqlMgr.getBindableStatement(insert);
        bs.setInt(1, -1);
        bs.setInt(2, 1);
        bs.executeUpdate();
        bs.setInt(1, -2);
        bs.setInt(2, 2);
        bs.executeUpdate();
        bs.setInt(1, -3);
        bs.setInt(2, 3);
        bs.executeUpdate();
    }
    
    private void doDeletes() throws Exception
    {
        sqlMgr.executeUpdate("delete from acc_accession where " + 
                             "_accession_key = -1");
        sqlMgr.executeUpdate("delete from acc_accession where " + 
                             "_accession_key = -2");
        sqlMgr.executeUpdate("delete from acc_accession where " + 
                             "_accession_key = -3");  
        sqlMgr.executeUpdate("delete from acc_accessionreference where " + 
                             "_accession_key = -1");
        sqlMgr.executeUpdate("delete from acc_accessionreference where " + 
                             "_accession_key = -2");
        sqlMgr.executeUpdate("delete from acc_accessionreference where " + 
                             "_accession_key = -3");     
    }

}

