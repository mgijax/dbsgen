
package org.jax.mgi.shr.dbutils.cg;

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

public class DBCodeGen
    extends VelocityGenerator {

  private String schema = null;
  private String pkg = null;
  private Logger logger = new ConsoleLogger();

  public DBCodeGen(String schema, String pkg) {
    this.schema = schema;
    this.pkg = pkg;
  }

  public void setLogger(Logger logger) {
    this.logger = logger;
  }

  public void run() throws Exception {

    Vector tableObjs = new Vector();

    SQLDataManager sqlMgr =
        SQLDataManagerFactory.getShared(this.schema);
    if (false) {
      ResultsNavigator nav = sqlMgr.getTables();
      while (nav.next()) {
        Table table = (Table) nav.getCurrent();
        System.out.println("processing definitions for " + table.getName());
        // dont add system tables from Sybase
        if (!table.getName().startsWith("sys"))
          tableObjs.add(table);
      }

      Vector transTypes = new Vector();
      String sql = "select _TranslationType_key, " +
          "       translationType " +
          "from mgi_translationType " +
          "order by _TranslationType_key";
      try {
        nav = sqlMgr.executeQuery(sql);
        nav = sqlMgr.executeQuery(sql);
        while (nav.next()) {
          RowReference row = (RowReference) nav.getCurrent();
          transTypes.add(new ConstDef(row.getInt(1).intValue(),
                                      row.getString(2)));
        }

      }
      catch (DBException e) {
        if (logger != null) {
          logger.logError("Could not access data in MGI_TranslationType");
        }
      }
      context.put("transTypes", transTypes);
      context.put("tableObjs", tableObjs);
      context.put("schema", this.schema);
      context.put("pkg", this.pkg);
      String code =
          generateCode("org/jax/mgi/shr/dbutils/cg/template_DBConstants.vm");
      String definitionsFile =
          pkgToDirName(this.pkg + File.separator + this.schema);
      BufferedWriter out = new BufferedWriter(new FileWriter("definitionsFile"));
      out.write(code);
      out.close();
    }

    /**
     * generate the DAO classes
     */
    String dir = pkgToDirName(this.pkg);
    String tableList = dir + "generatedTables.txt";
    BufferedReader in = null;
    try {
      in = new BufferedReader(new FileReader(tableList));
    }
    catch (IOException e) {
      System.out.println("Cannot open file " + tableList);
      System.exit(1);
    }
    String str = null;
    while ( (str = in.readLine()) != null) {
      Table table = Table.getInstance(str, sqlMgr);
      System.out.println("processing DAO classes for ... " + table.getName());
      generate(new XDaoCG(table, schema, pkg),
               dir + table.getName() + "DAO.java");
      generate(new XStateCG(table, schema, pkg),
               dir + table.getName() + "State.java");
      if (table.hasIncrementalKey())
        generate(new XKeyCG(table, schema, pkg),
                             dir + table.getName() + "Key.java");
      generate(new XInterpreterCG(table, schema, pkg),
               dir + table.getName() + "Interpreter.java");
      generate(new XLookupCG(table, schema, pkg),
               dir + table.getName() + "Lookup.java");
    }
    in.close();
  }

  public class ConstDef {
    private String name = null;
    private int key = 0;
    public ConstDef(int key, String name) {
      this.key = key;
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public int getKey() {
      return key;
    }
  }

  protected void generate(CodeGeneratable cg, String filename) throws Exception {
    String source = cg.generateCode();
    File daoClass = new File(filename);
    createFile(filename, source);
  }

  protected void createFile(String filename, String contents) throws
      IOException {
    File file = new File(filename);
    file.getParentFile().mkdirs();
    BufferedWriter writer = null;
    writer = new BufferedWriter(new FileWriter(filename));
    writer.write(contents);
    writer.close();
  }

  protected String pkgToDirName(String pkgName) {
    String sep = File.separator;
    String period = ".";
    String directory = "";
    String subPkgs[] = pkgName.split("\\.");
    for (int i = 0; i < subPkgs.length; i++) {
      if (!subPkgs[i].equals("")) {
        directory = directory + subPkgs[i] + sep;
      }
    }
    return directory;
  }

}