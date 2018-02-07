package com.hiveview.admin.rpc.pms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.hiveview.base.common.TreeEntity;
import com.hiveview.base.util.serializer.ObjectUtils;
import com.hiveview.common.api.PageDto;
import com.hiveview.config.dubbo.DubboConfiguration;
import com.hiveview.pms.api.SysResourceApiService;
import com.hiveview.pms.dto.SysResourceDto;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by leo on 2017/11/6.
 */
@Component
public class SysResourceApiConsumer {

    @Reference(registry = DubboConfiguration.ZOOKEEPER_CLIENT)
    private SysResourceApiService sysResourceApiService;

    public List<SysResourceDto> getResourceByRole(String roleCode){
        return sysResourceApiService.getResourceByRole(roleCode);
    }

    public SysResourceDto findById(String id){
        Assert.hasText(id,"ID不能为空");
        return sysResourceApiService.findById(Long.valueOf(id));
    }

    public int deleteData(SysResourceDto dto){
        return sysResourceApiService.deleteData(dto);
    }


    public List<SysResourceDto> findList(SysResourceDto dto){
        return sysResourceApiService.findList(dto);
    }

    /**
     * 数据保存
     * @param dto
     * @return
     */
    public int saveData(SysResourceDto dto){
        return sysResourceApiService.saveData(dto);
    }


    /**
     * 查询分页数据
     * @param page
     * @param params
     * @return
     */
    public PageDto<SysResourceDto> getPageData(PageDto<SysResourceDto> page, SysResourceDto params){
        return sysResourceApiService.findPage(page,params);
    }


    /**
     * 获取树结构数据
     * @param params
     * @return
     */
    public JSONArray getTreeData(SysResourceDto params){
        List<SysResourceDto> resourceDtoList=sysResourceApiService.findList(params);
        JSONArray resArray = ObjectUtils.wrapperGetTreeData(resourceDtoList);

        if(null != params && StringUtils.hasText(params.getRefRoleCode())){
            //这里需要查出 角色绑定的所有资源
            List<SysResourceDto> exists=sysResourceApiService.getResourceByRole(params.getRefRoleCode());
            ObjectUtils.wrapperCheckTreeData(resArray, ObjectUtils.copyListObject(exists, TreeEntity.class));
        }
        return ObjectUtils.setTreeHomeNode(resArray);
    }
}
