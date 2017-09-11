package cn.com.wavenet.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

public interface IBasicDao {
	  
	   Map<String,Object> find_simple(String tableName,Map<String,Object> param);//查询
	   List find(Map<String,Object> param);//查询
	   void insert(Map<String,Object> param);//插入数据   
	   void update(Map<String,Object> param);//更新数据  
       void delete(Map<String,Object> param);//删除数据  
       void procedure(Map<String,Object> param);//调用数据库存储过程
       void function(Map<String,Object> param);//调用数据库函数
       List findByPage(Map<String,Object> map);//分页查询
	 
       long getMaxValue(String tableName, String tablefield);
	   void call_p_info_getTableField(Map<String, Object> param);
	   void call_p_info_GetCrossResult(Map<String, Object> param);
       Map findByPrimaryKey(String sql);
	   List<Map<String, Object>> findCross(Map<String , Object> map);
	   List<Map<String, Object>> findCrossByPage(Map<String , Object> map);

	   List findBySql(String sql);//查询
	   List findBySqlByPage(Map<String , Object> map);//分页查询
	   int getSequenceId(Map<String , Object> map);
       List<Map> findAllColumn(Map map);
}
