package cn.com.wavenet.service;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;

import cn.com.wavenet.bean.DeleteBean;
import cn.com.wavenet.bean.InsertBean;
import cn.com.wavenet.bean.SelectSqlBean;
import cn.com.wavenet.bean.UpdateBean;

/**
 * @author chenjl
 * @createDate 2017年6月2日 功能描述：数据库操作
 * @datetime 下午4:48:54
 * @version 1.0
 * 
 */

public interface DataManager {
	public int getSequence(Map<String , Object> map) throws Exception;// 返回seq

	public String getMaxStringValue(String tableName, String tablefield) throws Exception;// 返回最大值

	public long getMaxValue(String tableName, String tablefield) throws Exception;// 返回最大值

	public JSONObject findByPrimaryKey(SelectSqlBean bean) throws Exception;

	public JSONObject find(SelectSqlBean bean) throws Exception;

	JSONObject insert(InsertBean bean) throws Exception;

	JSONObject update(UpdateBean bean) throws Exception;

	public JSONObject findCross(SelectSqlBean bean) throws Exception;

	JSONObject delete(DeleteBean bean);

	public JSONObject findBySql(SelectSqlBean bean) throws Exception;

	List<Map> findAllColumn(SelectSqlBean bean) throws SQLException, NullPointerException, IndexOutOfBoundsException;

	boolean insertOfBoolean(InsertBean bean) throws SQLException;

	int insert(String sql) throws SQLException;

	boolean insertOfBoolean(String sql) throws SQLException;

	boolean updateOfBoolean(UpdateBean bean) throws SQLException;

	boolean updateOfBoolean(String sql) throws SQLException;

	int update(String sql) throws SQLException;

	boolean deleteOfBoolean(DeleteBean bean);

	boolean deleteOfBoolean(String sql) throws SQLException;

	int delete(String sql) throws SQLException;

	List<Map<String, Object>> findBySqlToList(SelectSqlBean bean) throws SQLException, NullPointerException;

	Map<String,Object> findByPrimaryKeyToMap(SelectSqlBean bean)
			throws SQLException, NullPointerException, IndexOutOfBoundsException;

	JSONObject insertCross(InsertBean bean);

	JSONObject updateCross(UpdateBean bean);

	JSONObject deleteCross(DeleteBean bean);
}
