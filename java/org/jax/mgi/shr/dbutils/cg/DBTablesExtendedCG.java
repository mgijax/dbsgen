package org.jax.mgi.shr.dbutils.cg;

import org.apache.velocity.VelocityContext;

import java.util.Vector;


import org.jax.mgi.shr.dbutils.SQLDataManager;
import org.jax.mgi.shr.dbutils.SQLDataManagerFactory;
import org.jax.mgi.shr.dbutils.ResultsNavigator;
import org.jax.mgi.shr.dbutils.Table;


public class DBTablesExtendedCG
    extends VelocityGenerator
    implements CodeGeneratable {

  public DBTablesExtendedCG(String schema, String pkgName)
  throws Exception {
    context = new VelocityContext();
    context.put("schema", schema);
    context.put("packageName", pkgName);
    Vector tableObjs = new Vector();

    SQLDataManager sqlMgr = SQLDataManagerFactory.getShared(schema);

    //if (false) {
    ResultsNavigator nav = sqlMgr.getTables();
    while (nav.next())
    {
        Table table = (Table) nav.getCurrent();
        System.out.println("processing definitions for ... " + table.getName());
        // dont add system tables from Sybase
        if (!table.getName().startsWith("sys"))
            tableObjs.add(table);
    }

    context.put("tableObjs", tableObjs);
    setContext(context);
  }

  public String generateCode() throws Exception {
    return super.generateCode(
        "org/jax/mgi/shr/dbutils/cg/template_TableExtendedDefs.vm");
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
