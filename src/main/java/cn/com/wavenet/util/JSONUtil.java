/**
 * @createDate 2012-8-17
 * JSONUtil.java
 */
package cn.com.wavenet.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.com.wavenet.bean.DeleteBean;
import cn.com.wavenet.bean.InsertBean;
import cn.com.wavenet.bean.Pagination;
import cn.com.wavenet.bean.SelectSqlBean;
import cn.com.wavenet.bean.UpdateBean;


/**
 * 对JSON对象的统一处理
 * @author 加林
 * @editDate 2012-8-17	
 * @version 1.0
 * @since jdk1.5
 */
public class JSONUtil {
	
	
	/**
	 * 将字符串统一解析成JSONObject
	 * @param s
	 * @return
	 */

	/**
	 * 将字符串统一解析成JSONArray
	 * @param s
	 * @return
	 */
	public static JSONArray parseToJSA(String s){
		JSONArray jsa=JSON.parseArray(s);
		return jsa;
	}
	
	
	/**
	 * 获得JSON的属性值
	 * @param json
	 * @param key
	 * @return
	 */
	public static String getJsonProp(JSONObject json,String key){
		String s="";
		if(json.containsKey(key)){
			s=json.getString(key).trim();
			
		}
		return s;
	}
	/**
	 * 设置AJAX的错误信息
	 * @param json
	 * @param errMsg
	 * @return
	 */
	
	

	/*public static JSONObject converJsotoDbo(DataObject dbo,JSONObject json,boolean flag){
		//JSONObject json=new JSONObject();
		Collection col=dbo.getKeyName();
		if(col!=null){
			Iterator iter=col.iterator();
			String key;
			while(iter.hasNext()){
				key=(String)iter.next();
				if(key.indexOf("DT_")==0){
					try{
						if(dbo.getDate(key)!=null && !"".equals(dbo.getDate(key))){
							json.put(key, GlobalParam.SDF_YMDHIS.format(dbo.getDate(key)));
						}else{
							json.put(key, "");
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}else if(key.equalsIgnoreCase("NM_SID")){
					json.put(key, dbo.getString(key));
				}else if(key.indexOf("PK")==0){
					json.put(key, dbo.getString(key));
				}else{
					if(!flag)
						json.put(key, "<span title='"+dbo.getString(key)+"'>"+dbo.getString(key)+"</span>");
					else
						json.put(key, dbo.getString(key));
				}
			}
			
		}
		return json;
	}*/
	/**
	 *  JSON合并
	 * @param jso
	 * @param jsoExtend
	 * @return
	 */
	/*
	public static JSONObject mergetJSO(JSONObject jso,JSONObject jsoExtend)
	{
		if(jsoExtend!=null){
			Iterator<String> iterKey=jsoExtend.keys();
			String keyName="";
			while(iterKey.hasNext()){
				keyName=iterKey.next();
				if(!jso.containsKey(keyName)){
					jso.put(keyName, jsoExtend.getString(keyName));
				}
			}
		}
		return jso;
	}*/
	public static JSONObject mergetJSO(JSONObject jso,JSONObject jsoExtend){
		
		if(jsoExtend!=null){
			Set set=jsoExtend.keySet();
			if(set!=null){
				Iterator<String> iterKey=set.iterator();
				String keyName="";
				while(iterKey.hasNext()){
					keyName=iterKey.next();
					if(!jso.containsKey(keyName)){
						jso.put(keyName, jsoExtend.getString(keyName));
					}
				}
			}
		}
		return jso;
	}

	public static JSONObject CollectionToResult(List<Map<String,Object>> list ,Pagination page){
		JSONObject jsonObject = new JSONObject();		
        JSONArray  dataJsonArr = new JSONArray();
		JSONObject jsonObjectData = new JSONObject();
        
		jsonObject.put("dataType", "rows");

		if(page!=null){
			jsonObject.put("page", page.getCurrent());//当前页
			jsonObject.put("total", page.getPages());//总页数
			jsonObject.put("records", page.getCount());//总记录数
			jsonObject.put("countOfPage", page.getCountOfPage());//每页的记录数
			jsonObject.put("countOfCurrent", page.getCountOfCurrent());//每页的记录数

		}
		for(int i=0;i<list.size();i++){
			jsonObjectData = (JSONObject) JSON.toJSON(list.get(i));
        	dataJsonArr.add(jsonObjectData);  
		}
		jsonObject.put("rows", dataJsonArr);
		return jsonObject;
	}
	
	public static JSONObject beanToResult(Map map ){
		JSONObject jsonObject = new JSONObject();
		JSONObject jsonObjectData = new JSONObject();        
//		jsonObject.put("resultCode", resultCode);
//		jsonObject.put("resultMessage", resultMessage);
		jsonObject.put("dataType", "row");
		jsonObjectData = (JSONObject) JSON.toJSON(map);
		jsonObject.put("data", jsonObjectData);
		
		return jsonObject;
	}
	
	public static SelectSqlBean jsonStringToBean(String jsonString){
		int currentPage = 0;
		int pageRows = 0;
		SelectSqlBean bean = new SelectSqlBean();
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		String objectName = jsonObject.getString("objectName");
		bean.setTableName(objectName);
		String extendField = jsonObject.getString("extendField");
		String filed = jsonObject.getString("getChildName");
		bean.setField(filed);
		String order = jsonObject.getString("order");
		bean.setOrder(order);
		String dataSource = jsonObject.getString("dataSource");
		bean.setDataSource(dataSource);
	    if(jsonObject.getInteger("currentPage")!=null&&jsonObject.getInteger("pageRows")!=null){
		Pagination page = new Pagination();
	    currentPage = jsonObject.getInteger("currentPage");
	    pageRows = jsonObject.getInteger("pageRows");
	    page.setCurrent(currentPage);
	    page.setCountOfPage(pageRows);
	    bean.setPag(page);
	    }
	    if(jsonObject.containsKey("whereEQ")){
	    for(java.util.Map.Entry<String,Object> entry:jsonObject.getJSONObject("whereEQ").entrySet()){  
	    	bean.addMapWhereEQ(entry.getKey(), entry.getValue());
		 }
	    }
	    if(jsonObject.containsKey("whereIS")){
	    for(java.util.Map.Entry<String,Object> entry:jsonObject.getJSONObject("whereIS").entrySet()){  
	    	bean.addMapWhereIS(entry.getKey(), (Boolean)entry.getValue());
		 }
	    }
	    if(jsonObject.containsKey("whereIN")){
	    for(java.util.Map.Entry<String,Object> entry:jsonObject.getJSONObject("whereIN").entrySet()){  
	    	bean.addMapWhereIN(entry.getKey(), entry.getValue());
		 }
	    }
	    return bean;
	}
	public static InsertBean jsonStringToInsert(String jsonString){
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		JSONObject jsonObjectData = jsonObject.getJSONObject("data");
		String tableName = jsonObject.getString("objectName");
		String dataSource = jsonObject.getString("dataSource");
		Iterator iterator = (Iterator) jsonObjectData.keySet();
        InsertBean bean = new InsertBean();
		while(iterator.hasNext()){
			String key = (String) iterator.next();
			Object value = jsonObjectData.get("key");
			bean.addMapInsert(key, value);
		}
        bean.setTableName(tableName);
		bean.setDataSource(dataSource);               
		return bean;
	}
    public static UpdateBean jsonStringToUpdate(String jsonString){
		UpdateBean bean = new UpdateBean();
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		String tableName = jsonObject.getString("objectName");
		String dataSource = jsonObject.getString("dataSource");
	    if(jsonObject.containsKey("whereEQ")){
	    for(java.util.Map.Entry<String,Object> entry:jsonObject.getJSONObject("whereEQ").entrySet()){  
	    	bean.addMapWhereEQ(entry.getKey(), entry.getValue());
		 }
	    }
	    if(jsonObject.containsKey("whereIS")){
	    for(java.util.Map.Entry<String,Object> entry:jsonObject.getJSONObject("whereIS").entrySet()){  
	    	bean.addMapWhereIS(entry.getKey(), (Boolean)entry.getValue());
		 }
	    }
	    if(jsonObject.containsKey("whereIN")){
	    for(java.util.Map.Entry<String,Object> entry:jsonObject.getJSONObject("whereIN").entrySet()){  
	    	bean.addMapWhereIN(entry.getKey(), entry.getValue());
		 }
	    }
        bean.setTableName(tableName);
		bean.setDataSource(dataSource);               
		return bean;    
	}
    public static DeleteBean jsonStringToDelete(String jsonString){
		DeleteBean bean = new DeleteBean();
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		String tableName = jsonObject.getString("objectName");
		String dataSource = jsonObject.getString("dataSource");
	    if(jsonObject.containsKey("whereEQ")){
	    for(java.util.Map.Entry<String,Object> entry:jsonObject.getJSONObject("whereEQ").entrySet()){  
	    	bean.addMapWhereEQ(entry.getKey(), entry.getValue());
		 }
	    }
	    if(jsonObject.containsKey("whereIS")){
	    for(java.util.Map.Entry<String,Object> entry:jsonObject.getJSONObject("whereIS").entrySet()){  
	    	bean.addMapWhereIS(entry.getKey(), (Boolean)entry.getValue());
		 }
	    }
	    if(jsonObject.containsKey("whereIN")){
	    for(java.util.Map.Entry<String,Object> entry:jsonObject.getJSONObject("whereIN").entrySet()){  
	    	bean.addMapWhereIN(entry.getKey(), entry.getValue());
		 }
	    }
        bean.setTableName(tableName);
		bean.setDataSource(dataSource);               
		return bean;    
	}
	public static Map<String , Object> jsonToMapUtil(JSONObject jsonObject){
	   	 Iterator iteratorJson = (Iterator) jsonObject.keySet();
	   	 Map<String,Object> map = new HashMap<String,Object>();
	   	 while(iteratorJson.hasNext()){
	   		 String key = (String)iteratorJson.next();
	   		 map.put(key,jsonObject.get(key));
	   	 }
		return map;
	}
	

}
