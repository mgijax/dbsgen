/*
 * Created on Oct 15, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.jax.mgi.shr.dbutils.cg;

import java.io.*;

import junit.framework.TestCase;

/**
 * @author mbw
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MGDGenTest extends TestCase
{

        private DBCodeGenerator gen = null;

        /**
         * Constructor for MGDGenTest.
         * @param arg0
         */
        public MGDGenTest(String arg0)
        {
                super(arg0);
        }

        public void setUp() throws Exception
        {
                super.setUp();
                gen = new DBCodeGenerator("MGD", "org.jax.mgi.dbs.mgd");
        }

        public void teardown() throws Exception
        {
                gen = null;
                super.tearDown();
        }

        public void testCreateFile() throws Exception
        {
                String sep = File.separator;
                String filename = "output" + sep + "test" + sep + "data";
                String contents = "";
                File file = new File(filename);
                if (file.exists())
                   file.delete();
                gen.createFile(filename, contents);
                file = new File(filename);
                assertTrue(file.exists());
                file.delete();
        }

        public void testPkgToDirName()
        {
                String sep = File.separator;
                String s = gen.pkgToDirName("org.jax.mgi");
                assertEquals(s, "org" + sep + "jax" + sep + "mgi" + sep);
        }


        public void testRun() throws Exception
        {
                gen.run();
        }

}
