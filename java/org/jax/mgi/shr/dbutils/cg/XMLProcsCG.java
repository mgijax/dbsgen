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
import org.jax.mgi.shr.dbutils.SQLDataManagerFactory;
import org.jax.mgi.shr.dbutils.ResultsNavigator;
import org.jax.mgi.shr.dbutils.Table;


public class XMLProcsCG
    extends VelocityGenerator
    implements CodeGeneratable {

  public XMLProcsCG(String schema, String pkgName, String tableList)
  throws Exception {
    context = new VelocityContext();
    context.put("schema", schema);
    context.put("packageName", pkgName);
    Vector tableNames = new Vector();

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
            System.out.println("processing xml methods for ... " + str);
            // dont add system tables from Sybase
            if (!str.startsWith("sys"))
                tableNames.add(str);
        }
        daoIn.close();
    }

    context.put("tableNames", tableNames);
    setContext(context);
  }

  public String generateCode() throws Exception {
    return super.generateCode(
        "org/jax/mgi/shr/dbutils/cg/template_XMLProcs.vm");
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

