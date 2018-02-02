package com.hiveview.base.service;

import java.util.List;
import java.util.Map;

import com.hiveview.base.exception.ServiceException;
import com.hiveview.base.mybatis.page.Page;

/**
 * crud对应Service接口
 */
public interface BaseCrudService<T> {

	/**
	 * 根据ID删除数据
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public  int deleteById(Long id) throws ServiceException;

	/**
	 * 数据保存
	 * @param modelType
	 * @return
	 * @throws ServiceException
	 */
	public  int saveData(T modelType) throws ServiceException;

	/**
	 * 根据ID查找数据
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public  T findById(Long id) throws ServiceException;

	/**
	 * 根据参数查询 列表
	 *
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public  List<T> findByBiz(Map<String, Object> params) throws ServiceException;


	/**
	 * 根据参数查找一条数据
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public T findOneByBiz(Map<String, Object> params) throws ServiceException;

	/**
	 * 根据id修改实体
	 * 
	 * @param modelType
	 * @return
	 * @throws ServiceException
	 */
	public int updateData(T modelType) throws ServiceException;

	/**
	 * 根据参数查询总记录数
	 * 
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public int findCount(Map<String, Object> params) throws ServiceException;

	/**
	 * 不带查询参数的分页接口
	 * @param page
	 * @throws ServiceException
	 */
	public void findByPage(Page page) throws ServiceException;


	/**
	 * 没有排序参数的 分页接口
	 * @param page
	 * @param params
	 * @throws ServiceException
	 */
	public void findByPage(Page page, Map<String,Object> params) throws ServiceException;

	/**
	 * 带排序参数的分页接口
	 * 
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public  void findByPage(Page page, String orderByField,
			String orderBy, Map<String, Object> params) throws ServiceException;
}
