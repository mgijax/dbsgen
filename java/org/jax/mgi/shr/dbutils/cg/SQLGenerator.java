package org.jax.mgi.shr.dbutils.cg;

import java.sql.Timestamp;

public class SQLGenerator
{
	public static String toSQLString(String input)
	{
		return "'" + input + "'";
	}
	
	public static String toSQLString(Boolean input)
	{
		if (input.booleanValue())
		  return "1";
		else
		  return "0";
	}

	public static String toSQLString(Integer input)
	{
		return input.toString();
	}
	
	public static String toSQLString(Float input)
	{
		return input.toString();
	}
	
	public static String toSQLString(Timestamp input)
	{
		return "'" + input.toString() + "'";
	}
}
