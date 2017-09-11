package cn.com.wavenet.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.abel533.sql.SqlMapper;

import cn.com.wavenet.bean.DeleteBean;
import cn.com.wavenet.bean.InsertBean;
import cn.com.wavenet.bean.SelectSqlBean;
import cn.com.wavenet.bean.UpdateBean;
import cn.com.wavenet.dao.IBasicDao;
import cn.com.wavenet.factory.DbContextHolder;
import cn.com.wavenet.service.DataManager;
import cn.com.wavenet.util.JSONUtil;
import cn.com.wavenet.util.MybaitsMapChangeUtil;

/**
 * @author liwd
 * @date 2017年7月21日
 * @description 核心服务dao
 */
@Service
public class DataManagerImpl  implements DataManager {
	
	@Resource
	IBasicDao iBasicDao;
	@Resource
	MybaitsMapChangeUtil mybaitsMapChangeUtil;
	@Resource
	SqlMapper sqlMapper;
	
	private static Logger log = Logger.getLogger(DataManagerImpl.class);
	
	//获得自增序列
	@Override
	public int getSequence(Map<String , Object> map) throws SQLException {
		return iBasicDao.getSequenceId(map);
		
	}

    //根据主键或者唯一性标识查询单条记录的方法
	@Override
	public JSONObject findByPrimaryKey(SelectSqlBean  bean) throws SQLException,NullPointerException,IndexOutOfBoundsException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapResult = new HashMap<String,Object>();

		map.put("tableName", bean.getTableName());
		map.put("filed", bean.getField());
		map.put("order", bean.getOrder());
		map.put("params", bean.getMapWhereEQ());
		map.put("paramsSign", bean.getMapSIGN());
		map.put("paramsIn", bean.getMapWhereIN());
		map.put("paramsIsNull", bean.getMapWhereIS());
		
		DbContextHolder.setDbType(bean.getDataSource()); 
		mapResult =(Map<String, Object>) iBasicDao.find(mybaitsMapChangeUtil.findMapChange(map)).get(0);
        System.out.println(mapResult.get("NM_SID"));
		return JSONUtil.beanToResult(mapResult);
		
	}
    //根据主键或者唯一性标识查询单条记录的方法返回map
	@Override
	public Map<String,Object> findByPrimaryKeyToMap(SelectSqlBean  bean) throws SQLException,NullPointerException,IndexOutOfBoundsException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> mapResult = new HashMap<String,Object>();

		map.put("tableName", bean.getTableName());
		map.put("filed", bean.getField());
		map.put("order", bean.getOrder());
		map.put("params", bean.getMapWhereEQ());
		map.put("paramsSign", bean.getMapSIGN());
		map.put("paramsIn", bean.getMapWhereIN());
		map.put("paramsIsNull", bean.getMapWhereIS());
		
		DbContextHolder.setDbType(bean.getDataSource()); 
		mapResult =(Map<String, Object>) iBasicDao.find(mybaitsMapChangeUtil.findMapChange(map)).get(0);
        System.out.println(mapResult.get("NM_SID"));
		return mapResult;
		
	}
    //根据一系列where条件查询多条或单条记录的方法
	@Override
	public JSONObject find(SelectSqlBean  bean)throws SQLException,NullPointerException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tableName", bean.getTableName());
		map.put("filed", bean.getField());
		map.put("order", bean.getOrder());
		map.put("params", bean.getMapWhereEQ());
		map.put("paramsSign", bean.getMapSIGN());
		map.put("paramsIn", bean.getMapWhereIN());
		map.put("paramsIsNull", bean.getMapWhereIS());
        System.out.println("map"+map);
		List<Map<String , Object>> listResult = new ArrayList<Map<String , Object>>();
		DbContextHolder.setDbType(bean.getDataSource()); 
		 
		 if(bean.getPag()!=null&&!bean.getPag().equals("")){
			 map.put("page", bean.getPag());
			 listResult = (List<Map<String, Object>>) iBasicDao.findByPage(mybaitsMapChangeUtil.findMapChange(map));
		 }else{
		 listResult = iBasicDao.find(mybaitsMapChangeUtil.findMapChange(map));
		 }

		return JSONUtil.CollectionToResult(listResult,bean.getPag());
	}
	//可直接传入sql语句进行查询
	@Override
	public JSONObject findBySql(SelectSqlBean  bean)throws SQLException,NullPointerException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String , Object>> listResult = new ArrayList<Map<String , Object>>();
		DbContextHolder.setDbType(bean.getDataSource());
		
		if(bean.getPag()!=null&&!bean.getPag().equals("")){
			map.put("sql", bean.getSql());
			map.put("page", bean.getPag());
			listResult = iBasicDao.findBySqlByPage(map);			
		}else{
			listResult = iBasicDao.findBySql(bean.getSql());			
		}
		
		return JSONUtil.CollectionToResult(listResult,bean.getPag());
	}
	
	@Override
	public List<Map<String , Object>> findBySqlToList(SelectSqlBean  bean)throws SQLException,NullPointerException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String , Object>> listResult = new ArrayList<Map<String , Object>>();
		DbContextHolder.setDbType(bean.getDataSource());
		
		if(bean.getPag()!=null&&!bean.getPag().equals("")){
			map.put("sql", bean.getSql());
			map.put("page", bean.getPag());
			listResult = iBasicDao.findBySqlByPage(map);			
		}else{
			listResult = iBasicDao.findBySql(bean.getSql());			
		}
		
		return listResult;
	}
	//主从交叉表联合查询
	@Override
	public JSONObject findCross(SelectSqlBean bean)throws SQLException,NullPointerException{
	
		Map<String,Object> map = new HashMap<String,Object>();
		SelectSqlBean detailBean = bean.getDetailBean();
		map.put("pkeyMain", bean.getPkey());
        map.put("tableNameMain", bean.getTableName());
		map.put("filedMain", bean.getField());
		map.put("paramsMain", bean.getMapWhereEQ());
		map.put("paramsInMain", bean.getMapWhereIN());
		map.put("extendSqlMain", bean.getExtendSql());
		map.put("orderMain", bean.getOrder());
		
		map.put("tableNameFlow", detailBean.getTableName());
		map.put("pkeyFlow", detailBean.getPkey());
		map.put("filedFlow", detailBean.getField());
		map.put("groupFlow", detailBean.getGroup());
		map.put("paramsInFlow", detailBean.getMapWhereIN());
		
		List<Map<String , Object>> listResult = new ArrayList<Map<String , Object>>();
		System.out.println("dataMangager的map"+map);
		DbContextHolder.setDbType(bean.getDataSource()); 
		if(bean.getPag()!=null&&!bean.getPag().equals("")){
			map.put("page", bean.getPag());
			listResult = iBasicDao.findCrossByPage(mybaitsMapChangeUtil.findCrossMapChange(map));
		}else{
		listResult = iBasicDao.findCross(mybaitsMapChangeUtil.findCrossMapChange(map));
		}
		return JSONUtil.CollectionToResult(listResult,bean.getPag());
		}
	//主从交叉表联合插入数据
	@Override
	public JSONObject insertCross(InsertBean bean){
		InsertBean detailInsertBean = bean.getInsertDetailBean();
		boolean flag = true;
		String message = "保存数据成功";
		Map<String ,Object> mapI = bean.getMapInsert();
		Map<String ,Object> mapIS = detailInsertBean.getMapInsert();
		Map<String ,Object> mapISChange = detailInsertBean.getMapInsert();

        String tableName = bean.getTableName();
        String tableNameIS = detailInsertBean.getTableName();

        DbContextHolder.setDbType(bean.getDataSource()); 
        try{
		iBasicDao.insert(mybaitsMapChangeUtil.insertMapChange(tableName,mapI));
		for(Map.Entry<String , Object> entry : mapIS.entrySet() ){
			mapISChange.put("nm_pid", mapI.get("nm_sid"));//主键关联字段
			mapISChange.put("st_name",entry.getKey());
			mapISChange.put("st_content",entry.getValue());
			iBasicDao.insert(mybaitsMapChangeUtil.insertMapChange(tableNameIS,mapISChange));
		}
        }catch(Exception e){
			flag = false;
			message = "数据保存失败";
		}
        JSONObject resultJson = new JSONObject();
        resultJson.put("result", flag);
        resultJson.put("resultMessage", message);
        return resultJson;
	}
	//主从交叉表联合更新数据
	@Override
	public JSONObject updateCross(UpdateBean bean){
		UpdateBean UpdateDetailBean = bean.getUpdateDetailBean();
		boolean flag = true;
		String message = "更新数据成功";		
		String tableName = (String) bean.getTableName();
		Map paramsSET = bean.getMapSet();
		Map paramsWhereEQ = bean.getMapWhereEQ();
		Map paramsWhereIN = bean.getMapWhereIN();
		if(paramsWhereEQ.isEmpty()&&paramsWhereIN.isEmpty()){
    		throw new NullPointerException();
		}
		String tableNameIS = (String) UpdateDetailBean.getTableName();
		Map paramsSETIS = UpdateDetailBean.getMapSet();
		Map paramsWhereEQIS = UpdateDetailBean.getMapWhereEQ();
		Map paramsWhereINIS = UpdateDetailBean.getMapWhereIN();
		if(paramsWhereEQIS.isEmpty()&&paramsWhereINIS.isEmpty()){
			flag = false;
			message = "更新数据失败";
    		throw new NullPointerException();
		}
		DbContextHolder.setDbType(bean.getDataSource()); 
		try{
		iBasicDao.update(mybaitsMapChangeUtil.updateMapChange(tableName,paramsSET,paramsWhereEQ,paramsWhereIN));		
		iBasicDao.update(mybaitsMapChangeUtil.updateMapChange(tableNameIS,paramsSETIS,paramsWhereEQIS,paramsWhereINIS));		
		}catch(Exception e){
			flag = false;
			message = "数据更新失败";
		}
        JSONObject resultJson = new JSONObject();
        resultJson.put("result", flag);
        resultJson.put("resultMessage", message);
        return resultJson;
	}
	//主从交叉表联合删除数据
	@Override
	public JSONObject deleteCross(DeleteBean bean){
        JSONObject resultJson = new JSONObject();
        DeleteBean DeleteDetailBean = bean.getDeleteDetailBean();
		boolean flag = true;
		String message = "删除数据成功";		
		String tableName = (String) bean.getTableName();
		Map paramsWhereEQ = bean.getMapWhereEQ();
		Map paramsWhereIN = bean.getMapWhereIN();		
		DbContextHolder.setDbType(bean.getDataSource()); 
		if(paramsWhereEQ.isEmpty()&&paramsWhereIN.isEmpty()){
			flag = false;
    		throw new NullPointerException();
		}
		String tableNameIS = (String) bean.getTableName();
		Map paramsWhereEQIS = bean.getMapWhereEQ();
		Map paramsWhereINIS = bean.getMapWhereIN();		
		DbContextHolder.setDbType(bean.getDataSource()); 
		if(paramsWhereEQIS.isEmpty()&&paramsWhereINIS.isEmpty()){
			flag = false;
    		throw new NullPointerException();
		}
		try{
		iBasicDao.delete(mybaitsMapChangeUtil.deleteMapChange(tableName,paramsWhereEQ,paramsWhereIN));		
		iBasicDao.delete(mybaitsMapChangeUtil.deleteMapChange(tableNameIS,paramsWhereEQIS,paramsWhereINIS));		

		}catch(Exception e){
			flag = false;
			message = "数据删除失败";
		}
        resultJson.put("flag", flag);
        resultJson.put("message", message);
		return resultJson;
	}
    //插入数据
	@Override
	public JSONObject insert(InsertBean bean) throws SQLException {
        JSONObject resultJson = new JSONObject();
		boolean flag = true;
		String message = "保存数据成功";
		Map<String ,Object> map = bean.getMapInsert();
        String tableName = bean.getTableName();
        DbContextHolder.setDbType(bean.getDataSource()); 
        try{
		iBasicDao.insert(mybaitsMapChangeUtil.insertMapChange(tableName,map));
		}catch(Exception e){
			flag = false;
			message = "数据保存失败";
			log.debug(e);
		}
        resultJson.put("flag", flag);
        resultJson.put("message", message);
		return resultJson;
	}
	@Override
	public boolean insertOfBoolean(InsertBean bean) throws SQLException {
		boolean flag = true;
		Map<String ,Object> map = bean.getMapInsert();
        String tableName = bean.getTableName();
        DbContextHolder.setDbType(bean.getDataSource()); 
        try{
		iBasicDao.insert(mybaitsMapChangeUtil.insertMapChange(tableName,map));
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}
	@Override
	public int insert(String sql) throws SQLException {
		return sqlMapper.insert(sql);
	}
	@Override
	public boolean insertOfBoolean(String sql) throws SQLException {
		Boolean flag =true;
		try{
		sqlMapper.insert(sql);
		}catch(Exception ep){
			flag = false;
		}
		return flag;
	}

    //更新数据
	@Override
	public JSONObject update(UpdateBean bean){
        JSONObject resultJson = new JSONObject();
		boolean flag = true;
		String message = "更新数据成功";		
		String tableName = (String) bean.getTableName();
		Map paramsSET = bean.getMapSet();
		Map paramsWhereEQ = bean.getMapWhereEQ();
		Map paramsWhereIN = bean.getMapWhereIN();
		System.out.println("paramsWhereEQ="+paramsWhereEQ);
		if(paramsWhereEQ.isEmpty()&&paramsWhereIN.isEmpty()){
    		throw new NullPointerException();
		}
		DbContextHolder.setDbType(bean.getDataSource()); 
//		try{
		System.out.println(new MybaitsMapChangeUtil().updateMapChange(tableName,paramsSET,paramsWhereEQ,paramsWhereIN));
		iBasicDao.update(mybaitsMapChangeUtil.updateMapChange(tableName,paramsSET,paramsWhereEQ,paramsWhereIN));		
//		}catch(Exception e){
//			flag = false;
//			message = "数据更新失败";
//			log.debug(e);
//		}
        resultJson.put("flag", flag);
        resultJson.put("message", message);
		return resultJson;
	}
    //更新数据
	@Override
	public boolean updateOfBoolean(UpdateBean bean) throws SQLException {
		boolean flag = true;
		String tableName = (String) bean.getTableName();
		Map paramsSET = bean.getMapSet();
		Map paramsWhereEQ = bean.getMapWhereEQ();
		Map paramsWhereIN = bean.getMapWhereIN();
		if(paramsWhereEQ.isEmpty()&&paramsWhereIN.isEmpty()){
    		throw new NullPointerException();
		}
		DbContextHolder.setDbType(bean.getDataSource()); 
		try{
		iBasicDao.update(mybaitsMapChangeUtil.updateMapChange(tableName,paramsSET,paramsWhereEQ,paramsWhereIN));		
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}
	@Override
	public int update(String sql) throws SQLException {
		return sqlMapper.update(sql);
	}
	@Override
	public boolean updateOfBoolean(String sql) throws SQLException {
		Boolean flag =true;
		try{
		sqlMapper.update(sql);
		}catch(Exception ep){
			flag = false;
		}
		return flag;
	}
    //删除数据
	@Override
	public JSONObject delete(DeleteBean bean) {
        JSONObject resultJson = new JSONObject();
		boolean flag = true;
		String message = "删除数据成功";		
		String tableName = (String) bean.getTableName();
		Map paramsWhereEQ = bean.getMapWhereEQ();
		Map paramsWhereIN = bean.getMapWhereIN();		
		DbContextHolder.setDbType(bean.getDataSource()); 
		if(paramsWhereEQ.isEmpty()&&paramsWhereIN.isEmpty()){
			flag = false;
    		throw new NullPointerException();
		}
		try{
		iBasicDao.delete(mybaitsMapChangeUtil.deleteMapChange(tableName,paramsWhereEQ,paramsWhereIN));		
		}catch(Exception e){
			flag = false;
			message = "数据删除失败";
		}
        resultJson.put("flag", flag);
        resultJson.put("message", message);
		return resultJson;	
	}
	@Override
	public boolean deleteOfBoolean(DeleteBean bean) {
		boolean flag = true;
		String message = "删除数据成功";		
		String tableName = (String) bean.getTableName();
		Map paramsWhereEQ = bean.getMapWhereEQ();
		Map paramsWhereIN = bean.getMapWhereIN();		
		DbContextHolder.setDbType(bean.getDataSource()); 
		if(paramsWhereEQ.isEmpty()&&paramsWhereIN.isEmpty()){
			flag = false;
    		throw new NullPointerException();
		}
		try{
		iBasicDao.delete(mybaitsMapChangeUtil.deleteMapChange(tableName,paramsWhereEQ,paramsWhereIN));		
		}catch(Exception e){
			flag = false;
			message = "数据删除失败";
		}
		return flag;	
	}
	@Override
	public int delete(String sql) throws SQLException {
		return sqlMapper.update(sql);
	}
	@Override
	public boolean deleteOfBoolean(String sql) throws SQLException {
		Boolean flag =true;
		try{
		sqlMapper.delete(sql);
		}catch(Exception ep){
			flag = false;
		}
		return flag;
	}
	//跟数据库字段查询有关，调用该方法filed一定为大写字段
	@Override
	public List<Map> findAllColumn(SelectSqlBean bean) throws SQLException,NullPointerException{
		String fields = "";
		Map<String,Object> mapColumn = new HashMap<String,Object>();  
        DbContextHolder.setDbType(bean.getDataSource());
        mapColumn.put("tableName", bean.getTableName());
        if(bean.getField()!=null&&bean.getField()!=""&&bean.getField()!="*"){
        String field[] = bean.getField().split(",");
        mapColumn.put("field", field);
        }
		return iBasicDao.findAllColumn(mapColumn);
	}
	@Override
	public String getMaxStringValue(String tableName, String tablefield) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getMaxValue(String tableName, String tablefield) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}
