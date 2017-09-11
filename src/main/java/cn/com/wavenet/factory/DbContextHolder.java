package cn.com.wavenet.factory;

public class DbContextHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();  
	  
    /** 
     * 设置当前数据库。 
     * @param dbType 
     */  
    public static void setDbType(String dbType)  
    {  
        contextHolder.set(dbType);  
    }  
  
    /** 
     * 取得当前数据源。 
     * @return 
     */  
    public static String getDbType()  
    {  
        String str = (String) contextHolder.get();  
        return str;  
    }  
      
    /** 
     * 清除上下文数据 
     */  
    public static void clearDbType()  
    {  
        contextHolder.remove();  
    }
}
