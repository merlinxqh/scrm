package com.hiveview.common.api;

import java.util.List;

/**
 * Created by leo on 2017/11/3.
 */
public interface CrudApiService<T> {

     /**
      * 新增保存
      * @param data
      * @return
      */
     int saveData(T data);

     /**
      * 删除
      * @param data
      * @return
      */
     int deleteData(T data);

     /**
      * 查询数据
      * @param params
      * @return
      */
     List<T> findList(T params);


     /**
      * 根据ID查询
      * @param id
      * @return
      */
     T findById(Long id);

     /**
      * 查询分页数据
      * @param page
      * @param params
      * @return
      */
     PageDto<T> findPage(PageDto<T> page, T params);
}
