/**   
 * Copyright © 2016 本代码版权归周林波所有，严禁未经许可使用。
 * 
 * @Title: HibernateBaseDao.java 
 * @Prject: springweb
 * @Package: com.mckay.dao 
 * @Description:
 * @author:
 * @date: 2016年12月30日 下午8:54:47 
 * @version: V1.0   
 */
package com.mckay.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.util.Assert;




/** 
 * @ClassName: HibernateBaseDao 
 * @Description: TODO
 * @author: 周林波
 * @date: 2016年12月30日 下午8:54:47  
 */

public abstract class HibernateBaseDao<T, PK extends Serializable>  {
	

	private static final Logger log = Logger
			.getLogger(HibernateBaseDao.class);

	protected Class<T> entityClass;

	@Resource
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 用于扩展的DAO子类使用的构造函数.
	 * <p/>
	 * 通过子类的范型定义取得对象类型Class. eg. public class UserDao extends
	 * SimpleHibernateDao<User, Long>
	 */
	@SuppressWarnings("unchecked")
	public HibernateBaseDao() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	protected Session getSession() {
		return hibernateTemplate.getSessionFactory().getCurrentSession();
	}

	/**
	 * 保存新增或修改的对象.
	 */
	public void save(final T entity) {

		Assert.notNull(entity);

		hibernateTemplate.save(entity);
	}

	/**
	 * 保存新增或修改的对象.
	 */
	public void modify(final T entity) {

		Assert.notNull(entity);

		hibernateTemplate.update(entity);
	}

	/**
	 * 保存新增或修改的对象.
	 */
	public void saveOrUpdate(final T entity) {

		Assert.notNull(entity);

		hibernateTemplate.saveOrUpdate(entity);
	}

	/**
	 * 删除对象.
	 * 
	 * @param entity
	 *            对象必须是session中的对象或含id属性的transient对象.
	 */
	public void delete(final T entity) {
		if (entity == null) {
			return;
		}

		hibernateTemplate.delete(entity);
	}

	/**
	 * 按id删除对象.
	 */
	public void delete(final PK id) {
		if (id == null) {
			return;
		}
		delete(get(id));
	}

	/**
	 * 按id获取对象.
	 */
	public T get(final PK id) {
		Assert.notNull(id);
		return (T) hibernateTemplate.get(entityClass, id);
	}

	/**
	 * 获取全部对象.
	 */
	public List<T> getAll() {
		return findByCriteria();
	}

	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	public List<T> findByProperty(final String propertyName, final Object value) {
		Assert.hasText(propertyName);
		Criterion criterion = Restrictions.eq(propertyName, value);
		return findByCriteria(criterion);
	}

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	@SuppressWarnings("unchecked")
	public T findUniqueByProperty(final String propertyName, final Object value) {
		Assert.hasText(propertyName);
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数
	 */
	public int update(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final String hql, final Object... values) {
		return createQuery(hql, values).getResultList();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> findArray(final String hql, final Object... values) {
		return createQuery(hql, values).getResultList();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> findPage(final String hql, int limit, int size,
			final Object... values) {
		return createQuery(hql, values).setFirstResult(limit)
				.setMaxResults(size).getResultList();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数
	 */
	@SuppressWarnings("unchecked")
	public List<Object> findList(final String hql, final Object... values) {
		return createQuery(hql, values).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findIn(final String hql, final String placeHolder,
			final List<?> values) {

		return createQueryIn(hql, placeHolder, values).getResultList();
	}

	/**
	 * 按HQL查询唯一对象.
	 */
	@SuppressWarnings("unchecked")
	public T findUnique(final String hql, final Object... values) {
		return (T) createQuery(hql, values).getSingleResult(); 
	}

	/**
	 * 按HQL查询Integer类型结果.
	 */
	public Integer findInt(final String hql, final Object... values) {
		return (Integer) findUnique(hql, values);
	}

	/**
	 * 按HQL查询Long类型结果.
	 */
	public Long findLong(final String hql, final Object... values) {
		return (Long) findUnique(hql, values);
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * <p/>
	 * 返回对象类型不是Entity时可用此函数灵活查询.
	 * 
	 * @param values
	 *            数量可变的参数
	 */

	@SuppressWarnings("rawtypes")
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString);
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	@SuppressWarnings("rawtypes")
	public Query createQueryIn(final String queryString,
			final String placeHolder, final List<?> values) {
		Assert.hasText(queryString);
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setParameterList(placeHolder, values);
		}
		return query;
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}


	@SuppressWarnings("unchecked")
	public T findUnique(final Criterion... criterions) {
		return (T) createCriteria(criterions).uniqueResult();
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 * <p/>
	 * 返回对象类型不是Entity时可用此函数灵活查询.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	@SuppressWarnings("deprecation")
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * <p/>
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName,
			final Object newValue, final Object orgValue) {
		if (newValue == null || newValue.equals(orgValue))
			return true;
		Object object = findUniqueByProperty(propertyName, newValue);
		return (object == null);
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByLimit(final String hql, int limit,
			final Object... values) {
		return createQuery(hql, values).setFirstResult(limit).getResultList();
	}



	/**
	 * 
	 * 方法名 ：findMapBySQL<BR>
	 * 方法说明 ：根据ds查询记录<BR>
	 * 备注：无<BR>
	 * 
	 * @param sql
	 *            原生sql语句<BR>
	 * @param param
	 *            参数列表<BR>
	 * @param dsName
	 *            多ds情况下，指定的ds名称<BR>
	 * <BR>
	 * @return 记录结果集，返回字段对应Map集合<BR>
	 */
	public List<Map<String, Object>> findMapBySQL(String sql, List<Object> param) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Map<String, Object>> result = null;

		try {
			result = new ArrayList<Map<String, Object>>();
			conn = jdbcTemplate.getDataSource().getConnection();
			stmt = conn.prepareStatement(sql);
			fillStatement(stmt, param);
			rs = stmt.executeQuery();
			while (rs.next()) {
				result.add(toMap(rs));
			}
			return result;
		} catch (SQLException e) {
			log.error("", e);// :TODO 后续要引入异常框架
		} finally {
			try {
				closeAll(rs, stmt, conn);
			} catch (SQLException e) {
				log.error("", e);// :TODO 后续要引入异常框架
			}
		}

		return new ArrayList<Map<String, Object>>();
	}

	private Map<String, Object> toMap(ResultSet rs) throws SQLException {
		Map<String, Object> result = new HashMap<String, Object>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();

		for (int i = 1; i <= cols; i++) {
			result.put(rsmd.getColumnLabel(i).toUpperCase(), rs.getObject(i));
		}

		return result;
	}

	private void closeAll(ResultSet rs, PreparedStatement stmt, Connection conn)
			throws SQLException {
		if (rs != null) {
			rs.close();
		}

		if (stmt != null) {
			stmt.close();
		}

		if (conn != null) {
			conn.close();
		}
	}

	private void fillStatement(PreparedStatement stmt, List<Object> param)
			throws SQLException {

		// check the parameter count, if we can

		ParameterMetaData pmd = stmt.getParameterMetaData();
		int stmtCount = pmd.getParameterCount();
		int paramsCount = param == null ? 0 : param.size();

		if (stmtCount != paramsCount) {
			throw new SQLException("Wrong number of parameters: expected "
					+ stmtCount + ", was given " + paramsCount);
		}

		// nothing to do here
		if (param == null) {
			return;
		}

		for (int i = 0; i < param.size(); i++) {
			if (param.get(i) != null) {
				stmt.setObject(i + 1, param.get(i));
			} else {
				// VARCHAR works with many drivers regardless
				// of the actual column type. Oddly, NULL and
				// OTHER don't work with Oracle's drivers.
				int sqlType = Types.VARCHAR;

				try {
					sqlType = pmd.getParameterType(i + 1);
				} catch (SQLException e) {
					// pmdKnownBroken = true;
				}

				stmt.setNull(i + 1, sqlType);
			}
		}
	}

}
