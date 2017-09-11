package cn.com.wavenet.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import cn.com.wavenet.bean.Pagination;

/**
 * @author liwd
 * @date 2017年6月6日
 * 分页拦截器
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class MybaitsPageInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
		MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY, SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY);
		MappedStatement mappedStatement = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
		// 配置文件中SQL语句的ID
		String id = mappedStatement.getId();
		//拦截mapper的id字符串有ByPage的查询操作，将其分页
		if(id.matches(".+ByPage$")) {
			BoundSql boundSql = statementHandler.getBoundSql();
			// 原始的SQL语句
			String sql = boundSql.getSql();
			// 查询总条数的SQL语句
			String countSql = "select count(*) from (" + sql + ")a";
			Connection connection = (Connection)invocation.getArgs()[0];
			PreparedStatement countStatement = connection.prepareStatement(countSql);
			ParameterHandler parameterHandler = (ParameterHandler)metaObject.getValue("delegate.parameterHandler");
			parameterHandler.setParameters(countStatement);
			ResultSet rs = countStatement.executeQuery();
			
			Map<?,?> parameter = (Map<?,?>)boundSql.getParameterObject();
			Pagination page  = (Pagination) parameter.get("page");
			if(rs.next()) {
				page.setCount(rs.getInt(1));
			}
			// 改造后带分页查询的SQL语句
			String pageSql = "select * from (select t.*,ROWNUM rn  from ("+sql+") t WHERE ROWNUM <="+page.getEnd()+") where rn >="+page.getStart();
			metaObject.setValue("delegate.boundSql.sql", pageSql);
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);

	}

	@Override
	public void setProperties(Properties properties) {
		// TODO Auto-generated method stub

	}

}
