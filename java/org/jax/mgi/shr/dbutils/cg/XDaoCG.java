package org.jax.mgi.shr.dbutils.cg;

import org.apache.velocity.VelocityContext;

import org.jax.mgi.shr.dbutils.Table;
import org.jax.mgi.shr.dbutils.DBTypeConstants;

public class XDaoCG
    extends VelocityGenerator
    implements CodeGeneratable {

  public XDaoCG(Table table, String schema, String pkg) {
    context = new VelocityContext();
    context.put("Table", table);
    context.put("schema", schema);
    context.put("packageName", pkg);
    context.put("char", new Integer(DBTypeConstants.DB_CHAR));
		context.put("varchar", new Integer(DBTypeConstants.DB_VARCHAR));
		context.put("text", new Integer(DBTypeConstants.DB_TEXT));
    context.put("bit", new Integer(DBTypeConstants.DB_BIT));
		context.put("float", new Integer(DBTypeConstants.DB_FLOAT));
		context.put("date", new Integer(DBTypeConstants.DB_DATETIME));
		context.put("integer", new Integer(DBTypeConstants.DB_INTEGER));
    setContext(context);
  }

  public String generateCode() throws Exception {
    return super.generateCode("org/jax/mgi/shr/dbutils/cg/template_XDao.vm");
  }
}