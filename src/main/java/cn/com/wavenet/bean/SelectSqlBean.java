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
 * 功能描述：查询语句的封装
 * @datetime  下午4:57:17
 *  @version 1.0
 */
public class SelectSqlBean {
	protected String cacheKey;//缓存的键值	
	protected long cacheMinute;//缓存的时间
	protected String dataSource;//数据源
	protected String tableName;//表名
	protected String field="*";//字段
	protected String extendSql;//扩展字段
	protected String order;//排序

	protected String pkey;//主键的字段名
	protected String pkeyValue;
	protected String fkey;//外键字段的字段名
	protected String fkeyValue;
	protected String group ;//分组
	protected String having ;//分组条件

	protected Map<String,Object> mapWhereIN=new HashMap();//where 中 in 的值
	protected Map<String,Object> mapWhereEQ=new HashMap();//where 中  = 的值
	protected Map<String,Object> mapWhereIS=new HashMap();//where 中  null 的值
	protected Map<String,Object> mapWhereSIGN=new HashMap();//where 中  >,<,>=,<= 的值

	protected int iWhere=0;
	protected int iWhereEQ=0;
	protected int iWhereIN=0;
	protected int iWhereIS=0;
	protected String sql;
	protected  Pagination pag;//分布信息
	protected SelectSqlBean detailBean;
	/**
	 * 
	 */
	public SelectSqlBean() {
		// TODO Auto-generated constructor stub
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		String sname=tableName.trim().toUpperCase();

		this.tableName = sname;
		
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}

	public String getExtendSql() {
		return extendSql;
	}

	public void setExtendSql(String extendSql) {
		this.extendSql = extendSql;
	}

	public String getOrder() {
		
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	public String getPkey() {
		return pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	public String getFkey() {
		return fkey;
	}
	public void setFkey(String fkey) {
		this.fkey = fkey;
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
	public Map<String, Object> getMapSIGN() {
		// TODO Auto-generated method stub
		return mapWhereSIGN;
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
	public void addMapWhereEQ(String keyName,String keyValue){
		addMapWhereEQ(keyName,keyValue,0);
	}
	public void addMapWhereEQ(String keyName,Object keyValue,int operType) {
		iWhere++;
		iWhereEQ++;
		mapWhereEQ.put(keyName.toUpperCase(), keyValue);
		String[] oper={"=","<","<=",">",">=","<>"};
		mapWhereSIGN.put(keyName.toUpperCase(), oper[operType]);
	}
	public void addMapWhereEQ(String keyName,Object keyValue) {
		iWhere++;
		iWhereEQ++;
		addMapWhereEQ(keyName,keyValue,0);

	}
	public void addMapWhereSIGN(String keyName,Object keyValue) {
		mapWhereSIGN.put(keyName.toUpperCase(), keyValue);
		
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	


	/**
	 * 用于webservice的数据解析
	 * @param json
	 * @param type 解析的格式
	 */
	public void parseJSON(JSONObject json,String type){
		
	}
	public SelectSqlBean getDetailBean() {
		if(detailBean==null)
			detailBean=new SelectSqlBean();
		return detailBean;
	}
	public void setDetailBean(SelectSqlBean detailBean) {
		this.detailBean = detailBean;
	}
	public Pagination getPag() {
		return pag;
	}
	public void setPag(Pagination pag) {
		this.pag = pag;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getHaving() {
		return having;
	}
	public void setHaving(String having) {
		this.having = having;
	}
	public String getPkeyValue() {
		return pkeyValue;
	}
	public void setPkeyValue(String pkeyValue) {
		this.pkeyValue = pkeyValue;
	}
	public String getFkeyValue() {
		return fkeyValue;
	}
	public void setFkeyValue(String fkeyValue) {
		this.fkeyValue = fkeyValue;
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
	
}
