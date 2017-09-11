package cn.com.wavenet.factory;

import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource{

	 /** 
     * 取得当前使用那个数据源。 
     */  
    @Override  
    protected Object determineCurrentLookupKey() {  
        return DbContextHolder.getDbType();    
    }  
  
      
    public Logger getParentLogger() {  
        // TODO Auto-generated method stub  
        return null;  
    }

}
