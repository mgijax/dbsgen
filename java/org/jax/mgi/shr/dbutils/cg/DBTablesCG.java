package org.jax.mgi.shr.dbutils.cg;

import org.apache.velocity.VelocityContext;

import java.util.Vector;


import org.jax.mgi.shr.dbutils.SQLDataManager;
import org.jax.mgi.shr.dbutils.SQLDataManagerFactory;
import org.jax.mgi.shr.dbutils.ResultsNavigator;
import org.jax.mgi.shr.dbutils.Table;


public class DBTablesCG
    extends VelocityGenerator
    implements CodeGeneratable {

  public DBTablesCG(String schema, String pkgName)
  throws Exception {
    context = new VelocityContext();
    System.out.println("schema = " + schema);
    context.put("schema", schema);
    context.put("packageName", pkgName);
    Vector tableObjs = new Vector();

    SQLDataManager sqlMgr = SQLDataManagerFactory.getShared(schema);

    //if (false) {
    ResultsNavigator nav = sqlMgr.getTables();
    while (nav.next())
    {
        Table table = (Table) nav.getCurrent();
	if(table!=null)
	{
	    System.out.println("processing definitions for ... " +
			       table.getName());
	    // dont add system tables from Sybase
	    if (!table.getName().startsWith("sys") && !table.getName().startsWith("pg_"))
		tableObjs.add(table);
	}
    }

    context.put("tableObjs", tableObjs);
    setContext(context);
  }

  public String generateCode() throws Exception {
    return super.generateCode(
        "org/jax/mgi/shr/dbutils/cg/template_TableDefs.vm");
  }

  public class KeyConstDef
  {
      protected String name = null;
      protected Integer keyValue = null;

      public KeyConstDef(String name, Integer keyValue)
      {
          this.name = name;
          this.keyValue = keyValue;
      }
  }
}

/**************************************************************************
 *
 * Warranty Disclaimer and Copyright Notice
 *
 *  THE JACKSON LABORATORY MAKES NO REPRESENTATION ABOUT THE SUITABILITY OR
 *  ACCURACY OF THIS SOFTWARE OR DATA FOR ANY PURPOSE, AND MAKES NO WARRANTIES,
 *  EITHER EXPRESS OR IMPLIED, INCLUDING MERCHANTABILITY AND FITNESS FOR A
 *  PARTICULAR PURPOSE OR THAT THE USE OF THIS SOFTWARE OR DATA WILL NOT
 *  INFRINGE ANY THIRD PARTY PATENTS, COPYRIGHTS, TRADEMARKS, OR OTHER RIGHTS.
 *  THE SOFTWARE AND DATA ARE PROVIDED "AS IS".
 *
 *  This software and data are provided to enhance knowledge and encourage
 *  progress in the scientific community and are to be used only for research
 *  and educational purposes.  Any reproduction or use for commercial purpose
 *  is prohibited without the prior express written permission of The Jackson
 *  Laboratory.
 *
 * Copyright \251 1996, 1999, 2002 by The Jackson Laboratory
 *
 * All Rights Reserved
 *
 **************************************************************************/

