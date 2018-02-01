package com.hiveview.base.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hiveview.base.mybatis.page.Page;


/**
 * DAO支持类
 * @param <T>
 */
public interface CrudMapper {

	/**
	 * 根据主键 删除数据
	 * @param id
	 * @return
	 */
	public int deleteByPrimaryKey(Long id);

	/**
	 * 插入entity
	 * @param entity
	 * @param <T>
	 * @return
	 */
	public <T> int insert(T entity);

	/**
	 * insert传入entity实例不为null的属性
	 * @param entity
	 * @param <T>
	 * @return
	 */
	public <T> int insertSelective(T entity);

	public <T> T selectByPrimaryKey(Long id);
	
	public <T> List<T> selectByParams(@Param("params") Map<String, Object> params);

	/**
	 * 更新传入entity实例不为null的属性
	 * @param entity
	 * @param <T>
	 * @return
	 */
	public <T> int updateByPrimaryKeySelective(T entity);

	/**
	 * 更新传入entity实例的所有属性
	 * @param entity
	 * @param <T>
	 * @return
	 */
	public <T> int updateByPrimaryKey(T entity);
	
	public int selectCount(@Param("params") Map<String, Object> params);

	/**
	 * 分页
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @param <T>
	 * @return
	 */
	public <T> List<T> selectByPage(@Param("page") Page<T> page,
			@Param("orderByField") String orderByField,
			@Param("orderBy") String orderBy,
			@Param("params") Map<String, Object> params);

	/**
	 * 根据传入entity实例 id属性删除数据
	 * @param entity
	 * @param <T>
	 * @return
	 */
	public <T> int deleteByPrimarayKeyForModel(T entity);

}