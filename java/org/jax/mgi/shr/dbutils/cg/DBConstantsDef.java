
package org.jax.mgi.shr.dbutils.cg;


public class DBConstantsDef
{

    public String name = null;
    public Integer keyValue = null;

    public DBConstantsDef(String name, Integer keyValue)
    {
        this.name = name;
        this.keyValue = keyValue;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Integer getKeyValue()
    {
        return keyValue;
    }
}
