/**
 * 
 */
package org.lucius.framework.orm.mybatis.interceptor;

/**
 * @author zhongli@isoftstone.com 数据库方言类
 */
public abstract class Dialect {
	public static enum Type {
		MYSQL, ORACLE ,PGSQL
	}

	public abstract String getLimitString(String sql, int skipResults,
			int maxResults);
}
