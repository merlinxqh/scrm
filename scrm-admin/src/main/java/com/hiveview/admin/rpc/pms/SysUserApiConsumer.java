package com.hiveview.admin.rpc.pms;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hiveview.common.api.PageDto;
import com.hiveview.config.dubbo.DubboConfiguration;
import com.hiveview.pms.api.SysUserApiService;
import com.hiveview.pms.dto.SysUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by leo on 2017/11/6.
 */
@Component
public class SysUserApiConsumer {
    private static final Logger logger= LoggerFactory.getLogger(SysUserApiConsumer.class);

    @Reference(registry = DubboConfiguration.ZOOKEEPER_CLIENT)
    private SysUserApiService sysUserApiService;

    public SysUserDto getUserByUserName(String userName){
       return sysUserApiService.getUserByUserName(userName);
    }


    /**
     * 分页数据
     * @param page
     * @param params
     * @return
     */
    public PageDto<SysUserDto> findPage(PageDto page,SysUserDto params){
        return sysUserApiService.findPage(page,params);
    }

    public SysUserDto findById(String id){
        Assert.hasText(id, "ID不能为空");
        return sysUserApiService.findById(Long.valueOf(id));
    }

    /**
     * 保存数据
     * @param dto
     * @return
     */
    public int saveData(SysUserDto dto){
        return sysUserApiService.saveData(dto);
    }

    /**
     * 启用禁用
     * @param dto
     */
    public void modifyData(SysUserDto dto){
        sysUserApiService.modifyData(dto);
    }

    public List<SysUserDto> findList(SysUserDto dto){
        return sysUserApiService.findList(dto);
    }

}
