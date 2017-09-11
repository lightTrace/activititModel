package cn.com.wavenet.bean;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class UpdateBean extends BasicSqlBean {
	
	Map<String,Object> mapSet = new HashMap();
	
	public UpdateBean(){};
	private UpdateBean UpdateDetailBean;
	/**
	 * @author shil 
	 * @date 2017年6月27日 上午10:17:43 
	 * 逻辑删除构造方法
	 * @param mapWhereIN 修改条件 Map<字段,内容>
	 * @param mapSet  修改值的 Map<字段,内容>
	 * @param tableName  修改的表名
	 */
	public UpdateBean(String tableName,Map<String,Object> mapSet,Map<String,Object> mapWhereIN) {
		this.tableName = tableName;
		this.mapWhereIN = mapWhereIN;
		this.mapSet = mapSet;
	}
	
	public UpdateBean getUpdateDetailBean() {
		if(UpdateDetailBean==null)
			UpdateDetailBean=new UpdateBean();
		return UpdateDetailBean;
		
	}

	public void setDetailUpdateBean(UpdateBean UpdateDetailBean) {
		this.UpdateDetailBean = UpdateDetailBean;
	}

	/**
	 * @author shil 
	 * @date 2017年6月27日 上午10:17:43 
	 * 逻辑删除构造方法(指定数据源)
	 * @param mapWhereIN 修改条件 Map<字段,内容>
	 * @param mapSet  修改值的 Map<字段,内容>
	 * @param tableName  修改的表名
	 * @param dataSource  指定删除的数据源
	 */
	public UpdateBean(String tableName,Map<String,Object> mapSet,Map<String,Object> mapWhereIN,String dataSource) {
		this.tableName = tableName;
		this.mapWhereIN = mapWhereIN;
		this.mapSet = mapSet;
	}
	
	@Override
	public String toSql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseJSON(JSONObject json, String type) {
		// TODO Auto-generated method stub

	}

	public Map<String, Object> getMapSet() {
		return mapSet;
	}

	public void addMapSet(String keyName,Object keyValue ) {
		mapSet.put(keyName.toUpperCase(), keyValue);
	}
	
	public void addMap(Map map) {
		mapSet.putAll(map);
	}

}
