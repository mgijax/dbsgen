package org.jax.mgi.shr.dbutils.cg;

import org.apache.velocity.VelocityContext;

import java.util.Vector;

import org.jax.mgi.shr.dbutils.SQLDataManager;
import org.jax.mgi.shr.dbutils.RowReference;
import org.jax.mgi.shr.dbutils.ResultsNavigator;

public class DBConstantsCG
    extends VelocityGenerator
    implements CodeGeneratable {

  public DBConstantsCG(String pkgName, String className, String tableName,
                       String nameCol, String valueCol)
  throws Exception {
    context = new VelocityContext();
    context.put("className", className);
    context.put("tableName", tableName);
    context.put("packageName", pkgName);
    SQLDataManager sqlMgr = new SQLDataManager();
    Vector constDefs = new Vector();
    String sql =
        "select " + nameCol + ", " + valueCol + " " +
        "from " + tableName + " " +
        "order by " + valueCol;
    ResultsNavigator nav = sqlMgr.executeQuery(sql);
    while (nav.next())
    {
        RowReference row = nav.getRowReference();
        constDefs.add(new DBConstantsDef(row.getString(1), row.getInt(2)));
    }

    context.put("constDefs", constDefs);
    setContext(context);
  }

  public String generateCode() throws Exception {
    return super.generateCode(
        "org/jax/mgi/shr/dbutils/cg/template_DBConstants.vm");
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

