/*
 * Created on Oct 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.jax.mgi.shr.dbutils.cg;

/**
 * @author mbw
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MGDGen
    extends DBCodeGen {

  public MGDGen() {
    super("MGD", "org.jax.mgi.dbs.mgd.dao");
  }

  public static void main(String[] args) throws Exception {
    MGDGen gen = new MGDGen();
    gen.run();
  }
}
