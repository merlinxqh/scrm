package com.hiveview.admin.rpc.pms;

import com.hiveview.common.api.PageDto;
import com.hiveview.pms.api.SysRoleApiService;
import com.hiveview.pms.dto.RoleResourceDto;
import com.hiveview.pms.dto.SysRoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by leo on 2017/11/6.
 */
@Component
public class SysRoleApiConsumer {

    @Autowired
    private SysRoleApiService sysRoleApiService;

    /**
     * 查找数据
     * @param dto
     * @return
     */
    public List<SysRoleDto> findList(SysRoleDto dto){
        return sysRoleApiService.findList(dto);
    }

    public SysRoleDto findById(String id){
        Assert.hasText(id);
        return sysRoleApiService.findById(Long.valueOf(id));
    }
    /**
     * 查询分页数据
     * @param page
     * @param
     * @return
     */
    public PageDto<SysRoleDto> getPageData(PageDto<SysRoleDto> page, SysRoleDto dto){
        return sysRoleApiService.findPage(page,dto);
    }


    /**
     * 数据保存
     * @param dto
     * @return
     */
    public int saveData(SysRoleDto dto){
        return sysRoleApiService.saveData(dto);
    }


    /**
     * 数据 启用 禁用
     * @param dto
     * @return
     */
    public int modifyData(SysRoleDto dto){
        return sysRoleApiService.modifyData(dto);
    }


    /**
     * 角色资源设置保存
     * @param dto
     */
    public void roleResourceSave(RoleResourceDto dto){
        sysRoleApiService.roleResourceSave(dto);
    }


}
