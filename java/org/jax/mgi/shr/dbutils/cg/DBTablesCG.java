package org.jax.mgi.shr.dbutils.cg;

import org.apache.velocity.VelocityContext;

import java.util.Vector;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import org.jax.mgi.shr.dbutils.SQLDataManager;
import org.jax.mgi.shr.dbutils.RowReference;
import org.jax.mgi.shr.dbutils.SQLDataManagerFactory;
import org.jax.mgi.shr.dbutils.ResultsNavigator;
import org.jax.mgi.shr.dbutils.DBException;
import org.jax.mgi.shr.dbutils.Table;
import org.jax.mgi.shr.log.Logger;
import org.jax.mgi.shr.log.ConsoleLogger;

public class DBTablesCG
    extends VelocityGenerator
    implements CodeGeneratable {

  public DBTablesCG(String schema, String pkgName) 
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
    return super.generateCode("org/jax/mgi/shr/dbutils/cg/template_TableDefs.vm");
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
