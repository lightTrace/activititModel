package cn.com.wavenet.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class MybaitsMapChangeUtil {
	
	public Map insertMapChange(String tableName , Map<String,Object> param) {
		  String[] keys = new String[param.size()];
		  Set<String> sset = param.keySet();
	  	  int i = 0;
	  	  for(String os : sset){
	  		  keys[i++] = os;
	  	  }
	   	  Map<String, Object> map=new HashMap<String, Object>();
	  	  map.put("tablename" , tableName);
	  	  map.put("keys" , keys);
	  	  map.put("params" , param); 
	  	  return map;
	}

	public Map updateMapChange(String tableName,Map<String, Object> paramsSET,Map<String, Object> paramsWhereEQ,Map<String, Object> paramsWhereIN) {
		Map<String, Object> map=new HashMap<String, Object>();
   		String[] keys = new String[paramsSET.size()];
           Set<String> sset = paramsSET.keySet();
           int i = 0;
           for (String os : sset) {
               keys[i++] = os;
           }
           String[] keys2 = new String[paramsWhereEQ.size()];
           Set<String> sset2 = paramsWhereEQ.keySet();
           i = 0;
           for (String os : sset2) {
               keys2[i++] = os;
           }
 	  	  if(!paramsWhereIN.isEmpty()){
 			  Set<String> keysIn = paramsWhereIN.keySet();
 			  for(String key :keysIn){
 				  System.out.println(key+" "+paramsWhereIN.get(key));
 				   map.put("inKey",key);
 				   String inValue = (String) paramsWhereIN.get(key);
 				   String [] inValueArr= inValue.split(",");
 				   map.put("inValues",inValueArr);
 			  }
 		  }
           map.put("tablename", tableName);
           map.put("keys", keys);
           map.put("params", paramsSET);
           map.put("keys2", keys2);
           map.put("params2", paramsWhereEQ);
  	       return map;
	}
	
	public Map findMapChange(Map<String,Object> map) {
		Map paramsCondition = (Map) map.get("params");
		  String[] keys = new String[paramsCondition.size()];
	  	  Set<String> sset = paramsCondition.keySet();
	  	  int i = 0;
	  	  for(String os : sset){
	  		  keys[i++] = os;
	  	  }
	  	  map.put("keys" , keys);
	  	  
	  	  if(map.get("filed")==null||map.get("filed").equals("")){
	  		  map.put("columns","*");
	  	  }else{
	  		  map.put("columns",map.get("filed"));
	  	  }
	  	  if(map.get("paramsIn")!=null &&!map.get("paramsIn").equals("")){
		  Map paramsIn= (Map) map.get("paramsIn");
		  Set<String> keysIn = paramsIn.keySet();
		  for(String key :keysIn){
			  System.out.println(key+" "+paramsIn.get(key));
			   map.put("inKey",key);
			   String inValue = (String) paramsIn.get(key);
			   String [] inValueArr= inValue.split(",");
			   map.put("inValues",inValueArr);
		  }
	  	  }
	  	  if(map.containsKey("paramsIsNull")){
	  		  Map<String , Object> paramsIsNull = (Map<String,Object>) map.get("paramsIsNull");
			  String[] keysIsNull = new String[paramsIsNull.size()];
		  	  Set<String> ssetIsNull = paramsIsNull.keySet();
		  	  int j = 0;
		  	  for(String osIsNull : ssetIsNull){
		  		keysIsNull[j++] = osIsNull;
		  	  }
		  	  map.put("keysIsNull" , keysIsNull);
	  	  }
	  	  return map;
	}
	public Map deleteMapChange(String tableName,Map<String, Object> paramsWhereEQ,Map<String, Object> paramsWhereIN) {
		Map<String, Object> map=new HashMap<String, Object>();
   		
           String[] keys = new String[paramsWhereEQ.size()];
           Set<String> sset2 = paramsWhereEQ.keySet();
           int i = 0;
           for (String os : sset2) {
               keys[i++] = os;
           }
 	  	  if(paramsWhereIN!=null){
 			  Set<String> keysIn = paramsWhereIN.keySet();
 			  for(String key :keysIn){
 				  System.out.println(key+" "+paramsWhereIN.get(key));
 				   map.put("inKey",key);
 				   String inValue = (String) paramsWhereIN.get(key);
 				   String [] inValueArr= inValue.split(",");
 				   map.put("inValues",inValueArr);
 			  }
 		  }
           map.put("tablename", tableName);
           map.put("keys", keys);
           map.put("params", paramsWhereEQ);
  	       return map;
	}
	public Map<String , Object> findCrossMapChange(Map map){
		  System.out.println("**********");
		  Map paramsCondition = (Map) map.get("paramsMain");
		  String[] keys = new String[paramsCondition.size()];
	  	  Set<String> sset = paramsCondition.keySet();
	  	  int i = 0;
	  	  for(String os : sset){
	  		  keys[i++] = os;
	  	  }
	  	  map.put("keys" , keys);
	  	  System.out.println("filed*******"+map.get("filedMain"));
	  	  if(map.get("filedMain")==null||map.get("filedMain").equals("")){
	  		  map.put("filedMain","*");
	  	  }else{
	  		  map.put("filedMain",map.get("filedMain"));
	  	  }
	  	  System.out.println("paramsInMain"+map.get("paramsInMain"));
	  	  if(map.get("paramsInMain")!=null &&!map.get("paramsInMain").equals("")){
		  Map paramsInMain= (Map) map.get("paramsInMain");
	  	  }
	  	  
	  	  if(map.get("paramsInFlow")!=null &&!map.get("paramsInFlow").equals("")){
	  	  Map paramsInFlow= (Map) map.get("paramsInFlow");
		  Set<String> keysIn = paramsInFlow.keySet();
		  for(String key :keysIn){
			  System.out.println(key+" "+paramsInFlow.get(key));
			   map.put("inKeyFlow",key);
			   String inValues2 = (String) paramsInFlow.get(key);
			   map.put("inValuesFlow",inValues2);
		  }
	  	  }
	  	  System.out.println("map************"+map);
	  	  return map;
	}
	
}
