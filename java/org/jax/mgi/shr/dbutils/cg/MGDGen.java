package org.jax.mgi.shr.dbutils.cg;

public class MGDGen {
    
  public static void main(String[] args) throws Exception {
    DBCodeGenerator gen = new DBCodeGenerator("MGD", "org.jax.mgi.dbs.mgd");
    gen.run();
  }
}
