package org.jax.mgi.shr.dbutils.cg;
import junit.framework.TestCase;

import org.jax.mgi.shr.dbutils.*;

public class XKeyCGTest extends TestCase
{

	private XKeyCG cg = null;
	private Table table = null;
	private SQLDataManager sqlMgr = null;

	/**
	 * Constructor.
	 * @param arg0
	 */
	public XKeyCGTest(String arg0)
	{
		super(arg0);
	}

	public static void main(String[] args)
	{}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
		sqlMgr = SQLDataManagerFactory.getShared("MGD");
		table = Table.getInstance("PRB_Probe", sqlMgr);
		cg = new XKeyCG(table, "MGD", "org.jax.mgi.dbs.mgd.dao");
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		cg = null;
		table = null;
		sqlMgr = null;
		super.tearDown();
	}

	public void testGenerate() throws Exception
	{
		System.out.println(cg.generateCode());
	}

}
