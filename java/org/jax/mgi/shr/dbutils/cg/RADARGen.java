package org.jax.mgi.shr.dbutils.cg;

public class RADARGen {

  public RADARGen() {}

  public static void main(String[] args) throws Exception {
    DBCodeGenerator gen = new DBCodeGenerator("RADAR", "org.jax.mgi.dbs.rdr");
    gen.run();
  }
}
