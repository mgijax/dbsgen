package org.jax.mgi.shr.dbutils.cg;

import org.apache.velocity.VelocityContext;

import org.jax.mgi.shr.dbutils.Table;

public class XStateCG
    extends VelocityGenerator
    implements CodeGeneratable {

  public XStateCG(Table table, String schema, String pkg) {
    context = new VelocityContext();
    context.put("Table", table);
    context.put("schema", schema);
    context.put("packageName", pkg);
    setContext(context);
  }

  public String generateCode() throws Exception {
    return super.generateCode(
        "org/jax/mgi/shr/dbutils/cg/template_XState.vm");
  }
}

