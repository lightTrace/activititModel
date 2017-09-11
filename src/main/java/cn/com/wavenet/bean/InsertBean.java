package cn.com.wavenet.bean;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class InsertBean extends BasicSqlBean {
	
	Map<String,Object> mapInsert = new HashMap();
	private InsertBean detailInsertBean;

	@Override
	public String toSql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseJSON(JSONObject json, String type) {
		// TODO Auto-generated method stub

	}
	public Map<String, Object> getMapInsert() {
		return mapInsert;
	}

	public void addMapInsert(String keyName,Object keyValue ) {
		mapInsert.put(keyName.toUpperCase(), (String)keyValue);
	}
	
	public void addMap(Map map) {
		mapInsert.putAll(map);
	}
	
	public InsertBean getInsertDetailBean() {
		if(detailInsertBean==null)
			detailInsertBean=new InsertBean();
		return detailInsertBean;
	}
	public void setInsertDetailBean(InsertBean detailInsertBean) {
		this.detailInsertBean = detailInsertBean;
	}
}
