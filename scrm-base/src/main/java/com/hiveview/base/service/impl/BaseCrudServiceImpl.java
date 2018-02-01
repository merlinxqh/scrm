package com.hiveview.base.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hiveview.base.dao.CrudMapper;
import com.hiveview.base.exception.ServiceException;
import com.hiveview.base.mybatis.page.Page;
import com.hiveview.base.service.BaseCrudService;
import org.springframework.util.CollectionUtils;

/**
 * service基类实现
 */
public abstract class BaseCrudServiceImpl<T> implements BaseCrudService<T> {
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	private CrudMapper mapper;
	
	@PostConstruct
	private void initConfig() {
		this.mapper = init();
	}
	
	public abstract CrudMapper init();
	
	
	@Override
	@Transactional
	public int saveData(T entity) throws ServiceException{
		try {
			return mapper.insertSelective(entity);
		} catch (Exception e) {
			throw new ServiceException("",e.getMessage());
		}
	}

	@Override
	@Transactional
	public int updateData(T entity) throws ServiceException {
		try {
			return mapper.updateByPrimaryKeySelective(entity);
		} catch (Exception e) {
			throw new ServiceException("",e.getMessage());
		}
	}

	@Override
	@Transactional
	public int deleteById(Long id) throws ServiceException {
		try {
			return mapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			throw new ServiceException("",e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(Long id) throws ServiceException {
		try {
			return (T) mapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			throw new ServiceException("",e.getMessage());
		}
	}

	@Override
	public T findOneByBiz(Map<String, Object> params) throws ServiceException {
		List<T> resList=findByBiz(params);
		if(!CollectionUtils.isEmpty(resList)){
			return resList.get(0);
		}
		return null;
	}

	@Override
	public List<T> findByBiz(Map<String, Object> params) throws ServiceException {
		try {
			return mapper.selectByParams(params);
		} catch (Exception e) {
			throw new ServiceException("",e.getMessage());
		}
	}

	@Override
	public int findCount(Map<String,Object> params) throws ServiceException {
		try {
			return mapper.selectCount(params);
		} catch (Exception e) {
			throw new ServiceException("",e.getMessage());
		}
	}

	@Override
	public void findByPage(Page page) throws ServiceException {
		findByPage(page,null);
	}
	
	@Override
	public void findByPage(Page page, Map<String,Object> params)throws ServiceException{
		findByPage(page,null,null,params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void findByPage(Page page, String orderByField,
			String orderBy,Map<String,Object> params) throws ServiceException {
		try {
			page.setRecords(mapper.selectByPage(page, orderByField, orderBy, params));
		} catch (Exception e) {
			throw new ServiceException("",e.getMessage());
		}
	}
}
