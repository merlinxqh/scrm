package com.hiveview.pms.api;

import com.alibaba.dubbo.config.annotation.Service;
import com.hiveview.base.util.serializer.ObjectUtils;
import com.hiveview.common.api.PageDto;
import com.hiveview.config.dubbo.DubboConfiguration;
import com.hiveview.pms.common.WrapperApiService;
import com.hiveview.pms.dto.SysResourceDto;
import com.hiveview.pms.entity.sys.SysResource;
import com.hiveview.pms.entity.sys.SysRole;
import com.hiveview.pms.enums.RoleTypeEnums;
import com.hiveview.pms.service.SysResourceService;
import com.hiveview.pms.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by leo on 2017/11/6.
 */
@Service(registry = DubboConfiguration.ZOOKEEPER_SERVICE)
public class SysResourceApiServiceImpl implements SysResourceApiService {

    @Autowired
    private SysResourceService sysResourceService;

    @Autowired
    private SysRoleService roleService;

    @Override
    public SysResourceDto findById(Long id) {
        SysResource res=sysResourceService.findById(id);
        return ObjectUtils.copyObject(res,SysResourceDto.class);
    }

    @Override
    public List<SysResourceDto> getResourceByRole(String roleCode) {
        Map<String,Object> params=new HashMap<>();
        params.put("code",roleCode);
        SysRole role=roleService.findOneByBiz(params);
        if(null != role){
            Map<String,Object> map=new HashMap<>();
            if(!role.getRoleType().equals(RoleTypeEnums.SUPER_ADMIN.toString())){
                //非超级管理员
                map.put("roleCode",roleCode);
            }
            List<SysResource> resourceList=sysResourceService.findByBiz(map);
            return ObjectUtils.copyListObject(resourceList,SysResourceDto.class);
        }
        return null;
    }

    @Override
    @Transactional
    public int saveData(SysResourceDto data) {
        return sysResourceService.saveData(ObjectUtils.copyObject(data,SysResource.class));
    }

    @Override
    @Transactional
    public int deleteData(SysResourceDto data) {
        Assert.notNull(data,"参数不能为空");
        Assert.hasText(data.getId(),"ID不能为空");
        return sysResourceService.deleteById(Long.valueOf(data.getId()));
    }

    @Override
    public List<SysResourceDto> findList(SysResourceDto params) {
        List<SysResource> resList=sysResourceService.findByBiz(ObjectUtils.changeToMap(params));
        return resList.stream().map(res-> ObjectUtils.copyObject(res,SysResourceDto.class)).collect(Collectors.toList());
    }

    @Override
    public PageDto<SysResourceDto> findPage(PageDto<SysResourceDto> page, SysResourceDto params) {
        return WrapperApiService.findByPage(page,params,sysResourceService,SysResourceDto.class);
    }
}
