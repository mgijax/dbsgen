
package org.jax.mgi.shr.dbutils.cg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import org.jax.mgi.shr.dbutils.SQLDataManagerFactory;
import org.jax.mgi.shr.dbutils.Table;
import org.jax.mgi.shr.log.Logger;
import org.jax.mgi.shr.log.ConsoleLogger;

/**
 * @author mbw
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DBCodeGenerator 
{

    protected String schema = null;
    /**
     * the package name to be the root package 
     */
    protected String basePkg = null;
    /**
     * the package name to contain the dao classes 
     */
    protected String daoPkg = null;
    /**
     * the package name to contain the db constant classes 
     */
    protected String constPkg = null;
    /**
     * the system logger
     */
    protected Logger logger = new ConsoleLogger();
    /**
     * @param schema
     * @param basePkg
     */
    public DBCodeGenerator(String schema, String basePkg)
    {
        this.schema = schema;
        this.basePkg = basePkg;
        this.daoPkg = basePkg + ".dao";
        this.constPkg = basePkg + ".keys";
    }

    public void run() throws Exception
    {
        
        /**
         * generate the tables definition class
         */
        String baseDir = pkgToDirName(this.basePkg);
        generateFile(new DBTablesCG(this.schema, this.basePkg), 
            baseDir + File.separator + this.schema + ".java"); 
            
        /**
         * generate the db constants classes if constants list is found
         */      
        String constDir = pkgToDirName(this.constPkg);
        String constList = constDir + "constants.txt";
        if ((new File(constList).exists()))
        {
            BufferedReader constIn = null;
            try
            {
                constIn = new BufferedReader(new FileReader(constList));
            }
            catch (IOException e)
            {
                System.out.println("Cannot open file " + constList);
                System.exit(1);
            }
            System.out.println();
            String ln = null;
            // the following are fields extracted from the line read in
            String className = null;
            String tableName = null;
            String colName = null;
            String colValue = null;
            while ((ln = constIn.readLine()) != null)
            {
                String[] fields = ln.split(" ");
                className = fields[0];
                tableName = fields[1];
                colName = fields[2];
                colValue = fields[3];
                System.out.println("creating constant class for ... " + className);
                generateFile(
                    new DBConstantsCG(constPkg, className, tableName, 
                    colName, colValue), constDir + className + ".java");
            }
        }
        /**
         * generateFile the DAO classes if tables list is found 
         */
        String daoDir = pkgToDirName(this.daoPkg);
        String tableList = daoDir + "generatedTables.txt";
        if ((new File(tableList).exists()))
        {
            System.out.println();
            BufferedReader daoIn = null;
            try
            {
                daoIn = new BufferedReader(new FileReader(tableList));
            }
            catch (IOException e)
            {
                System.out.println("Cannot open file " + tableList);
                System.exit(1);
            }
            String str = null;
            while ((str = daoIn.readLine()) != null)
            {
                Table table = Table.getInstance(str, 
                    SQLDataManagerFactory.getShared(this.schema));
                System.out.println(
                    "processing DAO classes for ... " + table.getName());
                generateFile(
                    new XDaoCG(table, schema, daoPkg),
                    daoDir + table.getName() + "DAO.java");
                generateFile(
                    new XStateCG(table, schema, daoPkg),
                    daoDir + table.getName() + "State.java");
                if (table.hasIncrementalKey())
                    generateFile(
                        new XKeyCG(table, schema, daoPkg),
                        daoDir + table.getName() + "Key.java");
                generateFile(
                    new XInterpreterCG(table, schema, daoPkg),
                    daoDir + table.getName() + "Interpreter.java");
                generateFile(
                    new XLookupCG(table, schema, daoPkg),
                    daoDir + table.getName() + "Lookup.java");
            }
            daoIn.close();
        }
    }
    
    protected void generateFile(CodeGeneratable cg, String filename) throws Exception {
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
