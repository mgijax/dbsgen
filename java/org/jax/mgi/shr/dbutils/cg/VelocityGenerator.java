package org.jax.mgi.shr.dbutils.cg;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;

import java.io.StringWriter;
import java.io.File;
import java.util.Properties;
import java.net.URL;
import java.net.URI;

import org.jax.mgi.shr.log.Logger;
import org.jax.mgi.shr.log.ConsoleLogger;

public class VelocityGenerator
{
	protected VelocityContext context = new VelocityContext();
	
	protected Logger logger = new ConsoleLogger();

	public VelocityGenerator() {}
	
	public void setLogger(Logger logger)
	{
		this.logger = logger;
	}
	
	public void setContext(VelocityContext context)
	{
		this.context = context;
	}

  /**
   * generates code using the Velocity runtime engine 
   * (see jakarta.apache.org/velocity)
   * @param resource the velocity template name expressed in the format 
   * expected by java class loaders
   * @return the results of mering the Table class with the template
   * @throws Exception
   */
	public String generateCode(String resource) throws Exception
	{
		/*
		 * turn the resource into a File object
		 */
		URL url = 
			 this.getClass().getClassLoader().getResource(resource);
		if (url == null)
		{
			logger.logError("Cannot find template file " + resource);
			return("");
		}
		File file = new File(new URI(url.toString()));
		
		/*
		 * add the path directory of the File into the Velocity path
		 */
		Properties p = new Properties();
	  p.setProperty("file.resource.loader.path", file.getParent());
	  
	  /*
	   * initialize velocity runtime engine
	   */
		Velocity.init(p);
		
		/*
		 * add the Table class to the context and merge template
		 */
		Template template = null;
		template = Velocity.getTemplate(file.getName());
		StringWriter w = new StringWriter();
		template.merge( context, w );
		
		/**
		 * return results
		 */
		return w.toString();
	}
}
