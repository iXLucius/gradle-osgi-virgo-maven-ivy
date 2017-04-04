package org.lucius.framework.orm.mybatis.interceptor;

/**
 * pgsql 分页方言
 * @author sunsz@mpreader.com
 *
 */
public class PgsqlDialect extends Dialect {

	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.core.query.mybatis.interceptor.Dialect#getLimitString(java.lang.String, int, int)
	 */
	@Override
	public String getLimitString(String sql, int offset, int limit) {
		sql = sql.trim();    
        StringBuffer pagingSelect = new StringBuffer(sql.length() + 100); 
        pagingSelect.append(sql).append(" LIMIT ").append(limit).append(" OFFSET ").append(offset);
		return pagingSelect.toString();
	}

}
