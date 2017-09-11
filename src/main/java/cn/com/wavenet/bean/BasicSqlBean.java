/**
 * 
 */
package cn.com.wavenet.bean;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import com.alibaba.fastjson.JSONObject;

/**
 * @author chenjl
 * @createDate 2017年6月7日
 * 功能描述：基础的语句的封装
 * @datetime  下午4:57:17
 *  @version 1.0
 */
public abstract class BasicSqlBean {
	protected String cacheKey;//缓存的键值	
	protected long cacheMinute;//缓存的时间
	protected String dataSource;//数据源
	protected String tableName;//表名
	
	

	protected String pkey;//主键的字段名
	protected String pkeyValue;
	
	protected Map<String,Object> mapWhereIN=new HashMap();//where 中 =的值
	
	protected Map<String,Object> mapWhereEQ=new HashMap();//where 中  in 的值
	
	protected Map<String,Object> mapWhereIS=new HashMap();//where 中  in 的值
	protected int iWhere=0;
	protected int iWhereEQ=0;
	protected int iWhereIN=0;
	protected int iWhereIS=0;
	protected String sql;

	/**
	 * 
	 */
	public BasicSqlBean() {
		// TODO Auto-generated constructor stub
	}
	public String getTableName() {
		
		return tableName;
	}

	public void setTableName(String tableName) {
		String sname=tableName.trim().toUpperCase();
		this.tableName = sname;
		
	}

	public Map<String, Object> getMapWhereIN() {
		return mapWhereIN;
	}

	public void addMapWhereIN(String keyName,String keyValue) {
		iWhere++;
		iWhereIN++;
		mapWhereIN.put(keyName.toUpperCase(), (String)keyValue);
		
	}
	public void addMapWhereIN(String keyName,Object keyValue) {
		iWhere++;
		iWhereIN++;
		mapWhereIN.put(keyName.toUpperCase(), keyValue);
		
	}

	public Map<String, Object> getMapWhereEQ() {
		return mapWhereEQ;
	}
	public Map<String, Object> getMapWhereIS() {
		return mapWhereIS;
	}
	public void addMapWhereIS(String keyName,boolean isNull) {
		iWhere++;
		iWhereIS++;
		if(isNull)
			mapWhereIS.put(keyName.toUpperCase(), " null");
		else
			mapWhereIS.put(keyName.toUpperCase(), " not null");
	}




	public void addMapWhereEQ(String keyName,String keyValue) {
		iWhere++;
		iWhereEQ++;
		mapWhereEQ.put(keyName.toUpperCase(), (String)keyValue);
		
	}
	public void addMapWhereEQ(String keyName,Object keyValue) {
		iWhere++;
		iWhereEQ++;
		mapWhereEQ.put(keyName.toUpperCase(), keyValue);
		
	}
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	/**
	 * 返回常规表的sql
	 * @return
	 */
	public abstract String toSql();
	
	/**
	 * 用于webservice的数据解析
	 * @param json
	 * @param type 解析的格式
	 */
	public abstract void parseJSON(JSONObject json,String type);

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}


	public String getPkeyValue() {
		return pkeyValue;
	}

	public void setPkeyValue(String pkeyValue) {
		this.pkeyValue = pkeyValue;
	}



	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}


	public long getCacheMinute() {
		return cacheMinute;
	}


	public void setCacheMinute(long cacheMinute) {
		this.cacheMinute = cacheMinute;
	}
	public String getPkey() {
		return pkey;
	}
	public void setPkey(String pkey) {
		this.pkey = pkey;
	}


}
