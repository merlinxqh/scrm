package com.hiveview.pms.api;

import com.hiveview.base.util.serializer.ObjectUtils;
import com.hiveview.common.api.PageDto;
import com.hiveview.pms.common.WrapperApiService;
import com.hiveview.pms.dto.RoleResourceDto;
import com.hiveview.pms.dto.SysRoleDto;
import com.hiveview.pms.entity.sys.SysRole;
import com.hiveview.pms.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by leo on 2017/11/6.
 */
@Service
public class SysRoleApiServiceImpl implements SysRoleApiService{

    @Autowired
    private SysRoleService roleService;

    @Override
    public SysRoleDto findById(Long id) {
        SysRole role=roleService.findById(id);
        return ObjectUtils.copyObject(role,SysRoleDto.class);
    }

    @Override
    @Transactional
    public int saveData(SysRoleDto data) {
        return roleService.saveData(ObjectUtils.copyObject(data,SysRole.class));
    }

    @Override
    @Transactional
    public int deleteData(SysRoleDto data) {
        SysRole role=ObjectUtils.copyObject(data,SysRole.class);
        return roleService.deleteById(role.getId());
    }

    @Override
    public List<SysRoleDto> findList(SysRoleDto params) {
        List<SysRole> list=roleService.findByBiz(ObjectUtils.changeToMap(params));
        return ObjectUtils.copyListObject(list,SysRoleDto.class);
    }

    @Override
    public PageDto<SysRoleDto> findPage(PageDto<SysRoleDto> page, SysRoleDto params) {
        return WrapperApiService.findByPage(page,params,roleService,SysRoleDto.class);
    }

    @Override
    @Transactional
    public int modifyData(SysRoleDto dto) {
        return roleService.modifyData(ObjectUtils.copyObject(dto,SysRole.class));
    }

    @Override
    @Transactional
    public void roleResourceSave(RoleResourceDto dto) {
       roleService.roleResourceSave(dto);
    }
}
