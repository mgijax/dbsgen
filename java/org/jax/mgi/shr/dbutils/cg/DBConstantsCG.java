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
    return super.generateCode("org/jax/mgi/shr/dbutils/cg/template_DBConstants.vm");
  }
  

}
