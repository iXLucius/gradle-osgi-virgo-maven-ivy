/**
 * 
 */
package org.lucius.framework.orm.mybatis.interceptor;

/**
 * mysql 分页方言
 * @author zhongli@isoftstone.com
 *
 */
public class MysqlDialect extends Dialect {

	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.core.query.mybatis.interceptor.Dialect#getLimitString(java.lang.String, int, int)
	 */
	@Override
	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim();    
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100); 
        pagingSelect.append(sql).append(" LIMIT ").append(offset).append(",").append(limit);
		return pagingSelect.toString();
	}

}
