package org.jax.mgi.shr.dbutils.cg;

import org.apache.velocity.VelocityContext;

import org.jax.mgi.shr.dbutils.Table;

public class XLookupCG
    extends VelocityGenerator
    implements CodeGeneratable {

  public XLookupCG(Table table, String schema, String pkg) {
    context = new VelocityContext();
    context.put("Table", table);
    context.put("schema", schema);
    context.put("packageName", pkg);
    setContext(context);
  }

  public String generateCode() throws Exception {
    return super.generateCode(
        "org/jax/mgi/shr/dbutils/cg/template_XLookup.vm");
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

