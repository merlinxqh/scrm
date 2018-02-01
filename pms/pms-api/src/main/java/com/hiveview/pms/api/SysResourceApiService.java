package com.hiveview.pms.api;

import com.hiveview.common.api.CrudApiService;
import com.hiveview.pms.dto.SysResourceDto;

import java.util.List;

/**
 * Created by leo on 2017/11/3.
 */
public interface SysResourceApiService extends CrudApiService<SysResourceDto>{

    /**
     * 根据角色获取 权限信息
     * @param roleSn
     * @return
     */
    List<SysResourceDto> getResourceByRole(String roleSn);
}
