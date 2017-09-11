package cn.com.wavenet.bean;

import com.alibaba.fastjson.JSONObject;

public class DeleteBean extends BasicSqlBean {

	public DeleteBean(){};
	private DeleteBean DeleteDetailBean;
	/**
	 * @author shil 
	 * @date 2017年6月27日 上午10:17:43 
	 * 物理删除构造方法
	 * @param pkey 主键的字段名
	 * @param pkeyValue  主键的值
	 * @param tableName  删除的表名
	 */
	public DeleteBean(String pkey,String pkeyValue,String tableName){
		this.pkey = pkey;
		this.pkeyValue = pkeyValue;
		this.tableName = tableName;
	}
	
	public DeleteBean getDeleteDetailBean() {
		if(DeleteDetailBean==null)
			DeleteDetailBean=new DeleteBean();
		return DeleteDetailBean;
	}

	public void setDeleteDetailBean(DeleteBean deleteDetailBean) {
		DeleteDetailBean = deleteDetailBean;
	}

	/**
	 * @author shil 
	 * @date 2017年6月27日 上午10:17:43 
	 * 物理删除构造方法(指定数据源)
	 * @param pkey 主键的字段名
	 * @param pkeyValue  主键的值
	 * @param tableName  删除的表名
	 * @param dataSource  指定删除的数据源
	 */
	public DeleteBean(String pkey,String pkeyValue,String tableName,String dataSource){
		this.pkey = pkey;
		this.pkeyValue = pkeyValue;
		this.tableName = tableName;
		this.dataSource = dataSource;
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

}
