package org.jax.mgi.shr.dbutils.cg;

public class RADARGen
    extends DBCodeGen {

  public RADARGen() {
    super("RADAR", "org.jax.mgi.dbs.rdr.dao");
  }

  public static void main(String[] args) throws Exception {
    RADARGen gen = new RADARGen();
    gen.run();
  }
}
