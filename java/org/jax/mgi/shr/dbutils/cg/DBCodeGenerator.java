
package org.jax.mgi.shr.dbutils.cg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import org.jax.mgi.shr.dbutils.SQLDataManagerFactory;
import org.jax.mgi.shr.dbutils.SQLDataManager;
import org.jax.mgi.shr.dbutils.Table;
import org.jax.mgi.shr.log.Logger;
import org.jax.mgi.shr.log.ConsoleLogger;

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
         * generate the tables extended definition class
         */
        //generateFile(new DBTablesExtendedCG(this.schema, this.basePkg),
            //baseDir + File.separator + this.schema + "Ext.java");

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
                System.out.println("creating constant class for ... " +
                                   className);
                generateFile(
                    new DBConstantsCG(constPkg, className, tableName,
                    colName, colValue), constDir + className + ".java");
            }
        }
        /**
         * generate the DAO classes and the xml methods
         * if generatedTables.txt is found
         */
        String daoDir = pkgToDirName(this.daoPkg);
        String tableList = daoDir + "generatedTables.txt";

        /**
         * generate the XML processor class
         */
        //generateFile(new XMLProcsCG(this.schema, this.basePkg, tableList),
                     //baseDir + File.separator + "XMLProcs" +
                     //this.schema + ".java");

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
		SQLDataManager sqlMgr = SQLDataManagerFactory.getShared(this.schema);

                Table table = Table.getInstance(str, sqlMgr);
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

    protected void generateFile(CodeGeneratable cg, String filename)
        throws Exception {
      String source = cg.generateCode();
      File daoClass = new File(filename);
      createFile(filename, source);
    }

    protected void createFile(String filename, String contents)
        throws IOException {
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

