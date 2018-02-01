package com.hiveview.pms.api;

import com.hiveview.common.api.CrudApiService;
import com.hiveview.pms.dto.SysUserDto;

/**
 * Created by leo on 2017/10/24.
 */
public interface SysUserApiService extends CrudApiService<SysUserDto>{

    SysUserDto getUserByUserName(String userName);

    void modifyData(SysUserDto dto);

    void rpcTest();

}
